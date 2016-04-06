package edu.westga.wordjumble.controller;


import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import edu.westga.wordjumble.R;
import edu.westga.wordjumble.model.WordScrambler;
import edu.westga.wordjumble.model.Words;

/**
 *TODO:
 * Chris D:
 - Update 5 and 6 Letter Buttons
 - Rename play again to new game
 - Validate Play again button works
 - Validate 6 letter button click shows a 6 letter word on screen
 - Validate 5 letter button click shows a 5 letter word on screen

 Chris Yan:
 - Style UI (Include reverse landscape)
 - (test) Validate Enter button shows both correct and incorrect
 - (test) Validate Hint shows what we expect for one click and up to the max
*
 */
public class MainActivity extends AppCompatActivity {
    private Words words;
    private WordScrambler game;
    private TextView scrambledWordTextView,  resultTextView;
    private EditText editText;
    private Button enterButton;
    private ImageButton btnHint;
    private String hintWord;
    private boolean isHintEnabled;
    private boolean isEnterButtonEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Typeface newFont = Typeface.createFromAsset(getAssets(), "fonts/DK_Cool_Crayon.ttf");
        this.scrambledWordTextView = (TextView) findViewById(R.id.scrambledWordTextView);
        this.resultTextView = (TextView) findViewById(R.id.resultTextView);
        this.editText = (EditText) findViewById(R.id.userGuessTxt);
        this.scrambledWordTextView.setTypeface(newFont);
        this.resultTextView.setTypeface(newFont);
        this.scrambledWordTextView = (TextView) findViewById(R.id.scrambledWordTextView);
        this.btnHint = (ImageButton) findViewById(R.id.btnHint);
        this.enterButton = (Button) findViewById(R.id.enterButton);
        if (savedInstanceState == null) {
            this.isHintEnabled = false;
            this.isEnterButtonEnabled = false;
          }

        if (savedInstanceState != null) {
            if (savedInstanceState.getSerializable("WORDS_OBJECT") != null) {
                this.words = (Words) savedInstanceState.getSerializable("WORDS_OBJECT");
            }
            if (savedInstanceState.getSerializable("GAME") != null) {
                this.game = (WordScrambler) savedInstanceState.getSerializable("GAME");
                scrambledWordTextView.setText(this.game.getScrambledWord());
            }
            this.isHintEnabled = savedInstanceState.getBoolean("IS_HINT_ENABLED");
            this.isEnterButtonEnabled = savedInstanceState.getBoolean("IS_ENTER_ENABLED");
        }
        this.btnHint.setEnabled(this.isHintEnabled);
        this.enterButton.setEnabled(this.isEnterButtonEnabled);
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.words != null) {
            outState.putSerializable("WORDS_OBJECT", this.words);
        }
        if (this.game != null) {
            outState.putSerializable("GAME", this.game);
        }
        outState.putBoolean("IS_HINT_ENABLED", this.isHintEnabled);
        outState.putBoolean("IS_ENTER_ENABLED", this.isEnterButtonEnabled);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public  void didTapNewGame(View view) {
        this.words = new Words(this);
        RadioButton fiveLetterButton = (RadioButton) findViewById(R.id.fiveLetterRadioButton);
        if (fiveLetterButton.isChecked()) {
            this.words.get5LetterWords();
        } else {
            this.words.get6LetterWords();
        }
        this.isEnterButtonEnabled = true;
        this.isHintEnabled = true;
        this.btnHint.setEnabled(this.isHintEnabled);
        this.enterButton.setEnabled(this.isEnterButtonEnabled);
        this.startNewGame();
    }

    // either use a 5 or 6 letter word based on radio button selection
    public  void didTapEnter(View view) {
        if (this.editText.getText().toString().isEmpty()) {
            resultTextView.setTextColor(Color.parseColor("#FF0000"));
            resultTextView.setText("You must enter a word.  Try Again!");
            return;
        }
        if (this.game.compareWord(editText.getText().toString())) {
            resultTextView.setTextColor(Color.parseColor("#00FF00"));
            resultTextView.setText("Correct!  Great Job!");
            editText.setText("");
            this.startNewGame();
        } else {
            resultTextView.setTextColor(Color.parseColor("#FF0000"));
            resultTextView.setText("Incorrect.  Try Again!");
        }
    }

    private void startNewGame() {
        this.game = new WordScrambler(this.words.getRandomWord());
        scrambledWordTextView.setText(this.game.getScrambledWord());
    }

    /**
     * Displays a hint as a Toast
     * @param view the active activity
     */
    public void setHint(View view) {
        this.hintWord = this.game.setHint();
        if (this.game.compareWord(hintWord)) {
            Toast.makeText(getApplicationContext(),"You really need all those hints? Try again!",Toast.LENGTH_LONG).show();
            this.startNewGame();
        } else {
            Toast.makeText(getApplicationContext(),hintWord,Toast.LENGTH_LONG).show();
        }
    }

    public String getHintWord() {
        return this.hintWord;
    }


    /**
     * Returns the unjumbled word
     * @return unjumbled word
     */
    public String getUnJumbledWord() {
        return this.game.getWord();
    }
}
