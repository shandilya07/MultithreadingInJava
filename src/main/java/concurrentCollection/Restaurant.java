package concurrentCollection;

import java.util.concurrent.CountDownLatch;

/**
 * Let's build a simulation where a group of chefs needs to prepare different dishes in a restaurant kitchen.
 * Each chef is responsible for preparing a specific dish, and the kitchen manager wants to start serving
 * customers only when all dishes are ready.
 * Here's how you could use CountdownLatch in Java to coordinate the chefs!
 */
public class Restaurant {
    public static void main(String[] args) throws InterruptedException {
        int numberOfChefs = 3;
        CountDownLatch latch = new CountDownLatch(numberOfChefs);

        // Chefs start preparing their dishes
        new Thread(new Chef("Chef A", "Pizza", latch)).start();
        new Thread(new Chef("Chef B", "Pasta", latch)).start();
        new Thread(new Chef("Chef C", "Salad", latch)).start();

        // Wait for all dishes to be ready
        latch.await();

        System.out.println("All dishes are ready! Let's start serving customers.");
    }
}

class Chef implements Runnable {
    private final String name;
    private final String dish;
    private final CountDownLatch latch;

    public Chef(String name,
                String dish,
                CountDownLatch latch) {
        this.name = name;
        this.dish = dish;
        this.latch = latch;
    }

    @Override
    public void run() {
        // Simulate preparing the dish
        try {
            System.out.println(name + " is preparing " + dish);
            Thread.sleep(2000); // Simulate cooking time
            System.out.println(name + " has finished preparing " + dish);
            latch.countDown(); // Notify that this dish is ready
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}