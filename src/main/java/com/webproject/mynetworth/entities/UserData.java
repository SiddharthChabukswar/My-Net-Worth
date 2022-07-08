package com.webproject.mynetworth.entities;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UserData")
public class UserData {

	@Id
	@Column(nullable = false, length = 10)
	private Integer uid;

	@Column(columnDefinition = "DATE")
	private Date dob;
	@Column(columnDefinition = "TEXT")
	private String about_me;
	@Column(nullable = false, length = 3)
	private Integer age;
	@Column(nullable = false, length = 3)
	private String image_url;
	@Column(nullable = false, length = 10)
	private Integer total_net_worth;
	@Column(nullable = false, length = 10)
	private Integer numof_income_source;
	@Column(nullable = false, length = 10)
	private Integer sumof_income_source;
	@Column(nullable = false, length = 10)
	private Integer numof_needs;
	@Column(nullable = false, length = 10)
	private Integer sumof_needs;
	@Column(nullable = false, length = 10)
	private Integer numof_assets;
	@Column(nullable = false, length = 10)
	private Integer sumof_assets;

	public UserData() {
		super();
	}

	public UserData(Integer uid) {
		super();
		this.uid = uid;
		this.dob = Date.valueOf("2000-01-01");
		this.about_me = "";
		this.age = 22;
		this.image_url = "/user_images/default.png";
		this.total_net_worth = 0;
		this.numof_income_source = 0;
		this.sumof_income_source = 0;
		this.numof_needs = 0;
		this.sumof_needs = 0;
		this.numof_assets = 0;
		this.sumof_assets = 0;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
		int age = Period.between(this.dob.toLocalDate(), LocalDate.now()).getYears();
		this.setAge(age);
	}

	public String getAbout_me() {
		return about_me;
	}

	public void setAbout_me(String about_me) {
		this.about_me = about_me;
	}

	public Integer getAge() {
		return age;
	}

	private void setAge(Integer age) {
		this.age = age;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = "/user_images/" + image_url;
	}

	public Integer getTotal_net_worth() {
		return total_net_worth;
	}

	public void setTotal_net_worth(Integer total_net_worth) {
		this.total_net_worth = total_net_worth;
	}

	public Integer getNumof_income_source() {
		return numof_income_source;
	}

	public void setNumof_income_source(Integer numof_income_source) {
		this.numof_income_source = numof_income_source;
	}

	public Integer getSumof_income_source() {
		return sumof_income_source;
	}

	public void setSumof_income_source(Integer sumof_income_source) {
		this.sumof_income_source = sumof_income_source;
	}

	public Integer getNumof_needs() {
		return numof_needs;
	}

	public void setNumof_needs(Integer numof_needs) {
		this.numof_needs = numof_needs;
	}

	public Integer getSumof_needs() {
		return sumof_needs;
	}

	public void setSumof_needs(Integer sumof_needs) {
		this.sumof_needs = sumof_needs;
	}

	public Integer getNumof_assets() {
		return numof_assets;
	}

	public void setNumof_assets(Integer numof_assets) {
		this.numof_assets = numof_assets;
	}

	public Integer getSumof_assets() {
		return sumof_assets;
	}

	public void setSumof_assets(Integer sumof_assets) {
		this.sumof_assets = sumof_assets;
	}

	@Override
	public String toString() {
		return "UserData [uid=" + uid + ", dob=" + dob + ", about_me=" + about_me + ", age=" + age + ", image_url="
				+ image_url + ", total_net_worth=" + total_net_worth + ", numof_income_source=" + numof_income_source
				+ ", sumof_income_source=" + sumof_income_source + ", numof_needs=" + numof_needs + ", sumof_needs="
				+ sumof_needs + ", numof_assets=" + numof_assets + ", sumof_assets=" + sumof_assets + "]";
	}

}