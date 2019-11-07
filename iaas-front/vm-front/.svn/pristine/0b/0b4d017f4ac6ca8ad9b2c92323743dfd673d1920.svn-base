package com.appcloud.vm.action.shot;

import aliyun.openapi.client.AliInstanceClient;
import aliyun.openapi.client.AliSnapshotClient;
import aliyun.openapi.client.AliVolumeClient;
import appcloud.openapi.client.InstanceClient;
import appcloud.openapi.client.SnapshotClient;
import appcloud.openapi.client.VolumeClient;
import appcloud.openapi.datatype.DiskDetailItem;
import appcloud.openapi.response.BaseResponse;
import appcloud.openapi.response.DisksDetailReponse;
import com.aliyuncs.ecs.model.v20140526.DeleteSnapshotResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeDisksResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeDisksResponse.Disk;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse.Instance;
import com.aliyuncs.ecs.model.v20140526.ResetDiskResponse;
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

public class ShotOperateAction extends BaseAction {
	/**
	 * 云主机的操作
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(ShotOperateAction.class);
	private SnapshotClient snapshotClient = OpenClientFactory.getSnapshotClient();
	private VolumeClient diskClient = OpenClientFactory.getVolumeClient();
	private InstanceClient instanceClient = OpenClientFactory.getInstanceClient();
	private AliSnapshotClient aliSnapshotClient = OpenClientFactory.getAliSnapshotClient();
	private AliInstanceClient aliInstanceClient = OpenClientFactory.getAliInstanceClient();
	private AliVolumeClient aliVolumeClient = OpenClientFactory.getAliVolumeClient();
	private AppkeyManager appkeyManager = new AppkeyManager();
	private Integer userId = this.getSessionUserID();
	private Appkey appkey;
	//判断操作是回滚还是删除
	private final static String ROLLBACK = "rollback";
	private final static String DELETE = "delete";
	//操作传递的参数
	private String operation = "";
	private String snapshotId = "";
	private String result = "success";
	private String providerEn;
	private String userEmail;
	private String regionId;
	private String zoneId =com.appcloud.vm.common.Constants.ZONE_ID;
	private String diskId;
	private String appName;
	
	public String execute(){
		appkey = appkeyManager.getAppkeyByUserIdAndAppName(userId, appName.trim());
		switch (appkey.getProvider()) {
		case Constants.YUN_HAI:
			ShotOperateYunhaiInstance(appkey);
			break;
		case Constants.ALI_YUN:
			ShotOperateAliyunInstance(appkey);
			break;
		case Constants.AMAZON:
			ShotOperateAmazonInstance();
			break;
		default:
			break;
		}

		Map<String, String> mapLog = new HashMap<>();
		mapLog.put("userId", userId.toString());
		mapLog.put("device", "shot");
		String id = (operation == "delete") ? snapshotId:diskId;
		mapLog.put("deviceId", id);
		mapLog.put("provider", appkey.getProvider());
		mapLog.put("operateType", operation + "shot");
		mapLog.put("result", result);
		if (result == "success") {
			Log.INFO(mapLog, appkey, regionId);
		} else {
			Log.ERROR(mapLog, appkey, regionId);
		}
		return SUCCESS;
	}

	/**
	 * 	云海快照操作，磁盘状态必须为available的状态，才能执行此操作 
	 * 	云主机状态为active/suspended/stopped才可以，注意如果为active则会先关机！
	 */
	public void ShotOperateYunhaiInstance(Appkey appkey){
		if(operation.equals(ROLLBACK)){
			DisksDetailReponse DiskList = diskClient.DescribeDisks(regionId, zoneId, "["+diskId+"]",
					null, null, null, null, null, null, null, null, null,null,null,
					appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
			if (0 == DiskList.getDisks().size()) {
				result = "The disk had been deleted";
				return;
			}
			DiskDetailItem diskDetailItem = DiskList.getDisks().get(0);
			logger.info(diskDetailItem.getStatus()+diskDetailItem.getInstanceId());
			if(diskDetailItem.getStatus().equals("available")){
				if (diskDetailItem.getInstanceId()==null) {
					result = "The instance had been deleted";
					return;
				}
				appcloud.openapi.response.DescribeInstancesResponse instancesResponse = instanceClient.DescribeInstances(regionId,zoneId, "["+ diskDetailItem.getInstanceId() +"]", null, null,
						null, null, null, null, null, null, null, appkey.getAppkeyId(), appkey.getAppkeySecret(), null);
				if (instancesResponse.getTotalCount()==0) {
					result = "The instance had been deleted";
					return;
				}
				//关闭云主机,系统会自动判断
				BaseResponse resetDisk = diskClient.ResetDisk(regionId,zoneId,diskId,snapshotId, appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
				logger.info(ToStringBuilder.reflectionToString(resetDisk));
				if(resetDisk.getMessage()==null){
					result = "success";
				}else{
					result = resetDisk.getMessage();
				}
			}else{
				result = "unavilable";
			}
			
		}else if (operation.equals(DELETE)) {
			BaseResponse deleteSnapshot = snapshotClient.DeleteSnapshot(regionId,zoneId,snapshotId, appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
			if (deleteSnapshot.getErrorCode()==null) {
				result = "success";
			}else {
				result = deleteSnapshot.getMessage();
			}
		}
	}

	/**
	 * 阿里云快照回滚的操作
	 * 实例状态必须为 Stopped 时，才可以执行此操作。
	 * 磁盘状态必须为使用中（In_use）的状态，才能执行此操作。
	 * 指定的 SnapshotId 必须是由 DiskId 创建的快照，否则报错。
	 * 实例的 OperationLocks 中标记了 "LockReason" : "security" 的锁定状态，则报错。
	 */
	public void ShotOperateAliyunInstance(Appkey appkey){

		if(operation.equals(ROLLBACK)){
			DescribeDisksResponse DiskList = aliVolumeClient.DescribeDisks(regionId, null, "[\""+diskId.trim()+"\"]", 
					null, null, null, null, null, null, null, null, null,null,
					appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
			if (0 == DiskList.getDisks().size()) {
				result = "The disk had been deleted";
				return;
			}
			Disk diskDetailItem = DiskList.getDisks().get(0);
			logger.info(diskDetailItem.getStatus()+diskDetailItem.getInstanceId());
			if(diskDetailItem.getStatus().equals("In_use")){
				DescribeInstancesResponse instanceList = aliInstanceClient.DescribeInstances(
						regionId.trim(), null, "[\""+diskDetailItem.getInstanceId()+"\"]", null, null, null,null, null,
						null, null, null, null,	appkey.getAppkeyId(), appkey.getAppkeySecret(),	appkey.getUserEmail());
				if (instanceList.getTotalCount()==0) {
					result = "The instance had been deleted";
					return;
				}
				Instance aliInstances = instanceList.getInstances().get(0);
				if ((aliInstances.getStatus().toString().toLowerCase()).equals("stopped")) {
					try {
						ResetDiskResponse aliResetDisk = aliVolumeClient.ResetDisk(regionId,diskId,snapshotId, appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
						result = "success";
					} catch (ClientException e) {
						// TODO Auto-generated catch block
						result = e.getErrMsg();
					}
				}else {
					logger.info(aliInstances.getStatus().toString().toLowerCase());
					result = "The current status of the resource does not support this operation.";
				}
			}else{
				result = "The current disk status does not support this operation.";
			}
			
		}else if (operation.equals(DELETE)) {
			try {
				DeleteSnapshotResponse deleteSnapshot = aliSnapshotClient.DeleteSnapshot(regionId,snapshotId, appkey.getAppkeyId(), appkey.getAppkeySecret(), appkey.getUserEmail());
				result = "success";
			} catch (ClientException e) {
				// TODO Auto-generated catch block
				result = e.getErrMsg();
			}			
		}
	}


	public void ShotOperateAmazonInstance(){}

	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getProviderEn() {
		return providerEn;
	}

	public void setProviderEn(String providerEn) {
		this.providerEn = providerEn;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public String getDiskId() {
		return diskId;
	}

	public void setDiskId(String diskId) {
		this.diskId = diskId;
	}

	public String getOperation() {
		return operation;
	}

	public String getSnapshotId() {
		return snapshotId;
	}
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Override
	public String toString() {
		return "ShotOperateAction [operation=" + operation + ", snapshotId="
				+ snapshotId + ", result=" + result + ", providerEn="
				+ providerEn + ", userEmail=" + userEmail + ", regionId="
				+ regionId + ", diskId=" + diskId + "]";
	}


}
