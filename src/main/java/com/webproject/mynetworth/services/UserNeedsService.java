package com.webproject.mynetworth.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.webproject.mynetworth.dao.NeedsCategoriesRepository;
import com.webproject.mynetworth.dao.UserDataRepository;
import com.webproject.mynetworth.dao.UserNeedsRepository;
import com.webproject.mynetworth.entities.NeedsCategories;
import com.webproject.mynetworth.entities.UserData;
import com.webproject.mynetworth.entities.UserNeeds;
import com.webproject.mynetworth.exceptions.NeedsCategoryNotFoundException;
import com.webproject.mynetworth.exceptions.UserDataNotFoundException;
import com.webproject.mynetworth.exceptions.UserNeedAdditionFailedException;
import com.webproject.mynetworth.exceptions.UserNeedNotFoundException;

@Component
public class UserNeedsService {

	@Autowired
	private UserDataRepository userDataRepository;

	@Autowired
	private UserNeedsRepository userNeedsRepository;

	@Autowired
	private NeedsCategoriesRepository needsCategoriesRepository;

	// Fetch all needs categories
	public Model getAllNeedsCategories(Model model) throws Exception {
		try {
			List<NeedsCategories> needsCategories = this.needsCategoriesRepository.findAll();
			model.addAttribute("needsCategories", needsCategories);
		} catch (Exception e) {
			throw e;
		}
		return model;
	}

	// Fetch all Needs for User with uid
	public Model getAllUserNeeds(Model model, int uid) throws UserDataNotFoundException {
		try {
			Optional<UserData> userDataOptional = this.userDataRepository.findById(uid);
			if (userDataOptional.isPresent() == false)
				throw new UserDataNotFoundException("User with provided uid not found");

			UserData userData = userDataOptional.get();
			List<UserNeeds> userNeeds = this.userNeedsRepository.findByUserData(userData);
			for (UserNeeds userNeed : userNeeds) {
				userNeed.setUserData(null);
			}
			model.addAttribute("userNeeds", userNeeds);
		} catch (Exception e) {
			throw new UserDataNotFoundException("User with provided uid not found");
		}
		return model;
	}

	// Add new user need
	public UserNeeds addUserNeed(int uid, UserNeeds userNeed)
			throws UserDataNotFoundException, UserNeedAdditionFailedException {
		UserNeeds addedUserNeed;
		try {
			Optional<UserData> userDataOptional = this.userDataRepository.findById(uid);
			if (userDataOptional.isPresent() == false)
				throw new UserDataNotFoundException("User with provided uid not found");

			UserData userData = userDataOptional.get();
			Date currentDate = new java.util.Date();
			userNeed.setUserData(userData);
			userNeed.setDate_added(new Timestamp(currentDate.getTime()));
			userNeed.setLast_modified(new Timestamp(currentDate.getTime()));

			// Save new user need
			addedUserNeed = this.userNeedsRepository.save(userNeed);
			if (addedUserNeed == null)
				throw new UserNeedAdditionFailedException("Failed to add user need");

			// Update UserData user need column
			userData.setNumof_needs(userData.getNumof_needs() + 1);
			userData.setSumof_needs(userData.getSumof_needs() + addedUserNeed.getAmount());
			userData = this.userDataRepository.save(userData);

		} catch (UserDataNotFoundException e) {
			throw new UserDataNotFoundException("User with provided uid not found");
		} catch (UserNeedAdditionFailedException e) {
			throw new UserNeedAdditionFailedException("Failed to add user need");
		} catch (Exception e) {
			throw e;
		}
		return addedUserNeed;
	}
	
	// Update an existing user need
	public UserNeeds updateUserNeed(int uid, Integer user_need_id, String need_name, Integer amount, Integer need_id, String description)
			throws NeedsCategoryNotFoundException, UserNeedNotFoundException, UserDataNotFoundException, UserNeedAdditionFailedException {
		UserNeeds updatedUserNeed = null;
		try {
			Optional<NeedsCategories> needsCategoriesOptional = this.needsCategoriesRepository.findById(need_id);
			if(needsCategoriesOptional.isPresent() == false)
				throw new NeedsCategoryNotFoundException("Need category with provided need_id not found");
			
			NeedsCategories needCategory = needsCategoriesOptional.get();
			
			Optional<UserNeeds> userNeedOptional = this.userNeedsRepository.findById(user_need_id);
			if(userNeedOptional.isPresent() == false)
				throw new UserNeedNotFoundException("User need with provided user_need_id not found");
			
			UserNeeds userNeed = userNeedOptional.get();
			Date currentDate = new java.util.Date();
			int prev_amount = userNeed.getAmount();
			userNeed.setNeed_name(need_name);
			userNeed.setAmount(amount);
			userNeed.setNeed_id(needCategory);
			userNeed.setDescription(description);
			userNeed.setLast_modified(new Timestamp(currentDate.getTime()));
			
			Optional<UserData> userDataOptional = this.userDataRepository.findById(uid);
			if (userDataOptional.isPresent() == false)
				throw new UserDataNotFoundException("User with provided uid not found");

			UserData userData = userDataOptional.get();
			userData.setSumof_needs(userData.getSumof_needs() - prev_amount + userNeed.getAmount());
			userData = this.userDataRepository.save(userData);
			
			userNeed.setUserData(userData);
			updatedUserNeed = this.userNeedsRepository.save(userNeed);
			if (updatedUserNeed == null)
				throw new UserNeedAdditionFailedException("Failed to update need");
			
		} catch (NeedsCategoryNotFoundException e) {
			throw new NeedsCategoryNotFoundException("Need category with provided need_id not found");
		} catch (UserNeedNotFoundException e) {
			throw new UserNeedNotFoundException("User need with provided user_need_id not found");
		} catch (UserDataNotFoundException e) {
			throw new UserDataNotFoundException("User with provided uid not found");
		} catch (UserNeedAdditionFailedException e) {
			throw new UserNeedAdditionFailedException("Failed to update need");
		} catch (Exception e) {
			throw e;
		}
		return updatedUserNeed;
	}
	
	// Delete a list of user needs
	public UserNeeds deleteUserNeeds(int uid, Integer[] delete_user_need_id_list)
			throws UserDataNotFoundException, UserNeedNotFoundException, Exception {
		try {
			Integer sum_to_delete = 0;
			Integer num_to_delete = 0;
			try {
				for (Integer user_need_id : delete_user_need_id_list) {
					// System.out.println(income_id);

					Optional<UserNeeds> userNeedOptional = this.userNeedsRepository.findById(user_need_id);
					if (userNeedOptional.isPresent() == false)
						throw new UserNeedNotFoundException("User need with provided user_need_id not found");

					UserNeeds userNeed = userNeedOptional.get();
					sum_to_delete += userNeed.getAmount();
					num_to_delete += 1;
					this.userNeedsRepository.delete(userNeed);
				}
			} catch (UserNeedNotFoundException e) {
				// throw new UserNeedNotFoundException("User need with provided user_need_id not found");
			} catch (Exception e) {
				// System.out.println(e);
			}

			Optional<UserData> userDataOptional = this.userDataRepository.findById(uid);
			if (userDataOptional.isPresent() == false)
				throw new UserDataNotFoundException("User with provided uid not found");

			UserData userData = userDataOptional.get();
			userData.setSumof_needs(userData.getSumof_needs() - sum_to_delete);
			userData.setNumof_needs(userData.getNumof_needs() - num_to_delete);
			userData = this.userDataRepository.save(userData);
			if (userData == null)
				throw new Exception("Failed to commit changes on UserData");

		} catch (UserDataNotFoundException e) {
			throw new UserDataNotFoundException("User with provided uid not found");
		} catch (Exception e) {
			throw e;
		}
		return null;
	}
}
