package appcloud.common.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author XuanJiaxing
 * 
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class VmZone {

	private Integer id;
	private String name;
	private String regionId;
	private String zoneId;
	private Timestamp createdTime;
	private Timestamp updatedTime;
	
	public VmZone() {
		super();
	}

	public VmZone(Integer id, String name, String regionId, String zoneId, 
			Timestamp createdTime,	Timestamp updatedTime) {
		super();
		this.id = id;
		this.name = name;
		this.regionId = regionId;
		this.zoneId = zoneId;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
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

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
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

	public void setUpdatedTime(Timestamp updatedTimee) {
		this.updatedTime = updatedTimee;
	}

	@Override
	public String toString() {
		return "VmZone [id=" + id + ", name=" + name + ", regionId=" + regionId +
				", zoneId=" + zoneId + ", createdTime="	+ createdTime +
				", updatedTimee=" + updatedTime + "]";
	}

}
