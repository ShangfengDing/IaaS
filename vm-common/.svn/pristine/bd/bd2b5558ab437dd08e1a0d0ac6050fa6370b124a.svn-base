package appcloud.common.model;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author lzc
 *	集群信息
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class Cluster {
	private Integer id;
	private String name;			//集群名称
	private String resrcStrategyUuid;//资源调度策略id
	private Integer taskStrategyId;	//任务分发策略id
	private String extend;			//扩展字段
	
	private Timestamp createdTime;
	private Timestamp updatedTime;
	private Integer availabilityZoneId;

	private Integer publicVLAN; //公网VLAN号
	private Integer privateVLAN;//私网VLAN号
	
	private Integer cpuOversell;//cpu超售比例，100表示1:1,200表示1:2
	private Integer memoryOversell;//内存超售比例，100表示1:1,200表示1:2
	private Integer diskOversell;//硬盘超售比例，100表示1:1,200表示1:2
	
	private List<ResourceStrategy> resrcStrategys;//资源调度策略详情	
	private TaskStrategy taskStrategy;	//任务分发策略详情
	private List<Host> hosts;			//集群包含的主机列表
	private Integer hostNum;			//集群包含的主机个数
	private VmZone availabilityZone;
	
	public Cluster() {
		super();
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

	public Cluster(Integer id, String name, String resrcStrategyUuid,
			Integer taskStrategyId, String extend, Timestamp createdTime,
			Timestamp updatedTime, Integer availabilityZoneId,
			List<ResourceStrategy> resrcStrategys, TaskStrategy taskStrategy,
			List<Host> hosts, Integer hostNum, VmZone availabilityZone) {
		super();
		this.id = id;
		this.name = name;
		this.resrcStrategyUuid = resrcStrategyUuid;
		this.taskStrategyId = taskStrategyId;
		this.extend = extend;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.availabilityZoneId = availabilityZoneId;
		this.resrcStrategys = resrcStrategys;
		this.taskStrategy = taskStrategy;
		this.hosts = hosts;
		this.hostNum = hostNum;
		this.availabilityZone = availabilityZone;
	}
	

	public Cluster(Integer id, String name, String resrcStrategyUuid,
			Integer taskStrategyId, String extend, Timestamp createdTime,
			Timestamp updatedTime, Integer availabilityZoneId,
			List<ResourceStrategy> resrcStrategys, TaskStrategy taskStrategy,
			Integer hostNum, Integer publicVLAN, Integer privateVLAN) {
		super();
		this.id = id;
		this.name = name;
		this.resrcStrategyUuid = resrcStrategyUuid;
		this.taskStrategyId = taskStrategyId;
		this.extend = extend;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.availabilityZoneId = availabilityZoneId;
		this.resrcStrategys = resrcStrategys;
		this.taskStrategy = taskStrategy;
		this.hostNum = hostNum;
		this.publicVLAN = publicVLAN;
		this.privateVLAN = privateVLAN;
	}
	
	public Cluster(Integer id, String name, String resrcStrategyUuid,
			Integer taskStrategyId, String extend, Timestamp createdTime,
			Timestamp updatedTime, Integer availabilityZoneId,
			Integer publicVLAN, Integer privateVLAN, Integer cpuOversell,
			Integer memoryOversell, Integer diskOversell,
			String cpuMemoryStrategy, String diskStrategy,
			List<ResourceStrategy> resrcStrategys, TaskStrategy taskStrategy,
			List<Host> hosts, Integer hostNum, VmZone availabilityZone) {
		super();
		this.id = id;
		this.name = name;
		this.resrcStrategyUuid = resrcStrategyUuid;
		this.taskStrategyId = taskStrategyId;
		this.extend = extend;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.availabilityZoneId = availabilityZoneId;
		this.publicVLAN = publicVLAN;
		this.privateVLAN = privateVLAN;
		this.cpuOversell = cpuOversell;
		this.memoryOversell = memoryOversell;
		this.diskOversell = diskOversell;
		this.resrcStrategys = resrcStrategys;
		this.taskStrategy = taskStrategy;
		this.hosts = hosts;
		this.hostNum = hostNum;
		this.availabilityZone = availabilityZone;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResrcStrategyUuid() {
		return resrcStrategyUuid;
	}
	public void setResrcStrategyUuid(String resrcStrategyUuid) {
		this.resrcStrategyUuid = resrcStrategyUuid;
	}
	public List<ResourceStrategy> getResrcStrategys() {
		return resrcStrategys;
	}
	public void setResrcStrategys(List<ResourceStrategy> resrcStrategys) {
		this.resrcStrategys = resrcStrategys;
	}

	public Integer getTaskStrategyId() {
		return taskStrategyId;
	}
	public void setTaskStrategyId(Integer taskStrategyId) {
		this.taskStrategyId = taskStrategyId;
	}
	public TaskStrategy getTaskStrategy() {
		return taskStrategy;
	}
	public void setTaskStrategy(TaskStrategy taskStrategy) {
		this.taskStrategy = taskStrategy;
	}
	public String getExtend() {
		return extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
	public List<Host> getHosts() {
		return hosts;
	}
	public void setHosts(List<Host> hosts) {
		this.hosts = hosts;
	}
	public Integer getHostNum() {
		return hostNum;
	}
	public void setHostNum(Integer hostNum) {
		this.hostNum = hostNum;
	}
	public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
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

	public Integer getCpuOversell() {
		return cpuOversell;
	}

	public void setCpuOversell(Integer cpuOversell) {
		this.cpuOversell = cpuOversell;
	}

	public Integer getMemoryOversell() {
		return memoryOversell;
	}

	public void setMemoryOversell(Integer memoryOversell) {
		this.memoryOversell = memoryOversell;
	}

	public Integer getDiskOversell() {
		return diskOversell;
	}

	public void setDiskOversell(Integer diskOversell) {
		this.diskOversell = diskOversell;
	}

	@Override
	public String toString() {
		return "Cluster [id=" + id + ", name=" + name + ", resrcStrategyId="
				+ resrcStrategyUuid + ", resrcStrategyUuid=" + taskStrategyId
				+ ", extend=" + extend + ", createdTime=" + createdTime
				+ ", updatedTime=" + updatedTime + ", availabilityZoneId="
				+ availabilityZoneId + ", publicVLAN=" + publicVLAN
				+ ", privateVLAN=" + privateVLAN + ", cpuOversell="
				+ cpuOversell + ", memoryOversell=" + memoryOversell
				+ ", diskOversell=" + diskOversell + ", resrcStrategy="
				+ resrcStrategys + ", taskStrategys=" + taskStrategy + ", hosts="
				+ hosts + ", hostNum=" + hostNum + ", availabilityZone="
				+ availabilityZone + "]";
	}
}
