package com.webproject.mynetworth.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.webproject.mynetworth.dao.UserDataRepository;
import com.webproject.mynetworth.dao.UserRepository;
import com.webproject.mynetworth.entities.User;
import com.webproject.mynetworth.entities.UserData;
import com.webproject.mynetworth.exceptions.UserDataNotFoundException;

@Component
public class GetRequiredUserDetails {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserDataRepository userDataRepository;

	// Convert integer numbers to string millionth format
	private String convertIntMoneyToString(Integer amount) {
		if (amount == 0)
			return "0";
		String amount_string = "";
		int count = 0;
		while (amount != 0) {
			if (count % 3 == 0)
				amount_string = ',' + amount_string;
			amount_string = (char) (amount % 10 + '0') + amount_string;
			amount /= 10;
			count++;
		}
		return amount_string.substring(0, amount_string.length() - 1);
	}

	// Return home page details in model
	public Model homePageDetails(Model model, int uid) throws UserDataNotFoundException {
		try {
			Optional<User> opt_user = this.userRepository.findById(uid);
			Optional<UserData> opt_userData = this.userDataRepository.findById(uid);

			if (opt_user.isPresent() == false)
				throw new UserDataNotFoundException("User with provided uid not found");

			if (opt_userData.isPresent() == false)
				throw new UserDataNotFoundException("User with provided uid not found");

			User user = opt_user.get();
			UserData userData = opt_userData.get();

			model.addAttribute("user_name", userData.getName());
			model.addAttribute("email", "@" + user.getEmail().substring(0, user.getEmail().indexOf('@')));
			model.addAttribute("about_me", userData.getAbout_me());
			model.addAttribute("image_url", userData.getImage_url());
			model.addAttribute("cover_image_url", userData.getCover_image_url());
			model.addAttribute("total_net_worth", this.convertIntMoneyToString(userData.getTotal_net_worth()));
			model.addAttribute("numof_income_source", this.convertIntMoneyToString(userData.getNumof_income_source()));
			model.addAttribute("sumof_income_source", this.convertIntMoneyToString(userData.getSumof_income_source()));
			model.addAttribute("numof_needs", this.convertIntMoneyToString(userData.getNumof_needs()));
			model.addAttribute("sumof_needs", this.convertIntMoneyToString(userData.getSumof_needs()));
			model.addAttribute("numof_assets", this.convertIntMoneyToString(userData.getNumof_assets()));
			model.addAttribute("sumof_assets", this.convertIntMoneyToString(userData.getSumof_assets()));
		} catch (Exception e) {
			// System.out.println(e);
			throw new UserDataNotFoundException("User with provided uid not found");
		}
		return model;
	}

	// Return myprofile page details in model
	public Model myProfilePageDetails(Model model, int uid) throws UserDataNotFoundException {
		try {
			Optional<UserData> opt_userData = this.userDataRepository.findById(uid);

			if (opt_userData.isPresent() == false)
				throw new UserDataNotFoundException("User with provided uid not found");

			UserData userData = opt_userData.get();

			model.addAttribute("user_name", userData.getName());
			model.addAttribute("dob", userData.getDob());
			model.addAttribute("about_me", userData.getAbout_me());
			model.addAttribute("image_url", userData.getImage_url());
			model.addAttribute("cover_image_url", userData.getCover_image_url());
		} catch (Exception e) {
			// System.out.println(e);
			throw new UserDataNotFoundException("User with provided uid not found");
		}
		return model;
	}

}