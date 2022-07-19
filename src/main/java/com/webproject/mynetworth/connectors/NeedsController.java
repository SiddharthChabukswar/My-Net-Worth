package com.webproject.mynetworth.connectors;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NeedsController extends ControllerParent {

	// Needs page handler - default called when needs passed
	@RequestMapping(value = { "/needs" }, method = { RequestMethod.GET, RequestMethod.POST })
	private String needsPage(HttpSession session, Principal principal) {
		int uid = this.getUidFromSession(session, principal);
		System.out.println(uid);
		return "needs";
	}

}