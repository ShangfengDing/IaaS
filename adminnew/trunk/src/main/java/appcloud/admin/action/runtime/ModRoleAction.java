package appcloud.admin.action.runtime;

/*
 * @zydoer
 * 
 */
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import appcloud.common.model.AdminPrivilege;
import appcloud.common.model.AdminResource;
import appcloud.common.model.AdminRole;
import appcloud.common.proxy.AdminPrivilegeProxy;
import appcloud.common.proxy.AdminProxy;
import appcloud.common.proxy.AdminResourceProxy;
import appcloud.common.proxy.AdminRoleProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import appcloud.common.util.query.FilterBean.FilterBeanType;

public class ModRoleAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(ModRoleAction.class);
	private AdminPrivilegeProxy adminPrivilegeProxy = (AdminPrivilegeProxy) ConnectionFactory.getTipProxy(AdminPrivilegeProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private AdminRoleProxy adminRoleProxy = (AdminRoleProxy) ConnectionFactory.getTipProxy(AdminRoleProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private AdminResourceProxy adminResourceProxy = (AdminResourceProxy) ConnectionFactory.getTipProxy(AdminResourceProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private String value;
	private String name;
	private String roleid;
	private String result = "fail";
	private String zone;
	private String cluster;
	private String admin_group="";
	
	@Override
	public String execute() throws Exception {
		logger.info(roleid+", "+value);
		String[] roleIds = value.split(",");
		AdminRole newAdminRole = adminRoleProxy.getById(Integer.parseInt(roleid));
		newAdminRole.setRolename(name);
		adminRoleProxy.update(newAdminRole);
		List<AdminPrivilege> adminPrivileges = (List<AdminPrivilege>) adminPrivilegeProxy.getByRoleId(Integer.parseInt(roleid));
		for (AdminPrivilege adminPrivilege : adminPrivileges) {
			adminPrivilegeProxy.deleteById(adminPrivilege.getId());
		}
		for (int i = 0; i < roleIds.length; i++) {
			AdminPrivilege newAdminPrivilege = new AdminPrivilege();
			newAdminPrivilege.setRoleId(Integer.parseInt(roleid));
			newAdminPrivilege.setResourceId(Integer.parseInt(roleIds[i]));
			admin_group += adminResourceProxy.getById(Integer.parseInt(roleIds[i])).getLeftBarName()+",";
			adminPrivilegeProxy.save(newAdminPrivilege);
		}
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "修改管理组", "修改管理组为"+admin_group, "ModRoleAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		this.result = SUCCESS;
		return SUCCESS;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}
}
