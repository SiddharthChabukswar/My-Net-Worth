package com.webproject.mynetworth.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.webproject.mynetworth.dao.InvestmentsCategoriesRepository;
import com.webproject.mynetworth.dao.UserDataRepository;
import com.webproject.mynetworth.dao.UserInvestmentsRepository;
import com.webproject.mynetworth.entities.InvestmentsCategories;
import com.webproject.mynetworth.entities.UserData;
import com.webproject.mynetworth.entities.UserInvestments;
import com.webproject.mynetworth.exceptions.InvestmentsCategoryNotFoundException;
import com.webproject.mynetworth.exceptions.UserDataNotFoundException;
import com.webproject.mynetworth.exceptions.UserInvestmentAdditionFailedException;
import com.webproject.mynetworth.exceptions.UserInvestmentNotFoundException;

@Component
public class UserInvestmentsService {

	@Autowired
	private UserDataRepository userDataRepository;

	@Autowired
	private UserInvestmentsRepository userInvestmentsRepository;
	
	@Autowired
	private InvestmentsCategoriesRepository investmentsCategoriesRepository;
	
	// Fetch all Investments categories
	public Model getAllInvestmentsCategories(Model model) throws Exception {
		try {
			List<InvestmentsCategories> investmentsCategories = this.investmentsCategoriesRepository.findAll();
			model.addAttribute("investmentsCategories", investmentsCategories);
		} catch (Exception e) {
			throw e;
		}
		return model;
	}

	// Fetch all Investments for User with uid
	public Model getAllUserInvestments(Model model, int uid) throws UserDataNotFoundException {
		try {
			Optional<UserData> userDataOptional = this.userDataRepository.findById(uid);
			if (userDataOptional.isPresent() == false)
				throw new UserDataNotFoundException("User with provided uid not found");

			UserData userData = userDataOptional.get();
			List<UserInvestments> userInvestments = this.userInvestmentsRepository.findByUserData(userData);
			for (UserInvestments userInvestment : userInvestments) {
				userInvestment.setUserData(null);
			}
			model.addAttribute("userInvestments", userInvestments);
		} catch (Exception e) {
			throw new UserDataNotFoundException("User with provided uid not found");
		}
		return model;
	}
	
	// Add new user investment
	public UserInvestments addUserInvestment(int uid, UserInvestments userInvestment)
			throws UserDataNotFoundException, UserInvestmentAdditionFailedException {
		UserInvestments addedUserInvestment;
		try {
			Optional<UserData> userDataOptional = this.userDataRepository.findById(uid);
			if (userDataOptional.isPresent() == false)
				throw new UserDataNotFoundException("User with provided uid not found");

			UserData userData = userDataOptional.get();
			Date currentDate = new java.util.Date();
			userInvestment.setUserData(userData);
			userInvestment.setDate_added(new Timestamp(currentDate.getTime()));
			userInvestment.setLast_modified(new Timestamp(currentDate.getTime()));

			// Save new user investment
			addedUserInvestment = this.userInvestmentsRepository.save(userInvestment);
			if (addedUserInvestment == null)
				throw new UserInvestmentAdditionFailedException("Failed to add user investment");

			// Update UserData user investment column
			userData.setNumof_assets(userData.getNumof_assets() + 1);
			userData.setSumof_assets(userData.getSumof_assets() + addedUserInvestment.getAmount());
			userData = this.userDataRepository.save(userData);

		} catch (UserDataNotFoundException e) {
			throw new UserDataNotFoundException("User with provided uid not found");
		} catch (UserInvestmentAdditionFailedException e) {
			throw new UserInvestmentAdditionFailedException("Failed to add user investment");
		} catch (Exception e) {
			throw e;
		}
		return addedUserInvestment;
	}
	
	// Update an existing user investment
	public UserInvestments updateUserInvestment(int uid, Integer user_investment_id, String investment_name, Integer amount, Integer investment_id, String description)
			throws InvestmentsCategoryNotFoundException, UserInvestmentNotFoundException, UserDataNotFoundException, UserInvestmentAdditionFailedException {
		UserInvestments updatedUserInvestment = null;
		try {
			Optional<InvestmentsCategories> investmentsCategoriesOptional = this.investmentsCategoriesRepository.findById(investment_id);
			if(investmentsCategoriesOptional.isPresent() == false)
				throw new InvestmentsCategoryNotFoundException("Investment category with provided investment_id not found");
			
			InvestmentsCategories investmentCategory = investmentsCategoriesOptional.get();
			
			Optional<UserInvestments> userInvestmentOptional = this.userInvestmentsRepository.findById(user_investment_id);
			if(userInvestmentOptional.isPresent() == false)
				throw new UserInvestmentNotFoundException("User investment with provided user_investment_id not found");
			
			UserInvestments userInvestment = userInvestmentOptional.get();
			Date currentDate = new java.util.Date();
			int prev_amount = userInvestment.getAmount();
			userInvestment.setInvestment_name(investment_name);
			userInvestment.setAmount(amount);
			userInvestment.setInvestment_id(investmentCategory);
			userInvestment.setDescription(description);
			userInvestment.setLast_modified(new Timestamp(currentDate.getTime()));
			
			Optional<UserData> userDataOptional = this.userDataRepository.findById(uid);
			if (userDataOptional.isPresent() == false)
				throw new UserDataNotFoundException("User with provided uid not found");

			UserData userData = userDataOptional.get();
			userData.setSumof_assets(userData.getSumof_assets() - prev_amount + userInvestment.getAmount());
			userData = this.userDataRepository.save(userData);
			
			userInvestment.setUserData(userData);
			updatedUserInvestment = this.userInvestmentsRepository.save(userInvestment);
			if (updatedUserInvestment == null)
				throw new UserInvestmentAdditionFailedException("Failed to update user investment");
			
		} catch (InvestmentsCategoryNotFoundException e) {
			throw new InvestmentsCategoryNotFoundException("Investment category with provided investment_id not found");
		} catch (UserInvestmentNotFoundException e) {
			throw new UserInvestmentNotFoundException("User investment with provided user_investment_id not found");
		} catch (UserDataNotFoundException e) {
			throw new UserDataNotFoundException("User with provided uid not found");
		} catch (UserInvestmentAdditionFailedException e) {
			throw new UserInvestmentAdditionFailedException("Failed to update user investment");
		} catch (Exception e) {
			throw e;
		}
		return updatedUserInvestment;
	}
	
	// Delete a list of user investments
	public UserInvestments deleteUserInvestments(int uid, Integer[] delete_user_investment_id_list)
			throws UserDataNotFoundException, UserInvestmentNotFoundException, Exception {
		try {
			Integer sum_to_delete = 0;
			Integer num_to_delete = 0;
			try {
				for (Integer user_investment_id : delete_user_investment_id_list) {
					// System.out.println(user_investment_id);

					Optional<UserInvestments> userInvestmentOptional = this.userInvestmentsRepository.findById(user_investment_id);
					if (userInvestmentOptional.isPresent() == false)
						throw new UserInvestmentNotFoundException("User investment with provided user_investment_id not found");

					UserInvestments userInvestment = userInvestmentOptional.get();
					sum_to_delete += userInvestment.getAmount();
					num_to_delete += 1;
					this.userInvestmentsRepository.delete(userInvestment);
				}
			} catch (UserInvestmentNotFoundException e) {
				// throw new UserInvestmentNotFoundException("User investment with provided user_investment_id not found");
			} catch (Exception e) {
				// System.out.println(e);
			}

			Optional<UserData> userDataOptional = this.userDataRepository.findById(uid);
			if (userDataOptional.isPresent() == false)
				throw new UserDataNotFoundException("User with provided uid not found");

			UserData userData = userDataOptional.get();
			userData.setSumof_assets(userData.getSumof_assets() - sum_to_delete);
			userData.setNumof_assets(userData.getNumof_assets() - num_to_delete);
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
