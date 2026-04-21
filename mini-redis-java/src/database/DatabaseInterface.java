package database;

public interface DatabaseInterface {
    void set(String key, String value, long ttl);
    String get(String key);
    boolean del(String key);
}