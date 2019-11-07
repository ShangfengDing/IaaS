package com.appcloud.vm.action.hd;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import aliyun.openapi.client.AliInstanceClient;
import appcloud.api.beans.Server;
import appcloud.api.client.ServerClient;
import appcloud.openapi.client.InstanceClient;
import appcloud.openapi.datatype.InstanceAttributes;
import appcloud.openapi.response.DescribeInstancesResponse;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.entity.Instance;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;

@SuppressWarnings("serial")
public class ShowAttachHdAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(ShowAttachHdAction.class);
	private InstanceClient instanceClient = OpenClientFactory.getInstanceClient();
	private AliInstanceClient aliInstanceClient = OpenClientFactory.getAliInstanceClient();
	private String provider;
	private String regionId;
	private String userEmail;
	private String page;
	private Integer totalPage;
	private Appkey appkey;
	private AppkeyManager appkeyManager = new AppkeyManager();
	private List<Instance> instanceList = new ArrayList<Instance>();
	private Integer userId;
	private String appName;

	public String execute(){
		switch (provider.trim()) {
		case Constants.YUN_HAI:
			showYunhaiInstances();
			break;
		case Constants.ALI_YUN:
			showAliyunInstances();
			break;
		case Constants.AMAZON:
			showAmazonInstances();
			break;
		default:
			break;
		}
		return SUCCESS;
	}
	
	private void showYunhaiInstances(){
		appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId,appName);
		DescribeInstancesResponse instances = instanceClient.DescribeInstances(regionId.trim(), null, null, null, null, null,
				null, null, null, null, page, null, appkey.getAppkeyId(), appkey.getAppkeySecret(), userEmail.trim());
		page = instances.getPageNumber().toString();
		logger.info(instances.getTotalCount()/(10f));
		totalPage = (int) Math.ceil(instances.getTotalCount()/(10f));
		logger.info("TotalPage:"+totalPage);
		for(InstanceAttributes instance:instances.getInstances()){
			instanceList.add(new Instance(instance.getInstanceId(),instance.getInstanceName()));
		}
	}
	
	private void showAliyunInstances(){
		appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId,appName);
		com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse instances = aliInstanceClient.DescribeInstances(regionId.trim(), null, null, null, null, null,
				null, null, null, null, String.valueOf(Integer.valueOf(page)), null, appkey.getAppkeyId(), appkey.getAppkeySecret(), userEmail.trim());
		page = instances.getPageNumber().toString();
		totalPage = (int) Math.ceil(instances.getTotalCount()/(10f));
		for(com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse.Instance instance:instances.getInstances()){
			instanceList.add(new Instance(instance.getInstanceId(),instance.getInstanceName()));
		}
	}
	
	private void showAmazonInstances(){}
	
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
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

	public List<Instance> getInstanceList() {
		return instanceList;
	}

	public void setInstanceList(List<Instance> instanceList) {
		this.instanceList = instanceList;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
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
}
