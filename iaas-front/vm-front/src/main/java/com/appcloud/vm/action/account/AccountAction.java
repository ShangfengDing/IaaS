package com.appcloud.vm.action.account;

import java.util.List;

import aliyun.openapi.client.AliRegionClient;
import appcloud.openapi.client.RegionClient;
import appcloud.openapi.response.DescribeRegionsResponse;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;

public class AccountAction  extends BaseAction{
	private AppkeyManager appkeyManager = new AppkeyManager();
	private static RegionClient regionClient = OpenClientFactory.getRegionClient();
	private static AliRegionClient aliRegionClient = OpenClientFactory.getAliRegionClient();
	
	private Integer id;
	private String name;
	private String provider;
	private String appkeyId;
	private String appkeySecret;
	
	private String message="";
	private List<Appkey> appkeyList;
	private Appkey appkey;
	
	public String execute(){
		Integer userId = getSessionUserID();
		String userEmail = getSessionUserEmail();
		if(id != null){
			appkeyManager.updateAppkey(id, provider, name,appkeyId,appkeySecret);
		}else{
			appkeyManager.addAppkey(userId, userEmail, appkeyId, appkeySecret, provider, name);
		}
		
		setAppkeyList(appkeyManager.getAppkeyByUserId(userId));
		return SUCCESS;
	}
	
	public String preupdate(){
		appkey = appkeyManager.getAppkeyById(id);
		return SUCCESS;
	}
	
	public String deleteAccount(){
		appkeyManager.deleteAppkey(id);
		Integer userId = getSessionUserID();
		setAppkeyList(appkeyManager.getAppkeyByUserId(userId));
		return SUCCESS;
	}
	
	public String checkAccount(){
		Integer userId = getSessionUserID();
		List<Appkey> appkeylist = appkeyManager.getAppkeyByUserId(userId);
		for(Appkey appkey:appkeylist){
			if(id != null){
				if(appkey.getId().equals(id)){
					continue;
				}
			}
			if(appkey.getAppname().equals(name)){
				message = "Name already exists";
				return SUCCESS;
			}
		}
		String userEmail = getSessionUserEmail();
		switch(provider){
		case Constants.YUN_HAI:
			DescribeRegionsResponse response = regionClient.DescribeRegions(appkeyId, appkeySecret, userEmail);
			if(response.getRegionItems() == null){
				message = "appkey error";
				return SUCCESS;
			}
			break;
		case Constants.ALI_YUN:
			com.aliyuncs.ecs.model.v20140526.DescribeRegionsResponse aliresponse = aliRegionClient.DescribeRegions(appkeyId, appkeySecret, userEmail);
			if(aliresponse.getRegions() == null){
				message = "appkey error";
				return SUCCESS;
			}
			break;
		}
		message = "success";
		return SUCCESS;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getAppkeyId() {
		return appkeyId;
	}
	public void setAppkeyId(String appkeyId) {
		this.appkeyId = appkeyId;
	}
	public String getAppkeySecret() {
		return appkeySecret;
	}
	public void setAppkeySecret(String appkeySecret) {
		this.appkeySecret = appkeySecret;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public List<Appkey> getAppkeyList() {
		return appkeyList;
	}


	public void setAppkeyList(List<Appkey> appkeyList) {
		this.appkeyList = appkeyList;
	}

	public Appkey getAppkey() {
		return appkey;
	}

	public void setAppkey(Appkey appkey) {
		this.appkey = appkey;
	}
	
}