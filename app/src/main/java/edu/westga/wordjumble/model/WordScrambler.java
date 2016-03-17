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

    public String getWord() {
        return this.word;
    }

    public String getScrambledWord() {
        return this.scrambledWord;
    }

    public boolean compareWord(String word) {
        return this.word.equals(word);
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
}
