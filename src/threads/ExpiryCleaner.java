package threads;

import cache.LRUCache;

public class ExpiryCleaner extends Thread {

    private LRUCache cache;

    public ExpiryCleaner(LRUCache cache) {
        this.cache = cache;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(2000); // run every 2 sec
                cache.removeExpiredKeys();
            } catch (InterruptedException e) {
                System.out.println("Cleaner interrupted");
            }
        }
    }
}