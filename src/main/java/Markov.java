/**
 * Title: Markov.java
 * Abstract: A program will read a text file, generate a collection of words and words that follow
 *           that word, and will then use those words to generate new text.
 * Author: Leonardo Lopez
 * Date: 3/6/26
 */
import java.io.*;
import java.util.*;

public class Markov {
    // constants
    private static String BEGINS_SENTENCE = "__$";
    private static String PUNCTUATION_MARKS = ".!?$";

    // members
    private HashMap<String, ArrayList<String>> words;
    private String prevWord;

    // methods
    /**
     * Constructor for the Markov generator.
     *
     * Initializes the HashMap and creates the starting
     * BEGINS_SENTENCE key.
     */
    public Markov() {
        words = new HashMap<>();
        words.put(BEGINS_SENTENCE, new ArrayList<>());
        prevWord = BEGINS_SENTENCE;
    }

    /**
     * Generates a sentence using the Markov chain.
     *
     * The sentence starts with BEGINS_SENTENCE and continues selecting
     * random successor words until a word ending in punctuation is reached.
     *
     * @return a generated sentence
     */
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

    /**
     * Reads a file and adds all lines to the Markov structure.
     *
     * @param filename the name of the file to read
     */
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

    /**
     * Adds a single word to the Markov structure.
     *
     * The word is stored as a possible successor to the previous word.
     * If the word ends in punctuation, the next word will start a new sentence.
     *
     * @param word the word to add
     */
    public void addWord(String word) {
        if (!words.containsKey(prevWord)) {
            words.put(prevWord, new ArrayList<>());
        }
        words.get(prevWord).add(word);
        if (endsWithPunctuation(word)) {
            prevWord = BEGINS_SENTENCE;
        } else {
            prevWord = word;
        }
    }

    /**
     * Selects a random word that follows the given word.
     *
     * If only one word exists in the list, it returns that word directly.
     *
     * @param word the word whose successor should be selected
     * @return a randomly selected following word, or null if none exist
     */
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

    /**
     * Returns a string representation of the Markov word map.
     *
     * @return the HashMap as a string
     */
    public String toString() {
        return words.toString();
    }

    /**
     * Returns the HashMap containing the Markov word relationships.
     *
     * @return the HashMap of words and their possible following words
     */
    public HashMap<String, ArrayList<String>> getWords() {
        return words;
    }

    /**
     * Processes a line of text and adds each word to the Markov structure.
     *
     * @param line the line of text to process
     */
    void addLine(String line) { // might need to make public
        Scanner sc = new Scanner(line);
        while (sc.hasNext()) {
            addWord(sc.next());
        }
        sc.close();
    }

    /**
     * Determines whether a word ends with a punctuation mark.
     *
     * @param word the word to check
     * @return true if the word ends with punctuation, otherwise false
     */
    public static boolean endsWithPunctuation(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        char last = word.charAt(word.length() - 1);
        return PUNCTUATION_MARKS.indexOf(last) >= 0;
    }
}
