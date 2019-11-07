package appcloud.admin.action.runtime;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.mapping.Array;

import appcloud.admin.action.base.BaseAction;
import appcloud.common.model.Admin;
import appcloud.common.model.AdminRole;
import appcloud.common.proxy.AdminProxy;
import appcloud.common.proxy.AdminRoleProxy;
import appcloud.common.util.ConnectionFactory;

public class PreAddAdminAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1213492910375006306L;
	private static final Logger logger = Logger.getLogger(PreAddAdminAction.class);
	private AdminRoleProxy adminRoleProxy = (AdminRoleProxy) ConnectionFactory.getTipProxy(AdminRoleProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private List<AdminRole> roleList = new ArrayList<AdminRole>();
	private int id;
	
	@Override
	public String execute() throws Exception {
		logger.info(id);
		List<AdminRole> adminRoles = (List<AdminRole>) adminRoleProxy.findAll();
		for (AdminRole adminRole : adminRoles) {
			logger.info(adminRole.getRolename());
			roleList.add(adminRole);
		}
		if (roleList.size() == 0) {
			return ERROR;
		}
		return SUCCESS;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<AdminRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<AdminRole> roleList) {
		this.roleList = roleList;
	}
}
