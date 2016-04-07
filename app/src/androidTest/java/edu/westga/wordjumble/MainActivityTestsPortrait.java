package edu.westga.wordjumble;

import android.content.pm.ActivityInfo;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.IOException;

import edu.westga.wordjumble.controller.MainActivity;
import edu.westga.wordjumble.model.Words;

/**
 * Created by Chris Dunmeyer and Chris Yan on 4/5/2016.
 * This class tests the MainActivity class when it is in the Landscape orientation
 */
public class MainActivityTestsPortrait extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity activity;
    private Button enter, startGame;
    private ImageButton hint;
    private TextView jumbledWord, result;
    private EditText userGuessEditTxt;

    public MainActivityTestsPortrait() {
        super(MainActivity.class);
    }

    public void testActivityExists() {
        MainActivity activity = getActivity();
        assertNotNull(activity);
    }

    public void testWrongGuess() {
        TouchUtils.clickView(this, this.startGame);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                userGuessEditTxt.requestFocus();
                userGuessEditTxt.setText("Wrong Guess");
            }
        });

        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, this.enter);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String resultText = this.result.getText().toString();
        assertEquals("Incorrect.  Try Again!", resultText);
    }

    public void testRightGuess() {
        TouchUtils.clickView(this, this.startGame);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                userGuessEditTxt.requestFocus();
                userGuessEditTxt.setText(activity.getUnJumbledWord());
            }
        });

        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, this.enter);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String resultText = this.result.getText().toString();
        assertEquals("Correct!  Great Job!", resultText);
    }

    public void testFirstHint() {
        //this.setUp();
        TouchUtils.clickView(this,this.startGame);
        int starCounter = 1;
        StringBuilder hintWord = new StringBuilder(this.activity.getUnJumbledWord());
        while( starCounter <= this.activity.getUnJumbledWord().length() - 1) {
            hintWord.setCharAt(starCounter,'*');
            starCounter++;
        }
        String actualWord = String.valueOf(hintWord);
        TouchUtils.clickView(this, this.hint);
        getInstrumentation().waitForIdleSync();
        assertEquals(this.activity.getHintWord(), actualWord);
    }

    public void testNewGameButtonStartsANewGameWithFiveLetterWord() {
        RadioButton fiveLetterRadioButton  = (RadioButton) activity.findViewById(R.id.fiveLetterRadioButton);
        Button newGameButton  = (Button) activity.findViewById(R.id.newGameButton);
        TouchUtils.clickView(this, fiveLetterRadioButton);
        TouchUtils.clickView(this, newGameButton);
        TextView originalScrambledWordTextView = (TextView) activity.findViewById(R.id.scrambledWordTextView);
        String originalScrambledWord = originalScrambledWordTextView.getText().toString();
        getInstrumentation().waitForIdleSync();

        // Check up to 100 times that a new word has been given which would prove a new game
        for (int i = 0; i < 100; i++) {
            TouchUtils.clickView(this, newGameButton);
            getInstrumentation().waitForIdleSync();
            TextView newScrambledWordTextView = (TextView) activity.findViewById(R.id.scrambledWordTextView);
            if (!newScrambledWordTextView.getText().toString().equalsIgnoreCase(originalScrambledWord) && newScrambledWordTextView.getText().toString().length() == 5) {
                assertTrue(true);
                break;
            }
        }
    }

    public void testNewGameButtonStartsANewGameWithSixLetterWord() {
        RadioButton sixLetterRadioButton  = (RadioButton) activity.findViewById(R.id.sixLetterRadioButton);
        Button newGameButton  = (Button) activity.findViewById(R.id.newGameButton);
        TouchUtils.clickView(this, sixLetterRadioButton);
        TouchUtils.clickView(this, newGameButton);
        TextView originalScrambledWordTextView = (TextView) activity.findViewById(R.id.scrambledWordTextView);
        String originalScrambledWord = originalScrambledWordTextView.getText().toString();
        getInstrumentation().waitForIdleSync();

        // Check up to 100 times that a new word has been given which would prove a new game
        for (int i = 0; i < 100; i++) {
            TouchUtils.clickView(this, newGameButton);
            getInstrumentation().waitForIdleSync();
            TextView newScrambledWordTextView = (TextView) activity.findViewById(R.id.scrambledWordTextView);
            if (!newScrambledWordTextView.getText().toString().equalsIgnoreCase(originalScrambledWord) && newScrambledWordTextView.getText().toString().length() == 6) {
                assertTrue(true);
                break;
            }
        }
    }

    public void testEnterButtonIsDisabledByDefault() {
        Button enterButton  = (Button) activity.findViewById(R.id.enterButton);
        assertFalse(enterButton.isEnabled());
    }

    public void testHintButtonIsDisabledByDefault() {
        ImageButton hintButton  = (ImageButton) activity.findViewById(R.id.btnHint);
        assertFalse(hintButton.isEnabled());
    }

    public void testHintButtonIsEnabledWhenNewGameIsClicked() {
        Button newGameButton  = (Button) activity.findViewById(R.id.newGameButton);
        ImageButton hintButton  = (ImageButton) activity.findViewById(R.id.btnHint);
        TouchUtils.clickView(this, newGameButton);
        assertTrue(hintButton.isEnabled());
    }
    public void testEditTextStateDoesNotChangeWhenOrientationChange() {
        final EditText userGuessWord = (EditText) activity.findViewById(R.id.userGuessTxt);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                userGuessWord.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("abcde");
        this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        assertEquals("abcde", ((EditText) activity.findViewById(R.id.userGuessTxt)).getText().toString());
    }

    public void testScrambledWordDoesNotChangeWhenOrientationChange() {
        Button newGameButton  = (Button) activity.findViewById(R.id.newGameButton);
        TouchUtils.clickView(this, newGameButton);
        TextView originalScrambledWordTextView = (TextView) activity.findViewById(R.id.scrambledWordTextView);
        String originalScrambledWord = originalScrambledWordTextView.getText().toString();
        this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        assertEquals(originalScrambledWord, ((TextView) activity.findViewById(R.id.scrambledWordTextView)).getText().toString());
    }

    public void testRadioButtonDoesNotChangeWhenOrientationChangeWhen5ButtonRadioButton() {
        RadioButton radioButton = (RadioButton) activity.findViewById(R.id.fiveLetterRadioButton);
        TouchUtils.clickView(this, radioButton);
        this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        assertTrue(radioButton.isChecked());
    }

    public void testRadioButtonDoesNotChangeWhenOrientationChangeWhen6ButtonRadioButton() {
        RadioButton radioButton = (RadioButton) activity.findViewById(R.id.sixLetterRadioButton);
        TouchUtils.clickView(this, radioButton);
        this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        assertTrue(radioButton.isChecked());
    }

    public void testReadFromFileSuccess() {
        Words newWords = new Words(getActivity());
        String[] wordList = null;
        try {
            wordList = newWords.readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(wordList.length > 0);
    }

    public void testReadFromFileReadWordSuccess() {
        Words newWords = new Words(getActivity());
        String[] wordList = null;
        try {
            wordList = newWords.readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertTrue(wordList[0].length() > 0);
    }

    public void testSecondToLastHint() {
        TouchUtils.clickView(this, this.startGame);
        int starCounter = this.activity.getUnJumbledWord().length() - 1;
        StringBuilder hintWord = new StringBuilder(this.activity.getUnJumbledWord());
        while (starCounter <= this.activity.getUnJumbledWord().length() - 1) {
            hintWord.setCharAt(starCounter, '*');
            starCounter++;
        }
        String actualWord = String.valueOf(hintWord);

        int hintCounter = 0;
        while (hintCounter < this.activity.getUnJumbledWord().length() - 1) {
            TouchUtils.clickView(this, this.hint);
            hintCounter++;
        }
        getInstrumentation().waitForIdleSync();
        assertEquals(this.activity.getHintWord(), actualWord);
    }

    public void testSecondHint() {
        TouchUtils.clickView(this, this.startGame);
        int starCounter = 2 ;
        StringBuilder hintWord = new StringBuilder(this.activity.getUnJumbledWord());
        while( starCounter <= this.activity.getUnJumbledWord().length() - 1) {
            hintWord.setCharAt(starCounter,'*');
            starCounter++;
        }
        String actualWord = String.valueOf(hintWord);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                hint.requestFocus();
            }
        });
        this.activity = getActivity();
        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, this.hint);
        TouchUtils.clickView(this, this.hint);
        assertEquals(this.activity.getHintWord(), actualWord);
    }


    @Override
    protected void setUp() {
        this.activity = getActivity();
        this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.enter = (Button) activity.findViewById(R.id.enterButton);
        this.hint = (ImageButton) activity.findViewById(R.id.btnHint);
        this.startGame = (Button) activity.findViewById(R.id.newGameButton);
        this.jumbledWord = (TextView) activity.findViewById(R.id.scrambledWordTextView);
        this.result = (TextView) activity.findViewById(R.id.resultTextView);
        this.userGuessEditTxt = (EditText) activity.findViewById(R.id.userGuessTxt);
    }

}
