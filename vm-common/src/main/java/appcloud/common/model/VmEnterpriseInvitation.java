package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author LinXiong
 * 
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class VmEnterpriseInvitation {
	private Integer id;
	private Integer enterpriseId;
	private Integer userId;
	private String information;
	private VmEnterpriseInvitationStatus status;
	
	public VmEnterpriseInvitation() {
		super();
	}
	
	public VmEnterpriseInvitation(Integer id, Integer enterpriseId, Integer userId, 
			String information, VmEnterpriseInvitationStatus status) {
		this.id = id;
		this.enterpriseId = enterpriseId;
		this.userId = userId;
		this.information = information;
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "VmEnterpriseInvitation [" + 
				"id=" + id + ", enterpriseId=" + enterpriseId + 
				", userId=" + userId + ", information=" + information + 
				", status=" + status + "]";
	}
	
	public static enum VmEnterpriseInvitationStatus {
		NEW, AGGREED, DENIED, FINISHED;
		
		public String toString() {
			switch(this) {
			case NEW:
				return "new";
			case AGGREED:
				return "aggreed";
			case DENIED:
				return "denied";
			case FINISHED:
				return "finished";
			}
			return super.toString();
		}
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public VmEnterpriseInvitationStatus getStatus() {
		return status;
	}

	public void setStatus(VmEnterpriseInvitationStatus status) {
		this.status = status;
	}
	
	
}
