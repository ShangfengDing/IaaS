package com.appcloud.vm.fe.entity;

import java.util.List;

public class InstanceDetail {
	/**
	 *	云主机的uuid
	 */
	private String instanceId;
	/**
	 *	云主机名称
	 */
	private String instanceName;
	/**
	 *	云主机所在提供商英文
	 */
	private String providerEn;
	/**
	 *	云主机所在提供商中文
	 */
	private String provider;
	/**
	 *	云主机所在地域英文
	 */
	private String regionIdEn;
	/**
	 *	云主机所在地域Id
	 */
	private String regionId;
	/**
	 *	云主机所在可用区
	 */
	private String zoneId;
	/**
	 *	云主机当前状态
	 */
	private String status;
	/**
	 *  云主机描述
	 */
	private String description;
	/**
	 *  云主机镜像Id
	 */
    private String imageId;
	/**
	 *	云主机操作当前操作系统
	 */
	private String osType;
	/**
	 *  云主机规格
	 */
	private String instanceType;
	/**
	 *	云主机cpu信息
	 */
	private Integer vcpus;
	/**
	 *	云主机内存信息
	 */
	public Integer memory;
	/**
	 *	云主机带宽（公网）
	 */
	public Integer bandwidth;
	/**
	 *	云主机公网IP
	 */
	private String publicIpAddress;
	/**
	 *	云主机内网IP
	 */
	private String privateIpAddress;
	/**
	 *	云主机计费信息
	 */
	private String payType;
	/**
	 *  云主机创建时间
	 */
	private String creationTime;
	/**
	 *	云主机到期时间
	 */
	private String endTime;
	/**
	 *  系统盘Id
	 */
	private String systemDiskId;
	/**
	 *  系统盘名称
	 */
	private String systemDiskName;
	/**
	 *  系统盘大小
	 */
	private Integer systemDiskSize;
	/**
	 *  系统盘快照Ids
	 */
	private List<String> systemDiskSnapshotIds;
	/**
	 *  硬盘列表
	 */
	private List<DiskInventory> diskList;
	/**
	 *  防火墙规则ID
	 */
	private String SecurityGroupId;

	/**
	 * 防火墙名称
	 */
	private String securityGroupName;
	/**
	 * 用户Email
	 */
	private String userEmail;
	
    public InstanceDetail() {}

	public InstanceDetail(String instanceId, String instanceName,String providerEn,
			String provider,String regionIdEn, String regionId, String zoneId, String status,
			String description, String imageId, String osType, String instanceType, Integer vcpus, Integer memory,
			Integer bandwidth, String publicIpAddress, String privateIpAddress,
			String payType, String creationTime, String endTime, String systemDiskId, String systemDiskName, Integer systemDiskSize,
			List<String> systemDiskSnapshotIds, List<DiskInventory> diskList, String securityGroupId,String securityGroupName,String userEmail) {
		super();
		this.instanceId = instanceId;
		this.instanceName = instanceName;
		this.providerEn = providerEn;
		this.provider = provider;
		this.regionIdEn = regionIdEn;
		this.regionId = regionId;
		this.zoneId = zoneId;
		this.status = status;
		this.description = description;
		this.imageId = imageId;
		this.osType = osType;
		this.instanceType = instanceType;
		this.vcpus = vcpus;
		this.memory = memory;
		this.bandwidth = bandwidth;
		this.publicIpAddress = publicIpAddress;
		this.privateIpAddress = privateIpAddress;
		this.payType = payType;
		this.creationTime = creationTime;
		this.endTime = endTime;
		this.diskList = diskList;
		this.SecurityGroupId = securityGroupId;
		this.securityGroupName = securityGroupName;
		this.systemDiskId = systemDiskId;
		this.systemDiskName = systemDiskName;
		this.systemDiskSize = systemDiskSize;
		this.systemDiskSnapshotIds = systemDiskSnapshotIds;
		this.userEmail = userEmail;
	}
	
	
	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public Integer getVcpus() {
		return vcpus;
	}

	public void setVcpus(Integer vcpus) {
		this.vcpus = vcpus;
	}

	public Integer getMemory() {
		return memory;
	}

	public void setMemory(Integer memory) {
		this.memory = memory;
	}

	public Integer getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(Integer bandwidth) {
		this.bandwidth = bandwidth;
	}

	public String getPublicIpAddress() {
		return publicIpAddress;
	}

	public void setPublicIpAddress(String publicIpAddress) {
		this.publicIpAddress = publicIpAddress;
	}

	public String getPrivateIpAddress() {
		return privateIpAddress;
	}

	public void setPrivateIpAddress(String privateIpAddress) {
		this.privateIpAddress = privateIpAddress;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getSecurityGroupId() {
		return SecurityGroupId;
	}

	public void setSecurityGroupId(String securityGroupId) {
		SecurityGroupId = securityGroupId;
	}

	public String getSecurityGroupName() {
		return securityGroupName;
	}

	public void setSecurityGroupName(String securityGroupName) {
		this.securityGroupName = securityGroupName;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public Integer getSystemDiskSize() {
		return systemDiskSize;
	}

	public void setSystemDiskSize(Integer systemDiskSize) {
		this.systemDiskSize = systemDiskSize;
	}

	public List<DiskInventory> getDiskList() {
		return diskList;
	}

	public void setDiskList(List<DiskInventory> diskList) {
		this.diskList = diskList;
	}

	public String getSystemDiskId() {
		return systemDiskId;
	}

	public void setSystemDiskId(String systemDiskId) {
		this.systemDiskId = systemDiskId;
	}

	public String getSystemDiskName() {
		return systemDiskName;
	}

	public void setSystemDiskName(String systemDiskName) {
		this.systemDiskName = systemDiskName;
	}

	public List<String> getSystemDiskSnapshotIds() {
		return systemDiskSnapshotIds;
	}

	public void setSystemDiskSnapshotIds(List<String> systemDiskSnapshotIds) {
		this.systemDiskSnapshotIds = systemDiskSnapshotIds;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getInstanceType() {
		return instanceType;
	}

	public void setInstanceType(String instanceType) {
		this.instanceType = instanceType;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getRegionIdEn() {
		return regionIdEn;
	}

	public void setRegionIdEn(String regionIdEn) {
		this.regionIdEn = regionIdEn;
	}

}
