package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.VmEnterprise;

@Entity
@Table(name="vm_enterprise")
public class VmEnterpriseTable extends VmEnterprise{
	public VmEnterpriseTable() {
		super();
	}
	public VmEnterpriseTable(VmEnterprise enterprise) {
		this.setId(enterprise.getId());
		this.setOwnerId(enterprise.getOwnerId());
		this.setActive(enterprise.getActive());
		this.setName(enterprise.getName());
		this.setDescription(enterprise.getDescription());
		this.setPhone(enterprise.getPhone());
		this.setEmail(enterprise.getEmail());
		this.setAddress(enterprise.getAddress());
		this.setPostcode(enterprise.getPostcode());
		this.setHomepage(enterprise.getHomepage());
		this.setParentCompanyId(enterprise.getParentCompanyId());
	}
	
	@Id
	@Column(name="id")
	public Integer getId() {
		return super.getId();
	}
	public void setId(Integer id) {
		super.setId(id);
	}

	@Column(name="owner_id")
	public Integer getOwnerId() {
		return super.getOwnerId();
	}
	public void setOwnerId(Integer ownerId) {
		super.setOwnerId(ownerId);
	}

	@Column(name="is_active")
	public Boolean getActive() {
		return super.getActive();
	}
	public void setActive(Boolean active) {
		super.setActive(active);
	}

	@Column(name="name")
	public String getName() {
		return super.getName();
	}
	public void setName(String name) {
		super.setName(name);
	}

	@Column(name="description")
	public String getDescription() {
		return super.getDescription();
	}
	public void setDescription(String description) {
		super.setDescription(description);
	}

	@Column(name="phone")
	public String getPhone() {
		return super.getPhone();
	}
	public void setPhone(String phone) {
		super.setPhone(phone);
	}
	
	@Column(name="email")
	public String getEmail() {
		return super.getEmail();
	}
	public void setEmail(String email) {
		super.setEmail(email);
	}

	@Column(name="address")
	public String getAddress() {
		return super.getAddress();
	}
	public void setAddress(String address) {
		super.setAddress(address);
	}

	@Column(name="postcode")
	public String getPostcode() {
		return super.getPostcode();
	}
	public void setPostcode(String postcode) {
		super.setPostcode(postcode);
	}

	@Column(name="homepage")
	public String getHomepage() {
		return super.getHomepage();
	}
	public void setHomepage(String homepage) {
		super.setHomepage(homepage);
	}

	@Column(name="parent_company")
	public Integer getParentCompanyId() {
		return super.getParentCompanyId();
	}
	public void setParentCompanyId(Integer parentCompanyId) {
		super.setParentCompanyId(parentCompanyId);
	}
}
