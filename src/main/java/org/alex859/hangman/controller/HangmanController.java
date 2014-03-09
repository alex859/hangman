package org.alex859.hangman.controller;

import org.alex859.hangman.model.Level;
import org.alex859.hangman.service.HangmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Controller used mainly to manage routing to web pages
 */
@Controller
@RequestMapping("/")
public class HangmanController {

	@RequestMapping(method = RequestMethod.GET)
	public String hangman(ModelMap model) {
        model.addAttribute("levels", Level.values());
		return "hangman";
	}

    //TODO Access to this page should be secured
    @RequestMapping(value = "management", method = RequestMethod.GET)
    public String management() {
        return "management";
    }
}