package org.alex859.hangman.controller;

import org.alex859.hangman.model.Hangman;
import org.alex859.hangman.model.Level;
import org.alex859.hangman.service.HangmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller to access hangman REST services
 *
 * @author alex859
 */
@Controller
@RequestMapping("/rest/")
public class HangmanRestController {
    @Autowired
    private HangmanService hangmanService;
    @RequestMapping(value = "hangman", method = RequestMethod.GET)
    public @ResponseBody Hangman getNewHangman(Level level, HttpServletResponse response) {
        //get a new hangman
        Hangman hangman = hangmanService.getNewHangman(level);
        //save to DB and get the key
        hangmanService.saveHangman(hangman);
        //set the cookie
        Cookie cookie=new Cookie("hangman", ""+hangman.getId());
        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
        return hangman;
    }

    @RequestMapping(value = "hangman/{id}", method = RequestMethod.GET)
    public @ResponseBody Hangman getHangman(@PathVariable String id) {
        Hangman hangman=null;
        //get from the DB
        hangman=hangmanService.getHangman(Long.parseLong(id));
        return hangman;
    }

    @RequestMapping(value="hangman/{id}", method = RequestMethod.POST)
    public @ResponseBody Hangman check(@PathVariable Long id, char letter) {
        Hangman hangman=hangmanService.getHangman(id);
        hangman.check(letter);
        //save the hangman state
        hangmanService.saveHangman(hangman);
        return hangman;
    }

    @RequestMapping(value = "hangman/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void deleteHangman(@PathVariable Long id) {
        hangmanService.deleteHangman(id);
    }

    @RequestMapping(value = "hangmen", method = RequestMethod.GET)
    public @ResponseBody Collection<Hangman> getAll() {
        return hangmanService.getAllHangmen();
    }


}
