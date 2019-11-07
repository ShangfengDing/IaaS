package appcloud.admin.action.runtime;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.common.model.Admin;
import appcloud.common.model.AdminRole;
import appcloud.common.proxy.AdminProxy;
import appcloud.common.proxy.AdminRoleProxy;
import appcloud.common.util.ConnectionFactory;

public class PreModAdminAction extends BaseAction {
	private static final long serialVersionUID = -1213492910375006306L;
	private static final Logger logger = Logger.getLogger(PreAddAdminAction.class);
	private AdminProxy adminProxy = (AdminProxy) ConnectionFactory.getTipProxy(AdminProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private AdminRoleProxy adminRoleProxy = (AdminRoleProxy) ConnectionFactory.getTipProxy(AdminRoleProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private int id;
	private String username;
	private String mobile;
	private String email;
	private String des;
	private Integer roleid;
	
	@Override
	public String execute() throws Exception {
		logger.info(id);
		Admin admin = adminProxy.getById(id, false, false, false);
		this.setUsername(admin.getUsername());
		this.setMobile(admin.getMobile());
		this.setEmail(admin.getEmail());
		this.setRoleid(admin.getRoleId());
//		List<AdminRole> adminRoles = (List<AdminRole>) adminRoleProxy.findAll();
//		for (AdminRole adminRole : adminRoles) {
//			logger.info(adminRole.getRolename());
//			roleList.add(adminRole);
//		}
//		if (roleList.size() == 0) {
//			return ERROR;
//		}
		return SUCCESS;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
