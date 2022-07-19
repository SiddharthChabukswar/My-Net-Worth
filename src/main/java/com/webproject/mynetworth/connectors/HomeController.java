package com.webproject.mynetworth.connectors;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController extends ControllerParent {

	// Home page handler - default called when home passed
	@RequestMapping(value = { "/", "/home" }, method = { RequestMethod.GET, RequestMethod.POST })
	private String homePage(HttpSession session, Principal principal, Model model) {
		int uid = this.getUidFromSession(session, principal);
		try {
			model = this.getRequiredUserDetails.homePageDetails(model, uid);
		} catch (Exception e) {
			 System.out.println(e);
		}
		return "home";
	}

}