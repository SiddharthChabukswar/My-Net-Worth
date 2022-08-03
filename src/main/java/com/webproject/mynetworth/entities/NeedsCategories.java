package com.webproject.mynetworth.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NeedsCategories")
public class NeedsCategories {

	@Id
	private Integer need_id;

	@Column(nullable = false, unique = true, length = 100)
	private String category_name;

	public NeedsCategories() {
		super();
	}

	public NeedsCategories(Integer need_id, String category_name) {
		super();
		this.need_id = need_id;
		this.category_name = category_name;
	}

	public Integer getNeed_id() {
		return need_id;
	}

	public void setNeed_id(Integer need_id) {
		this.need_id = need_id;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	@Override
	public String toString() {
		return "NeedsCategories [need_id=" + need_id + ", category_name=" + category_name + "]";
	}

}
