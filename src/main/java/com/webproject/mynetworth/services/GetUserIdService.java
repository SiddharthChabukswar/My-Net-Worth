package com.webproject.mynetworth.services;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.webproject.mynetworth.dao.UserRepository;
import com.webproject.mynetworth.entities.User;

@Component
public class GetUserIdService {

	@Autowired
	private UserRepository userRepository;

	public int getUserIdFromEmail(Principal principal) {
		String email = principal.getName();
		User user = this.userRepository.findByEmail(email);
		// System.out.println(user);
		return user.getUid();
	}

}