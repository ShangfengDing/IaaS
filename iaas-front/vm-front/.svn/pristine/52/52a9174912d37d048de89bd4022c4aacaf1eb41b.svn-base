package com.appcloud.vm.action.vm;

import java.util.List;

import appcloud.api.client.App;
import com.appcloud.vm.fe.manager.AppkeyManager;
import org.apache.log4j.Logger;

import aliyun.openapi.client.AliInstanceClient;
import appcloud.openapi.client.InstanceClient;
import appcloud.openapi.datatype.InstanceAttributes;
import appcloud.openapi.datatype.InstanceStatusItem;
import appcloud.openapi.datatype.InstanceStatusSet;
import appcloud.openapi.response.DescribeInstanceStatusResponse;
import appcloud.openapi.response.DescribeInstancesResponse;

import com.aliyuncs.ecs.model.v20140526.DescribeInstanceStatusResponse.InstanceStatus;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;

import static com.appcloud.vm.common.ResultMessage.SUCCESS;

public class VmStatusAction extends BaseAction{
	/**
	 * Modify By Rain
	 * 将provider修改AppName
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(VmStatusAction.class);
	private InstanceClient instanceClient = OpenClientFactory.getInstanceClient();
	private AliInstanceClient aliInstanceClient = OpenClientFactory.getAliInstanceClient();
	private AppkeyManager appkeyManager = new AppkeyManager();
	private Integer userId = this.getSessionUserID();
	private Appkey appkey;
	private String instanceId;		//虚拟机instanceId，从vmlist.jsp页面获取
	private String regionId;    //地域的ID，必不可少
	private String userEmail;
	private String status = "";
	private String appname;      //jsp页面获取的appname
	private boolean deleted;
	
	public String execute(){
		appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId,appname.trim());
		deleted = true;
		switch (appkey.getProvider()) {
		case Constants.YUN_HAI:
			YunhaiStatus(appkey);
			break;
		case Constants.ALI_YUN:
			AliyunStatus(appkey);
			break;
		case Constants.AMAZON:
			AmazonStatus();
			break;
		default:
			break;
		}
		return SUCCESS;
	}
	
	public void YunhaiStatus(Appkey appkey) {
		DescribeInstancesResponse instanceList = instanceClient.DescribeInstances(regionId.trim(), null,
                "[" + instanceId.trim() + "]", null, null, null, null, null, null, null, null, null,
                appkey.getAppkeyId(), appkey.getAppkeySecret(), null);
		if (instanceList.getMessage() == null) {
			InstanceAttributes instance = instanceList.getInstances().get(0);
			status = instance.getStatus();
		} else {
			status = "deleted";
		}
		logger.info("status is:"+status);
	}

	
	public void AliyunStatus(Appkey appkey) {
		com.aliyuncs.ecs.model.v20140526.DescribeInstanceStatusResponse instanceStatus = aliInstanceClient.DescribeInstanceStatus(regionId.trim(), null, null, null,
				appkey.getAppkeyId(), appkey.getAppkeySecret(), null);
		List<InstanceStatus> instanceStatuses = instanceStatus.getInstanceStatuses();
		for(InstanceStatus insStatus:instanceStatuses){
			if(instanceId.trim().equals(insStatus.getInstanceId())){
				deleted = false;
				status = insStatus.getStatus().toString();
				logger.info(status);
				break;
			}
		}
		if(status.equals("STOPPING") || status.equals("STARTING")){
			status = "other";
		}
		if(deleted)status = "deleted";
	}

	public void AmazonStatus() {}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
}

