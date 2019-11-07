package appcloud.dbproxy.mysql.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.Service;

/**
 * 
 * @author wylazy
 *
 */

@Entity
@Table(name="service")
public class ServiceTable extends Service {

	public ServiceTable() {}
	
	public ServiceTable(Service service) {
		super.setId(service.getId());
		super.setHostId(service.getHostId());
		super.setZoneId(service.getZoneId());
		super.setHostUuid(service.getHostUuid());
		super.setHostIp(service.getHostIp()); 
		super.setMonitorPort(service.getMonitorPort());
		super.setType(service.getType());
		super.setUpdateTime(service.getUpdateTime());
		super.setLastStartTime(service.getLastStartTime());
		super.setLastStopTime(service.getLastStopTime());
		super.setServiceStatus(service.getServiceStatus());
		super.setMetaData(service.getMetaData());
		super.setClusterId(service.getClusterId());
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

	@Column(name="host_id")
	public Integer getHostId() {
		return super.getHostId();
	}

	public void setHostId(Integer hostId) {
		super.setHostId(hostId);
	}

	@Column(name="host_uuid")
	public String getHostUuid() {
		return super.getHostUuid();
	}

	public void setHostUuid(String hostUuid) {
		super.setHostUuid(hostUuid);
	}

	@Column(name="host_ip")
	public String getHostIp() {
		return super.getHostIp();
	}

	public void setHostIp(String hostIp) {
		super.setHostIp(hostIp);
	}

	@Column(name="monitor_port")
	public Integer getMonitorPort() {
		return super.getMonitorPort();
	}

	public void setMonitorPort(Integer monitorPort) {
		super.setMonitorPort(monitorPort);
	}

	@Column(name="zone_id")
	public Integer getZoneId() {
		return super.getZoneId();
	}

	public void setZoneId(Integer zoneId) {
		super.setZoneId(zoneId);
	}
	
	@Column(name="cluster_id")
	public Integer getClusterId() {
		return super.getClusterId();
	}

	public void setClusterId(Integer clusterId) {
		super.setClusterId(clusterId);
	}
	
	@Column(name="type")
	@Enumerated(EnumType.STRING)
	public ServiceTypeEnum getType() {
		return super.getType();
	}

	public void setType(ServiceTypeEnum type) {
		super.setType(type);
	}
	
	@Column(name="update_time")
	public Timestamp getUpdateTime() {
		return super.getUpdateTime();
	}

	public void setUpdateTime(Timestamp updateTime) {
		super.setUpdateTime(updateTime);
	}
	
	@Column(name="last_start_time")
	public Timestamp getLastStartTime() {
		return super.getLastStartTime();
	}

	public void setLastStartTime(Timestamp lastStartTime) {
		super.setLastStartTime(lastStartTime);
	}

	@Column(name="last_stop_time")
	public Timestamp getLastStopTime() {
		return super.getLastStopTime();
	}

	public void setLastStopTime(Timestamp lastStopTime) {
		super.setLastStopTime(lastStopTime);
	}
	
	@Column(name="service_status")
	@Enumerated(EnumType.STRING)
	public ServiceStatus getServiceStatus() {
		return super.getServiceStatus();
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		super.setServiceStatus(serviceStatus);
	}
	
	@Column(name="meta_data")
	public String getMetaData() {
		return super.getMetaData();
	}

	public void setMetaData(String metaData) {
		super.setMetaData(metaData);
	}
}
