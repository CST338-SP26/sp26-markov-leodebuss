/**
 * Title: Markov.java
 * Abstract: A program will read a text file, generate a collection of words and words that follow
 *           that word, and will then use those words to generate new text.
 * Author: Leonardo Lopez
 * Date: 3/6/26
 */
import java.io.*;
import java.util.*;

public class Markov() {
    // constants
    private static String BEGINS_SENTENCE = "__$";
    private static String PUNCTUATION_MARKS = ".!?$";

    // members
    private HashMap<String, ArrayList<String>> words;
    private String prevWord;

    // methods
    public Markov() {
        words = new HashMap<>();
        words.put(BEGINS_SENTENCE, new ArrayList<>());
        prevWord = BEGINS_SENTENCE;
    }
    public String getSentence(){
        StringBuilder sentence = new StringBuilder();
        String word = randomWord(BEGINS_SENTENCE);
        while (word != null) {
            if (sentence.length() > 0) {
                sentence.append(" ");
            }
            sentence.append(word);
            if (endsWithPunctuation(word)) {
                break;
            }
            word = randomWord(word);
        }
        return sentence.toString();
    }
    void addFromFile(String filename) {
        try {
            Scanner file = new Scanner(new File(filename));

            while (file.hasNextLine()) {
                addLine(file.nextLine());
            }

            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    void addWord(String prevWord) { // might need to make public
        if (!words.containsKey(prevWord)) {
            words.put(prevWord, new ArrayList<>());
        }
        words.get(prevWord).add(word);
        if (!words.containsKey(word)) {
            words.put(word, new ArrayList<>());
        }
        if (endsWithPunctuation(word)) {
            prevWord = BEGINS_SENTENCE;
        } else {
            prevWord = word;
        }
    }
    String randomWord(String word) { // might need to make public
        ArrayList<String> list = words.get(word);
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
    public String toString() {
        return words.toString();
    }

    public HashMap<String, ArrayList<String>> getWords() {
        return words;
    }
    void addLine(String line) { // might need to make public
        Scanner sc = new Scanner(line);
        while (sc.hasNext()) {
            addWord(sc.next());
        }
        sc.close();
    }
    public static boolean endsWithPunctuation(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        char last = word.charAt(word.length() - 1);
        return PUNCTUATION_MARKS.indexOf(last) >= 0;
    }
}
