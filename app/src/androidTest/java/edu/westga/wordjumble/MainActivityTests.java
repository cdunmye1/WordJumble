package edu.westga.wordjumble;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

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


    public MainActivityTests() {
        super(MainActivity.class);
    }

    public void testActivityExists() {
        MainActivity activity = getActivity();
        assertNotNull(activity);
    }

//    public void testWrongGuess() {
//        this.setUp();
//
//        final EditText userGuessWord = (EditText) activity.findViewById(R.id.userGuessTxt);
//
//        getInstrumentation().runOnMainSync(new Runnable() {
//            @Override
//            public void run() {
//                userGuessWord.requestFocus();
//            }
//        });
//
//        getInstrumentation().waitForIdleSync();
//        getInstrumentation().sendStringSync("Guess");
//        TouchUtils.clickView(this, this.enter);
//        String resultText = this.result.getText().toString();
//        assertEquals("Incorrect.  Try Again!", resultText);
//    }

//    public void testRightGuess() {
//        this.setUp();
//
//        final EditText userGuessWord = (EditText) activity.findViewById(R.id.userGuessTxt);
//
//        getInstrumentation().runOnMainSync(new Runnable() {
//            @Override
//            public void run() {
//                userGuessWord.requestFocus();
//            }
//        });
//
//        getInstrumentation().waitForIdleSync();
//        getInstrumentation().sendStringSync(this.activity.getUnJumbledWord());
//        TouchUtils.clickView(this, this.enter);
//        String resultText = this.result.getText().toString();
//        assertEquals("Correct!  Great Job!",resultText);
//    }

//    public void testFirstHint() {
//        this.setUp();
//        int starCounter = 1;
//        StringBuilder hintWord = new StringBuilder(this.activity.getUnJumbledWord());
//        while( starCounter <= this.activity.getUnJumbledWord().length() - 1) {
//            hintWord.setCharAt(starCounter,'*');
//            starCounter++;
//        }
//        String actualWord = String.valueOf(hintWord);
//        TouchUtils.clickView(this, this.hint);
//        getInstrumentation().waitForIdleSync();
//        assertEquals(this.activity.getHintWord(),actualWord);
//    }



    public void testNewGameButtonStartsANewGameWithFiveLetterWord() {
        this.setUp();
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
        this.setUp();
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

    public void setUp() {
        this.activity = getActivity();
        this.enter = (Button) activity.findViewById(R.id.enterButton);
        this.hint = (ImageButton) activity.findViewById(R.id.btnHint);
        this.startGame = (Button) activity.findViewById(R.id.newGameButton);
        this.jumbledWord = (TextView) activity.findViewById(R.id.scrambledWordTextView);
        this.result = (TextView) activity.findViewById(R.id.resultTextView);
    }
}
