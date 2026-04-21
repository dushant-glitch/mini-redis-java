import database.*;
import threads.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Database db = new Database(2);
        db.loadFromFile();

        // 🔥 Start background thread
        ExpiryCleaner cleaner = new ExpiryCleaner(db.getCache());
        cleaner.start();

        Scanner sc = new Scanner(System.in);

        System.out.println("Mini Redis Started (type EXIT to stop)");

        while (true) {
            String line = sc.nextLine();

            if (line.isEmpty()) continue;

            String[] parts = line.split(" ");
            String op = parts[0];

            try {

                if (op.equals("SET")) {
                    String key = parts[1];
                    String value = parts[2];

                    if (parts.length == 4) {
                        long ttl = Long.parseLong(parts[3]);
                        db.set(key, value, ttl);
                    } else {
                        db.set(key, value, -1);
                    }

                    db.saveToFile();
                    System.out.println("Added successfully");
                }

                else if (op.equals("GET")) {
                    String key = parts[1];
                    String result = db.get(key);

                    if (result.equals("NULL"))
                        System.out.println("Key not found or expired");
                    else
                        System.out.println(result);
                }

                else if (op.equals("DEL")) {
                    String key = parts[1];

                    if (db.del(key)) {
                        db.saveToFile();
                        System.out.println("Deleted successfully");
                    } else {
                        System.out.println("Key not found");
                    }
                }

                else if (op.equals("EXIT")) {
                    break;
                }

                else {
                    System.out.println("Invalid command");
                }

            } catch (Exception e) {
                System.out.println("Error: Invalid input");
            }
        }

        sc.close();
    }
}