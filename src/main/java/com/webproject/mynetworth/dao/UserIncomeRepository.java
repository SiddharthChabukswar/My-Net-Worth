package com.webproject.mynetworth.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webproject.mynetworth.entities.UserIncome;

public interface UserIncomeRepository extends JpaRepository<UserIncome, Integer> {
	
}
