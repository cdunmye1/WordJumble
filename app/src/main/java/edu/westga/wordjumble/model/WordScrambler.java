package edu.westga.wordjumble.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Chris Dunmyer and Chris Yan
 */
public class WordScrambler {

    private String word;
    private String scrambledWord;

    /**
     * Constructor that accepts a word String
     * @param word the unjumbled word
     */
    public WordScrambler(String word) {
        if (word.isEmpty()) {
            throw new IllegalArgumentException("The word cannot be empty");
        }
        this.word = word;
        this.scramble();
        while (this.word.equals(this.scrambledWord)) {
            this.scramble();
        }
    }

    /**
     * Returns the unjumbled word
     * @return unjumbled word
     */
    public String getWord() {
        return this.word;
    }

    /**
     * Returns the scrambled word
     * @return scrambled word
     */
    public String getScrambledWord() {
        return this.scrambledWord;
    }

    /**
     * Compares the guess versus the unjumbled word
     * @param word the user's guess
     * @return true when user's guess matches unjumbled word otherwise false
     */
    public boolean compareWord(String word) {
        return this.word.equalsIgnoreCase(word);
    }

    private void scramble() {
        List<String> letters = Arrays.asList(this.word.split(""));
        Collections.shuffle(letters);
        String scrambled = "";
        for (String letter : letters) {
            scrambled += letter;
        }
        this.scrambledWord = scrambled;
    }

    /**
     * Returns a censored word to be shown on a Toast
     * @param hintCounter amount of hints the user has used
     * @return the hint of the jumbled word
     */
    public String getHint(int hintCounter) {
        int starCounter = hintCounter + 1;
        StringBuilder hintWord = new StringBuilder(this.word);
        while( starCounter <= this.word.length()) {
            hintWord.setCharAt(starCounter,'*');
        }
        return String.valueOf(hintWord);
    }
}
