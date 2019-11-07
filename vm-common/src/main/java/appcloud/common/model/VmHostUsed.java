package appcloud.common.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class VmHostUsed {

	private Integer id;
	private String hostUuid;//主机的uuid
	private Integer cpuUsed;//CPU已用核数
	private Integer memoryUsed;//内存使用量，单位MB
	private Integer diskUsed;//硬盘使用量，单位GB
	private Timestamp time;
	
	public VmHostUsed() {
		super();
	}

	public VmHostUsed(Integer id, String hostUuid, Integer cpuUsed,
			Integer memoryUsed, Integer diskUsed, Timestamp time) {
		super();
		this.id = id;
		this.hostUuid = hostUuid;
		this.cpuUsed = cpuUsed;
		this.memoryUsed = memoryUsed;
		this.diskUsed = diskUsed;
		this.time = time;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getHostUuid() {
		return hostUuid;
	}

	public void setHostUuid(String hostUuid) {
		this.hostUuid = hostUuid;
	}

	public Integer getCpuUsed() {
		return cpuUsed;
	}

	public void setCpuUsed(Integer cpuUsed) {
		this.cpuUsed = cpuUsed;
	}

	public Integer getMemoryUsed() {
		return memoryUsed;
	}

	public void setMemoryUsed(Integer memoryUsed) {
		this.memoryUsed = memoryUsed;
	}

	public Integer getDiskUsed() {
		return diskUsed;
	}

	public void setDiskUsed(Integer diskUsed) {
		this.diskUsed = diskUsed;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "VmHostUsed [id=" + id + ", hostUuid=" + hostUuid + ", cpuUsed="
				+ cpuUsed + ", memoryUsed=" + memoryUsed + ", diskUsed="
				+ diskUsed + ", time=" + time + "]";
	}
}
