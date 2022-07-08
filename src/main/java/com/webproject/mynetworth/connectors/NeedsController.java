package com.webproject.mynetworth.connectors;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NeedsController extends GetUserIdParent {

	// Needs page handler - default called when needs passed
	@RequestMapping(value = { "/needs" }, method = { RequestMethod.GET, RequestMethod.POST })
	private String needsPage(HttpSession session, Principal principal) {
		if (session.getAttribute("uid") == null) {
			int uid = getUserIdService.getUserIdFromEmail(principal);
			session.setAttribute("uid", uid);
		}
		return "needs";
	}

}