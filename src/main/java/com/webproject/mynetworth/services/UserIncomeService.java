package com.webproject.mynetworth.services;

import java.util.Optional;
import java.util.Date;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.webproject.mynetworth.dao.UserDataRepository;
import com.webproject.mynetworth.dao.UserIncomeRepository;
import com.webproject.mynetworth.entities.UserData;
import com.webproject.mynetworth.entities.UserIncome;
import com.webproject.mynetworth.exceptions.UserDataNotFoundException;
import com.webproject.mynetworth.exceptions.UserIncomeSourceAdditionFailedException;

@Component
public class UserIncomeService {

	@Autowired
	private UserIncomeRepository userIncomeRepository;
	@Autowired
	private UserDataRepository userDataRepository;

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
}
