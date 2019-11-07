package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.AlertSetting;

@Entity
@Table(name="alert_setting")
public class AlertSettingTable extends AlertSetting {
	
	public AlertSettingTable() {
		super();
	}
	
	public AlertSettingTable(AlertSetting alertSetting) {
		this.setId(alertSetting.getId());
		this.setName(alertSetting.getName());
		this.setHighLimit(alertSetting.getHighLimit());
		this.setMidLimit(alertSetting.getMidLimit());
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
	
	@Column(name="high_limit")
	public Float getHighLimit() {
		return super.getHighLimit();
	}
	public void setHighLimit(Float highLimit) {
		super.setHighLimit(highLimit);
	}
	
	@Column(name="mid_limit")
	public Float getMidLimit() {
		return super.getMidLimit();
	}
	public void setMidLimit(Float midLimit) {
		super.setMidLimit(midLimit);
	}
}
