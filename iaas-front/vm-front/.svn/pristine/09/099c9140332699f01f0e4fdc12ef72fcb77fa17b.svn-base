package com.appcloud.vm.action.hd;

import aliyun.openapi.client.AliVolumeClient;
import appcloud.openapi.client.VolumeClient;
import appcloud.openapi.response.BaseResponse;
import com.aliyuncs.ecs.model.v20140526.AttachDiskResponse;
import com.aliyuncs.ecs.model.v20140526.DeleteDiskResponse;
import com.aliyuncs.ecs.model.v20140526.DetachDiskResponse;
import com.aliyuncs.exceptions.ClientException;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.common.Log;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;


public class HdOperateAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(HdOperateAction.class);
	private VolumeClient volumeClient = OpenClientFactory.getVolumeClient();
	private AliVolumeClient aliVolumeClient = OpenClientFactory.getAliVolumeClient();
	private String provider;
	private String instanceId;
	private String diskId;
	private String userEmail;
	private String operation;
	private String regionId;
	private String zoneId=com.appcloud.vm.common.Constants.ZONE_ID;;
	private String result;
	private AppkeyManager appkeyManager = new AppkeyManager();
	private Appkey appkey;
	private Integer userId;
	private String appName;
	
	private final static String ATTACH = "attach";
	private final static String DETACH = "detach";
	private final static String DELETE = "delete";
	
	public String execute(){
		appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId,appName);
		switch (provider.trim()) {
		case Constants.YUN_HAI:
			operateYunhaiDisk();
			break;
		case Constants.ALI_YUN:
			operateAliyunDisk();
			break;
		case Constants.AMAZON:
			operateAmazonDisk();
			break;
		default:
			break;
		}

		Map<String, String> mapLog = new HashMap<>();
		mapLog.put("userId", userId.toString());
		mapLog.put("device", "hd");
		mapLog.put("deviceId", diskId);
		mapLog.put("provider", appkey.getProvider());
		mapLog.put("operateType", operation);
		mapLog.put("result", result);
		if (result=="success") {
			Log.INFO(mapLog, appkey, regionId);
		}else {
			Log.ERROR(mapLog, appkey, regionId);
		}
		return SUCCESS;
	}
	
	private void operateYunhaiDisk(){
		zoneId=com.appcloud.vm.common.Constants.ZONE_ID;
		logger.info("操作中:"+userId+appName);
		appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId,appName);
		if(operation.equals(DETACH)){
			BaseResponse baseResponse = volumeClient.DetachDisk(regionId.trim(),zoneId, diskId.trim(), instanceId.trim(),
					appkey.getAppkeyId(), appkey.getAppkeySecret(), userEmail.trim());
			logger.info("detach");
			if(baseResponse.getMessage()==null){
				result = "success";
			}else{
				result = baseResponse.getMessage();
			}
			logger.info(baseResponse.getErrorCode());
			logger.info(baseResponse.getMessage());
		}else if(operation.equals(ATTACH)){
			logger.info(regionId+diskId+instanceId);
			BaseResponse baseResponse = volumeClient.AttachDisk(regionId.trim(), zoneId,diskId.trim(), instanceId.trim(),
					null, null, appkey.getAppkeyId(), appkey.getAppkeySecret(), userEmail.trim());
			logger.info("attach");
			if(baseResponse.getMessage()==null){
				result = "success";
			}else{
				result = baseResponse.getMessage();
			}
		}else if(operation.equals(DELETE)){
			logger.info(regionId+diskId+instanceId);
			BaseResponse baseResponse = volumeClient.DeleteDisk(regionId.trim(), zoneId,diskId.trim(), appkey.getAppkeyId(), appkey.getAppkeySecret(), userEmail.trim());
			logger.info("delete");
			if(baseResponse.getMessage()==null){
				result = "success";
			}else{
				result = baseResponse.getMessage();
			}
		}
	}
	
	private void operateAliyunDisk(){
		appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId,appName);
		if(operation.equals(DETACH)){
			try {
				DetachDiskResponse baseResponse = aliVolumeClient.DetachDisk(regionId.trim(), diskId.trim(), instanceId.trim(),
                        appkey.getAppkeyId(), appkey.getAppkeySecret(), userEmail.trim());
				result = "success";
			} catch (ClientException e) {
				result = e.getErrMsg();
			}
			logger.info("detach");
		}else if(operation.equals(ATTACH)){
			logger.info(regionId+diskId+instanceId);
			try {
				AttachDiskResponse baseResponse = aliVolumeClient.AttachDisk(regionId.trim(), diskId.trim(), instanceId.trim(),
                        null, null, appkey.getAppkeyId(), appkey.getAppkeySecret(), userEmail.trim());
				result = "success";
			} catch (ClientException e) {
				result = e.getErrMsg();
			}
		}else if(operation.equals(DELETE)){
			logger.info(regionId+diskId+instanceId);
			try {
				DeleteDiskResponse baseResponse = aliVolumeClient.DeleteDisk(regionId.trim(), diskId.trim(), appkey.getAppkeyId(), appkey.getAppkeySecret(), userEmail.trim());
				result = "success";
			} catch (ClientException e) {
				result = e.getErrMsg();
			}
		}
	}


	private void operateAmazonDisk(){}
	
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getDiskId() {
		return diskId;
	}
	public void setDiskId(String diskId) {
		this.diskId = diskId;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
}