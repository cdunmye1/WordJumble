package edu.westga.wordjumble;
import android.app.Instrumentation;
import android.content.pm.ActivityInfo;
import android.os.SystemClock;
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
 * Created by Chris Dunmeyer and Chris Yan on 3/29/2016.
 */
public class MainActivityTests  extends ActivityInstrumentationTestCase2<MainActivity>{
    private MainActivity activity;

    private Button enter, startGame;
    private ImageButton hint;
    private TextView jumbledWord, result;
    private EditText userGuessEditTxt;



    public MainActivityTests() {
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

    /*********************
     * Landscape tests **
     ********************/

    public void testFirstHintLandscape() {
        this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getInstrumentation().waitForIdleSync();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startGame = (Button) this.activity.findViewById(R.id.newGameButton);
        hint = (ImageButton) this.activity.findViewById(R.id.btnHint);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                startGame.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        startGame.performClick();
        hint.performClick();
        int starCounter = 1;
        StringBuilder hintWord = new StringBuilder(this.activity.getUnJumbledWord());
        while( starCounter <= this.activity.getUnJumbledWord().length() - 1) {
            hintWord.setCharAt(starCounter,'*');
            starCounter++;
        }
        String actualWord = String.valueOf(hintWord);
        assertEquals(this.activity.getHintWord(), actualWord);
    }

    public void testSecondHintLandscape() {
        TouchUtils.clickView(this, this.startGame);
        int starCounter = 2 ;
        StringBuilder hintWord = new StringBuilder(this.activity.getUnJumbledWord());
        while( starCounter <= this.activity.getUnJumbledWord().length() - 1) {
            hintWord.setCharAt(starCounter,'*');
            starCounter++;
        }
        String actualWord = String.valueOf(hintWord);
        this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                hint.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        int hintCounter = 0;
        do {
            this.hint.performClick();
            hintCounter++;
        } while(hintCounter < 2);
        assertEquals(this.activity.getHintWord(), actualWord);
    }

    public void testWrongGuessLandscape() {

        TouchUtils.clickView(this, this.startGame);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                userGuessEditTxt.requestFocus();
                userGuessEditTxt.setText("Wrong Guess");
            }
        });
        this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getInstrumentation().waitForIdleSync();
        this.enter.performClick();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String resultText = this.result.getText().toString();
        assertEquals("Incorrect.  Try Again!", resultText);
    }

    public void testCorrectAnswerGuessLandscape() {

        TouchUtils.clickView(this, this.startGame);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                userGuessEditTxt.requestFocus();
                userGuessEditTxt.setText(activity.getUnJumbledWord());
            }
        });
        this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getInstrumentation().sendStringSync(this.activity.getUnJumbledWord());
        this.enter.performClick();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String resultText = this.result.getText().toString();
        assertEquals("Correct!  Great Job!", resultText);
    }

    public void testDisabledEnterButtonSwitchFromPortraitToLandscape() {
        this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        String resultText = this.result.getText().toString();
        assertEquals(this.enter.isEnabled(), false);
    }

    public void testDisabledHintButtonSwitchFromPortraitToLandscape() {
        this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        String resultText = this.result.getText().toString();
        assertEquals(this.hint.isEnabled(), false);
    }

    public void testWrongAnswerMessageSwitchFromPortraitToLandscape() {
        TouchUtils.clickView(this, this.startGame);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                userGuessEditTxt.requestFocus();
                userGuessEditTxt.setText("Wrong answer");
            }
        });

        getInstrumentation().waitForIdleSync();
        TouchUtils.clickView(this, this.enter);

        this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        String resultText = this.result.getText().toString();
        assertEquals("Incorrect.  Try Again!", resultText);
    }

    public void testCorrectAnswerMessageSwitchFromPortraitToLandscape() {
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

        this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        String resultText = this.result.getText().toString();
        assertEquals("Correct!  Great Job!", resultText);
    }

    public void testCorrectAnswerEnterButtonSwitchFromPortraitToLandscape() {
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

        this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        assertEquals(this.enter.isEnabled(), true);
    }

    public void testCorrectAnswerHintButtonSwitchFromPortraitToLandscape() {
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

        this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        assertEquals(this.hint.isEnabled(), true);
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
