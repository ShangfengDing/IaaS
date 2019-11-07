package com.appcloud.vm.fe.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vm_hd_endtime")
public class VmHdEndtime implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer userId;
	private String uuid;
	private String type;
	private Timestamp endTime;
	private String payType;
	
	public final static String TYPE_VM = "VM";
	public final static String TYPE_HD = "HD";
	public final static String TYPE_VMPACKAGE = "VMPACKAGE";
	public final static String TYPE_INSTANCETYPE = "INSTANCETYPE";
	public final static String PAYTYPE_HOUR = "按需";
	public final static String PAYTYPE_DAY = "包日";
	public final static String PAYTYPE_MONTH = "包月";
	public final static String PAYTYPE_YEAR = "包年";
	
	
	public VmHdEndtime() {
	}
	
	public VmHdEndtime(Integer userId, String uuid, String type, Timestamp endTime, String payType) {
		super();
		this.userId = userId;
		this.uuid = uuid;
		this.type = type;
		this.endTime = endTime;
		this.payType = payType;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@Column(name = "uuid")
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}	
	
	@Column(name = "end_time")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Column(name = "pay_type")
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
}
