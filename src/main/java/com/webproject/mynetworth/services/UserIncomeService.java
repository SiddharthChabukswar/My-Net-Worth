package com.webproject.mynetworth.services;

import java.util.Optional;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.webproject.mynetworth.dao.UserDataRepository;
import com.webproject.mynetworth.dao.UserIncomeRepository;
import com.webproject.mynetworth.entities.UserData;
import com.webproject.mynetworth.entities.UserIncome;
import com.webproject.mynetworth.exceptions.UserDataNotFoundException;
import com.webproject.mynetworth.exceptions.UserIncomeNotFoundException;
import com.webproject.mynetworth.exceptions.UserIncomeSourceAdditionFailedException;

@Component
public class UserIncomeService {

	@Autowired
	private UserIncomeRepository userIncomeRepository;
	@Autowired
	private UserDataRepository userDataRepository;

	// Fetch all income sources for User with uid
	public Model getAllIncomeSources(Model model, int uid) throws UserDataNotFoundException {
		try {
			Optional<UserData> userDataOptional = this.userDataRepository.findById(uid);
			if (userDataOptional.isPresent() == false)
				throw new UserDataNotFoundException("User with provided uid not found");

			UserData userData = userDataOptional.get();
			List<UserIncome> userIncomes = this.userIncomeRepository.findByUserData(userData);
			for (UserIncome userIncome : userIncomes)
				userIncome.setUserData(null);
			model.addAttribute("userIncomes", userIncomes);
		} catch (Exception e) {
			throw new UserDataNotFoundException("User with provided uid not found");
		}
		return model;
	}

	// Add new income source
	public UserIncome addIncomeSource(int uid, UserIncome userIncome)
			throws UserDataNotFoundException, UserIncomeSourceAdditionFailedException {
		UserIncome addedUserIncome = null;
		try {
			Optional<UserData> userDataOptional = this.userDataRepository.findById(uid);
			if (userDataOptional.isPresent() == false)
				throw new UserDataNotFoundException("User with provided uid not found");

			UserData userData = userDataOptional.get();
			Date currentDate = new java.util.Date();
			userIncome.setUserData(userData);
			userIncome.setDate_added(new Timestamp(currentDate.getTime()));
			userIncome.setLast_modified(new Timestamp(currentDate.getTime()));

			// Save new income source
			addedUserIncome = this.userIncomeRepository.save(userIncome);
			if (addedUserIncome == null)
				throw new UserIncomeSourceAdditionFailedException("Failed to add income source");

			// Update UserData income source column
			userData.setNumof_income_source(userData.getNumof_income_source() + 1);
			userData.setSumof_income_source(userData.getSumof_income_source() + addedUserIncome.getAmount());
			userData = this.userDataRepository.save(userData);

		} catch (UserDataNotFoundException e) {
			throw new UserDataNotFoundException("User with provided uid not found");
		} catch (UserIncomeSourceAdditionFailedException e) {
			throw new UserIncomeSourceAdditionFailedException("Failed to add income source");
		} catch (Exception e) {
			throw e;
		}
		return addedUserIncome;
	}

	// Update existing income source
	public UserIncome updateIncomeSource(int uid, Integer income_id, String source_name, Integer amount,
			String description)
					throws UserDataNotFoundException, UserIncomeNotFoundException, UserIncomeSourceAdditionFailedException {
		try {
			// System.out.println(uid + income_id + source_name + amount + description);
			Optional<UserIncome> userIncomeOptional = this.userIncomeRepository.findById(income_id);
			if (userIncomeOptional.isPresent() == false)
				throw new UserIncomeNotFoundException("User income with provided income_id not found");

			UserIncome userIncome = userIncomeOptional.get();
			Date currentDate = new java.util.Date();
			uid = userIncome.getUserData().getUid();
			int prev_amount = userIncome.getAmount();
			userIncome.setSource_name(source_name);
			userIncome.setAmount(amount);
			userIncome.setDescription(description);
			userIncome.setLast_modified(new Timestamp(currentDate.getTime()));

			Optional<UserData> userDataOptional = this.userDataRepository.findById(uid);
			if (userDataOptional.isPresent() == false)
				throw new UserDataNotFoundException("User with provided uid not found");

			UserData userData = userDataOptional.get();
			userData.setSumof_income_source(userData.getSumof_income_source() - prev_amount + userIncome.getAmount());
			userData = this.userDataRepository.save(userData);

			userIncome.setUserData(userData);
			UserIncome updatedUserIncome = this.userIncomeRepository.save(userIncome);
			if (updatedUserIncome == null)
				throw new UserIncomeSourceAdditionFailedException("Failed to update income source");

		} catch (UserIncomeNotFoundException e) {
			throw new UserIncomeNotFoundException("User income with provided income_id not found");
		} catch (UserDataNotFoundException e) {
			throw new UserDataNotFoundException("User with provided uid not found");
		} catch (UserIncomeSourceAdditionFailedException e) {
			throw new UserIncomeSourceAdditionFailedException("Failed to update income source");
		} catch (Exception e) {
			throw e;
		}
		return null;
	}

	// Delete a list of user income sources
	public UserIncome deleteIncomeSources(int uid, Integer[] income_id_list)
			throws UserDataNotFoundException, UserIncomeNotFoundException, Exception {
		try {
			Integer sum_to_delete = 0;
			Integer num_to_delete = 0;
			try {
				for(Integer income_id: income_id_list) {
					// System.out.println(income_id);

					Optional<UserIncome> userIncomeOptional = this.userIncomeRepository.findById(income_id);
					if(userIncomeOptional.isPresent() == false)
						throw new UserIncomeNotFoundException("User income with provided income_id not found");

					UserIncome userIncome = userIncomeOptional.get();
					sum_to_delete += userIncome.getAmount();
					num_to_delete += 1;
					this.userIncomeRepository.delete(userIncome);
				}
			} catch (UserIncomeNotFoundException e) {
				// throw new UserIncomeNotFoundException("User income with provided income_id not found");
			} catch (Exception e) {
				// System.out.println(e);
			}

			Optional<UserData> userDataOptional = this.userDataRepository.findById(uid);
			if(userDataOptional.isPresent() == false)
				throw new UserDataNotFoundException("User with provided uid not found");

			UserData userData = userDataOptional.get();
			userData.setSumof_income_source(userData.getSumof_income_source() - sum_to_delete);
			userData.setNumof_income_source(userData.getNumof_income_source() - num_to_delete);
			userData = this.userDataRepository.save(userData);
			if(userData == null)
				throw new Exception("Failed to commit changes on UserData");

		} catch (UserDataNotFoundException e) {
			throw new UserDataNotFoundException("User with provided uid not found");
		} catch (Exception e) {
			throw e;
		}
		return null;
	}
}
