package appcloud.api.beans;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import appcloud.common.model.VmEnterpriseInvitation.VmEnterpriseInvitationStatus;

@XmlRootElement(name="enterprise")
public class EnterpriseInvitation {
	@XmlAttribute
	public  Integer id;
	@XmlAttribute
	public  Integer enterpriseId;
	@XmlAttribute
	public  Integer userId;
	@XmlAttribute
	public  String information;
	@XmlElement
	public  VmEnterpriseInvitationStatus status;
	
	public EnterpriseInvitation() {
		super();
	}
	
	public EnterpriseInvitation(Integer id, Integer enterpriseId, Integer userId,
			String information, VmEnterpriseInvitationStatus status) {
		this.id = id;
		this.enterpriseId = enterpriseId;
		this.userId = userId;
		this.information = information;
		this.status = status;
	}
}
