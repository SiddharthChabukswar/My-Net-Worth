package com.webproject.mynetworth.connectors;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.webproject.mynetworth.services.GetRequiredUserDetails;
import com.webproject.mynetworth.services.GetUserIdService;
import com.webproject.mynetworth.services.SetRequiredUserDetails;
import com.webproject.mynetworth.services.UserIncomeService;
import com.webproject.mynetworth.services.UserInvestmentsService;
import com.webproject.mynetworth.services.UserNeedsService;

public class ControllerParent {

	@Autowired
	protected GetUserIdService getUserIdService;

	@Autowired
	protected GetRequiredUserDetails getRequiredUserDetails;

	@Autowired
	protected SetRequiredUserDetails setRequiredUserDetails;

	@Autowired
	protected UserIncomeService userIncomeService;
	
	@Autowired
	protected UserNeedsService userNeedsService;
	
	@Autowired
	protected UserInvestmentsService userInvestmentsService;

	// Get uid from session - called by every handler except login & signup
	protected int getUidFromSession(HttpSession session, Principal principal) {
		if (session.getAttribute("uid") == null) {
			int uid = getUserIdService.getUserIdFromEmail(principal);
			session.setAttribute("uid", uid);
		}
		int uid = (int) session.getAttribute("uid");
		return uid;
	}

}