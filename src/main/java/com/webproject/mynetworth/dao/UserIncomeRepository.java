package com.webproject.mynetworth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webproject.mynetworth.entities.UserData;
import com.webproject.mynetworth.entities.UserIncome;

public interface UserIncomeRepository extends JpaRepository<UserIncome, Integer> {
	
	List<UserIncome> findByUserData(UserData userData);

}
