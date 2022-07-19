package com.webproject.mynetworth.services;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.webproject.mynetworth.dao.UserDataRepository;
import com.webproject.mynetworth.entities.UserData;
import com.webproject.mynetworth.exceptions.UserDataNotFoundException;

@Component
public class SetRequiredUserDetails {

	@Autowired
	private UserDataRepository userDataRepository;

	public UserData setUserPersonalDetails(int uid, String name, Date dob, String about_me, String profile_image_name, String cover_image_name) throws UserDataNotFoundException {
		UserData added_user;
		try {
			Optional<UserData> opt_userData = this.userDataRepository.findById(uid);

			if (opt_userData.isPresent() == false)
				throw new UserDataNotFoundException("User with provided uid not found");

			UserData userData = opt_userData.get();
			if(name != "")
				userData.setName(name);
			userData.setDob(dob);
			userData.setAbout_me(about_me);
			if(profile_image_name != "")
				userData.setImage_url(profile_image_name);
			if(cover_image_name != "")
				userData.setCover_image_url(cover_image_name);

			added_user = this.userDataRepository.save(userData);
			/* System.out.println(added_user); */
		} catch (Exception e) {
			// System.out.println(e);
			throw new UserDataNotFoundException("User with provided uid not found");
		}
		return added_user;
	}

}
