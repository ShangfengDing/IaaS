package com.appcloud.vm.fe.entity;

import java.util.Date;
import java.util.List;

/**
 *  此类用于磁盘详情页的显示
 */
public class DiskDetail {
	private String DiskId;
	private String DiskName;
	private String regionId;
	private String region;
	private String providerEn;
	private String provider;
	private String Description;
	private String Status;
	private String StatusCn;
	private Integer Size;
	private String DiskCategory;
	private String ZoneId;
	private String CreateTime;
	private String DiskType;
	private String Device;
	private String InstanceId;	
	private String ChargeType;
	private String ExpiredTime;
	private List<ShotProfile> snapshotList;
		
	public DiskDetail() {
		super();
	}

	public DiskDetail(String diskId, String diskName, String regionId, String region, String providerEn, String provider,
			String description, String status, String statusCn, Integer size,
			String diskCategory, String zoneId,
			String createTime, String diskType, String device,
			String instanceId, String chargeType, String expiredTime, List<ShotProfile> snapshotList) {
		super();
        DiskId = diskId;
        DiskName = diskName;
        this.regionId = regionId;
        this.region = region;
        this.providerEn = providerEn;
		this.provider = provider;
		Description = description;
		Status = status;
		StatusCn = statusCn;
		Size = size;
		DiskCategory = diskCategory;
		ZoneId = zoneId;
		CreateTime = createTime;
		DiskType = diskType;
		Device = device;
		InstanceId = instanceId;
		ChargeType = chargeType;
		ExpiredTime = expiredTime;
		this.snapshotList = snapshotList;
	}
	
	public String getDiskId() {
		return DiskId;
	}
	public void setDiskId(String diskId) {
		DiskId = diskId;
	}
	public String getDiskName() {
		return DiskName;
	}
	public void setDiskName(String diskName) {
		DiskName = diskName;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public Integer getSize() {
		return Size;
	}
	public void setSize(Integer size) {
		Size = size;
	}
	public String getDiskCategory() {
		return DiskCategory;
	}
	public void setDiskCategory(String diskCategory) {
		DiskCategory = diskCategory;
	}
	public String getZoneId() {
		return ZoneId;
	}
	public void setZoneId(String zoneId) {
		ZoneId = zoneId;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public String getDiskType() {
		return DiskType;
	}
	public void setDiskType(String diskType) {
		DiskType = diskType;
	}
	public String getDevice() {
		return Device;
	}
	public void setDevice(String device) {
		Device = device;
	}
	public String getInstanceId() {
		return InstanceId;
	}
	public void setInstanceId(String instanceId) {
		InstanceId = instanceId;
	}
	public String getChargeType() {
		return ChargeType;
	}
	public void setChargeType(String chargeType) {
		ChargeType = chargeType;
	}
	public String getExpiredTime() {
		return ExpiredTime;
	}
	public void setExpiredTime(String expiredTime) {
		ExpiredTime = expiredTime;
	}

	public String getProviderEn() {
		return providerEn;
	}

	public void setProviderEn(String providerEn) {
		this.providerEn = providerEn;
	}

	public String getStatusCn() {
		return StatusCn;
	}

	public void setStatusCn(String statusCn) {
		StatusCn = statusCn;
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

	public List<ShotProfile> getSnapshotList() {
		return snapshotList;
	}

	public void setSnapshotList(List<ShotProfile> snapshotList) {
		this.snapshotList = snapshotList;
	}
}
