package locks;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    private final ReentrantLock lock = new ReentrantLock();
    private int sharedData = 0;

    public void methodA() {
        lock.lock();
        try {
            // Critical section
            sharedData++;
            System.out.println("Method A: sharedData = " + sharedData);
            // Call methodB(), which also requires the lock
            methodB();
        } finally {
            lock.unlock();
        }
    }

    public void methodB() {
        lock.lock();
        try {
            // Critical section
            sharedData--;
            System.out.println("Method B: sharedData = " + sharedData);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLockDemo example = new ReentrantLockDemo();

        // Create and start multiple threads
        for (int i = 0; i < 5; i++) {
            new Thread(example::methodA).start();
        }
    }
}
