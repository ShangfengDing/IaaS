package appcloud.common.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author lzc
 * 管理员信息
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class Admin {
	private Integer id;
	private String username;	//登录用户名，全局唯一
	private String password;	//密码
	private AdminRoleEnum type;	//类型：平台管理员，应用管理员
	private String displayName;	//显示姓名
	private String email;		//邮箱
	private String mobile;		//手机
	private Integer roleId;     //角色Id
	
	private AdminRole adminRole;
	private List<AdminPrivilege> adminPrivileges;
	private List<AdminResource> adminResources;
	
	public Admin() {
		super();
	}
	
	public Admin(Integer id, String username, String password,
			AdminRoleEnum type, String displayName, String email,
			String mobile, Integer roleId, AdminRole adminRole,
			List<AdminPrivilege> adminPrivileges,
			List<AdminResource> adminResources) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.type = type;
		this.displayName = displayName;
		this.email = email;
		this.mobile = mobile;
		this.roleId = roleId;
		this.adminRole = adminRole;
		this.adminPrivileges = adminPrivileges;
		this.adminResources = adminResources;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public AdminRoleEnum getType() {
		return type;
	}
	public void setType(AdminRoleEnum type) {
		this.type = type;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public AdminRole getAdminRole() {
		return adminRole;
	}

	public void setAdminRole(AdminRole adminRole) {
		this.adminRole = adminRole;
	}

	public List<AdminPrivilege> getAdminPrivileges() {
		return adminPrivileges;
	}

	public void setAdminPrivileges(List<AdminPrivilege> adminPrivileges) {
		this.adminPrivileges = adminPrivileges;
	}

	public List<AdminResource> getAdminResources() {
		return adminResources;
	}

	public void setAdminResources(List<AdminResource> adminResources) {
		this.adminResources = adminResources;
	}

	@Override
	public String toString() {
		return "Admin [id=" + id + ", username=" + username + ", password="
				+ password + ", type=" + type + ", displayName=" + displayName
				+ ", email=" + email + ", mobile=" + mobile + ", roleId="
				+ roleId + ", adminRole=" + adminRole + ", adminPrivileges="
				+ adminPrivileges + ", adminResources=" + adminResources + "]";
	}

	public static enum AdminRoleEnum {
		PLATADMIN, APPADMIN;
		
		public String toString() {
			switch (this) {
				case PLATADMIN:
					return "平台管理员";
				case APPADMIN:
					return "应用管理员";
			}
			return super.toString();
		}
	}
}
