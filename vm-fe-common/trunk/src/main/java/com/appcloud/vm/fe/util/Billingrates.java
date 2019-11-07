package com.appcloud.vm.fe.util;

public class Billingrates {
	/**
	 * 原vm-billingrate工程的com.appcloud.vm.billingrate.util.Billingrates类
	 * 合并至此 
	 */
	private int id;

	private String name;

	private String ptype;
	
	private String description;

	private int clusterid;

	private int cpu;

	private int memory;

	private int bandwidth;

	private int harddisk;

	private String hourprice;

	private String dayprice;

	private String monthprice;

	private String yearprice;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getClusterid() {
		return clusterid;
	}

	public void setClusterid(int clusterid) {
		this.clusterid = clusterid;
	}

	public int getCpu() {
		return cpu;
	}

	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public int getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(int bandwidth) {
		this.bandwidth = bandwidth;
	}

	public int getHarddisk() {
		return harddisk;
	}

	public void setHarddisk(int harddisk) {
		this.harddisk = harddisk;
	}

	public String getHourprice() {
		return hourprice;
	}

	public void setHourprice(String hourprice) {
		this.hourprice = hourprice;
	}

	public String getDayprice() {
		return dayprice;
	}

	public void setDayprice(String dayprice) {
		this.dayprice = dayprice;
	}

	public String getMonthprice() {
		return monthprice;
	}

	public void setMonthprice(String monthprice) {
		this.monthprice = monthprice;
	}

	public String getYearprice() {
		return yearprice;
	}

	public void setYearprice(String yearprice) {
		this.yearprice = yearprice;
	}

}
