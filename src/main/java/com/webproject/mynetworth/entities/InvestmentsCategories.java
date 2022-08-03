package com.webproject.mynetworth.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "InvestmentsCategories")
public class InvestmentsCategories {

	@Id
	private Integer investment_id;

	@Column(nullable = false, unique = true, length = 100)
	private String category_name;

	public InvestmentsCategories() {
		super();
	}

	public InvestmentsCategories(Integer investment_id, String category_name) {
		super();
		this.investment_id = investment_id;
		this.category_name = category_name;
	}

	public Integer getInvestment_id() {
		return investment_id;
	}

	public void setInvestment_id(Integer investment_id) {
		this.investment_id = investment_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	@Override
	public String toString() {
		return "InvestmentsCategories [investment_id=" + investment_id + ", category_name=" + category_name + "]";
	}

}
