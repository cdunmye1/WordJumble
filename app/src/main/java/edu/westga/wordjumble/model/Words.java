package edu.westga.wordjumble.model;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Chris Dunmeyer and Chris Yan on 3/16/2016.
 */
public class Words {

    private ArrayList<String> words;
    private Context context;

    public Words(Context context) {
        this.context = context;
    }

    public Words() {
        this.words = new ArrayList<String>();
        this.words.add("Stair");
        this.words.add("Pills");
        this.words.add("Bread");
        this.words.add("Fryer");
        this.words.add("Knives");
        this.words.add("bottle");
    }

    public String getRandomWord() {
        Random random = new Random(this.words.size());
        return words.get(random.nextInt());
    }

    public void get5LetterWords() {
        AssetManager assetManager = this.context.getAssets();
        InputStream input;

        try {
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
            this.words.clear();
            for (String current : wordList) {
                if (current.length() == 5) {
                    this.words.add(current);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void get6LetterWords() {
        AssetManager assetManager = this.context.getAssets();
        InputStream input;

        try {
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
            this.words.clear();
            for(String current : wordList) {
                if (current.length() == 6) {
                    this.words.add(current);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
