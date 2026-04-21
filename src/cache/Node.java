package cache;

public class Node {
    public String key, value;
    public long expiry;
    public Node prev, next;

    public Node(String k, String v, long exp) {
        key = k;
        value = v;
        expiry = exp;
    }
}