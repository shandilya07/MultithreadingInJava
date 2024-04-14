package threadSynchronisation;

public class SynchronisationDemo {

    private static int counter = 0;

    public static void main(String[] args) {
        Thread one = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                //counter++;
                increment();
            }
        });

        Thread two = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                //counter++;
                increment();
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

        System.out.println("Counter is : " + counter);
    }

    private synchronized static void increment() {
        counter++;
    }
}