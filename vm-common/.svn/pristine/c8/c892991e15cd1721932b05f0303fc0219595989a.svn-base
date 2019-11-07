package appcloud.common.model;

import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * added in version 3.5
 *
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class Service{

	private Integer id;
	private Integer hostId;
	private String hostUuid;
	private String hostIp;
	private Integer monitorPort;
	private Integer zoneId;
	private Integer clusterId;    //仅DHCP-CONTROLLER VOLUME—PROVIDER VM—CONTROLLER会从属于某
	private ServiceTypeEnum type;	//服务的类型,如：db-proxy volume-scheduler
	private Timestamp updateTime;
	private Timestamp lastStartTime;
	private Timestamp lastStopTime;
	private ServiceStatus serviceStatus;
	private String metaData;
	
	private Host host; //Service所在的Host
	
	public Service() {
		// TODO Auto-generated constructor stub
	}


	public Service(Integer id, Integer hostId, String hostUuid, String hostIp,
			Integer monitorPort, Integer zoneId, Integer clusterId,
			ServiceTypeEnum type, Timestamp updateTime,
			Timestamp lastStartTime, Timestamp lastStopTime,
			ServiceStatus serviceStatus, String metaData, Host host) {
		super();
		this.id = id;
		this.hostId = hostId;
		this.hostUuid = hostUuid;
		this.hostIp = hostIp;
		this.monitorPort = monitorPort;
		this.zoneId = zoneId;
		this.clusterId = clusterId;
		this.type = type;
		this.updateTime = updateTime;
		this.lastStartTime = lastStartTime;
		this.lastStopTime = lastStopTime;
		this.serviceStatus = serviceStatus;
		this.metaData = metaData;
		this.host = host;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getHostId() {
		return hostId;
	}

	public void setHostId(Integer hostId) {
		this.hostId = hostId;
	}

	public String getHostUuid() {
		return hostUuid;
	}

	public void setHostUuid(String hostUuid) {
		this.hostUuid = hostUuid;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public Integer getMonitorPort() {
		return monitorPort;
	}

	public void setMonitorPort(Integer monitorPort) {
		this.monitorPort = monitorPort;
	}

	public Integer getZoneId() {
		return zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}
	
	public ServiceTypeEnum getType() {
		return type;
	}

	public void setType(ServiceTypeEnum type) {
		this.type = type;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getLastStartTime() {
		return lastStartTime;
	}

	public void setLastStartTime(Timestamp lastStartTime) {
		this.lastStartTime = lastStartTime;
	}

	public Timestamp getLastStopTime() {
		return lastStopTime;
	}

	public void setLastStopTime(Timestamp lastStopTime) {
		this.lastStopTime = lastStopTime;
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public String getMetaData() {
		return metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}



	@Override
	public String toString() {
		return "Service [id=" + id + ", hostId=" + hostId + ", hostUuid="
				+ hostUuid + ", hostIp=" + hostIp + ", monitorPort="
				+ monitorPort + ", zoneId=" + zoneId + ", clusterId="
				+ clusterId + ", type=" + type + ", updateTime=" + updateTime
				+ ", lastStartTime=" + lastStartTime + ", lastStopTime="
				+ lastStopTime + ", serviceStatus=" + serviceStatus
				+ ", metaData=" + metaData + ", host=" + host + "]";
	}


	public Integer getClusterId() {
		return clusterId;
	}

	public void setClusterId(Integer clusterId) {
		this.clusterId = clusterId;
	}

	public static enum ServiceStatus {
		INIT,
		RUNNING,
		STOPED
	}
	
	public static enum ServiceTypeEnum {
		UNKNOWN,RESOURCE_SCHEDULER, VOLUME_SCHEDULER, VOLUME_PROVIDER, VM_SCHEDULER, VM_CONTROLLER, 
		IMAGE_SERVER, NODE_MONITOR, NETWORK_PROVIDER, DHCP_CONTROLLER,  FLOW_CONTROLLER, LOL_SERVER;
		
		public String toString() {
			switch (this) {
				case RESOURCE_SCHEDULER:
					return "resource-scheduler";
				case VOLUME_SCHEDULER:
					return "volume-scheduler";
				case VOLUME_PROVIDER:
				    return "volume-provider";
				case VM_SCHEDULER:
				    return "vm-scheduler";
				case VM_CONTROLLER:
				    return "vm-controller";				    
				case IMAGE_SERVER:
					return "image-server";
				case NODE_MONITOR:
					return "node-monitor";
				case NETWORK_PROVIDER:	
					return "network-provider";
				case DHCP_CONTROLLER:
					return "dhcp-controller";
				case FLOW_CONTROLLER:
					return "flow-controller";
				case LOL_SERVER:
					return "lol-server";
				case UNKNOWN:
					return "unknown";
			}
			return super.toString();
		}
		
		public static ServiceTypeEnum toEnum(String type) {
			if (type.equalsIgnoreCase("resource-scheduler")) {
				return RESOURCE_SCHEDULER;
			} else if (type.equalsIgnoreCase("volume-scheduler")) {
				return VOLUME_SCHEDULER;
			} else if (type.equalsIgnoreCase("volume-provider")) {
				return VOLUME_PROVIDER;
			} else if (type.equalsIgnoreCase("vm-scheduler")) {
				return VM_SCHEDULER;
			} else if (type.equalsIgnoreCase("vm-controller")) {
				return VM_CONTROLLER;
			} else if (type.equalsIgnoreCase("image-server")) {
				return IMAGE_SERVER;
			} else if (type.equalsIgnoreCase("node-monitor")) {
				return NODE_MONITOR;
			} else if (type.equalsIgnoreCase("network-provider")) {
				return NETWORK_PROVIDER;
			} else if (type.equalsIgnoreCase("dhcp-controller")) {
				return DHCP_CONTROLLER;
			} else if (type.equalsIgnoreCase("flow-controller")) {
				return FLOW_CONTROLLER;
			} else if (type.equalsIgnoreCase("lol-server")) {
				return LOL_SERVER;
			} else {
				return UNKNOWN;
			}
		}
	}
}
