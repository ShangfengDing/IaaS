package com.appcloud.vm.fe.entity;

/**
 * 此类用于地域的中英文的转换
 * @author rain
 *
 */
public class RegionId {

	private String regionId;//地域对应的英文
	private String regionCn;//地域对应的中文

	public RegionId(String regionId,String regionCn) {
		// TODO Auto-generated constructor stub
		this.regionId = regionId;
		this.regionCn = regionCn;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegionCn() {
		return regionCn;
	}

	public void setRegionCn(String regionCn) {
		this.regionCn = regionCn;
	}

}
