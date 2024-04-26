package concurrentCollection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentCache {
    private static final Map<String, String> cache = new ConcurrentHashMap<>();

    private static String compute(String key) {
        System.out.println(key + " not present in the cache, so going to compute!");
        try {
            Thread.sleep(500); // Simulating computation time
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Value for " + key;
    }

    public static String getCachedValue(String key) {
        String value = cache.get(key);

        // If not in the cache, compute and put it in the cache
        if (value == null) {
            value = compute(key);
            cache.put(key, value);
        }

        return value;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            new Thread(() -> {
                String key = "Key @ " + threadNum;
                for (int j = 0; j < 3; j++) { // Fetch the same key 3 times
                    String value = getCachedValue(key);
                    System.out.println("Thread " + Thread.currentThread().getName() + ": Key=" + key + ", Value=" + value);
                }
            }).start();
        }
    }
}