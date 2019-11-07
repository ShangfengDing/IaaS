package appcloud.openapi.datatype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class SecurityGroupRuleDetailItem {

	private String IpProtocol;
	private String PortRange;
	private String Policy;
	private String SourceCidrIp;
	
	public SecurityGroupRuleDetailItem(){
		
	}

	public String getIpProtocol() {
		return IpProtocol;
	}

	public void setIpProtocol(String ipProtocol) {
		IpProtocol = ipProtocol;
	}

	public String getPortRange() {
		return PortRange;
	}

	public void setPortRange(String portRange) {
		PortRange = portRange;
	}

	public String getPolicy() {
		return Policy;
	}

	public void setPolicy(String policy) {
		Policy = policy;
	}

	public String getSourceCidrIp() {
		return SourceCidrIp;
	}

	public void setSourceCidrIp(String sourceCidrIp) {
		SourceCidrIp = sourceCidrIp;
	}

	
	
}
