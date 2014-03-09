package org.alex859.hangman.service;

import org.alex859.hangman.model.Hangman;
import org.alex859.hangman.model.Level;

import java.util.Collection;

/**
 * Defines the different useful methods for the game, for example the creation of a new Hangman game and the
 * check of the attempt to guess a letter
 * @author alex859
 */
public interface HangmanService {
    /**
     * Creates a new Hangman
     * @param level The chosen level
     * @return the newly created hangman
     */
    public Hangman getNewHangman(Level level);

    public Hangman getHangman(long id);

    public Collection<Hangman> getAllHangmen();

    public void saveHangman(Hangman hangman);

    public void deleteHangman(long id);


}
