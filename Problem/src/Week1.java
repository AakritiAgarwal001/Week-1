import java.util.*;

public class Week1 {
    // Maps each n-gram to a set of Document IDs that contain it
    private Map<String, Set<String>> ngramIndex = new HashMap<>();
    private Map<String, Integer> documentTotalNgrams = new HashMap<>();
    private final int N = 5; // Using 5-grams as suggested in the hints

    /**
     * Processes a document, breaks it into n-grams, and adds to the index.
     */
    public void addDocument(String docId, String content) {
        String[] words = content.toLowerCase().split("\\s+");
        if (words.length < N) return;

        int count = 0;
        for (int i = 0; i <= words.length - N; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < N; j++) {
                sb.append(words[i + j]).append(" ");
            }
            String ngram = sb.toString().trim();

            ngramIndex.computeIfAbsent(ngram, k -> new HashSet<>()).add(docId);
            count++;
        }
        documentTotalNgrams.put(docId, count);
    }

    /**
     * Analyzes a new document against the database to detect similarity.
     */
    public void analyzeDocument(String newDocId, String content) {
        String[] words = content.toLowerCase().split("\\s+");
        List<String> inputNgrams = new ArrayList<>();

        for (int i = 0; i <= words.length - N; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < N; j++) {
                sb.append(words[i + j]).append(" ");
            }
            inputNgrams.add(sb.toString().trim());
        }

        // Count matches against other documents
        Map<String, Integer> matchCounts = new HashMap<>();
        for (String ngram : inputNgrams) {
            if (ngramIndex.containsKey(ngram)) {
                for (String existingDocId : ngramIndex.get(ngram)) {
                    matchCounts.put(existingDocId, matchCounts.getOrDefault(existingDocId, 0) + 1);
                }
            }
        }

        System.out.println("Analysis for: " + newDocId);
        System.out.println("Extracted " + inputNgrams.size() + " n-grams");

        for (Map.Entry<String, Integer> entry : matchCounts.entrySet()) {
            String otherDocId = entry.getKey();
            int matches = entry.getValue();
            double similarity = (double) matches / inputNgrams.size() * 100;

            String status = similarity > 60 ? "PLAGIARISM DETECTED" :
                    similarity > 10 ? "suspicious" : "clean";

            System.out.printf("-> Found %d matching n-grams with \"%s\"\n", matches, otherDocId);
            System.out.printf("-> Similarity: %.1f%% (%s)\n", similarity, status);
        }
    }

    public static void main(String[] args) {
        Week1 detector = new Week1();

        // Sample Data
        detector.addDocument("essay_089.txt", "the quick brown fox jumps over the lazy dog repeatedly");
        detector.addDocument("essay_092.txt", "data structures are essential for efficient software development and hashing is key");

        // Analyzing a suspicious document
        String newEssay = "data structures are essential for efficient software development and hashing is very important";
        detector.analyzeDocument("essay_123.txt", newEssay);
    }
}