package com.appcloud.vm.fe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Deprecated
@Entity
@Table(name = "user")
public class User implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String email;
	
	public User() {
	}

	public User(Integer id, String email) {
		super();
		this.id = id;
		this.email = email;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
