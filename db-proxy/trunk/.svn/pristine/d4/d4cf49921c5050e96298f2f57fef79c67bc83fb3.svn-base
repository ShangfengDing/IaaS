/**
 * File: VMAppTable.java
 * Author: weed
 * Create Time: 2012-11-25
 */
package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.VMApp;

/**
 * @author weed
 *
 */
@Entity
@Table(name="vm_app")
public class VMAppTable extends VMApp{

	public VMAppTable() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public VMAppTable(VMApp vmApp) {
		super();
		this.setId(vmApp.getId());
		this.setDevId(vmApp.getDevId());
		this.setUuid(vmApp.getUuid());
		this.setTag(vmApp.getTag());
		this.setTemplateId(vmApp.getTemplateId());
		this.setSystemId(vmApp.getSystemId());
		this.setMenuId(vmApp.getMenuId());
		this.setIp(vmApp.getIp());
		this.setMac(vmApp.getMac());
		this.setState(vmApp.getState());
		this.setStateInfo(vmApp.getStateInfo());
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	public Integer getId() {
		return super.getId();
	}

	public void setId(Integer id) {
		super.setId(id);
	}

	@Column(name="dev_id")
	public Integer getDevId() {
		return super.getDevId();
	}

	public void setDevId(Integer devId) {
		super.setDevId(devId);
	}

	@Column(name="uuid")
	public String getUuid() {
		return super.getUuid();
	}

	public void setUuid(String uuid) {
		super.setUuid(uuid);
	}

	@Column(name="tag")
	public String getTag() {
		return super.getTag();
	}

	public void setTag(String tag) {
		super.setTag(tag);
	}

	@Column(name="template_id")
	public Integer getTemplateId() {
		return super.getTemplateId();
	}

	public void setTemplateId(Integer templateId) {
		super.setTemplateId(templateId);
	}

	@Column(name="system_id")
	public Integer getSystemId() {
		return super.getSystemId();
	}

	public void setSystemId(Integer systemId) {
		super.setSystemId(systemId);
	}

	@Column(name="menu_id")
	public Integer getMenuId() {
		return super.getMenuId();
	}

	public void setMenuId(Integer menuId) {
		super.setMenuId(menuId);
	}

	@Column(name="ip")
	public String getIp() {
		return super.getIp();
	}

	public void setIp(String ip) {
		super.setIp(ip);
	}

	@Column(name="mac")
	public String getMac() {
		return super.getMac();
	}

	public void setMac(String mac) {
		super.setMac(mac);
	}
	
	@Column(name="state")
	public Integer getState() {
		return super.getState();
	}

	public void setState(Integer state) {
		super.setState(state);
	}
	
	@Column(name="state_info")
	public String getStateInfo() {
		return super.getStateInfo();
	}

	public void setStateInfo(String stateInfo) {
		super.setStateInfo(stateInfo);
	}
//
//	@Column(name="status")
//	@Enumerated(EnumType.STRING)
//	public VMAppStatusEnum getStatus() {
//		return super.getStatus();
//	}
//
//	public void setStatus(VMAppStatusEnum status) {
//		super.setStatus(status);
//	}
}
