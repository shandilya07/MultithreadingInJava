package sequential;

public class SequentialExecutionDemo {
    public static void main(String[] args) {
        demo1();
        demo2();
    }

    private static void demo1() {
        for (int i = 0; i < 7; i++) {
            System.out.println("Demo1 at " + i);
        }
    }
    private static void demo2() {
        for (int i = 0; i < 7; i++) {
            System.out.println("Demo2 at " + i);
        }
    }
}
