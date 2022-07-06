package com.webproject.mynetworth.connectors;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ExploreController {

	// Explore page handler - default called when explore passed
	@RequestMapping(value = {"/explore"}, method = {RequestMethod.GET, RequestMethod.POST})
	private String explorePage(HttpSession session) {
		return "explore";
	}
	
}