package com.webproject.mynetworth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webproject.mynetworth.entities.UserData;
import com.webproject.mynetworth.entities.UserInvestments;

public interface UserInvestmentsRepository extends JpaRepository<UserInvestments, Integer> {
	
	List<UserInvestments> findByUserData(UserData userData);

}
