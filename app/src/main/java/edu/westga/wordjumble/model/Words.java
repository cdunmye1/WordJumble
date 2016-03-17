package edu.westga.wordjumble.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Chris on 3/16/2016.
 */
public class Words {

    private ArrayList<String> words;

    public Words() {
        this.words = new ArrayList<String>();
        this.words.add("Stair");
        this.words.add("Pills");
        this.words.add("Bread");
        this.words.add("Fryer");
        this.words.add("Knives");
        this.words.add("bottle");
    }

    public String getWord() {
        Random random = new Random(this.words.size());
        return words.get(random.nextInt());
    }

}
