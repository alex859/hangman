package org.alex859.hangman.model;

import java.io.Serializable;

/**
 * The possible levels of the game
 * @author alex859
 */
public enum Level implements Serializable{
    EASY(0.5f),
    MEDIUM(0.3f),
    DIFFICULT(0.f);

    /**
     * Percentage of total letters to be displayed when the game starts (0.5 means 50%)
     */
    private float percentage;

    private Level(float percentage){
        this.percentage=percentage;
    }

    public float getPercentage() {
        return percentage;
    }
}
