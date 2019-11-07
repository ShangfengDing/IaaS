package com.appcloud.vm.fe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.log4j.Logger;

@Entity
@Table(name="appkey")
public class Appkey{
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id") 
	private Integer id;

	@Column(name="user_id")
	private Integer userId;

	@Column(name="user_email")
	private String userEmail;

	@Column(name="appkey_id")
	private String appkeyId;

	@Column(name="appkey_secret")
	private String appkeySecret;
	
	@Column(name="provider") 
	private String provider;
	
	@Column(name="appname") 
	private String appname;

	@Column(name="region")
	private String region;

	@Column(name="zone")
	private String zone;

	@Column(name="appcloud_userEmail")
	private String appcloud_userEmail;

	@Column(name="state")
	private Integer state;

	public Appkey() {
		super();
	}
	public Appkey(Appkey appkey) {
		this.setId(appkey.getId());
		this.setUserId(appkey.getUserId());
		this.setUserEmail(appkey.getUserEmail());
		this.setAppkeyId(appkey.getAppkeyId());
		this.setAppkeySecret(appkey.getAppkeySecret());
		this.setProvider(appkey.getProvider());
		this.setAppname(appkey.getAppname());
		this.setRegion(appkey.getRegion());
		this.setZone(appkey.getZone());
		this.setAppcloud_userEmail(appkey.getAppcloud_userEmail());
		this.setState(appkey.getState());
	}
	
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getUserId() {
		return this.userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getUserEmail() {
		return this.userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public String getAppkeyId() {
		return this.appkeyId;
	}
	public void setAppkeyId(String appkeyId) {
		this.appkeyId = appkeyId;
	}

	public String getAppkeySecret() {
		return this.appkeySecret;
	}
	public void setAppkeySecret(String appkeySecret) {
		this.appkeySecret = appkeySecret;
	}

	public String getProvider() {
		return this.provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}

	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getAppcloud_userEmail() {
		return appcloud_userEmail;
	}
	public void setAppcloud_userEmail(String appcloud_userEmail) {
		this.appcloud_userEmail = appcloud_userEmail;
	}

	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
