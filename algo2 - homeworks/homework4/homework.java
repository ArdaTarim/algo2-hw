import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

//----------------------------------------------------- 
// Title: Main class
// Author: Arda Tarım 
// ID: 12859191938
// Section: 1 
// Assignment: 4
// Description: Main class for the assigment.
//-----------------------------------------------------
public class homework {

    public static void main(String[] args) {

        // get the file name with the scanner
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.next();

        // read the txt file
        DataRead dataRead = new DataRead(fileName);

        // create the Trie class
        Trie trie = new Trie();

        // add words in the txt file to the Trie class
        while (true) {
            String word = dataRead.getNext();
            if (word == null) {
                break;
            }
            trie.insert(word);
        }

        // get the command with the scanner
        String command = scanner.next();
        switch (command) {
            // search the word in the trie
            case "search":
                boolean result = trie.Search(scanner.next().toLowerCase());
                System.out.println(result ? "True" : "False");
                break;

            // find words that start with the given prefix
            case "autocomplete":
                trie.autoComplete(scanner.next().toLowerCase());
                break;

            // find words that ends with the given suffix
            case "reverse":
                trie.reverseAutoComplete(scanner.next().toLowerCase());
                break;

            // find words that start with the given prefix and ends with given suffix
            case "full":
                trie.FullAutoComplete(scanner.next().toLowerCase(), scanner.next().toLowerCase());
                break;

            // find the top k most frequent words
            case "topk":
                trie.findTopK(scanner.nextInt());
                break;

            default:
                System.out.println("Invalid command.");
                break;
        }
        scanner.close();
    }
}

// -----------------------------------------------------
// Title: Trie
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 4
// Description: Implementation of Trie class for the assigment.
// -----------------------------------------------------
class Trie {

    private TrieNode root;
    private TrieNode reverseTrieRoot;
    private HashMap<String, Integer> wordCounts;

    // -----------------------------------------------------
    // Summary: Initializs the Trie class.
    // -----------------------------------------------------
    Trie() {
        root = new TrieNode();
        reverseTrieRoot = new TrieNode();
        wordCounts = new HashMap<>();
    }

    // -----------------------------------------------------
    // Summary: Inserts the given word into the Trie.
    // Also, inserts the word into the reverse Trie for reverseAutoComplete method.
    // -----------------------------------------------------
    void insert(String word) {
        TrieNode current = root;

        for (char c : word.toLowerCase().toCharArray()) {
            current.children.putIfAbsent(c, new TrieNode());
            current = current.children.get(c);
        }

        current.endOfWord = true;

        // inserting to reverse Trie for reverseAutoComplete method
        TrieNode reverseTrieCurrent = reverseTrieRoot;

        String reverseWord = new StringBuilder(word).reverse().toString();
        for (char c : reverseWord.toCharArray()) {
            reverseTrieCurrent.children.putIfAbsent(c, new TrieNode());
            reverseTrieCurrent = reverseTrieCurrent.children.get(c);
        }

        reverseTrieCurrent.endOfWord = true;

        // keeping track of word counts for topK method
        wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
    }

    // -----------------------------------------------------
    // Summary: Searches the given word in the Trie.
    // Returns true if the word is found, otherwise returns false.
    // -----------------------------------------------------
    boolean Search(String arg) {
        TrieNode current = root;

        for (char c : arg.toCharArray()) {
            current = current.children.get(c);
            if (current == null) {
                return false;
            }
        }

        return current.endOfWord;
    }

    // -----------------------------------------------------
    // Summary: Finds the possible words that start with the given prefix.
    // -----------------------------------------------------
    LinkedList<String> findPossibleWords(TrieNode node, String prefix) {
        LinkedList<String> list = new LinkedList<String>();

        if (node.endOfWord) {
            list.add(prefix);
        }

        for (char c : node.children.keySet()) {
            list.addAll(findPossibleWords(node.children.get(c), prefix + c));
        }

        return list;
    }

    // -----------------------------------------------------
    // Summary: Finds the possible words that start with the given prefix.
    // -----------------------------------------------------
    void autoComplete(String prefix) {
        TrieNode current = root;

        for (char c : prefix.toCharArray()) {
            current = current.children.get(c);
            if (current == null) {
                System.out.println("No words");
                return;
            }
        }

        LinkedList<String> list = findPossibleWords(current, prefix);

        // sort the words lexographicly before printing them
        Collections.sort(list);
        
        if (list.isEmpty()) {
            System.out.println("No words");
            return;
        }
        
        System.out.print(String.join(", ", list));
    }

    // -----------------------------------------------------
    // Summary: Finds the possible words that end with the given suffix.
    // Uses the reverse Trie to find the words.
    // -----------------------------------------------------
    void reverseAutoComplete(String suffix) {

        TrieNode current = reverseTrieRoot;

        // reverse the suffix
        String reverseSuffix = new StringBuilder(suffix).reverse().toString();

        // search the suffix in the reverse Trie
        for (char c : reverseSuffix.toCharArray()) {
            current = current.children.get(c);

            if (current == null) {
                System.out.println("No words");
                return;
            }
        }

        // find the possible words that start with the reverse suffix
        LinkedList<String> list = findPossibleWords(current, reverseSuffix);

        // reverse all the words in the list
        for (int i = 0; i < list.size(); i++) {
            list.set(i, new StringBuilder(list.get(i)).reverse().toString());
        }

        // sort the words lexographically before printing them
        Collections.sort(list);

        if (list.isEmpty()) {
            System.out.println("No words");
            return;
        }

        System.out.print(String.join(", ", list));
    }

    // -----------------------------------------------------
    // Summary: Finds the possible words that start with the given prefix and ends
    // with the given suffix.
    // -----------------------------------------------------
    void FullAutoComplete(String prefix, String suffix) {

        TrieNode current = root;

        for (char c : prefix.toCharArray()) {
            current = current.children.get(c);

            if (current == null) {
                System.out.println("No words");
                return;
            }
        }

        // find all possible words that start with the given prefix
        LinkedList<String> possibleWords = findPossibleWords(current, prefix);

        // filter words that end with the given suffix
        LinkedList<String> list = new LinkedList<>();
        for (String word : possibleWords) {
            if (word.endsWith(suffix)) {
                list.add(word);
            }
        }

        // sort the words lexographically before printing them
        Collections.sort(list);

        if (list.isEmpty()) {
            System.out.println("No words");
            return;
        }

        System.out.print(String.join(", ", list));
    }

    // -----------------------------------------------------
    // Summary: Prints top k words that have most occurrences
    // -----------------------------------------------------
    void findTopK(int k) {
        // create a list to store the words and their counts
        List<HashMap.Entry<String, Integer>> wordCountsList = new ArrayList<>(wordCounts.entrySet());

        // sort the list based on the word counts
        wordCountsList.sort((a, b) -> {
            if (!a.getValue().equals(b.getValue())) {
                return b.getValue() - a.getValue(); 
            }
            return a.getKey().compareTo(b.getKey());
        });

        // create a new list to store the top k words
        List<String> list = new ArrayList<>();
        for (int i = 0; i < k && i < wordCountsList.size(); i++) {
            list.add(wordCountsList.get(i).getKey().toLowerCase());
        }

        if (list.isEmpty()) {
            System.out.println("No words");
        } else {
            // sort the list
            Collections.sort(list); 
            // print the list
            System.out.print(String.join(", ", list));
        }
    }
}

// -----------------------------------------------------
// Title: TrieNode
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 4
// Description: Implementation of TrieNode class for the Trie class.
// Uses a hashmap to store children nodes.
// -----------------------------------------------------
class TrieNode {
    HashMap<Character, TrieNode> children;
    boolean endOfWord;

    public TrieNode() {
        children = new HashMap<>();
        endOfWord = false;
    }
}

// -----------------------------------------------------
// Title: DataRead
// Author: Arda Tarım
// ID: 12859191938
// Section: 1
// Assignment: 4
// Description: DataRead class to read the data from the txt file.
// -----------------------------------------------------
class DataRead {

    LinkedList<String> list = new LinkedList<String>();

    DataRead(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNext()) {
                list.add(scanner.next());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("A file error occurred.");
        }
    }

    String getNext() {
        if (list.size() > 0) {
            return list.poll();
        } else {
            return null;
        }
    }
}