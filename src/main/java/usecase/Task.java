package usecase;

public class Task implements Comparable<Task> {

    private final Runnable task;
    private final long interval;
    private final long executionTime;

    public Task(Runnable task, long interval, long executionTime) {
        this.task = task;
        this.interval = interval;
        this.executionTime = executionTime;
    }

    public Runnable getTask() {
        return task;
    }

    public long getInterval() {
        return interval;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    @Override
    public int compareTo(Task task) {
        return Long.compare(this.executionTime, task.executionTime);
    }
}