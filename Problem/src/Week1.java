import java.util.*;

public class Week1 {
    // Metric Storage
    private Map<String, Integer> pageViews = new HashMap<>();
    private Map<String, Set<String>> uniqueVisitors = new HashMap<>();
    private Map<String, Integer> trafficSources = new HashMap<>();

    /**
     * Processes an incoming page view event.
     */
    public void processEvent(String url, String userId, String source) {
        // 1. Update total page views
        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        // 2. Update unique visitors (Set handles duplicates automatically)
        uniqueVisitors.computeIfAbsent(url, k -> new HashSet<>()).add(userId);

        // 3. Update traffic source counts
        trafficSources.put(source, trafficSources.getOrDefault(source, 0) + 1);
    }

    /**
     * Returns the top N most visited pages using a PriorityQueue (Min-Heap).
     * Time Complexity: O(P log N) where P is total unique pages.
     */
    public List<Map.Entry<String, Integer>> getTopPages(int n) {
        PriorityQueue<Map.Entry<String, Integer>> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        for (Map.Entry<String, Integer> entry : pageViews.entrySet()) {
            minHeap.offer(entry);
            if (minHeap.size() > n) {
                minHeap.poll();
            }
        }

        List<Map.Entry<String, Integer>> topPages = new ArrayList<>(minHeap);
        topPages.sort((a, b) -> b.getValue() - a.getValue()); // Sort descending for display
        return topPages;
    }

    /**
     * Displays the current dashboard statistics.
     */
    public void displayDashboard() {
        System.out.println("\n--- REAL-TIME ANALYTICS DASHBOARD ---");

        System.out.println("Top Pages:");
        List<Map.Entry<String, Integer>> top = getTopPages(3);
        for (Map.Entry<String, Integer> entry : top) {
            String url = entry.getKey();
            int views = entry.getValue();
            int unique = uniqueVisitors.get(url).size();
            System.out.printf("- %s: %d views (%d unique)\n", url, views, unique);
        }

        System.out.println("\nTraffic Sources:");
        int totalViews = pageViews.values().stream().mapToInt(Integer::intValue).sum();
        trafficSources.forEach((source, count) -> {
            double percentage = (double) count / totalViews * 100;
            System.out.printf("- %s: %.1f%%\n", source, percentage);
        });
    }

    public static void main(String[] args) {
        Week1 dashboard = new Week1();

        // Simulate traffic
        dashboard.processEvent("/article/breaking-news", "user_1", "Google");
        dashboard.processEvent("/article/breaking-news", "user_2", "Facebook");
        dashboard.processEvent("/article/breaking-news", "user_1", "Google"); // Repeat user
        dashboard.processEvent("/sports/championship", "user_3", "Direct");
        dashboard.processEvent("/sports/championship", "user_4", "Google");
        dashboard.processEvent("/home", "user_5", "Direct");

        dashboard.displayDashboard();
    }
}