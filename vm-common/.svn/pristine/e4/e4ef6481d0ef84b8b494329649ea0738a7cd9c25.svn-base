package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * @author lzc
 * 实例信息
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class Instance {
	private Integer id;
	private String uuid;		//全局唯一标识
	private InstanceTypeEnum type;		//类型，“j2ee”，“vm”
	private String appUuid;		//所属应用的uuid
	private String hostUuid;	//所在主机的uuid
	private String serviceIP;	//j2ee应用：所在主机的IP；VM应用：VM的IP地址
	private String servicePort;	//j2ee应用：所在主机的port；VM应用：空字符串
	private InstanceStatusEnum status;		//状态信息：高负载，低负载，正常
	private String extend;		//扩展信息

	private J2EEApp j2eeApp;	//j2ee应用详情
	private VMApp vmApp;		//vm详情
	private Host host;			//主机详情
	private Load load;			//Load负载信息
	
	public Instance() {
		super();
	}
	public Instance(Integer id, String uuid, InstanceTypeEnum type,
			String appUuid, String hostUuid, String serviceIP,
			String servicePort, InstanceStatusEnum status,
			String extend) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.type = type;
		this.appUuid = appUuid;
		this.hostUuid = hostUuid;
		this.serviceIP = serviceIP;
		this.servicePort = servicePort;
		this.status = status;
		this.extend = extend;
	}
	
	public Instance shallowCopy() {
		Instance instance = new Instance();
		instance.id = id;
		instance.uuid = uuid;
		instance.type = type;
		instance.appUuid = appUuid;
		instance.hostUuid = hostUuid;
		instance.serviceIP = serviceIP;
		instance.servicePort = servicePort;
		instance.status = status;
		instance.extend = extend;
		return instance;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public InstanceTypeEnum getType() {
		return type;
	}
	public void setType(InstanceTypeEnum type) {
		this.type = type;
	}
	public String getAppUuid() {
		return appUuid;
	}
	public void setAppUuid(String appUuid) {
		this.appUuid = appUuid;
	}
	public String getHostUuid() {
		return hostUuid;
	}
	public void setHostUuid(String hostUuid) {
		this.hostUuid = hostUuid;
	}
	public Host getHost() {
		return host;
	}
	public void setHost(Host host) {
		this.host = host;
	}
	public String getServiceIP() {
		return serviceIP;
	}
	public void setServiceIP(String serviceIP) {
		this.serviceIP = serviceIP;
	}
	public String getServicePort() {
		return servicePort;
	}
	public void setServicePort(String servicePort) {
		this.servicePort = servicePort;
	}
	public InstanceStatusEnum getStatus() {
		return status;
	}
	public void setStatus(InstanceStatusEnum status) {
		this.status = status;
	}
	public String getExtend() {
		return extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
	public Load getLoad() {
		return load;
	}
	public void setLoad(Load load) {
		this.load = load;
	}
	public J2EEApp getJ2eeApp() {
		return j2eeApp;
	}
	public void setJ2eeApp(J2EEApp j2eeApp) {
		this.j2eeApp = j2eeApp;
	}
	public VMApp getVmApp() {
		return vmApp;
	}
	public void setVmApp(VMApp vmApp) {
		this.vmApp = vmApp;
	}

	@Override
	public String toString() {
		return "Instance [id=" + id + ", uuid=" + uuid + ", type=" + type
				+ ", appUuid=" + appUuid + ", hostUuid=" + hostUuid
				+ ", serviceIP=" + serviceIP + ", servicePort=" + servicePort
				+ ", status=" + status + ", extend=" + extend + ", j2eeApp="
				+ j2eeApp + ", vmApp=" + vmApp + ", host=" + host + ", load="
				+ load + "]";
	}

	public static enum InstanceTypeEnum {
		J2EE, VM;
		
		public String toString() {
			switch (this) {
				case J2EE:
					return "j2ee";
				case VM:
					return "vm";
			}
			return super.toString();
		}
	}
	
	public static enum InstanceStatusEnum {
		HIGH_LOAD, NORMAL_LOAD, LOW_LOAD, CRASH;
		
		public String toString() {
			switch (this) {
				case HIGH_LOAD:
					return "高负载";
				case NORMAL_LOAD:
					return "负载正常";
				case LOW_LOAD:
					return "低负载";
				case CRASH:
					return "异常宕掉";
			}
			return super.toString();
		}
	}
}
