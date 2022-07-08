package com.webproject.mynetworth.connectors;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webproject.mynetworth.services.CheckAuthenticatedService;

@Controller
public class LoginController {

	// Login handler - default called when login passed
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	private String loginPage(HttpSession session) {
		// Check if already authenticated
		if (CheckAuthenticatedService.isAuthenticated())
			return "redirect:/home";
		return "login";
	}

}