package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author lzc
 * 告警参数设置
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class AlertSetting {
	private Integer id;
	private String name;		//告警参数名称
	private Float highLimit;	//高阈值
	private Float midLimit;		//中阈值
	
	
	public AlertSetting() {
		super();
	}
	
	public AlertSetting(Integer id, String name, Float highLimit, Float midLimit) {
		super();
		this.id = id;
		this.name = name;
		this.highLimit = highLimit;
		this.midLimit = midLimit;
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
	public Float getHighLimit() {
		return highLimit;
	}
	public void setHighLimit(Float highLimit) {
		this.highLimit = highLimit;
	}
	public Float getMidLimit() {
		return midLimit;
	}
	public void setMidLimit(Float midLimit) {
		this.midLimit = midLimit;
	}
	@Override
	public String toString() {
		return "AlertSetting [id=" + id + ", name=" + name + ", highLimit="
				+ highLimit + ", midLimit=" + midLimit + "]";
	}
	
}
