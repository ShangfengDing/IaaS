package appcloud.dbproxy.mysql.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.ResourceStrategy;

@Entity
@Table(name="resource_strategy")
public class ResourceStrategyTable extends ResourceStrategy {

	public ResourceStrategyTable() {
		super();
	}

	public ResourceStrategyTable(ResourceStrategy resourceStrategy) {
		this.setId(resourceStrategy.getId());
		this.setUuid(resourceStrategy.getUuid());
		this.setType(resourceStrategy.getType());
		this.setName(resourceStrategy.getName());
		this.setDescription(resourceStrategy.getDescription());
		this.setTime(resourceStrategy.getTime());
		this.setParams(resourceStrategy.getParams());
		this.setClazzs(resourceStrategy.getClazzs());
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

	@Column(name="uuid")
	public String getUuid() {
		return super.getUuid();
	}
	public void setUuid(String uuid) {
		super.setUuid(uuid);
	}

	@Column(name="type")
    @Enumerated(EnumType.STRING)
	public StrategyTypeEnum getType() {
		return super.getType();
	}
	public void setType(StrategyTypeEnum type) {
		super.setType(type);
	}

	@Column(name="name")
	public String getName() {
		return super.getName();
	}
	public void setName(String name) {
		super.setName(name);
	}

	@Column(name="description")
	public String getDescription() {
		return super.getDescription();
	}
	public void setDescription(String description) {
		super.setDescription(description);
	}

	@Column(name="params")
	public String getParams() {
		return super.getParams();
	}
	public void setParams(String params) {
		super.setParams(params);
	}

	@Column(name="clazz")
	public String getClazzs() {
		return super.getClazzs();
	}
	public void setClazzs(String clazzs) {
		super.setClazzs(clazzs);
	}

	@Column(name="time")
	public Timestamp getTime() {
		return super.getTime();
	}
	public void setTime(Timestamp time) {
		super.setTime(time);
	}
}
