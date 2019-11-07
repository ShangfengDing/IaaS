package appcloud.api.beans.securitygroup;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "securityGroup")
public class SecurityGroupCreateReq {
	@XmlAttribute
	public String name;
	@XmlElement
	public String description;
	
	public SecurityGroupCreateReq(){}
	
	public SecurityGroupCreateReq(String name, String description){
		this.name = name;
		this.description = description;
	}
}
