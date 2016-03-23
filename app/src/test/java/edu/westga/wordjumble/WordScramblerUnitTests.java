package edu.westga.wordjumble;

import org.junit.Test;

import edu.westga.wordjumble.model.WordScrambler;
import edu.westga.wordjumble.model.Words;

import static org.junit.Assert.*;

/**
 * Unit Tests for the WordScrambler class
 */
public class WordScramblerUnitTests {

    @Test
    public void validateWordDoesNotMatchWhenScrambled() throws Exception {
        WordScrambler scrambler = new WordScrambler("chair");
        assertFalse("chair".equals(scrambler.getScrambledWord()));
    }

    @Test
    public void validateWordMatchesCorrectWord() {
        WordScrambler scrambler = new WordScrambler("chair");
        assertEquals("chair", scrambler.getWord());
    }

    @Test
    public void validateCompareWordReturnsTrueWhenWordMatches() {
        WordScrambler scrambler = new WordScrambler("chair");
        assert(scrambler.compareWord("chair"));
    }

    @Test
    public void validateCompareWordReturnsFalseWhenWordDoesNotMatch() {
        WordScrambler scrambler = new WordScrambler("chair");
        assertFalse(scrambler.compareWord("chairs"));
    }

    @Test
    public void validateIfHintShowsCensoredWordOnFirstHint() {
        WordScrambler scrambler = new WordScrambler("chair");
        String censored = scrambler.getHint(1);
        assertEquals("c****", censored);
    }

    @Test
    public void validateIfHintShowsCensoredWordOnLastHint() {
        WordScrambler scrambler = new WordScrambler("chair");
        String censored = scrambler.getHint(5);
        assertEquals("chair",censored);
    }

    @Test
    public void validateIfHintShowsCensoredWordOnOverIndexHint() {
        WordScrambler scrambler = new WordScrambler("chair");
        String censored = scrambler.getHint(12);
        assertEquals("chair",censored);
    }

    @Test
    public void validateIfGet5LetterWordsFirstWordFromFile() {
        //TODO: Need to find a way to get Context
    }
}