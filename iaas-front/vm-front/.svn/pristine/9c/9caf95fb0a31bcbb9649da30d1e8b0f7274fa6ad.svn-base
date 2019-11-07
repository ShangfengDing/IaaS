package com.appcloud.vm.action.vm;

import appcloud.openapi.client.InstanceClient;
import appcloud.openapi.response.BaseResponse;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.common.Log;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class VmInstallAction extends BaseAction {
	private Logger logger = Logger.getLogger(VmInstallAction.class);
	private InstanceClient instanceClient = OpenClientFactory.getInstanceClient();
	private AppkeyManager appkeyManager = new AppkeyManager();
	private Integer userId = this.getSessionUserID();
	private final static String YUNHAI = Constants.YUN_HAI;
	private final static String ALIYUN = Constants.ALI_YUN;
	private Appkey appkey;
	private String instanceId;
	private String regionId;
	private String zoneId  =com.appcloud.vm.common.Constants.ZONE_ID;
	private String appname;
	private String isoId;
	private String userEmail;
	private String result;

	public String execute() {
		appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId, appname);
		switch (appkey.getProvider()) {
		case Constants.YUN_HAI:
			installYunhaiInstance(appkey);
			break;
		case Constants.ALI_YUN:
			installAliyunInstance(appkey);
			break;
		case Constants.AMAZON:
			installAmazonInstance();
			break;
		default:
			break;
		}
		return SUCCESS;
	}
	
	private void installYunhaiInstance(Appkey appkey){
		BaseResponse baseResponse = instanceClient.IsoBoot(regionId.trim(),zoneId, instanceId.trim(),
				isoId.trim(), appkey.getAppkeyId(), appkey.getAppkeySecret(), userEmail.trim());
		//根据result插入日志
		Map<String, String> mapLog = new HashMap<>();
		mapLog.put("userId", userId.toString());
		mapLog.put("device", "vm");
		mapLog.put("deviceId", instanceId);
		mapLog.put("provider", appkey.getProvider());
		mapLog.put("operateType", "IsoBoot");

		if(baseResponse.getMessage()==null){
			result = "success";
			mapLog.put("result", result);
			Log.INFO(mapLog, appkey, regionId);
		}else{
			result = baseResponse.getMessage();
			mapLog.put("result", result);
			Log.ERROR(mapLog, appkey, regionId);
		}
	}



	private void installAliyunInstance(Appkey appkey){}
	
	private void installAmazonInstance(){}
	
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
    public String getAppname() {
        return appname;
    }
    public void setAppname(String appname) {
        this.appname = appname;
    }
    public String getIsoId() {
		return isoId;
	}
	public void setIsoId(String isoId) {
		this.isoId = isoId;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
}
