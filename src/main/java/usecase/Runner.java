package usecase;

public class Runner {
    public static void main(String[] args) {
        TaskScheduler scheduler = new TaskScheduler(2);
        scheduler.schedule(() -> System.out.println("Task 1 executed at specific time"), System.currentTimeMillis() + 5000);
        scheduler.scheduleAtFixedInterval(() -> System.out.println("Task 2 executed at fixed interval"), 2000);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        scheduler.shutdown();
    }
}
