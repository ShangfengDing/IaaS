package appcloud.api.beans;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import appcloud.api.enums.AcVlanTypeEnum;

@XmlRootElement(name="service")
public class AcVlan {
	@XmlAttribute
	public Integer id;
	@XmlAttribute
	public String name;
	@XmlAttribute
	public AcVlanTypeEnum type;
	@XmlAttribute
	public String description;
	
	public AcVlan() {
		super();
	}

	public AcVlan(Integer id, String name, AcVlanTypeEnum type,
			String description) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
	}
	
}
