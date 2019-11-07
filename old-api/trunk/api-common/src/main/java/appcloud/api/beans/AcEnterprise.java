package appcloud.api.beans;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="enterprise")
public class AcEnterprise {
	@XmlAttribute(name="enterprise_id")
	public Integer enterpriseId;
	@XmlAttribute(name="owner_id")
	public Integer ownerId;
	@XmlAttribute(name="is_active")
	public Boolean isActive;
	@XmlAttribute(name="name")
	public String name;
	@XmlAttribute(name="description")
	public String description;
	@XmlAttribute(name="phone")
	public String phone;
	@XmlAttribute(name="email")
	public String email;
	@XmlAttribute(name="address")
	public String address;
	@XmlAttribute(name="postcode")
	public String postcode;
	@XmlAttribute(name="homepage")
	public String homepage;
	@XmlAttribute(name="parent_company")
	public Integer parentCompany;
	
	public AcEnterprise(Integer enterpriseId, Integer ownerId, Boolean isActive,
			String name, String description, String phone, String email, String address,
			String postcode, String homepage, Integer parentCompany) {
		super();
		this.enterpriseId = enterpriseId;
		this.ownerId = ownerId;
		this.isActive = isActive;
		this.name = name;
		this.description = description;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.postcode = postcode;
		this.homepage = homepage;
		this.parentCompany = parentCompany;
		
	}
	
	public AcEnterprise() {
		super();
	}
	
	@Override
	public String toString() {
		return "AcEnterprise [enterpriseId=" + enterpriseId 
				+ ", ownerId=" + ownerId 
				+ ", isActive=" + isActive
				+ ", name=" + name
				+ ", description=" + description
				+ ", phone=" + phone
				+ ", email=" + email
				+ ", address=" + address
				+ ", postcode=" + postcode
				+ ", homepage=" + homepage
				+ ", parentCompany=" + parentCompany
				+ "]";
	}
	
	
}
