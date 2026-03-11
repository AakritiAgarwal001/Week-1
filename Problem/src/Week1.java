import java.util.*;

public class Week1 {
    static HashMap<String, Integer> users = new HashMap<>();
    static HashMap<String, Integer> attempts = new HashMap<>();
    static boolean checkAvailability(String username) {
        attempts.put(username, attempts.getOrDefault(username, 0) + 1);
        return !users.containsKey(username);
    }
    static List<String> suggest(String username) {
        List<String> list = new ArrayList<>();
        list.add(username + "1");
        list.add(username + "2");
        list.add(username.replace("_", "."));
        return list;
    }
    static String getMostAttempted() {
        String name = "";
        int max = 0;
        for (Map.Entry<String, Integer> e : attempts.entrySet()) {
            if (e.getValue() > max) {
                name = e.getKey();
                max = e.getValue();
            }
        }
        return name + " (" + max + " attempts)";
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        users.put("john_doe", 1);
        users.put("admin", 2);
        System.out.print("Enter username to check: ");
        String username = sc.nextLine();
        if (checkAvailability(username)) {
            System.out.println("Username available");
        } else {
            System.out.println("Username taken");
            System.out.println("Suggestions: " + suggest(username));
        }
        System.out.println("Most attempted username: " + getMostAttempted());
        sc.close();
    }
}