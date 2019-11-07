package com.appcloud.vm.action.vm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import appcloud.api.client.App;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import aliyun.openapi.client.AliInstanceClient;
import appcloud.api.beans.Load;
import appcloud.api.client.ServerClient;
import appcloud.openapi.client.InstanceClient;
import appcloud.openapi.datatype.InstanceMonitorDataType;
import appcloud.openapi.response.InstancesMonitorInfoReponse;

import com.aliyuncs.ecs.model.v20140526.DescribeInstanceMonitorDataResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeInstanceMonitorDataResponse.InstanceMonitorData;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse.Instance;
import com.aliyuncs.exceptions.ClientException;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.ClientFactory;
import com.appcloud.vm.fe.util.OpenClientFactory;

@SuppressWarnings("serial")
public class VmMonitorAction extends BaseAction{
	private Logger logger = Logger.getLogger(VmMonitorAction.class);
	private AppkeyManager appkeyManager = new AppkeyManager();
	private InstanceClient instanceClient = OpenClientFactory.getInstanceClient();
	private AliInstanceClient aliInstanceClient = OpenClientFactory.getAliInstanceClient();
	private Integer userId = this.getSessionUserID();
	List<InstanceMonitorDataType> monitorData;
	List<InstanceMonitorData> monitorDataAli;
	private Appkey appkey;

	private String instanceId; //主机的id
	private String userEmail;  //主机的Email，此时用不上
	private String appname;    //与userId共用获取appky
	private String regionId;	 //地域Id

	public String execute(){
		appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId,appname.trim());
		switch (appkey.getProvider()) {
		case Constants.YUN_HAI:
			showYunhaiMonitor(appkey);
			break;
		case Constants.ALI_YUN:
			showAliyunMonitor(appkey);
			break;
		case Constants.AMAZON:
			showAmazonMonitor();
			break;
		default:
			break;
		}
		return SUCCESS;
	}
	
	public void showYunhaiMonitor(Appkey appkey){
		String startTime = null;
		String endTime = null;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat matterT = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat matterZ = new SimpleDateFormat("HH:mm:ss");
		String dateT = matterT.format(cal.getTime());
		String dateZ = matterZ.format(cal.getTime());
		endTime = dateT+"T"+dateZ+"Z";
		cal.add(Calendar.HOUR_OF_DAY, -1);
		dateT = matterT.format(cal.getTime());
		dateZ = matterZ.format(cal.getTime());
		startTime = dateT+"T"+dateZ+"Z";
		//TODO 2018/3/22 此处应该传入的是zoneId，此处如果有坑，还请改吧改吧
		InstancesMonitorInfoReponse instanceMonitor = instanceClient.DescribeInstanceMonitorData(regionId.trim(), null, instanceId.trim(), startTime, endTime,
				"minute", appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
		logger.info(ToStringBuilder.reflectionToString(instanceMonitor));
        monitorData = instanceMonitor.getMonitorData();
	}
	
    public void showAliyunMonitor(Appkey appkey){
		String startTime = null;
		String endTime = null;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat matterT = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat matterZ = new SimpleDateFormat("HH:mm:ss");
		cal.add(Calendar.HOUR_OF_DAY, -8);
		String dateT = matterT.format(cal.getTime());
		String dateZ = matterZ.format(cal.getTime());
		endTime = dateT+"T"+dateZ+"Z";
		cal.add(Calendar.HOUR_OF_DAY, -1);
		dateT = matterT.format(cal.getTime());
		dateZ = matterZ.format(cal.getTime());
		startTime = dateT+"T"+dateZ+"Z";
		DescribeInstanceMonitorDataResponse instanceMonitor = null;
		try {
			instanceMonitor = aliInstanceClient.DescribeInstanceMonitorData(regionId.trim(), instanceId.trim(), startTime, endTime,
					60, appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			logger.info(e.getErrMsg());
		}
		monitorDataAli = instanceMonitor.getMonitorData();
    }
    
    public List<InstanceMonitorData> getMonitorDataAli() {
		return monitorDataAli;
	}

	public void setMonitorDataAli(List<InstanceMonitorData> monitorDataAli) {
		this.monitorDataAli = monitorDataAli;
	}

	public void showAmazonMonitor(){}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
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

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public List<InstanceMonitorDataType> getMonitorData() {
		return monitorData;
	}

	public void setMonitorData(List<InstanceMonitorDataType> monitorData) {
		this.monitorData = monitorData;
	}
	
}
