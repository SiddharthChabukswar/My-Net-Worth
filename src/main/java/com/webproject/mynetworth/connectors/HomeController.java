package com.webproject.mynetworth.connectors;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController extends GetUserIdParent {

	// Home page handler - default called when home passed
	@RequestMapping(value = { "/", "/home" }, method = { RequestMethod.GET, RequestMethod.POST })
	private String homePage(HttpSession session, Principal principal) {
		if (session.getAttribute("uid") == null) {
			int uid = getUserIdService.getUserIdFromEmail(principal);
			session.setAttribute("uid", uid);
		}
		// int uid = (int) session.getAttribute("uid");

		return "home";
	}

}