package com.appcloud.vm.fe.entity;

import java.util.Date;



/**
 *	此类用于云硬盘列表页的显示
 */
public class DiskStatus {
	/**
	 * 云硬盘Id
	 */
	private String diskId;
	/**
	 * 云硬盘名称
	 */
	private String diskName;
	/**
	 * 云硬盘提供商英文
	 */
	private String providerEn;
	/**
	 * 云硬盘提供商中文
	 */
	private String provider;
	/**
	 * 云硬盘地域
	 */
	private String region;
	/**
	 * 云硬盘所在地域Id
	 */
	private String regionId;
	/**
	 * 云硬盘所在可用区
	 */
	private String zoneId;
	/**
	 * 云硬盘挂载状态英文
	 */
	private String attachStatus;
	/**
	 * 云硬盘挂载状态中文
	 */
	private String attachStatusCn;
	/**
	 * 挂载实例
	 */
	private String instanceName;
	/**
	 * 云硬盘种类
	 */
	private String diskType;
	/**
	 * 云硬盘大小
	 */
	private Integer size;
	/**
	 * 云硬盘描述
	 */
	private String description;
	/**
	 * 云硬盘属性
	 */
	private String diskCategory;
	private String snapshotId;
	private Date createTime;
	/**
	 * 云硬盘付费方式
	 */
	private String payType;
	/**
	 * 云硬盘到期时间
	 */
	private String endTime;
	/**
	 * 云硬盘挂载
	 */
	private String device;
	/**
	 * 云硬盘所属云主机Id
	 */
	private String instanceId;
	/**
	 * 云硬盘用户Id
	 */
	private String userEmail;

	public DiskStatus(){}
	
	public DiskStatus(String diskId, String diskName, String providerEn, String provider, String regionId,
			String region,String zoneId, String attachStatus, String attachStatusCn,String instanceName, String diskType,
			Integer size,String diskCategory,String payType, String endTime, String userEmail) {
		this.diskId = diskId;
		this.diskName = diskName;
		this.providerEn = providerEn;
		this.provider = provider;
		this.regionId = regionId;
		this.region = region;
		this.zoneId = zoneId;
		this.attachStatus = attachStatus;
		this.attachStatusCn = attachStatusCn;
		this.instanceName = instanceName;
		this.diskType = diskType;
		this.size = size;
		this.diskCategory = diskCategory;
		this.payType = payType;
		this.endTime = endTime;
        this.userEmail = userEmail;
	}

	public String getDiskId() {
		return diskId;
	}

	public void setDiskId(String diskId) {
		this.diskId = diskId;
	}

	public String getDiskName() {
		return diskName;
	}

	public void setDiskName(String diskName) {
		this.diskName = diskName;
	}

	public String getProviderEn() {
		return providerEn;
	}

	public void setProviderEn(String providerEn) {
		this.providerEn = providerEn;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getAttachStatus() {
		return attachStatus;
	}

	public void setAttachStatus(String attachStatus) {
		this.attachStatus = attachStatus;
	}

	public String getAttachStatusCn() {
		return attachStatusCn;
	}

	public void setAttachStatusCn(String attachStatusCn) {
		this.attachStatusCn = attachStatusCn;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getDiskType() {
		return diskType;
	}

	public void setDiskType(String diskType) {
		this.diskType = diskType;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDiskCategory() {
		return diskCategory;
	}

	public void setDiskCategory(String diskCategory) {
		this.diskCategory = diskCategory;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public String getSnapshotId() {
		return snapshotId;
	}

	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
}
