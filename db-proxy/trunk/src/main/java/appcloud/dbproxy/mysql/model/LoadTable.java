/**
 * File: LoadTable.java
 * Author: weed
 * Create Time: 2012-11-25
 */
package appcloud.dbproxy.mysql.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.Load;

/**
 * @author weed
 *
 */
@Entity
@Table(name="basic_load")
public class LoadTable extends Load {

	public LoadTable() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public LoadTable(Load load) {
		// TODO Auto-generated constructor stub
		this.setCpuUsePercent(load.getCpuUsePercent());
		this.setDiskUseMb(load.getDiskUseMb());
		this.setFatherUuid(load.getFatherUuid());
		this.setId(load.getId());
		this.setMemoryUseMb(load.getMemoryUseMb());
		this.setTime(load.getTime());
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
	
    @Column(name="father_uuid")
	public String getFatherUuid() {
		return super.getFatherUuid();
	}
	public void setFatherUuid(String fatherUuid) {
		super.setFatherUuid(fatherUuid);
	}
	
    @Column(name="time")
	public Timestamp getTime() {
		return super.getTime();
	}
	public void setTime(Timestamp time) {
		super.setTime(time);
	}
	
    @Column(name="cpu_use_percent")
	public Float getCpuUsePercent() {
		return super.getCpuUsePercent();
	}
	public void setCpuUsePercent(Float cpuUsePercent) {
		super.setCpuUsePercent(cpuUsePercent);
	}
	
    @Column(name="memory_use_mb")
	public Integer getMemoryUseMb() {
		return super.getMemoryUseMb();
	}
	public void setMemoryUseMb(Integer memoryUseMb) {
		super.setMemoryUseMb(memoryUseMb);
	}
	
    @Column(name="disk_use_mb")
	public Integer getDiskUseMb() {
		return super.getDiskUseMb();
	}
	public void setDiskUseMb(Integer diskUseMb) {
		super.setDiskUseMb(diskUseMb);
	}
}
