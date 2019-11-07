package com.appcloud.vm.fe.entity;

import java.util.List;

import appcloud.openapi.datatype.ImageDetailSet;
import appcloud.openapi.datatype.InstanceTypeSet;
import appcloud.openapi.datatype.RegionItem;
import appcloud.openapi.datatype.ZoneItem;

/**
 * 某一云服务提供商可提供的提供的云主机产品及其属性
 */
public class InstanceProduct {

	/**
	 *	云服务提供商
	 */
	private String provider;
	/**
	 *	某个云服务提供商下的所有region信息
	 */
	private List<RegionItem> regionList;
	/**
	 *	某个region下的所有可用区信息
	 */
	private List<List<ZoneItem>> zoneList;
	/**
	 *	某个可用区下的所有可用镜像
	 */
	private ImageDetailSet images;
	/**
	 *	某个可用区下的所有云主机配置信息
	 */
	private InstanceTypeSet instanceTypeSet;

	public InstanceProduct(){};

	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public List<RegionItem> getRegionList() {
		return regionList;
	}
	public void setRegionList(List<RegionItem> regionList) {
		this.regionList = regionList;
	}
	public List<List<ZoneItem>> getZoneList() {
		return zoneList;
	}
	public void setZoneList(List<List<ZoneItem>> zoneList) {
		this.zoneList = zoneList;
	}
	public ImageDetailSet getImages() {
		return images;
	}
	public void setImages(ImageDetailSet images) {
		this.images = images;
	}
	public InstanceTypeSet getInstanceType() {
		return instanceTypeSet;
	}
	public void setInstanceTypeSet(InstanceTypeSet instanceType) {
		this.instanceTypeSet = instanceType;
	}
	
}
