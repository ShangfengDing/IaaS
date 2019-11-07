package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.VmEnterpriseInvitation;

@Entity
@Table(name="vm_enterprise_invitation")
public class VmEnterpriseInvitationTable extends VmEnterpriseInvitation {
	public VmEnterpriseInvitationTable() {
		super();
	}
	public VmEnterpriseInvitationTable(VmEnterpriseInvitation veit) {
		this.setId(veit.getId());
		this.setEnterpriseId(veit.getEnterpriseId());
		this.setUserId(veit.getUserId());
		this.setInformation(veit.getInformation());
		this.setStatus(veit.getStatus());
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	
	@Column(name="id")
	public Integer getId() {
		return super.getId();
	}
	public void setId(Integer id) {
		super.setId(id);
	}
	
	@Column(name="enterprise_id")
	public Integer getEnterpriseId() {
		return super.getEnterpriseId();
	}
	public void setEnterpriseId(Integer enterpriseId) {
		super.setEnterpriseId(enterpriseId);
	}
	
	@Column(name="user_id")
	public Integer getUserId() {
		return super.getUserId();
	}
	public void setUserId(Integer userId) {
		super.setUserId(userId);
	}
	
	@Column(name="information")
	public String getInformation() {
		return super.getInformation();
	}
	public void setInformation(String information) {
		super.setInformation(information);
	}
	
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	public VmEnterpriseInvitationStatus getStatus() {
		return super.getStatus();
	}
	public void setStatus(VmEnterpriseInvitationStatus status) {
		super.setStatus(status);
	}
}
