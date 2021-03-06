package edu.westga.wordjumble.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Chris Dunmeyer and Chris Yan on 3/16/2016.
 */
public class Words implements Serializable {

    private ArrayList<String> words;
    private Context context;
    private String currentWord;

    /**
     * 1-parameter constructor that accepts context from the MainActivity
     * so that it can be used to getAssets and read text from a file
     * @param context
     */
    public Words(Context context) {
        this.context = context;
        this.words = new ArrayList<String>();
        this.get5LetterWords();
    }

    /**
     * Creates a list of strings
     */
    public Words() {
    }

    /**
     * Gets a random word a list of strings
     * @return
     */
    public String getRandomWord() {
        Random random = new Random();
        this.currentWord = words.get(random.nextInt(this.words.size() -1));
        return currentWord;
    }

    /**
     * Gets 5 letter words from a file and stores them in a list
     */
    public void get5LetterWords() {


        try {
            String[] wordList = readFromFile();
            for (String current : wordList) {
                if (current.length() == 5) {
                    this.words.add(current);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets 6 letter words from a file and stores them in a list
     */
    public void get6LetterWords() {


        try {
            String[] wordList = readFromFile();

            for(String current : wordList) {
                if (current.length() == 6) {
                    this.words.add(current);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    public String[] readFromFile() throws IOException {
        AssetManager assetManager = this.context.getAssets();
        InputStream input;
        // load wordBank.txt file
        input = assetManager.open("wordBank.txt");
        int size = input.available();
        byte[] buffer = new byte[size];
        input.read(buffer);
        input.close();

        // byte buffer into a string
        String text = new String(buffer);
        String[] wordList = text.split(",");

        // Clear words ArrayList
        if (this.words == null){
            this.words = new ArrayList<String>();
        } else {
            this.words.clear();
        }
        return wordList;
    }

    /**
     * Gets the current word being used in the game
     * @return
     */
    public String getCurrentWord() {
        return this.currentWord;
    }

}
