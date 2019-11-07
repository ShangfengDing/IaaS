/**
 * File: InstanceTable.java
 * Author: weed
 * Create Time: 2012-11-25
 */
package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.Host;
import appcloud.common.model.Instance;
import appcloud.common.model.J2EEApp;
import appcloud.common.model.Load;
import appcloud.common.model.VMApp;

/**
 * @author weed
 *
 */
@Entity
@Table(name="instance")
public class InstanceTable extends Instance {

	public InstanceTable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InstanceTable(Instance instance) {
		// TODO Auto-generated constructor stub
		this.setAppUuid(instance.getAppUuid());
		this.setExtend(instance.getExtend());
		this.setHostUuid(instance.getHostUuid());
		this.setId(instance.getId());
		this.setServiceIP(instance.getServiceIP());
		this.setServicePort(instance.getServicePort());
		this.setStatus(instance.getStatus());
		this.setType(instance.getType());
		this.setUuid(instance.getUuid());
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
	
    @Column(name="uuid")
	public String getUuid() {
		return super.getUuid();
	}
	public void setUuid(String uuid) {
		super.setUuid(uuid);
	}
	
    @Column(name="type")
	@Enumerated(EnumType.STRING)
	public InstanceTypeEnum getType() {
		return super.getType();
	}
	public void setType(InstanceTypeEnum type) {
		super.setType(type);
	}
	
	@Column(name="app_uuid")
	public String getAppUuid() {
		return super.getAppUuid();
	}
	public void setAppUuid(String appUuid) {
		super.setAppUuid(appUuid);
	}
	
	@Column(name="host_uuid")
	public String getHostUuid() {
		return super.getHostUuid();
	}
	public void setHostUuid(String hostUuid) {
		super.setHostUuid(hostUuid);
	}

	@Column(name="service_ip")
	public String getServiceIP() {
		return super.getServiceIP();
	}
	public void setServiceIP(String serviceIP) {
		super.setServiceIP(serviceIP);
	}
	
	@Column(name="service_port")
	public String getServicePort() {
		return super.getServicePort();
	}
	public void setServicePort(String servicePort) {
		super.setServicePort(servicePort);
	}
	
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	public InstanceStatusEnum getStatus() {
		return super.getStatus();
	}
	public void setStatus(InstanceStatusEnum status) {
		super.setStatus(status);
	}
	
	@Column(name="extend")
	public String getExtend() {
		return super.getExtend();
	}
	public void setExtend(String extend) {
		super.setExtend(extend);
	}
}
