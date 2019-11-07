package appcloud.api.beans.securitygroup;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name="securityGroupRule")
public class RuleCreateReq {

	@XmlElement
	public String ipProtocal;
	@XmlElement
	public Integer fromPort;
	@XmlElement
	public Integer toPort;
	@XmlElement
	public String cidr;
	@XmlElement
	public String groupId;
	@XmlAttribute
	public Integer parentGroupId;
	
	public RuleCreateReq() {
		super();
	}
	public RuleCreateReq(String ipProtocal, Integer fromPort,
			Integer toPort, String cidr, String groupId, Integer parentGroupId) {
		super();
		this.ipProtocal = ipProtocal;
		this.fromPort = fromPort;
		this.toPort = toPort;
		this.cidr = cidr;
		this.groupId = groupId;
		this.parentGroupId = parentGroupId;
	}
}
