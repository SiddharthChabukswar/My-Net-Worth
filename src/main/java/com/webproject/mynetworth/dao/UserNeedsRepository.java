package com.webproject.mynetworth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webproject.mynetworth.entities.UserData;
import com.webproject.mynetworth.entities.UserNeeds;

public interface UserNeedsRepository extends JpaRepository<UserNeeds, Integer> {
	
	List<UserNeeds> findByUserData(UserData userData);

}
