package cache;

import java.util.*;

public class LRUCache implements Cache {

    private int capacity;
    private HashMap<String, Node> map;

    public Node head, tail;

    public LRUCache(int cap) {
        capacity = cap;
        map = new HashMap<>();

        head = new Node("", "", -1);
        tail = new Node("", "", -1);

        head.next = tail;
        tail.prev = head;
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void insert(Node node) {
        node.next = head.next;
        node.prev = head;

        head.next.prev = node;
        head.next = node;
    }

    public synchronized void set(String key, String value, long ttl) {
        long expiry = (ttl == -1) ? -1 : System.currentTimeMillis() + ttl;

        if (map.containsKey(key)) {
            Node old = map.get(key);
            remove(old);
            map.remove(key);
        }

        if (map.size() == capacity) {
            Node lru = tail.prev;
            remove(lru);
            map.remove(lru.key);
        }

        Node node = new Node(key, value, expiry);
        insert(node);
        map.put(key, node);
    }

    public synchronized String get(String key) {
        if (!map.containsKey(key)) return "NULL";

        Node node = map.get(key);

        long now = System.currentTimeMillis();

        if (node.expiry != -1 && now > node.expiry) {
            remove(node);
            map.remove(key);
            return "NULL";
        }

        remove(node);
        insert(node);

        return node.value;
    }

    public synchronized boolean del(String key) {
        if (!map.containsKey(key)) return false;

        Node node = map.get(key);
        remove(node);
        map.remove(key);
        return true;
    }

    // 🔥 Remove expired keys (for thread)
    public synchronized void removeExpiredKeys() {
        Node curr = head.next;

        long now = System.currentTimeMillis();

        while (curr != tail) {
            Node next = curr.next;

            if (curr.expiry != -1 && now > curr.expiry) {
                remove(curr);
                map.remove(curr.key);
            }

            curr = next;
        }
    }
}