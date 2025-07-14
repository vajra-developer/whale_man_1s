import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordAnalyzer {

    private static final String FILE_URL = "resources/moby.txt";
    private static final Set<String> EXCLUDED_WORDS = new HashSet<>(Arrays.asList(
        // Articles
        "a", "an", "the",

        // Pronouns
        "i", "you", "he", "she", "it", "we", "they",
        "me", "him", "her", "us", "them",
        "my", "your", "his", "her", "its", "our", "their",
        "mine", "yours", "hers", "ours", "theirs",
        "myself", "yourself", "himself", "herself", "itself", "ourselves", "yourselves", "themselves",
        "who", "whom", "whose", "which", "that",
        "this", "these", "those",
        "someone", "anyone", "everyone", "no one",
        "something", "anything", "everything", "nothing",
        "each", "all", "some", "few", "many", "any", "none", "several", "one", "both", "either", "neither",
        "what",

        // Prepositions (simple and complex)
        "about", "above", "across", "after", "against", "along", "among", "around",
        "as", "at", "before", "behind", "below", "beneath", "beside", "besides",
        "between", "beyond", "by", "concerning", "despite", "down", "during",
        "except", "for", "from", "in", "inside", "into", "like", "near", "of", "off",
        "on", "onto", "out", "outside", "over", "past", "regarding", "since",
        "through", "throughout", "to", "toward", "under", "underneath", "until",
        "up", "upon", "with", "within", "without",
        "according to", "ahead of", "along with", "apart from", "because of",
        "close to", "due to", "in addition to", "in front of", "in place of",
        "in spite of", "instead of", "next to", "on account of", "out of",
        "prior to", "regardless of",

        // Conjunctions
        "and", "but", "or", "nor", "for", "so", "yet",
        "after", "although", "as", "because", "before", "if", "once", "since",
        "than", "that", "though", "unless", "until", "when", "where", "while",
        "either", "neither", "not only", "but also", "whether",

        // Linking/Auxiliary verb “to be” forms
        "be", "is", "am", "are", "was", "were", "being", "been",

        // Negation particle
        "not", "no",

        // Modal verbs (core)
        "can", "could", "may", "might", "must", "shall", "should", "will", "would", "ought",

        // Semi-modal / quasi-modal expressions
        "need", "need to", "dare", "had better", "have to", "have got to", "used to", "be able to", "ought to",

        // Verb “to have” in all forms
        "have", "has", "had",

        // (Optionally) filler adverb/pronoun “there”
        "there", "then"
    ));

    public static void main(String[] args) {
        try {
            long start = System.currentTimeMillis();
            BufferedReader reader = new BufferedReader(new FileReader(FILE_URL));
            
            Map<String, Integer> wordCounts = new HashMap<>();
            int totalWords = 0;
            
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.toLowerCase().split("\\s+");
                for (String word : words) {
                    word = word.replaceAll("[^a-zA-Z']", "").replaceAll("'s$", "").replaceAll("^'|'$", "");
                    if (word.isEmpty() || EXCLUDED_WORDS.contains(word)) {
                        continue;
                    }
                    totalWords++;
                    wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                }
            }
            reader.close();
            
            System.out.println("Total word count (excluding filtered words): " + totalWords);
            
            List<Map.Entry<String, Integer>> sortedWords = new ArrayList<>(wordCounts.entrySet());
            sortedWords.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
            
            System.out.println("\nTop 5 most frequent words:");
            for (int i = 0; i < 5 && i < sortedWords.size(); i++) {
                Map.Entry<String, Integer> entry = sortedWords.get(i);
                System.out.println((i + 1) + ". " + entry.getKey() + ": " + entry.getValue());
            }
            
            List<String> uniqueWords = new ArrayList<>(wordCounts.keySet());
            Collections.sort(uniqueWords);
            
            System.out.println("\nAlphabetically sorted list of all unique words (top 50):");
            for (int i = 0; i < 50 && i < uniqueWords.size(); i++) {
                System.out.println(uniqueWords.get(i));
            }
            long end = System.currentTimeMillis();
            long duration = (end - start) / 1000;

            System.out.println("Processing time: " + duration + " seconds");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
