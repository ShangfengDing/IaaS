package com.appcloud.vm.fe.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="service_num")
public class ServiceNum {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id") 
	private Integer id;
	
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="instance_num")
	private Integer instanceNum;
	
	@Column(name="disk_num")
	private Integer diskNum;
	
	@Column(name="snapshot_num")
	private Integer snapshotNum;
	
	@Column(name="image_num")
	private Integer imageNum;
	
	@Column(name="securitygroup_num")
	private Integer securitygroupNum;

	@Column(name="instance_ac_num")
	private Integer instanceAcNum;
	
	@Column(name="disk_ac_num")
	private Integer diskAcNum;

	@Column(name="instance_ref_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date instanceRefTime;

	@Column(name="disk_ref_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date diskRefTime;

	@Column(name="snapshot_ref_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date snapshotRefTime;

	@Column(name="image_ref_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date imageRefTime;

	@Column(name="securitygroup_ref_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date securitygroupRefTime;




	public ServiceNum() {
		super();
	}

	public ServiceNum(Integer userId, Integer instanceNum, Integer diskNum, Integer snapshotNum, Integer imageNum, Integer securitygroupNum, Integer instanceAcNum, Integer diskAcNum, Date instanceRefTime, Date diskRefTime, Date snapshotRefTime, Date imageRefTime, Date securitygroupRefTime) {
		this.userId = userId;
		this.instanceNum = instanceNum;
		this.diskNum = diskNum;
		this.snapshotNum = snapshotNum;
		this.imageNum = imageNum;
		this.securitygroupNum = securitygroupNum;
		this.instanceAcNum = instanceAcNum;
		this.diskAcNum = diskAcNum;
		this.instanceRefTime = instanceRefTime;
		this.diskRefTime = diskRefTime;
		this.snapshotRefTime = snapshotRefTime;
		this.imageRefTime = imageRefTime;
		this.securitygroupRefTime = securitygroupRefTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getInstanceNum() {
		return instanceNum;
	}

	public void setInstanceNum(Integer instanceNum) {
		this.instanceNum = instanceNum;
	}

	public Integer getDiskNum() {
		return diskNum;
	}

	public void setDiskNum(Integer diskNum) {
		this.diskNum = diskNum;
	}

	public Integer getSnapshotNum() {
		return snapshotNum;
	}

	public void setSnapshotNum(Integer snapshotNum) {
		this.snapshotNum = snapshotNum;
	}

	public Integer getImageNum() {
		return imageNum;
	}

	public void setImageNum(Integer imageNum) {
		this.imageNum = imageNum;
	}

	public Integer getSecuritygroupNum() {
		return securitygroupNum;
	}

	public void setSecuritygroupNum(Integer securitygroupNum) {
		this.securitygroupNum = securitygroupNum;
	}

	public Integer getInstanceAcNum() {
		return instanceAcNum;
	}

	public void setInstanceAcNum(Integer instanceAcNum) {
		this.instanceAcNum = instanceAcNum;
	}

	public Integer getDiskAcNum() {
		return diskAcNum;
	}

	public void setDiskAcNum(Integer diskAcNum) {
		this.diskAcNum = diskAcNum;
	}

	public Date getInstanceRefTime() {
		return instanceRefTime;
	}

	public void setInstanceRefTime(Date instanceRefTime) {
		this.instanceRefTime = instanceRefTime;
	}

	public Date getDiskRefTime() {
		return diskRefTime;
	}

	public void setDiskRefTime(Date diskRefTime) {
		this.diskRefTime = diskRefTime;
	}

	public Date getSnapshotRefTime() {
		return snapshotRefTime;
	}

	public void setSnapshotRefTime(Date snapshotRefTime) {
		this.snapshotRefTime = snapshotRefTime;
	}

	public Date getImageRefTime() {
		return imageRefTime;
	}

	public void setImageRefTime(Date imageRefTime) {
		this.imageRefTime = imageRefTime;
	}

	public Date getSecuritygroupRefTime() {
		return securitygroupRefTime;
	}

	public void setSecuritygroupRefTime(Date securitygroupRefTime) {
		this.securitygroupRefTime = securitygroupRefTime;
	}

	@Override
	public String toString() {
		return "ServiceNum{" +
				"id=" + id +
				", userId=" + userId +
				", instanceNum=" + instanceNum +
				", diskNum=" + diskNum +
				", snapshotNum=" + snapshotNum +
				", imageNum=" + imageNum +
				", securitygroupNum=" + securitygroupNum +
				", instanceAcNum=" + instanceAcNum +
				", diskAcNum=" + diskAcNum +
				", instanceRefTime=" + instanceRefTime +
				", diskRefTime=" + diskRefTime +
				", snapshotRefTime=" + snapshotRefTime +
				", imageRefTime=" + imageRefTime +
				", securitygroupRefTime=" + securitygroupRefTime +
				'}';
	}
}
