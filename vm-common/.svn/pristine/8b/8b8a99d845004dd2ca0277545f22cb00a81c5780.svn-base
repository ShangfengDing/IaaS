package appcloud.common.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author lzc
 * 主机或者GC容器的常用负载信息
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class Load {
	private Integer id;
	private String fatherUuid;	//host或者instance的uuid
	private Timestamp time;		//该条记录产生时间
	private Float cpuUsePercent;//cpu占用率
	private Integer memoryUseMb;//内存占用大小
	private Integer diskUseMb;	//硬盘占用大小，该项只对主机有用，instance无用
	
	public Load() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Load(Integer id, String fatherUuid, Timestamp time,
			Float cpuUsePercent, Integer memoryUseMb, Integer diskUseMb) {
		super();
		this.id = id;
		this.fatherUuid = fatherUuid;
		this.time = time;
		this.cpuUsePercent = cpuUsePercent;
		this.memoryUseMb = memoryUseMb;
		this.diskUseMb = diskUseMb;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFatherUuid() {
		return fatherUuid;
	}
	public void setFatherUuid(String fatherUuid) {
		this.fatherUuid = fatherUuid;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public Float getCpuUsePercent() {
		return cpuUsePercent;
	}
	public void setCpuUsePercent(Float cpuUsePercent) {
		this.cpuUsePercent = cpuUsePercent;
	}
	public Integer getMemoryUseMb() {
		return memoryUseMb;
	}
	public void setMemoryUseMb(Integer memoryUseMb) {
		this.memoryUseMb = memoryUseMb;
	}
	public Integer getDiskUseMb() {
		return diskUseMb;
	}
	public void setDiskUseMb(Integer diskUseMb) {
		this.diskUseMb = diskUseMb;
	}
	@Override
	public String toString() {
		return "Load [id=" + id + ", fatherUuid=" + fatherUuid + ", time="
				+ time + ", cpuUsePercent=" + cpuUsePercent + ", memoryUseMb="
				+ memoryUseMb + ", diskUseMb=" + diskUseMb + "]";
	}
	
	
}
