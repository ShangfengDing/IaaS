package appcloud.dbproxy.mysql.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.VmHostUsed;

@Entity
@Table(name="vm_host_used")
public class VmHostUsedTable extends VmHostUsed {

	public VmHostUsedTable() {
		super();
	}

	public VmHostUsedTable(VmHostUsed vmHostUsed) {
		this.setId(vmHostUsed.getId());
		this.setHostUuid(vmHostUsed.getHostUuid());
		this.setCpuUsed(vmHostUsed.getCpuUsed());
		this.setMemoryUsed(vmHostUsed.getMemoryUsed());
		this.setDiskUsed(vmHostUsed.getDiskUsed());
		this.setTime(vmHostUsed.getTime());
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

	@Column(name="host_uuid")
	public String getHostUuid() {
		return super.getHostUuid();
	}
	public void setHostUuid(String hostUuid) {
		super.setHostUuid(hostUuid);
	}

	@Column(name="cpu_used")
	public Integer getCpuUsed() {
		return super.getCpuUsed();
	}
	public void setCpuUsed(Integer cpuUsed) {
		super.setCpuUsed(cpuUsed);
	}

	@Column(name="memory_used_mb")
	public Integer getMemoryUsed() {
		return super.getMemoryUsed();
	}
	public void setMemoryUsed(Integer memoryUsed) {
		super.setMemoryUsed(memoryUsed);
	}

	@Column(name="disk_used_gb")
	public Integer getDiskUsed() {
		return super.getDiskUsed();
	}
	public void setDiskUsed(Integer diskUsed) {
		super.setDiskUsed(diskUsed);
	}

	@Column(name="time")
	public Timestamp getTime() {
		return super.getTime();
	}
	public void setTime(Timestamp time) {
		super.setTime(time);
	}
	
}
