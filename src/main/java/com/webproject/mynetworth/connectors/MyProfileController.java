package com.webproject.mynetworth.connectors;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyProfileController {

	// MyProfile page handler - default called when myprofile passed
	@RequestMapping(value = {"/myprofile"}, method = {RequestMethod.GET, RequestMethod.POST})
	private String myProfilePage(HttpSession session) {
		return "myprofile";
	}
	
}