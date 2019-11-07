package appcloud.api.beans;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="user")
public class AcUser {
	@XmlAttribute(name="user_id")
	public String userId;
	@XmlAttribute(name="user_email")
	public String userEmail;
	@XmlAttribute(name="is_active")
	public Boolean isActive;
	@XmlAttribute(name="group_id")
	public Integer groupId;
	@XmlAttribute(name="appkey_id")
	public String appkeyId;
	@XmlAttribute(name="appkey_secret")
	public String appkeySecret;
	@XmlAttribute(name="enterprise_id")
	public Integer enterpriseId;
	
	public AcGroup group;
	public AcEnterprise enterprise;
	
	
	public AcUser() {
		super();
	}

	public AcUser(String userId, String userEmail, Boolean isActive, Integer groupId, Integer enterpriseId) {
		super();
		this.userId = userId;
		this.userEmail = userEmail;
		this.isActive = isActive;
		this.groupId = groupId;
		this.enterpriseId = enterpriseId;
	}

	@Override
	public String toString() {
		return "AcUser [userId=" + userId 
				+ ", userEmail=" + userEmail 
				+ ", isActive=" + isActive
				+ ", groupId=" + groupId  
				+ ", enterpriseId=" + enterpriseId + "]";
	}
	
	public String getUserEmail(){
		return userEmail;
	}
	
}
