package appcloud.api.beans;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import appcloud.api.enums.AcStrategyTypeEnum;

@XmlRootElement(name="resource_strategy")
public class AcResourceStrategy {
	@XmlAttribute
	public Integer id;
	@XmlAttribute
	public String uuid;
	@XmlAttribute
	public AcStrategyTypeEnum type;
	@XmlAttribute
	public String name;
	@XmlAttribute
	public String description;
	@XmlAttribute
	public String clazzs;
	@XmlAttribute
	public String params;
	@XmlAttribute
	public Date time;
	
	public AcResourceStrategy() {
		super();
	}
	
	public AcResourceStrategy(Integer id, String uuid, AcStrategyTypeEnum type,
			String name, String description, String clazzs, String params, Date time) {
		this.id = id;
		this.uuid = uuid;
		this.type = type;
		this.name = name;
		this.description = description;
		this.clazzs = clazzs;
		this.params = params;
		this.time = time;
	}
}
