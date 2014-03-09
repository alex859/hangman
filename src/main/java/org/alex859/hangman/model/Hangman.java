package org.alex859.hangman.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * This class models a Hangman game
 *
 * @author alex859
 */
public class Hangman implements Serializable {
    /**
     * The id of the hangman
     */
    private long id;
    /**
     * The word to be guessed
     */
    private char[] word;
    /**
     * Total number of errors
     */
    private int errors = 0;
    /**
     * Maximum number of errors, aka lives
     */
    private final int LIVES = 5;
    /**
     * Number of letters needed to solve the game
     */
    private int lettersNeeded;
    /**
     * Word guessed
     */
    private char[] guessedWord;
    /**
     * Result of last guess
     */
    private CheckResult lastResult;
    /**
     * The level of the game
     */
    private Level level;
    /**
     * Set of already guessed letters
     */
    private Set<Character> alreadyGuessedLetters=new HashSet<Character>();

    /**
     * Creates a new game with the specified word to guess and level
     *
     * @param word  The word to guess
     * @param level The difficult level
     */
    public Hangman(String word, Level level) {
        this.setWord(word);
        this.level = level;
        initGuessedWord();
    }

    public Hangman(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGuessedWord() {
        return new String(this.guessedWord);
    }

    public void setGuessedWord(String guessedWord) {
        if(guessedWord!=null){
            this.guessedWord = guessedWord.toCharArray();
        }
    }

    public CheckResult getLastResult() {
        return lastResult;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public int getLives() {
        return LIVES - this.errors;
    }

    public int getLettersNeeded() {
        return lettersNeeded;
    }

    public void setLettersNeeded(int lettersNeeded) {
        this.lettersNeeded = lettersNeeded;
    }

    public void setWord(String word) {
        this.word = word.toCharArray();
    }

    public String getWord() {
        return new String(this.word);
    }

    /**
     * @return The concatenation of the already guessed words for DB persistence purpose
     */
    public String getAlreadyGuessedLetters() {
        String result="";
        for(Character c:this.alreadyGuessedLetters){
            result+=c;
        }
        return result;
    }

    public void setAlreadyGuessedLetters(String alreadyGuessedLetters) {
        if(alreadyGuessedLetters!=null){
            for(int i=0;i<alreadyGuessedLetters.length();i++){
                this.alreadyGuessedLetters.add(alreadyGuessedLetters.charAt(i));
            }
        }
    }

    /**
     * Initialize the guessed word with a number of displayed letters depending on the level
     */
    private void initGuessedWord() {
        int N = word.length;
        guessedWord = new char[N];
        Arrays.fill(guessedWord, 0, N, ' ');
        //compute the total starting letters
        int numLetters = (int) (N * level.getPercentage());
        lettersNeeded = N;
        //randomly choose letters to be displayed
        //have to keep track of already shown letters
        Set<Integer> alreadyUsedIndexes = new HashSet<Integer>();
        Random rnd = new Random(System.currentTimeMillis());
        for (int i = 0; i < numLetters; i++) {
            int index = rnd.nextInt(N);
            while (alreadyUsedIndexes.contains(index)) {
                index = rnd.nextInt(N);
            }
            alreadyUsedIndexes.add(index);
            char letterToDisplay = word[index];
            alreadyGuessedLetters.add(Character.toLowerCase(letterToDisplay));
            //we have to display all letterToDisplay
            for (int j = 0; j < N; j++) {
                if (word[j] == letterToDisplay) {
                    //display the letter
                    guessedWord[j] = word[j];
                    lettersNeeded--;
                }
            }

        }
    }

    /**
     * Checks if letter is contained or not in the word
     *
     * @param s The letter
     * @return Number of remaining lives
     */
    public void check(char s) {
        s = Character.toLowerCase(s);
        //firstly check if the letter has already been guessed
        if(alreadyGuessedLetters.contains(s)){
            lastResult = CheckResult.ALREADY_GUESSED;
        }else{
            boolean found = false;
            //have to iterate over the full string to find all the occurrencies of the letter and copy them into the guessed word
            for (int i = 0; i < word.length; i++) {
                if (s == word[i]) {
                    found = true;
                    guessedWord[i] = s;
                    lettersNeeded--;
                    lastResult = CheckResult.GUESSED;
                }
            }
            if (!found) {
                lastResult = CheckResult.NOT_GUESSED;
                errors++;
            }else{
                alreadyGuessedLetters.add(s);
            }
        }
    }

    @Override
    public String toString() {
        return "Hangman{" +
                "id=" + id +
                ", word=" + Arrays.toString(word) +
                ", errors=" + errors +
                ", lettersNeeded=" + lettersNeeded +
                ", guessedWord=" + Arrays.toString(guessedWord) +
                ", alreadyGuessedLetters=" + alreadyGuessedLetters +
                '}';
    }
}
