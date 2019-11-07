package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.AdminRole;

@Entity
@Table(name="admin_role")
public class AdminRoleTable extends AdminRole {
	
	public AdminRoleTable () {
		super();
	}
	
	public AdminRoleTable(AdminRole adminRole) {
		this.setId(adminRole.getId());
		this.setRolename(adminRole.getRolename());
		this.setZoneId(adminRole.getZoneId());
		this.setClusterId(adminRole.getClusterId());
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

	@Column(name="rolename")
	public String getRolename() {
		return super.getRolename();
	}

	public void setRolename(String rolename) {
		super.setRolename(rolename);
	}

	@Column(name="zone_id")
	public String getZoneId() {
		return super.getZoneId();
	}

	public void setZoneId(String zoneId) {
		super.setZoneId(zoneId);
	}

	@Column(name="cluster_id")
	public String getClusterId() {
		return super.getClusterId();
	}

	public void setClusterId(String clusterId) {
		super.setClusterId(clusterId);
	}	
}
