package org.alex859.hangman.service.impl;

import org.alex859.hangman.model.Hangman;
import org.alex859.hangman.service.WordService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Use in-memory access to words
 * @author alex859
 */
@Service
public class WordMemoryService implements WordService{
    private static List<String> words=new ArrayList<String>();
    static{
        words.add("flower");
        words.add("monkey");
        words.add("snake");
        words.add("elephant");
        words.add("elephant");
        words.add("crocodile");
        words.add("lion");
    }
    @Override
    public String getWord() {
        Random rnd=new Random(System.currentTimeMillis());
        return words.get(rnd.nextInt(words.size()));
    }
}
