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

    public void validateWordMatchesCorrectWord() {
        WordScrambler scrambler = new WordScrambler("chair");
        assertEquals("chair", scrambler.getWord());
    }

    public void validateCompareWordReturnsTrueWhenWordMatches() {
        WordScrambler scrambler = new WordScrambler("chair");
        assert(scrambler.compareWord("chair"));
    }

    public void validateCompareWordReturnsFalseWhenWordDoesNotMatch() {
        WordScrambler scrambler = new WordScrambler("chair");
        assertFalse(scrambler.compareWord("chairs"));
    }
}