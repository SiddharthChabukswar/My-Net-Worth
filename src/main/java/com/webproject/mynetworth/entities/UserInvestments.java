package com.webproject.mynetworth.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "UserInvestments")
public class UserInvestments {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer user_investment_id;
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Integer user_investment_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "investment_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private InvestmentsCategories investment_id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "uid")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private UserData userData;
	@Column(nullable = false, length = 100)
	private String investment_name;
	@Column(nullable = false, length = 10)
	private Integer amount;
	@Column(nullable = false, columnDefinition = "TIMESTAMP")
	private Timestamp date_added;
	@Column(nullable = false, columnDefinition = "TIMESTAMP")
	private Timestamp last_modified;
	@Column(columnDefinition = "TEXT")
	private String description;

	public UserInvestments() {
		super();
	}

	public UserInvestments(Integer user_investment_id, InvestmentsCategories investment_id, UserData userData,
			String investment_name, Integer amount, Timestamp date_added, Timestamp last_modified, String description) {
		super();
		this.user_investment_id = user_investment_id;
		this.investment_id = investment_id;
		this.userData = userData;
		this.investment_name = investment_name;
		this.amount = amount;
		this.date_added = date_added;
		this.last_modified = last_modified;
		this.description = description;
	}

	public Integer getUser_investment_id() {
		return user_investment_id;
	}

	public void setUser_investment_id(Integer user_investment_id) {
		this.user_investment_id = user_investment_id;
	}

	public InvestmentsCategories getInvestment_id() {
		return investment_id;
	}

	public void setInvestment_id(InvestmentsCategories investment_id) {
		this.investment_id = investment_id;
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public String getInvestment_name() {
		return investment_name;
	}

	public void setInvestment_name(String investment_name) {
		this.investment_name = investment_name;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Timestamp getDate_added() {
		return date_added;
	}

	public void setDate_added(Timestamp date_added) {
		this.date_added = date_added;
	}

	public Timestamp getLast_modified() {
		return last_modified;
	}

	public void setLast_modified(Timestamp last_modified) {
		this.last_modified = last_modified;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "UserInvestments [user_investment_id=" + user_investment_id + ", investment_id=" + investment_id
				+ ", userData=" + userData + ", investment_name=" + investment_name + ", amount=" + amount
				+ ", date_added=" + date_added + ", last_modified=" + last_modified + ", description=" + description
				+ "]";
	}

}