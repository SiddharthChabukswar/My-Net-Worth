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
@Table(name = "UserIncome")
public class UserIncome {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer income_id;
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Integer income_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "uid")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private UserData userData;
	@Column(nullable = false, length = 10)
	private Integer amount;
	@Column(nullable = false, length = 100)
	private String source_name;
	@Column(nullable = false, columnDefinition = "TIMESTAMP")
	private Timestamp date_added;
	@Column(nullable = false, columnDefinition = "TIMESTAMP")
	private Timestamp last_modified;
	@Column(columnDefinition = "TEXT")
	private String description;

	public UserIncome() {
		super();
	}

	public UserIncome(Integer income_id, UserData userData, Integer amount, String source_name, Timestamp date_added,
			Timestamp last_modified, String description) {
		super();
		this.income_id = income_id;
		this.userData = userData;
		this.amount = amount;
		this.source_name = source_name;
		this.date_added = date_added;
		this.last_modified = last_modified;
		this.description = description;
	}

	public Integer getIncome_id() {
		return income_id;
	}

	public void setIncome_id(Integer income_id) {
		this.income_id = income_id;
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getSource_name() {
		return source_name;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
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
		return "UserIncome [income_id=" + income_id + ", userData=" + userData + ", amount=" + amount + ", source_name="
				+ source_name + ", date_added=" + date_added + ", last_modified=" + last_modified + ", description="
				+ description + "]";
	}

}
