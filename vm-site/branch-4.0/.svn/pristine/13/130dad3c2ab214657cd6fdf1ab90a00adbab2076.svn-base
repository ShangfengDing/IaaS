package com.appcloud.vm.action.account;

import java.util.*;

import aliyun.openapi.client.AliRegionClient;
import appcloud.openapi.client.AccountClient;
import appcloud.openapi.client.ControllerClient;
import appcloud.openapi.client.RegionClient;
import appcloud.openapi.response.DescribeRegionsResponse;

import appcloud.openapi.response.RouteInfoResponse;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;
import com.distributed.common.service.controller.RouteInfoService;
import org.apache.log4j.Logger;

public class AccountAction  extends BaseAction{
	private Logger logger = Logger.getLogger(AccountAction.class);
	private AppkeyManager appkeyManager = new AppkeyManager();
	private static RegionClient regionClient = OpenClientFactory.getRegionClient();
	private static AliRegionClient aliRegionClient = OpenClientFactory.getAliRegionClient();
	private static AccountClient accountClient = OpenClientFactory.getAccountClient();
	private static ControllerClient controllerClient = OpenClientFactory.getControllerClient();
	
	private Integer id;
	private String name;
	private String provider;
	private String appkeyId;
	private String appkeySecret;
	private String region;
	private String zone;
	private int state;
	
	private String message="";
	private List<Appkey> appkeyList;
	private Appkey appkey;
	
	//动态获得可用区
	private Set<String> regionIds;
	private List<Map<String, String>> productDomain;
	private List<String> zoneIds = new ArrayList();

	//分布式云获得regionId和zoneId
	private List<String> disRegionIds = new ArrayList<>();
	private Map<String, List<ZoneAndAppName>> disZoneIds = new HashMap<>();
	
	public String execute(){
		Integer userId = getSessionUserID();
		String userEmail = getSessionUserEmail();
		/*if(id != null){
			//appkeyManager.updateAppkey(id, provider, name,appkeyId,appkeySecret);
			state = 1;
			appkeyManager.updateAppkey(id, provider, name,appkeyId,appkeySecret,region,zone,state);
		}else{
			state = 1;//1表示绑定
			appkeyManager.addAppkey(userId, userEmail, appkeyId, appkeySecret, provider, name, region, zone, state);
		}*/
		state = 1;
		appkeyManager.updateAppkey(id, provider, name,appkeyId,appkeySecret,region,zone,state);
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

	public String unbundlingAccount(){
		appkey = appkeyManager.getAppkeyById(id);
		state = 0;
		appkeyManager.updateAppkey(appkey.getId(),appkey.getProvider(),appkey.getAppname(),appkey.getAppkeyId(),appkey.getAppkeySecret(),appkey.getRegion(),appkey.getZone(),state);
		Integer userId = getSessionUserID();
		setAppkeyList(appkeyManager.getAppkeyByUserId(userId));
		return SUCCESS;
	}

	public String findAccountZoneId(){
		Integer userId = getSessionUserID();
		appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId,name);
		if(appkey!=null){
			zone = appkey.getZone();
		}
		return SUCCESS;
	}

	// 分布式云获得云海账户
	public String findYunhaiAccount(){
		List<Appkey> appkeys = appkeyManager.getAppkeyByEmailAndProvider(getSessionUserEmail(), Constants.YUN_HAI);
		for (Appkey appkey: appkeys) {
			String regionIdTemp = appkey.getRegion();
			ZoneAndAppName zoneAppNameTemp = new ZoneAndAppName(appkey.getZone(), appkey.getAppname());
			List<ZoneAndAppName> zoneTempList = disZoneIds.get(regionIdTemp);
			if (!disRegionIds.contains(regionIdTemp)) {
				disRegionIds.add(regionIdTemp);
				zoneTempList = new ArrayList<>();
				zoneTempList.add(zoneAppNameTemp);
				disZoneIds.put(regionIdTemp, zoneTempList);
			} else {
				zoneTempList.add(zoneAppNameTemp);
				disZoneIds.put(regionIdTemp, zoneTempList);
			}
		}
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
			//判断此用户在这个可用区内所提供的appkey是否正确
			for(Appkey appkey:appkeylist){
				if(appkey.getZone().equals(zone)){
					if(appkey.getAppkeyId().equals(appkeyId)&&appkey.getAppkeySecret().equals(appkeySecret)){
						id = appkey.getId();
						message = "success";
						return SUCCESS;
					} else {
						message = "appkey error";
						return SUCCESS;
					}

				}
			}
			break;
		case Constants.ALI_YUN:
			com.aliyuncs.ecs.model.v20140526.DescribeRegionsResponse aliresponse = aliRegionClient.DescribeRegions(appkeyId, appkeySecret, userEmail);
			if(aliresponse.getRegions() == null){
				message = "appkey error";
				return SUCCESS;
			}
			break;
		default:
			break;
		}
		message = "success";
		return SUCCESS;
	}

	public String checkAccountExist(){
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
			if(appkey.getRegion()!=null){
				if(appkey.getRegion().equals(com.appcloud.vm.common.Constants.DEFAULT_REGIONID)){
					if(appkey.getZone().equals(zone)){
						message = "此可用区下已注册云账号，请勿重复注册!";
						return SUCCESS;
					}
				}
			}
		}
		message = "success";
		return SUCCESS;
	}

	public String newAppcloudAccountModal(){
		ControllerClient controllerClient = new ControllerClient();
		RouteInfoResponse routeInfoResponse = controllerClient.routeInfo();
		regionIds = routeInfoResponse.getRegionIds();
		if(regionIds.isEmpty()){
			logger.info("get regionId failed! ");
		} else {
			logger.info("success get regionId! ");
		}

		productDomain = routeInfoResponse.getProductDomains();
		if(productDomain.isEmpty()){
			logger.info("get product domain failed! ");
		} else {
			logger.info("success get product domain! ");
			for(int i=0; i<productDomain.size(); i++){
				/*Set<String> keySet = productDomain.get(i).keySet();
				for(String key : keySet) {
					String value = productDomain.get(i).get(key);
					zoneIds.add(value);
				}*/
				String zoneId = productDomain.get(i).get("zoneId");
				zoneIds.add(zoneId);
			}
		}
		return SUCCESS;
	}

	public String newAccountInIaaS(){
		String userEmail = getSessionUserEmail();
		String groupSecretKey = "328dd3acb6d5408e88affd2e792f2177";
		String appkeyId = "f00577d2c1764688be5e483a21f4fe92";
		String appkeySecret = "1KS2AMYX0UEjvQA6sVYK1AXfRpvrtArQ";
//		accountClient.userCreateForDistributed(region,zone,userEmail,groupSecretKey,appkeyId,appkeySecret,userEmail,name);
		controllerClient.userCreate(region, zone, userEmail, groupSecretKey, appkeyId, appkeySecret, userEmail, name);
//		Integer userId = getSessionUserID();
//		setAppkeyList(appkeyManager.getAppkeyByUserId(userId));
		return SUCCESS;
	}

	class ZoneAndAppName {
		private String zoneId;
		private String appname;

		public ZoneAndAppName(String zoneId, String appname) {
			this.zoneId = zoneId;
			this.appname = appname;
		}

		public String getZoneId() {
			return zoneId;
		}

		public void setZoneId(String zoneId) {
			this.zoneId = zoneId;
		}

		public String getAppname() {
			return appname;
		}

		public void setAppname(String appname) {
			this.appname = appname;
		}
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

	public Set<String> getRegionIds() {
		return regionIds;
	}

	public void setRegionIds(Set<String> regionIds) {
		this.regionIds = regionIds;
	}

	public List<Map<String, String>> getProductDomain() {
		return productDomain;
	}

	public void setProductDomain(List<Map<String, String>> productDomain) {
		this.productDomain = productDomain;
	}

	public List<String> getZoneIds() {
		return zoneIds;
	}

	public void setZoneIds(List<String> zoneIds) {
		this.zoneIds = zoneIds;
	}

	public List<String> getDisRegionIds() {
		return disRegionIds;
	}

	public void setDisRegionIds(List<String> disRegionIds) {
		this.disRegionIds = disRegionIds;
	}

	public Map<String, List<ZoneAndAppName>> getDisZoneIds() {
		return disZoneIds;
	}

	public void setDisZoneIds(Map<String, List<ZoneAndAppName>> disZoneIds) {
		this.disZoneIds = disZoneIds;
	}
}