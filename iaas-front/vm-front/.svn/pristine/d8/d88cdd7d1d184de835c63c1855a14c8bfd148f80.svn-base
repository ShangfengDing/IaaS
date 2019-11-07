package com.appcloud.vm.action.vm;

import java.util.ArrayList;
import java.util.List;

import com.appcloud.vm.fe.manager.AppkeyManager;
import org.apache.log4j.Logger;


import appcloud.openapi.client.ImageClient;
import appcloud.openapi.datatype.ImageDetailItem;
import appcloud.openapi.datatype.ImageDetailSet;
import appcloud.openapi.response.GetImageListResponse;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.entity.Iso;

import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;

@SuppressWarnings("serial")
public class PreVmInstallAction extends BaseAction{
	private Logger logger = Logger.getLogger(PreVmInstallAction.class);
	private ImageClient imageClient = OpenClientFactory.getImageClient();
	private AppkeyManager appkeyManager = new AppkeyManager();
	private Integer userId = this.getSessionUserID();
	private Appkey appkey;
	private String regionId;
	private String zoneId  =com.appcloud.vm.common.Constants.ZONE_ID;
	private String userEmail;
	private String appname;
	private List<Iso> isos = new ArrayList<Iso>();

	public String execute(){
		appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId, appname);
		switch (appkey.getProvider()) {
		case Constants.YUN_HAI:
			getYunhaiImages(appkey);
			break;
		case Constants.ALI_YUN:
			getAliyunImages(appkey);
			break;
		case Constants.AMAZON:
			getAmazonImages();
			break;
		default:
			break;
		}
		return SUCCESS;
	}
	
	private void getYunhaiImages(Appkey appkey){
		GetImageListResponse imageList = imageClient.DescribeImages(regionId.trim(),zoneId, "iso",
				null, null, null, null, null, null, null, null, null, null, null, null,null,null,
				appkey.getAppkeyId(), appkey.getAppkeySecret(), userEmail.trim());
		ImageDetailSet imageDetails = imageList.getImageDetails();
		List<ImageDetailItem> imageDetailItems = imageDetails.getImageDetailItems();
		logger.info("iso");
		logger.info(imageDetailItems.size());
		for(ImageDetailItem imageDetailItem:imageDetailItems){
			logger.info(imageDetailItem.getImageName()+" "+imageDetailItem.getImageUuid());
			isos.add(new Iso(imageDetailItem.getImageUuid(),imageDetailItem.getImageName()));
		}
	}
	
	private void getAliyunImages(Appkey appkey){}
	
	private void getAmazonImages(){}
	
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public List<Iso> getIsos() {
		return isos;
	}

	public void setIsos(List<Iso> isos) {
		this.isos = isos;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
}
