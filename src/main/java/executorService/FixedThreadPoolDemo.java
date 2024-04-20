package executorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPoolDemo {
    public static void main(String[] args) {
        try (ExecutorService executor = Executors.newFixedThreadPool(2)) {

            for (int i = 0; i < 7; i++) {
                executor.execute(new Work(i + 1));
            }
        }
    }
}

class Work implements Runnable {

    private final int workId;

    public Work(int workId) {
        this.workId = workId;
    }

    @Override
    public void run() {
        System.out.println("Task with ID : " + workId + " being executed by thread : " + Thread.currentThread().getName());
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}