package edu.westga.wordjumble;
import android.content.pm.ActivityInfo;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import edu.westga.wordjumble.controller.MainActivity;

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
//      this.setUp();
        TouchUtils.clickView(this, this.startGame);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                userGuessEditTxt.requestFocus();
                userGuessEditTxt.setText("Wrong Guess");
            }
        });

        getInstrumentation().waitForIdleSync();
//      getInstrumentation().sendStringSync(this.activity.getUnJumbledWord());
        TouchUtils.clickView(this, this.enter);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String resultText = this.result.getText().toString();
        assertEquals("Incorrect.  Try Again!", resultText);
    }

    public void testRightGuess() {
//      this.setUp();
        TouchUtils.clickView(this, this.startGame);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                userGuessEditTxt.requestFocus();
                userGuessEditTxt.setText(activity.getUnJumbledWord());
            }
        });

        getInstrumentation().waitForIdleSync();
//        getInstrumentation().sendStringSync(this.activity.getUnJumbledWord());
        TouchUtils.clickView(this, this.enter);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String resultText = this.result.getText().toString();
        assertEquals("Correct!  Great Job!", resultText);
    }

//    public void testFirstHint() {
//        //this.setUp();
//        TouchUtils.clickView(this,this.startGame);
//        int starCounter = 1;
//        StringBuilder hintWord = new StringBuilder(this.activity.getUnJumbledWord());
//        while( starCounter <= this.activity.getUnJumbledWord().length() - 1) {
//            hintWord.setCharAt(starCounter,'*');
//            starCounter++;
//        }
//        String actualWord = String.valueOf(hintWord);
//        TouchUtils.clickView(this, this.hint);
//        getInstrumentation().waitForIdleSync();
//        assertEquals(this.activity.getHintWord(), actualWord);
//    }

//    public void testNewGameButtonStartsANewGameWithFiveLetterWord() {
////      this.setUp();
//        RadioButton fiveLetterRadioButton  = (RadioButton) activity.findViewById(R.id.fiveLetterRadioButton);
//        Button newGameButton  = (Button) activity.findViewById(R.id.newGameButton);
//        TouchUtils.clickView(this, fiveLetterRadioButton);
//        TouchUtils.clickView(this, newGameButton);
//        TextView originalScrambledWordTextView = (TextView) activity.findViewById(R.id.scrambledWordTextView);
//        String originalScrambledWord = originalScrambledWordTextView.getText().toString();
//        getInstrumentation().waitForIdleSync();
//
//        // Check up to 100 times that a new word has been given which would prove a new game
//        for (int i = 0; i < 100; i++) {
//            TouchUtils.clickView(this, newGameButton);
//            getInstrumentation().waitForIdleSync();
//            TextView newScrambledWordTextView = (TextView) activity.findViewById(R.id.scrambledWordTextView);
//            if (!newScrambledWordTextView.getText().toString().equalsIgnoreCase(originalScrambledWord) && newScrambledWordTextView.getText().toString().length() == 5) {
//                assertTrue(true);
//                break;
//            }
//        }
//    }
//
//    public void testNewGameButtonStartsANewGameWithSixLetterWord() {
////      this.setUp();
//        RadioButton sixLetterRadioButton  = (RadioButton) activity.findViewById(R.id.sixLetterRadioButton);
//        Button newGameButton  = (Button) activity.findViewById(R.id.newGameButton);
//        TouchUtils.clickView(this, sixLetterRadioButton);
//        TouchUtils.clickView(this, newGameButton);
//        TextView originalScrambledWordTextView = (TextView) activity.findViewById(R.id.scrambledWordTextView);
//        String originalScrambledWord = originalScrambledWordTextView.getText().toString();
//        getInstrumentation().waitForIdleSync();
//
//        // Check up to 100 times that a new word has been given which would prove a new game
//        for (int i = 0; i < 100; i++) {
//            TouchUtils.clickView(this, newGameButton);
//            getInstrumentation().waitForIdleSync();
//            TextView newScrambledWordTextView = (TextView) activity.findViewById(R.id.scrambledWordTextView);
//            if (!newScrambledWordTextView.getText().toString().equalsIgnoreCase(originalScrambledWord) && newScrambledWordTextView.getText().toString().length() == 6) {
//                assertTrue(true);
//                break;
//            }
//        }
//    }
//
//    public void testEnterButtonIsDisabledByDefault() {
////      this.setUp();
//        Button enterButton  = (Button) activity.findViewById(R.id.enterButton);
//        assertFalse(enterButton.isEnabled());
//    }
//
//    public void testHintButtonIsDisabledByDefault() {
//        //this.setUp();
//        ImageButton hintButton  = (ImageButton) activity.findViewById(R.id.btnHint);
//        assertFalse(hintButton.isEnabled());
//    }
//
//    public void testHintButtonIsEnabledWhenNewGameIsClicked() {
////      this.setUp();
//        Button newGameButton  = (Button) activity.findViewById(R.id.newGameButton);
//        ImageButton hintButton  = (ImageButton) activity.findViewById(R.id.btnHint);
//        TouchUtils.clickView(this, newGameButton);
//        assertTrue(hintButton.isEnabled());
//    }
//
//    public void testEditTextStateDoesNotChangeWhenOrientationChange() {
////      this.setUp();
//
//        final EditText userGuessWord = (EditText) activity.findViewById(R.id.userGuessTxt);
//
//        getInstrumentation().runOnMainSync(new Runnable() {
//            @Override
//            public void run() {
//                userGuessWord.requestFocus();
//            }
//        });
//        getInstrumentation().waitForIdleSync();
//        getInstrumentation().sendStringSync("abcde");
//        this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        assertEquals("abcde", ((EditText) activity.findViewById(R.id.userGuessTxt)).getText().toString());
//    }
//
//    public void testScrambledWordDoesNotChangeWhenOrientationChange() {
////      this.setUp();
//        Button newGameButton  = (Button) activity.findViewById(R.id.newGameButton);
//        TouchUtils.clickView(this, newGameButton);
//        TextView originalScrambledWordTextView = (TextView) activity.findViewById(R.id.scrambledWordTextView);
//        String originalScrambledWord = originalScrambledWordTextView.getText().toString();
//        this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        assertEquals(originalScrambledWord, ((TextView) activity.findViewById(R.id.scrambledWordTextView)).getText().toString());
//    }
//
//    public void testRadioButtonDoesNotChangeWhenOrientationChangeWhen5ButtonRadioButton() {
////      this.setUp();
//        RadioButton radioButton = (RadioButton) activity.findViewById(R.id.fiveLetterRadioButton);
//        TouchUtils.clickView(this, radioButton);
//        this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        assertTrue(radioButton.isChecked());
//    }
//
//    public void testRadioButtonDoesNotChangeWhenOrientationChangeWhen6ButtonRadioButton() {
////      this.setUp();
//        RadioButton radioButton = (RadioButton) activity.findViewById(R.id.sixLetterRadioButton);
//        TouchUtils.clickView(this, radioButton);
//        this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        assertTrue(radioButton.isChecked());
//    }
//
//    /*********************
//     * Landscape tests **
//     ********************/
//
//    public void testFirstHintLandscape() {
////      this.setUp();
//        TouchUtils.clickView(this, this.startGame);
//        int starCounter = 1;
//        StringBuilder hintWord = new StringBuilder(this.activity.getUnJumbledWord());
//        while( starCounter <= this.activity.getUnJumbledWord().length() - 1) {
//            hintWord.setCharAt(starCounter,'*');
//            starCounter++;
//        }
//        String actualWord = String.valueOf(hintWord);
//        this.activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        getInstrumentation().runOnMainSync(new Runnable() {
//            @Override
//            public void run() {
//                hint.requestFocus();
//            }
//        });
//        this.activity.finish();
//        setActivity(null);
//        this.activity = getActivity();
//        getInstrumentation().waitForIdleSync();
//        TouchUtils.clickView(this, this.hint);
//        assertEquals(this.activity.getHintWord(), actualWord);
//    }

    @Override
    protected void setUp() {
        //this.activity = null;
        this.activity = getActivity();
        this.enter = (Button) activity.findViewById(R.id.enterButton);
        this.hint = (ImageButton) activity.findViewById(R.id.btnHint);
        this.startGame = (Button) activity.findViewById(R.id.newGameButton);
        this.jumbledWord = (TextView) activity.findViewById(R.id.scrambledWordTextView);
        this.result = (TextView) activity.findViewById(R.id.resultTextView);
        this.userGuessEditTxt = (EditText) activity.findViewById(R.id.userGuessTxt);
    }
}
