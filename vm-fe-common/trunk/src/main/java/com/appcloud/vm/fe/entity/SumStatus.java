package com.appcloud.vm.fe.entity;

public class SumStatus {

	/**
	 *	云主机的总数
	 */
	private Integer instanceSum;
	/**
	 *	云主机的运行中数目
	 */
	private Integer instanceAcSum;
	/**
	 *	云主机的即将过期中数目
	 */
	private Integer instanceExpSum;
	/**
	 *	云硬盘的总数
	 */
	private Integer diskSum;
	/**
	 *	云硬盘的运行中数目
	 */
	private Integer diskAcSum;
	/**
	 *	云硬盘的即将过期中数目
	 */
	private Integer diskExpSum;
	/**
	 *	快照的总数
	 */
	private Integer backupSum;
	/**
	 *	镜像的总数
	 */
	private Integer shotSum;
	/**
	 *	镜像的总数
	 */
	private String balance;

	public SumStatus(){}

	public SumStatus(Integer instanceSum,Integer instanceAcSum,Integer instanceExpSum,Integer diskSum,Integer diskAcSum,
			Integer diskExpSum,Integer backupSum,Integer shotSum,String balance){
		this.instanceSum = instanceSum;
		this.instanceAcSum = instanceAcSum;
		this.instanceExpSum = instanceExpSum;
		this.diskSum = diskSum;
		this.diskAcSum = diskAcSum;
		this.diskExpSum = diskExpSum;
		this.backupSum = backupSum;
		this.shotSum = shotSum;
		this.balance = balance;
	}
	public Integer getInstanceSum() {
		return instanceSum;
	}
	public void setInstanceSum(Integer instanceSum) {
		this.instanceSum = instanceSum;
	}
	public Integer getInstanceAcSum() {
		return instanceAcSum;
	}
	public void setInstanceAcSum(Integer instanceAcSum) {
		this.instanceAcSum = instanceAcSum;
	}
	public Integer getInstanceExpSum() {
		return instanceExpSum;
	}
	public void setInstanceExpSum(Integer instanceExpSum) {
		this.instanceExpSum = instanceExpSum;
	}
	public Integer getDiskSum() {
		return diskSum;
	}
	public void setDiskSum(Integer diskSum) {
		this.diskSum = diskSum;
	}
	public Integer getDiskAcSum() {
		return diskAcSum;
	}
	public void setDiskAcSum(Integer diskAcSum) {
		this.diskAcSum = diskAcSum;
	}
	public Integer getDiskExpSum() {
		return diskExpSum;
	}
	public void setDiskExpSum(Integer diskExpSum) {
		this.diskExpSum = diskExpSum;
	}
	public Integer getBackupSum() {
		return backupSum;
	}
	public void setBackupSum(Integer backupSum) {
		this.backupSum = backupSum;
	}
	public Integer getShotSum() {
		return shotSum;
	}
	public void setShotSum(Integer shotSum) {
		this.shotSum = shotSum;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}	
}
