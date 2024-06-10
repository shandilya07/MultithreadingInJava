package usecase;

import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TaskScheduler {
    private final PriorityQueue<Task> tasks;
    private final Thread[] threads;
    private final AtomicBoolean running;
    private final Lock lock;
    private final Condition newTaskAdded;

    public TaskScheduler(int numThreads) {
        this.tasks = new PriorityQueue<>();
        this.threads = new Thread[numThreads];
        this.running = new AtomicBoolean(true);
        this.lock = new ReentrantLock();
        this.newTaskAdded = lock.newCondition();
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(new TaskExecutor());
            threads[i].start();
        }
    }

    public void schedule(Runnable task, long time) {
        lock.lock();
        try {
            Task newTask = new Task(task, time, 0);
            tasks.add(newTask);
            newTaskAdded.signal();
        } finally {
            lock.unlock();
        }
    }

    public void scheduleAtFixedInterval(Runnable task,long interval) {
        lock.lock();
        try {
            Task newTask = new Task(task, System.currentTimeMillis() + interval, interval);
            tasks.add(newTask);
            newTaskAdded.signal();
        } finally {
            lock.unlock();
        }
    }

    public void shutdown() {
        running.set(false);
        lock.lock();
        try {
            newTaskAdded.signalAll();
        } finally {
            lock.unlock();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class TaskExecutor implements Runnable {

        @Override
        public void run() {
            while (running.get()) {
                lock.lock();
                try {
                    while (tasks.isEmpty()) {
                        newTaskAdded.await();
                    }

                    Task task = tasks.peek();
                    long currentTime = System.currentTimeMillis();
                    long delay = Math.max(task.getExecutionTime() - currentTime, 0);

                    if (delay > 0) {
                        try {
                            newTaskAdded.await(delay, TimeUnit.MILLISECONDS);
                        } catch (InterruptedException e) {
                            if (!running.get()) {
                                return;
                            }
                        }
                    } else {
                        tasks.poll();
                        task.getTask().run();
                        if (task.getInterval() > 0 && running.get()) {
                            task = new Task(task.getTask(), currentTime + task.getInterval(), task.getInterval());
                            tasks.add(task);
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
