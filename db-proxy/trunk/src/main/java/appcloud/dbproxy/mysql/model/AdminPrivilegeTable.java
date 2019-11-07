package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.AdminPrivilege;

@Entity
@Table(name="admin_privilege")
public class AdminPrivilegeTable extends AdminPrivilege {
	public AdminPrivilegeTable() {
		super();
	}
	
	public AdminPrivilegeTable(AdminPrivilege adminPrivilege) {
		this.setId(adminPrivilege.getId());
		this.setRoleId(adminPrivilege.getRoleId());
		this.setResourceId(adminPrivilege.getResourceId());
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

	@Column(name="role_id")
	public Integer getRoleId() {
		return super.getRoleId();
	}

	public void setRoleId(Integer roleId) {
		super.setRoleId(roleId);
	}
	
	@Column(name="resource_id")
	public Integer getResourceId() {
		return super.getResourceId();
	}

	public void setResourceId(Integer resourceId) {
		super.setResourceId(resourceId);
	}
}
