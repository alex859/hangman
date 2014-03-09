package org.alex859.hangman.dao.impl;

import org.alex859.hangman.dao.HangmanDao;
import org.alex859.hangman.model.Hangman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author alex859
 */
@Repository
public class HangmanDaoJdbc implements HangmanDao {
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert insertHangman;

    private static final String GET="select * from hangman where id=?";
    private static final String GET_ALL="select * from hangman";
    private static final String INSERT="insert into hangman(word, errors, letters_needed, guessed_word, already_guessed_letters) values(?,?,?,?,?)";
    private static final String UPDATE="update hangman set word=?, errors=?, letters_needed=?, guessed_word=?, already_guessed_letters=? where id=?";
    private static final String DELETE="delete from hangman where id=?";

    @Override
    public Collection<Hangman> getHangmen() {
        return this.jdbcTemplate.query(GET_ALL, rowMapper);
    }

    @Override
    public Hangman get(long id) {
        return this.jdbcTemplate.queryForObject(GET, new Object[]{id}, rowMapper);
    }

    @Override
    public void insert(final Hangman hangman) {
        Map<String, Object> params=new HashMap<String, Object>();
        params.put("word", hangman.getWord());
        params.put("errors", hangman.getErrors());
        params.put("letters_needed", hangman.getLettersNeeded());
        params.put("guessed_word", hangman.getGuessedWord());
        params.put("already_guessed_letters", hangman.getAlreadyGuessedLetters());
        Number newId = insertHangman.executeAndReturnKey(params);
        hangman.setId(newId.longValue());
    }

    @Override
    public void update(Hangman hangman) {
        this.jdbcTemplate.update(UPDATE, hangman.getWord(), hangman.getErrors(), hangman.getLettersNeeded(), hangman.getGuessedWord(), hangman.getAlreadyGuessedLetters(), hangman.getId());
    }

    @Override
    public void delete(long id) {
        this.jdbcTemplate.update(DELETE, id);
    }

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate=new JdbcTemplate(dataSource);
        this.insertHangman=new SimpleJdbcInsert(dataSource).withTableName("hangman").usingGeneratedKeyColumns("id");
    }

    private RowMapper<Hangman> rowMapper=new RowMapper<Hangman>() {
        @Override
        public Hangman mapRow(ResultSet resultSet, int i) throws SQLException {
            Hangman hangman=new Hangman();
            hangman.setId(resultSet.getLong(1));
            hangman.setWord(resultSet.getString(2));
            hangman.setErrors(resultSet.getInt(3));
            hangman.setLettersNeeded(resultSet.getInt(4));
            hangman.setGuessedWord(resultSet.getString(5));
            hangman.setAlreadyGuessedLetters(resultSet.getString(6));
            return hangman;
        }
    };
}
