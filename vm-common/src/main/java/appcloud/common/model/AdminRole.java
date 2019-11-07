package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/*
 * 管理员角色信息
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class AdminRole {
	
	private Integer id;
	private String rolename;
	private String zoneId;
	private String clusterId;
	
	public AdminRole() {
		super();
	}
	
	public AdminRole(Integer id, String rolename, String zoneId,
			String clusterId) {
		super();
		this.id = id;
		this.rolename = rolename;
		this.zoneId = zoneId;
		this.clusterId = clusterId;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getRolename() {
		return rolename;
	}
	
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	
	public String getZoneId() {
		return zoneId;
	}
	
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	
	public String getClusterId() {
		return clusterId;
	}
	
	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	@Override
	public String toString() {
		return "AdminRole [id=" + id + ", rolename=" + rolename + ", zoneId="
				+ zoneId + ", clusterId=" + clusterId + "]";
	}
}
