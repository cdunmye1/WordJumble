package edu.westga.wordjumble.controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.westga.wordjumble.R;
import edu.westga.wordjumble.model.WordScrambler;
import edu.westga.wordjumble.model.Words;

public class MainActivity extends AppCompatActivity {
    private Words words;
    private WordScrambler game;
    private int hintCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        this.words = new Words(this);
        //this.words = new Words();
        this.startNewGame();
    }

    public  void didTapPlayAgain(View view) {
        this.startNewGame();
    }

    public  void didTapEnter(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        TextView resultTextView = (TextView) findViewById(R.id.resultTextView);
        if (this.game.compareWord(editText.getText().toString())) {
            resultTextView.setText("Correct!  Great Job!");
            editText.setText("");
            this.startNewGame();
        } else {
            resultTextView.setText("Incorrect.  Try Again!");
        }
    }

    private void startNewGame() {
        TextView scrambledWordTextView = (TextView) findViewById(R.id.scrambledWordTextView);
        this.game = new WordScrambler(this.words.getRandomWord());
        scrambledWordTextView.setText(this.game.getScrambledWord());
        this.hintCounter = 0;
    }

    /**
     * Displays a hint as a Toast
     * @param view the active activity
     */
    public void getHint(View view) {
        hintCounter += 1;
        if(hintCounter < this.words.getCurrentWord().length()) {
            String hintWord = this.game.getHint(this.hintCounter);
            Toast.makeText(getApplicationContext(),hintWord,Toast.LENGTH_LONG).show();
        }
    }

    //TODO: Do we need this code below?
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
