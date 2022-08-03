package com.webproject.mynetworth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webproject.mynetworth.entities.InvestmentsCategories;

public interface InvestmentsCategoriesRepository extends JpaRepository<InvestmentsCategories, Integer> {

	@Query("SELECT ic FROM InvestmentsCategories ic WHERE ic.category_name = ?1")
	InvestmentsCategories findByCategory_name(String category_name);

}
