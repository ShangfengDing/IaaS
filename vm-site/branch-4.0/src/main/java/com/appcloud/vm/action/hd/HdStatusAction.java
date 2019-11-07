package com.appcloud.vm.action.hd;

import java.util.List;

import org.apache.log4j.Logger;

import aliyun.openapi.client.AliVolumeClient;
import appcloud.openapi.client.VolumeClient;
import appcloud.openapi.datatype.DiskDetailItem;
import appcloud.openapi.response.DisksDetailReponse;

import com.aliyuncs.ecs.model.v20140526.DescribeDisksResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeDisksResponse.Disk;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.action.vm.VmStatusAction;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;

public class HdStatusAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(VmStatusAction.class);
	private VolumeClient volumeClient = OpenClientFactory.getVolumeClient();
	private AliVolumeClient aliVolumeClient = OpenClientFactory.getAliVolumeClient();
	private AppkeyManager appkeyManager = new AppkeyManager();
	private Appkey appkey;
	private String diskId;		//虚拟机instanceId，从vmlist.jsp页面获取
	private String regionId;
	private String zoneId;
	private String userEmail;
	private String status = "";
	private String provider;      //提供商（英文）
	private boolean deleted;
	
	public String execute(){
		deleted = true;
		switch (provider.trim()) {
		case Constants.YUN_HAI:
			YunhaiDiskStatus();
			break;
		case Constants.ALI_YUN:
			AliyunDiskStatus();
			break;
		case Constants.AMAZON:
			AmazonDiskStatus();
			break;
		default:
			break;
		}
		return SUCCESS;
	}

	private void YunhaiDiskStatus(){
		appkey = appkeyManager.getAppkeyByUserEmailAndProvider(userEmail.trim(),provider.trim());
		DisksDetailReponse disksDetail = volumeClient.DescribeDisks(
				regionId.trim(), zoneId, "[" + diskId.trim() + "]", null, null,
				null, null, null, null, null, null, null, null, null,
				appkey.getAppkeyId(), appkey.getAppkeySecret(),
				userEmail.trim());
		if (disksDetail.getTotalCount() == 0) {
			deleted = true;
		} else {
			List<DiskDetailItem> disks = disksDetail.getDisks();
			for (DiskDetailItem disk : disks) {
				if (disk.getDiskId().equals(diskId.trim())) {
					deleted = false;
					logger.info(disk.getAttachStatus());
					status = disk.getAttachStatus();
					break;
				}
			}
		}
		if(deleted)status = "deleted";
	}
	
	private void AliyunDiskStatus(){
		appkey = appkeyManager.getAppkeyByUserEmailAndProvider(userEmail.trim(),provider.trim());
		DescribeDisksResponse disksDetail = aliVolumeClient.DescribeDisks(
				regionId.trim(), null, "[\"" + diskId.trim() + "\"]", null, null,
				null, null, null, null, null, null, null, null,
				appkey.getAppkeyId(), appkey.getAppkeySecret(),
				userEmail.trim());
		if (disksDetail.getTotalCount() == 0) {
			deleted = true;
		} else {
			List<Disk> disks = disksDetail.getDisks();
			for (Disk disk : disks) {
				if (disk.getDiskId().equals(diskId.trim())) {
					deleted = false;
					logger.info(disk.getStatus());
					status = disk.getStatus();
					break;
				}
			}
			if(status.equals("Attaching") || status.equals("Detaching") || status.equals("Creating")
					|| status.equals("ReIniting")){
				status = "other";
			}
		}
		if(deleted)status = "deleted";
	}
	
	private void AmazonDiskStatus(){}
	
	public String getDiskId() {
		return diskId;
	}

	public void setDiskId(String diskId) {
		this.diskId = diskId;
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

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
}
