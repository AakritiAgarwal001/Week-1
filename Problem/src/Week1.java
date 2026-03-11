import java.util.*;

public class Week1 {

    static class Entry {
        String ip;
        long expiry;
        Entry(String ip, int ttl) {
            this.ip = ip;
            this.expiry = System.currentTimeMillis() + ttl * 1000;
        }
    }
    static HashMap<String, Entry> cache = new HashMap<>();
    static String resolve(String domain) {
        if (cache.containsKey(domain)) {
            Entry e = cache.get(domain);
            if (System.currentTimeMillis() < e.expiry) {
                return "Cache HIT → " + e.ip;
            }
            cache.remove(domain);
        }
        String newIp = "172.217.14." + new Random().nextInt(200);
        cache.put(domain, new Entry(newIp, 10));
        return "Cache MISS → " + newIp;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter domain: ");
        String domain = sc.nextLine();
        System.out.println(resolve(domain));
        sc.close();
    }
}