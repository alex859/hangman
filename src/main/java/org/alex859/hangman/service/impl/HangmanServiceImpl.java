package org.alex859.hangman.service.impl;

import org.alex859.hangman.dao.HangmanDao;
import org.alex859.hangman.model.Hangman;
import org.alex859.hangman.model.Level;
import org.alex859.hangman.service.HangmanService;
import org.alex859.hangman.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Implements the {@link org.alex859.hangman.service.HangmanService} interface using a simple in-memory store
 * to store the words
 * @author alex859
 */
@Service
public class HangmanServiceImpl implements HangmanService {
    @Autowired
    private HangmanDao hangmanDao;
    @Autowired
    private WordService wordService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Hangman getNewHangman(Level level) {
        Random rnd=new Random(System.currentTimeMillis());
        return new Hangman(wordService.getWord(),level);
    }

    @Override
    public Hangman getHangman(long id) {
        return hangmanDao.get(id);
    }

    @Override
    public Collection<Hangman> getAllHangmen() {
        return hangmanDao.getHangmen();
    }

    @Override
    public void saveHangman(Hangman hangman) {
        if(hangman.getId()!=0){
            hangmanDao.update(hangman);
        }else{
            hangmanDao.insert(hangman);
        }
    }

    @Override
    public void deleteHangman(long id) {
        hangmanDao.delete(id);
    }
}
