package appcloud.dbproxy.mysql.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.VmZone;

@Entity
@Table(name="vm_zone")
public class VmZoneTable extends VmZone{
	
	public VmZoneTable() {
		super();
	}

	public VmZoneTable(VmZone zone) {
		super();
		this.setId(zone.getId()) ;
		this.setName(zone.getName());
		this.setRegionId(zone.getRegionId());
		this.setZoneId(zone.getZoneId());
		this.setCreatedTime(zone.getCreatedTime());
		this.setUpdatedTime(zone.getUpdatedTime());
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

	@Column(name="name")
	public String getName() {
		return super.getName();
	}
	public void setName(String name) {
		super.setName(name);
	}

	@Column(name="region_id")
	public String getRegionId() {
		return super.getRegionId();
	}
	public void setRegionId(String regionId) {
		super.setRegionId(regionId);
	}

	@Column(name="zone_id")
	public String getZoneId() {
		return super.getZoneId();
	}
	public void setZoneId(String zoneId) {
		super.setZoneId(zoneId);
	}

	@Column(name="created_time")
	public Timestamp getCreatedTime() {
		return super.getCreatedTime();
	}
	public void setCreatedTime(Timestamp createdTime) {
		super.setCreatedTime(createdTime);
	}

	@Column(name="updated_time")
	public Timestamp getUpdatedTime() {
		return super.getUpdatedTime();
	}
	public void setUpdatedTime(Timestamp updatedTime) {
		super.setUpdatedTime(updatedTime);
	}

}
