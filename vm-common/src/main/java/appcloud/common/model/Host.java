package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author lzc
 * 主机信息
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class Host {
	private Integer id;
	/** 自动获取配置的项 **/
	private String uuid;		//全局唯一标识，默认以机器网卡MAC地址生成
	
	private String ip;			//节点ip,私网地址
	
	/** 需要配置的项 **/
	// 拥有相同的publicVLAN privateVLAN才能被划归到一个
	private Integer publicVLAN; //公网VLAN号
	private Integer privateVLAN;//私网VLAN号
	private Integer availabilityZoneId;//所属数据中心 手动设定
	private Integer clusterId;	//计算节点所属集群id 在前台划归
	private HostTypeEnum type;	//计算节点，管理节点，存储结点
	private String location;	//“物理机”或者“虚拟机@192.168.1.13”
	
	private String name;     // 名称，只在Iaas中用到
	private String hypervisorType; //管理程序类型，只在Iaas中用到
	private Integer hypervisorVersion; //只在Iaas中用到

//	private String capability;	//可承载能力，“j2ee，vm”，“nginx，nginxController”
//	private String agentAddr;	//nodeagent地址
//	private String scheduleTag;	//AppMaster调度算法标识
	private String extend; 		//扩展信息
	/** 状态信息 **/
	private HostStatusEnum status;		//主机状态
	
	private String os;			//操作系统
	private Integer cpuMhz;		//cpu主频
	private Integer cpuCore;	//cpu核心数
	private Integer memoryMb;	//内存总大小
	private Integer diskMb;		//硬盘总大小
	private Integer networkMb;	//网络带宽
	private Integer j2eeInstanceNum;
	private Integer vmInstanceNum;
	
	private Load load;			//Load负载信息
	private NetworkLoad networkLoad;//网络负载信息
	private Cluster cluster; 	//集群详情
	private VmZone availabilityZone;//所属数据中心
	
	public Host() {
		super();
	}

    public Host(Integer id, String uuid, String ip, HostTypeEnum type,
			String location, Integer clusterId, Integer availabilityZoneId,
			Integer publicVLAN, Integer privateVLAN, HostStatusEnum status,
			String extend, String os, Integer cpuMhz, Integer cpuCore,
			Integer memoryMb, Integer diskMb, Integer networkMb, String name,
			String hypervisorType, Integer hypervisorVersion, Cluster cluster,
			Load load, NetworkLoad networkLoad, Integer j2eeInstanceNum,
			Integer vmInstanceNum, VmZone availabilityZone) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.ip = ip;
		this.type = type;
		this.location = location;
		this.clusterId = clusterId;
		this.availabilityZoneId = availabilityZoneId;
		this.publicVLAN = publicVLAN;
		this.privateVLAN = privateVLAN;
		this.status = status;
		this.extend = extend;
		this.os = os;
		this.cpuMhz = cpuMhz;
		this.cpuCore = cpuCore;
		this.memoryMb = memoryMb;
		this.diskMb = diskMb;
		this.networkMb = networkMb;
		this.name = name;
		this.hypervisorType = hypervisorType;
		this.hypervisorVersion = hypervisorVersion;
		this.cluster = cluster;
		this.load = load;
		this.networkLoad = networkLoad;
		this.j2eeInstanceNum = j2eeInstanceNum;
		this.vmInstanceNum = vmInstanceNum;
		this.availabilityZone = availabilityZone;
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
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public HostTypeEnum getType() {
		return type;
	}
	public void setType(HostTypeEnum type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getClusterId() {
		return clusterId;
	}
	public void setClusterId(Integer clusterId) {
		this.clusterId = clusterId;
	}
	public Cluster getCluster() {
		return cluster;
	}
	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	public HostStatusEnum getStatus() {
		return status;
	}
	public void setStatus(HostStatusEnum status) {
		this.status = status;
	}
	public String getExtend() {
		return extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public Integer getCpuMhz() {
		return cpuMhz;
	}
	public void setCpuMhz(Integer cpuMhz) {
		this.cpuMhz = cpuMhz;
	}
	public Integer getCpuCore() {
		return cpuCore;
	}
	public void setCpuCore(Integer cpuCore) {
		this.cpuCore = cpuCore;
	}
	public Integer getMemoryMb() {
		return memoryMb;
	}
	public void setMemoryMb(Integer memoryMb) {
		this.memoryMb = memoryMb;
	}
	public Integer getDiskMb() {
		return diskMb;
	}
	public void setDiskMb(Integer diskMb) {
		this.diskMb = diskMb;
	}
	public Integer getNetworkMb() {
		return networkMb;
	}
	public void setNetworkMb(Integer networkMb) {
		this.networkMb = networkMb;
	}
	public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getHypervisorType() {
        return hypervisorType;
    }
    public void setHypervisorType(String hypervisorType) {
        this.hypervisorType = hypervisorType;
    }
    public Integer getHypervisorVersion() {
        return hypervisorVersion;
    }
    public void setHypervisorVersion(Integer hypervisorVersion) {
        this.hypervisorVersion = hypervisorVersion;
    }
    public Load getLoad() {
		return load;
	}
	public void setLoad(Load load) {
		this.load = load;
	}
	public NetworkLoad getNetworkLoad() {
		return networkLoad;
	}
	public void setNetworkLoad(NetworkLoad networkLoad) {
		this.networkLoad = networkLoad;
	}
	public Integer getJ2eeInstanceNum() {
		return j2eeInstanceNum;
	}
	public void setJ2eeInstanceNum(Integer j2eeInstanceNum) {
		this.j2eeInstanceNum = j2eeInstanceNum;
	}
	public Integer getVmInstanceNum() {
		return vmInstanceNum;
	}
	public void setVmInstanceNum(Integer vmInstanceNum) {
		this.vmInstanceNum = vmInstanceNum;
	}

	public Integer getAvailabilityZoneId() {
		return availabilityZoneId;
	}

	public void setAvailabilityZoneId(Integer availabilityZoneId) {
		this.availabilityZoneId = availabilityZoneId;
	}

	public VmZone getAvailabilityZone() {
		return availabilityZone;
	}

	public void setAvailabilityZone(VmZone availabilityZone) {
		this.availabilityZone = availabilityZone;
	}
	
	

	@Override
	public String toString() {
		return "Host [id=" + id + ", uuid=" + uuid + ", ip=" + ip + ", type="
				+ type + ", location=" + location + ", clusterId=" + clusterId
				+ ", availabilityZoneId=" + availabilityZoneId
				+ ", publicVLAN=" + publicVLAN + ", privateVLAN=" + privateVLAN
				+ ", status=" + status + ", extend=" + extend + ", os=" + os
				+ ", cpuMhz=" + cpuMhz + ", cpuCore=" + cpuCore + ", memoryMb="
				+ memoryMb + ", diskMb=" + diskMb + ", networkMb=" + networkMb
				+ ", name=" + name + ", hypervisorType=" + hypervisorType
				+ ", hypervisorVersion=" + hypervisorVersion + ", cluster="
				+ cluster + ", load=" + load + ", networkLoad=" + networkLoad
				+ ", j2eeInstanceNum=" + j2eeInstanceNum + ", vmInstanceNum="
				+ vmInstanceNum + ", availabilityZone=" + availabilityZone
				+ "]";
	}

	public Integer getPublicVLAN() {
		return publicVLAN;
	}

	public void setPublicVLAN(Integer publicVLAN) {
		this.publicVLAN = publicVLAN;
	}

	public Integer getPrivateVLAN() {
		return privateVLAN;
	}

	public void setPrivateVLAN(Integer privateVLAN) {
		this.privateVLAN = privateVLAN;
	}

	public static enum HostTypeEnum {
		COMPUTE_NODE, FUNCTION_NODE, STORAGE_NODE ;
		
		public String toString() {
			switch (this) {
				case COMPUTE_NODE:
					return "计算节点";
				case FUNCTION_NODE:
					return "管理结点";
				case STORAGE_NODE:
				    return "存储结点";
			}
			return super.toString();
		}
	}

	public static enum HostStatusEnum {
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
					return "死机";
			}
			return super.toString();
		}
	}
}
