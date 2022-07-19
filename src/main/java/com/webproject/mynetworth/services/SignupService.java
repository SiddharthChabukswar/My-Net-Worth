package com.webproject.mynetworth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.webproject.mynetworth.dao.UserRepository;
import com.webproject.mynetworth.entities.User;
import com.webproject.mynetworth.exceptions.UserAlreadyPresentException;

@Component
public class SignupService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	UserDataIntitializerService userDataIntitializerService;

	private User user;

	// Check if user already present in DB with email
	private Boolean checkUserAlreadyPresent() {
		User existing_user = this.userRepository.findByEmail(this.user.getEmail());
		if (existing_user == null)
			return false;
		return true;
	}

	// Generate password hash value to store in DB
	private void generateHashPassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(this.user.getPassword());
		this.user.setPassword(hashedPassword);
	}

	// Add new user to DB
	public User addUserToDB(User user, String name) throws UserAlreadyPresentException {
		this.user = user;
		if (checkUserAlreadyPresent() == true) {
			throw new UserAlreadyPresentException("User with email already present");
		}
		this.generateHashPassword();
		User added_user = this.userRepository.save(this.user);
		// Add newly added user to user data repo
		try {
			this.userDataIntitializerService.addUserDataToDB(added_user.getUid(), name);
		} catch (Exception e) {
			// System.out.println(e);
			this.userRepository.delete(added_user);
			throw new UserAlreadyPresentException("UserData with email already present");
		}
		return added_user;
	}

}