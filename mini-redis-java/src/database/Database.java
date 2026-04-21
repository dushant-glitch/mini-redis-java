package database;

import cache.*;
import java.io.*;
import java.util.*;

public class Database implements DatabaseInterface {

    private LRUCache cache;

    public Database(int capacity) {
        cache = new LRUCache(capacity);
    }

    public LRUCache getCache() {
        return cache;
    }

    public void set(String key, String value, long ttl) {
        cache.set(key, value, ttl);
    }

    public String get(String key) {
        return cache.get(key);
    }

    public boolean del(String key) {
        return cache.del(key);
    }

    // SAVE
    public void saveToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"));

            Node curr = cache.head.next;

            while (curr != cache.tail) {
                writer.write(curr.key + " " + curr.value + " " + curr.expiry);
                writer.newLine();
                curr = curr.next;
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving file");
        }
    }

    // LOAD
    public void loadFromFile() {
        try {
            File file = new File("data.txt");
            if (!file.exists()) return;

            Scanner sc = new Scanner(file);

            while (sc.hasNext()) {
                String key = sc.next();
                String value = sc.next();
                long expiry = sc.nextLong();

                long now = System.currentTimeMillis();

                if (expiry != -1 && now > expiry) continue;

                long ttl = (expiry == -1) ? -1 : (expiry - now);

                cache.set(key, value, ttl);
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Error loading file");
        }
    }
}