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
@Table(name = "UserNeeds")
public class UserNeeds {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer user_need_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "need_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private NeedsCategories need_id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "uid")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private UserData userData;
	@Column(nullable = false, length = 100)
	private String need_name;
	@Column(nullable = false, length = 10)
	private Integer amount;
	@Column(nullable = false, columnDefinition = "TIMESTAMP")
	private Timestamp date_added;
	@Column(nullable = false, columnDefinition = "TIMESTAMP")
	private Timestamp last_modified;
	@Column(columnDefinition = "TEXT")
	private String description;

	public UserNeeds() {
		super();
	}

	public UserNeeds(Integer user_need_id, NeedsCategories need_id, UserData userData, String need_name, Integer amount,
			Timestamp date_added, Timestamp last_modified, String description) {
		super();
		this.user_need_id = user_need_id;
		this.need_id = need_id;
		this.userData = userData;
		this.need_name = need_name;
		this.amount = amount;
		this.date_added = date_added;
		this.last_modified = last_modified;
		this.description = description;
	}

	public Integer getUser_need_id() {
		return user_need_id;
	}

	public void setUser_need_id(Integer user_need_id) {
		this.user_need_id = user_need_id;
	}

	public NeedsCategories getNeed_id() {
		return need_id;
	}

	public void setNeed_id(NeedsCategories need_id) {
		this.need_id = need_id;
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public String getNeed_name() {
		return need_name;
	}

	public void setNeed_name(String need_name) {
		this.need_name = need_name;
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
		return "UserNeeds [user_need_id=" + user_need_id + ", need_id=" + need_id + ", userData=" + userData
				+ ", need_name=" + need_name + ", amount=" + amount + ", date_added=" + date_added + ", last_modified="
				+ last_modified + ", description=" + description + "]";
	}

}