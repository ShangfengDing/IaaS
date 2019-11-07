package appcloud.api.beans;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "rule")
public class Rule {
	@XmlAttribute
	public Integer id;
	@XmlAttribute
	public Integer parentGroupId;
	@XmlElement
	public Integer fromPort;
	@XmlElement
	public Integer toPort;
	@XmlElement
	public String ipProtocal;

	@XmlElement
	public SecurityGroup group = new SecurityGroup();
	@XmlElement
	public IpRange ipRange;
	
	public Rule() {
		super();
	}
	public Rule(Integer id, Integer parentGroupId, Integer fromPort,
			Integer toPort, String ipProtocal, SecurityGroup group, IpRange ipRange) {
		super();
		this.id = id;
		this.parentGroupId = parentGroupId;
		this.fromPort = fromPort;
		this.toPort = toPort;
		this.ipProtocal = ipProtocal;
		this.group = group;
		this.ipRange = ipRange;
	}
	
}
