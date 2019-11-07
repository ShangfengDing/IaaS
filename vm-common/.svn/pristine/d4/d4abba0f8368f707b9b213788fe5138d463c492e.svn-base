package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * @author LinXiong
 * 
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class VmEnterprise {
	private Integer id;
	private Integer ownerId;
	private Boolean active;
	private String name;
	private String description;
	private String phone;
	private String email;
	private String address;
	private String postcode;
	private String homepage;
	private Integer parentCompanyId;
	
	public VmEnterprise() {
		super();
	}
	
	public VmEnterprise(Integer id, Integer ownerId, Boolean active, String name, 
			String description, String phone, String email, String address, 
			String postcode, String homepage, Integer parentCompanyId) {
		this.id = id;
		this.ownerId = ownerId;
		this.active = active;
		this.name = name;
		this.description = description;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.postcode = postcode;
		this.homepage = homepage;
		this.parentCompanyId = parentCompanyId;
	}
	
	@Override
	public String toString() {
		return "Enterprise [id=" + id + ", ownerId=" + ownerId + ", active=" + active + ", name=" + name + 
				", description=" + description + ", phone=" + phone + ", email=" + email + ", address=" + address + 
				", postcode=" + postcode + ", homepage=" + homepage + ", parentCompanyId=" + parentCompanyId +"]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public Integer getParentCompanyId() {
		return parentCompanyId;
	}

	public void setParentCompanyId(Integer parentCompanyId) {
		this.parentCompanyId = parentCompanyId;
	}
	
	
}
