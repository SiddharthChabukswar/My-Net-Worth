package com.webproject.mynetworth.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webproject.mynetworth.entities.UserData;

public interface UserDataRepository extends JpaRepository<UserData, Integer> {

}
