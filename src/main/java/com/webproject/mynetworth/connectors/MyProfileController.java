package com.webproject.mynetworth.connectors;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyProfileController extends GetUserIdParent {

	// MyProfile page handler - default called when myprofile passed
	@RequestMapping(value = { "/myprofile" }, method = { RequestMethod.GET, RequestMethod.POST })
	private String myProfilePage(HttpSession session, Principal principal) {
		if (session.getAttribute("uid") == null) {
			int uid = getUserIdService.getUserIdFromEmail(principal);
			session.setAttribute("uid", uid);
		}
		return "myprofile";
	}

}