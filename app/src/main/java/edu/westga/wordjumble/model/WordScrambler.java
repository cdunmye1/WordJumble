package edu.westga.wordjumble.model;

/**
 * Created by Chris on 3/16/2016.
 */
public class WordScrambler {

    private String word;

    public WordScrambler(String word) {
        if (word.isEmpty()) {
            throw new IllegalArgumentException("The word cannot be empty");
        }
        this.word = word;
    }

    public String getWord() {
        return this.word;
    }

    public boolean compareWord(String word) {
        return true;
    }

    public String scramble() {
        return "blah";
    }
}
