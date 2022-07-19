package com.webproject.mynetworth.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.webproject.mynetworth.dao.UserDataRepository;
import com.webproject.mynetworth.entities.UserData;
import com.webproject.mynetworth.exceptions.UserAlreadyPresentException;

@Component
public class UserDataIntitializerService {

	@Autowired
	private UserDataRepository userDataRepository;

	public UserData userData;

	// Check if user data already present in DB with email
	private Boolean checkUserDataAlreadyPresent(int uid) {
		Optional<UserData> existing_userData = this.userDataRepository.findById(uid);
		if (existing_userData.isPresent() == false)
			return false;
		return true;
	}

	// Add new user data to DB
	public UserData addUserDataToDB(int uid, String name) throws UserAlreadyPresentException {
		if (checkUserDataAlreadyPresent(uid) == true) {
			throw new UserAlreadyPresentException("UserData with email already present");
		}
		this.userData = new UserData(uid, name);
		
		return this.userDataRepository.save(this.userData);
	}

}
