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
 * This class tests the MainActivity class when it is started in the Portrait, has something done, then
 * switched to Landscape
 */
public class MainActivityTestsPortraitToLandscape extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity activity;
    private Button enter, startGame;
    private ImageButton hint;
    private TextView jumbledWord, result;
    private EditText userGuessEditTxt;

    public MainActivityTestsPortraitToLandscape() {
        super(MainActivity.class);
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
