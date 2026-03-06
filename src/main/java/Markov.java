/**
 *
 *
 *
 *
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
    void addWord(String prevWord) {

    }
    String randomWord(String word) {

    }
    public String toString() {

    }

    public HashMap<String, ArrayList<String>> getWords() {
        return words;
    }
    void addLine(String line) {
        Scanner sc = new Scanner(line);
        while (sc.hasNext()) {
            addWord(sc.next());
        }
        sc.close();
    }
    public static boolean endsWithPunctuation(String senString) {

    }
}
