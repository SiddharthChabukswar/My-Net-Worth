package com.webproject.mynetworth.connectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.webproject.mynetworth.entities.User;
import com.webproject.mynetworth.services.CheckAuthenticatedService;
import com.webproject.mynetworth.services.SignupService;

@Controller
public class SignupController {

	@Autowired
	private SignupService signupService;

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	private String signupPage(Model model) {
		// Check if already authenticated
		if (CheckAuthenticatedService.isAuthenticated())
			return "redirect:/home";
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	private String registerUser(
			@RequestParam(value = "name", required = true) String name,
			User user,
			Model model) {
		// Check if already authenticated
		if (CheckAuthenticatedService.isAuthenticated())
			return "redirect:/home";
		model.addAttribute("error", null);
		String result_page = "";
		try {
			this.signupService.addUserToDB(user, name);
			// System.out.println(added_user);
			result_page = "signup_successful";
		} catch (Exception e) {
			// System.out.println(e);
			model.addAttribute("error", true);
			result_page = "signup";
		}
		return result_page;
	}

}