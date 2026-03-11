import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
public class Week1{
    static HashMap<String, AtomicInteger> stock = new HashMap<>();
    static HashMap<String, Queue<Integer>> waitlist = new HashMap<>();
    static void addProduct(String productId, int qty) {
        stock.put(productId, new AtomicInteger(qty));
        waitlist.put(productId, new LinkedList<>());
    }
    static void purchase(String productId, int userId) {
        AtomicInteger s = stock.get(productId);
        if (s.get() > 0) {
            s.decrementAndGet();
            System.out.println("Purchase successful. Remaining: " + s.get());
        } else {
            waitlist.get(productId).add(userId);
            System.out.println("Added to waiting list. Position: " + waitlist.get(productId).size());
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        addProduct("IPHONE15", 2);
        System.out.print("Enter user id: ");
        int user = sc.nextInt();
        purchase("IPHONE15", user);
        sc.close();
    }
}