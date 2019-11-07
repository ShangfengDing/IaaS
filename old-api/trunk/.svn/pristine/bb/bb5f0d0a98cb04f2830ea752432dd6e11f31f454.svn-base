package appcloud.api.beans;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="enterprise")
public class Enterprise {
	@XmlAttribute
	public  Integer id;
	@XmlAttribute
	public  Integer ownerId;
	@XmlAttribute
	public Boolean isActive;
	@XmlAttribute
	public String name;
	@XmlAttribute
	public String description;
	@XmlAttribute
	public String phone;
	@XmlAttribute
	public String email;
	@XmlAttribute
	public String address;
	@XmlAttribute
	public String postcode;
	@XmlAttribute
	public String homepage;
	@XmlAttribute
	public Integer parentCompanyId;
	
	public Enterprise() {
		super();
	}
	
	public Enterprise(Integer id, Integer ownerId, Boolean isActive, String name, 
			String description, String phone, String email, String address, 
			String postcode, String homepage, Integer parentCompanyId) {
		this.id = id;
		this.ownerId = ownerId;
		this.isActive = isActive;
		this.name = name;
		this.description = description;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.postcode = postcode;
		this.homepage = homepage;
		this.parentCompanyId = parentCompanyId;
	}
}
