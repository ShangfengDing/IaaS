package com.appcloud.vm.action.shot;

import aliyun.openapi.client.AliSnapshotClient;
import appcloud.openapi.client.SnapshotClient;
import appcloud.openapi.response.CreateSnapshotResponse;
import com.aliyuncs.exceptions.ClientException;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.common.Log;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class NewShotAction extends BaseAction {
	/**
	 * 基于硬盘创建快照
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(NewShotAction.class);
	private SnapshotClient shotClient = OpenClientFactory.getSnapshotClient();
	private AliSnapshotClient AlishotClient = OpenClientFactory.getAliSnapshotClient();
	private AppkeyManager appkeyManager = new AppkeyManager();
	private Integer userId = this.getSessionUserID();
	private Appkey appkey;

	private String displayName = "";
	private String displayDescription = "";
	private String diskId = "";
	private String result = "fail";
	private String providerEn ="";
	private String regionId = "";
	private String zoneId =com.appcloud.vm.common.Constants.ZONE_ID;;
	private String userEmail = "";
	private String appName;

	public String execute() {
		appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId,appName);
		switch (providerEn.trim()) {
		case Constants.YUN_HAI:
			NewShotYunhai(appkey);
			break;
		case Constants.ALI_YUN:
			NewShotAliyun(appkey);
			break;
		case Constants.AMAZON:
			NewShotAmazon();
			break;
		default:
			break;
		}

		Map<String, String> mapLog = new HashMap<>();
		mapLog.put("userId", userId.toString());
		mapLog.put("device", "shot");
		mapLog.put("deviceId", diskId);
		mapLog.put("appkeyId", appkey.getAppkeyId());
		mapLog.put("provider", appkey.getProvider());
		mapLog.put("operateType", "create a shot");
		mapLog.put("result", result);
		if (result=="success") {
			Log.INFO(mapLog, appkey, regionId);
		}else {
			Log.ERROR(mapLog, appkey, regionId);
		}
		//查找虚拟机已有的快照个数，若达到最大限制，则删除最早创建的快照
		return SUCCESS;
	}

	public void NewShotYunhai(Appkey appkey) {
		CreateSnapshotResponse createSnapshot = shotClient.CreateSnapshot(regionId,zoneId,diskId, displayName, displayDescription, appkey.getAppkeyId(), appkey.getAppkeySecret(), userEmail);
		logger.info(ToStringBuilder.reflectionToString(createSnapshot));
		if(null==createSnapshot.getMessage()) {
			result = "success";
		}else {
			result = createSnapshot.getMessage();
		}
	}

	public void NewShotAliyun(Appkey appkey) {
		try {
			com.aliyuncs.ecs.model.v20140526.CreateSnapshotResponse  createSnapshot = AlishotClient.CreateSnapshot(regionId.trim(),diskId.trim(), displayName, null, appkey.getAppkeyId(), appkey.getAppkeySecret(), userEmail);
			result = "success";
			//ResultUtils.toJson(ServletActionContext.getResponse(), createSnapshot);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			result = e.getErrMsg();
		}
	}

	public void NewShotAmazon() {}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayDescription() {
		return displayDescription;
	}

	public void setDisplayDescription(String displayDescription) {
		this.displayDescription = displayDescription;
	}

	public String getDiskId() {
		return diskId;
	}

	public void setDiskId(String diskId) {
		this.diskId = diskId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getProviderEn() {
		return providerEn;
	}

	public void setProviderEn(String providerEn) {
		this.providerEn = providerEn;
	}

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
