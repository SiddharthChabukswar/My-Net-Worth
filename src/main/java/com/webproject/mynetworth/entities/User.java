package com.webproject.mynetworth.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer uid;
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Integer uid;

	@Column(nullable = false, unique = true, length = 100)
	private String email;
	@Column(nullable = false, length = 100)
	private String password;

	public User() {
		super();
	}

	public User(Integer uid, String email, String name, String password) {
		super();
		this.uid = uid;
		this.email = email;
		this.password = password;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", email=" + email + ", password=" + password + "]";
	}

}
