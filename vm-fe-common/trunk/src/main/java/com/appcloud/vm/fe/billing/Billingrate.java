package com.appcloud.vm.fe.billing;

import org.json.JSONException;
import org.json.JSONObject;

import com.appcloud.vm.fe.common.Constants;

public class Billingrate {

	private Integer id;
	private String name;
	private String description;
	private String ptype;
	private Integer cpu;
	private Integer memory;
	private Integer harddisk;
	private Integer bandwidth;
	private String dayPrice;
	private String monthPrice;
	private String yearPrice;
	private String hourPrice;
	private Integer clusterid;
	
	public Billingrate() {
		super();
	}

	public Billingrate(Integer id, String name, String description, Integer clusterid, String ptype,
			Integer cpu, Integer memory, Integer harddisk, Integer bandwidth,
			String yearPrice, String monthPrice, String dayPrice, String hourPrice) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.ptype = ptype;
		this.cpu = cpu;
		this.memory = memory;
		this.harddisk = harddisk;
		this.bandwidth = bandwidth;
		this.dayPrice = dayPrice;
		this.monthPrice = monthPrice;
		this.yearPrice = yearPrice;
		this.hourPrice = hourPrice;
		this.clusterid = clusterid;
	}

	public Billingrate(JSONObject obj) throws JSONException {
		this.id = Integer.parseInt(obj.getString(Constants.BILLING_ID));
		this.name = obj.getString(Constants.BILLING_NAME);
		this.description = obj.getString(Constants.BILLING_DESCRIPTION);
		this.ptype = obj.getString(Constants.BILLING_PTYPE);
		this.clusterid = Integer.parseInt(obj.getString(Constants.BILLING_CLUSTERID));
		this.cpu = Integer.parseInt(obj.getString(Constants.BILLING_CPU));
		this.memory = Integer.parseInt(obj.getString(Constants.BILLING_MEM));
		this.harddisk = Integer.parseInt(obj.getString(Constants.BILLING_HD));
		this.bandwidth = Integer.parseInt(obj.getString(Constants.BILLING_BW));
		this.yearPrice = obj.getString(Constants.BILLING_YEARPRICE);
		this.monthPrice = obj.getString(Constants.BILLING_MONTHPRICE);
		this.dayPrice = obj.getString(Constants.BILLING_DAYPRICE);
		this.hourPrice = obj.getString(Constants.BILLING_HOURPRICE);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCpu() {
		return cpu;
	}

	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}

	public Integer getMemory() {
		return memory;
	}

	public void setMemory(Integer memory) {
		this.memory = memory;
	}

	public Integer getHarddisk() {
		return harddisk;
	}

	public void setHarddisk(Integer harddisk) {
		this.harddisk = harddisk;
	}

	public Integer getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(Integer bandwidth) {
		this.bandwidth = bandwidth;
	}

	public String getDayPrice() {
		return dayPrice;
	}

	public void setDayPrice(String dayPrice) {
		this.dayPrice = dayPrice;
	}

	public String getMonthPrice() {
		return monthPrice;
	}

	public void setMonthPrice(String monthPrice) {
		this.monthPrice = monthPrice;
	}

	public String getYearPrice() {
		return yearPrice;
	}

	public void setYearPrice(String yearPrice) {
		this.yearPrice = yearPrice;
	}

	public String getHourPrice() {
		return hourPrice;
	}

	public void setHourPrice(String hourPrice) {
		this.hourPrice = hourPrice;
	}

	public Integer getClusterid() {
		return clusterid;
	}

	public void setClusterid(Integer clusterid) {
		this.clusterid = clusterid;
	}

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}
	
}
