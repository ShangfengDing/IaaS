package appcloud.api.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "securityGroup")
public class SecurityGroup {
	@XmlAttribute
	public Integer id;
	@XmlAttribute
	public String name;
	@XmlAttribute
	public String tenantId;
	
	@XmlElementWrapper(name="rules")
	@XmlElements(
			@XmlElement(name="rule", type=Rule.class)
	)
	public List<Rule> rules;
	
	public String description;

	public SecurityGroup() {}
	
	public SecurityGroup(Integer id, String name, String tenantId,
			List<Rule> rules, String description) {
		this.id = id;
		this.name = name;
		this.tenantId = tenantId;
		this.rules = rules;
		this.description = description;
	}
}
