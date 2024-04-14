package threadSynchronisation;

public class WaitNotifyDemo {
    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        Thread one = new Thread(() -> {
            try {
                one();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread two = new Thread(() -> {
            try {
                two();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        one.start();
        two.start();
    }

    private static void one() throws InterruptedException {
        synchronized (LOCK) {
            System.out.println("Hello from method one...");
            LOCK.wait();
            System.out.println("Back Again in the method one");
        }
    }

    private static void two() throws InterruptedException {
        synchronized (LOCK) {
            System.out.println("Hello from method two...");
            LOCK.notify(); // Remaining code lines in the block are executed
            System.out.println("Hello from method two even after notify...");
        }
    }
}