package com.webproject.mynetworth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webproject.mynetworth.entities.NeedsCategories;

public interface NeedsCategoriesRepository extends JpaRepository<NeedsCategories, Integer> {

	@Query("SELECT nc FROM NeedsCategories nc WHERE nc.category_name = ?1")
	NeedsCategories findByCategory_name(String category_name);

}
