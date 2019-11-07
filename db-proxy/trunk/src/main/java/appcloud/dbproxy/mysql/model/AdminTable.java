package appcloud.dbproxy.mysql.model;

import appcloud.common.model.Admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="admin")
public class AdminTable extends Admin {
	
	public AdminTable() {
		super();
	}
	
	public AdminTable(Admin admin) {
		this.setId(admin.getId());
		this.setUsername(admin.getUsername());
		this.setPassword(admin.getPassword());
		this.setType(admin.getType());
		this.setDisplayName(admin.getDisplayName());
		this.setEmail(admin.getEmail());
		this.setMobile(admin.getMobile());
		this.setRoleId(admin.getRoleId());
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
	
    @Column(name="username")
	public String getUsername() {
		return super.getUsername();
	}
	public void setUsername(String username) {
		super.setUsername(username);
	}
	
	@Column(name="password")
	public String getPassword() {
		return super.getPassword();
	}
	public void setPassword(String password) {
		super.setPassword(password);
	}
	
	@Column(name="type")
	@Enumerated(EnumType.STRING)
	public AdminRoleEnum getType() {
		return super.getType();
	}
	public void setType(AdminRoleEnum type) {
		super.setType(type);
	}
	
	@Column(name="display_name")
	public String getDisplayName() {
		return super.getDisplayName();
	}
	public void setDisplayName(String displayName) {
		super.setDisplayName(displayName);
	}
	
	@Column(name="email")
	public String getEmail() {
		return super.getEmail();
	}
	public void setEmail(String email) {
		super.setEmail(email);
	}
	
	@Column(name="mobile")
	public String getMobile() {
		return super.getMobile();
	}
	public void setMobile(String mobile) {
		super.setMobile(mobile);
	}

	@Column(name="role_id")
	public Integer getRoleId() {
		return super.getRoleId();
	}
	public void setRoleId(Integer roleId) {
		super.setRoleId(roleId);
	}
}
