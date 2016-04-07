package edu.westga.wordjumble;

import android.content.pm.ActivityInfo;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import edu.westga.wordjumble.controller.MainActivity;

/**
 * Created by Chris Dunmeyer and Chris Yan on 4/5/2016.
 */
public class LandscapeActivityTests extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity activity;
    private Button enter, startGame;
    private ImageButton hint;
    private TextView jumbledWord, result;
    private EditText userGuessEditTxt;

    public LandscapeActivityTests() {
        super(MainActivity.class);
    }

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

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.enter.performClick();
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
