package edu.westga.wordjumble.controller;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        Typeface newFont = Typeface.createFromAsset(getAssets(), "fonts/DK_Cool_Crayon.ttf");
        this.scrambledWordTextView = (TextView) findViewById(R.id.scrambledWordTextView);
        this.resultTextView = (TextView) findViewById(R.id.resultTextView);
        this.editText = (EditText) findViewById(R.id.editText);
        this.scrambledWordTextView.setTypeface(newFont);
        this.resultTextView.setTypeface(newFont);
        this.words = new Words(this);
        this.startNewGame();
    }

    public  void didTapPlayAgain(View view) {
        this.startNewGame();
    }

    public  void didTapEnter(View view) {

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
    public void getHint(View view) {
        String hintWord = this.game.getHint();
        if (this.game.compareWord(hintWord)) {
            Toast.makeText(getApplicationContext(),"You really need all those hints? Try again!",Toast.LENGTH_LONG).show();
            this.startNewGame();
        } else {
            Toast.makeText(getApplicationContext(),hintWord,Toast.LENGTH_LONG).show();
        }
    }
}
