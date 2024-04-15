package threadSynchronisation;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProduceConsumerWithLocks {
    public static void main(String[] args) {
        Processor processor = new Processor();

        Thread one = new Thread(() -> {
            try {
                processor.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread two = new Thread(() -> {
            try {
                processor.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        one.start();
        two.start();

        try {
            one.join();
            two.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Processor {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void produce() throws InterruptedException {
        lock.lock();
        System.out.println("Producing...");
        condition.await();
        System.out.println("Again from the producer");
        lock.unlock();
    }

    public void consume() throws InterruptedException {
        Thread.sleep(2000);
        lock.lock();
        System.out.println("Consuming...");
        Thread.sleep(3000);
        condition.signal();
        lock.unlock();
    }
}