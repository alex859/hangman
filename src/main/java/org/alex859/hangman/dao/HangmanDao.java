package org.alex859.hangman.dao;

import org.alex859.hangman.model.Hangman;

import java.util.Collection;

/**
 * Data access methods for Hangman objects
 * @author alex859
 */
public interface HangmanDao {
    public Hangman get(long id);
    public Collection<Hangman> getHangmen();
    public void insert(Hangman hangman);
    public void update(Hangman hangman);
    public void delete(long hangman);
}
