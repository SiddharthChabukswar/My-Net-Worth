package com.webproject.mynetworth.connectors;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class InvestmentsController extends ControllerParent {

	// Investments page handler - default called when investments passed
	@RequestMapping(value = { "/investments" }, method = { RequestMethod.GET, RequestMethod.POST })
	private String investmentsPage(HttpSession session, Principal principal) {
		int uid = this.getUidFromSession(session, principal);
		System.out.println(uid);
		return "investments";
	}

}