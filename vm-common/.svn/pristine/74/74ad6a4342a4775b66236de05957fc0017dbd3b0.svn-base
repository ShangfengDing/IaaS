package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class AdminPrivilege {
	private Integer id;
	private Integer roleId;
	private Integer resourceId;
	
	public AdminPrivilege() {
		super();
	}
	
	public AdminPrivilege(Integer id, Integer roleId, Integer resourceId) {
		super();
		this.id = id;
		this.roleId = roleId;
		this.resourceId = resourceId;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	public Integer getResourceId() {
		return resourceId;
	}
	
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}


	@Override
	public String toString() {
		return "AdminPrivilege [id=" + id + ", roleId=" + roleId
				+ ", resourceId=" + resourceId + "]";
	}	
}
