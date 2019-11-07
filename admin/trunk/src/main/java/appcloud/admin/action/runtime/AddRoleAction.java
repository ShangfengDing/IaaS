package appcloud.admin.action.runtime;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import appcloud.common.model.AdminPrivilege;
import appcloud.common.model.AdminRole;
import appcloud.common.proxy.AdminPrivilegeProxy;
import appcloud.common.proxy.AdminResourceProxy;
import appcloud.common.proxy.AdminRoleProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;

public class AddRoleAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(AddRoleAction.class);
	private AdminRoleProxy adminRoleProxy = (AdminRoleProxy) ConnectionFactory.getTipProxy(AdminRoleProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private AdminPrivilegeProxy adminPrivilegeProxy = (AdminPrivilegeProxy) ConnectionFactory.getTipProxy(AdminPrivilegeProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private AdminResourceProxy adminResourceProxy = (AdminResourceProxy) ConnectionFactory.getTipProxy(AdminResourceProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private String value;
	private String name;
	private String result = "fail";
	private String zone;
	private String cluster;
	private String admin_group = "";
	
	@Override
	public String execute() throws Exception {
		String[] roleIds = value.split(",");
		AdminRole newAdminRole = new AdminRole();
		newAdminRole.setRolename(name);
		newAdminRole.setClusterId("");
		newAdminRole.setZoneId("");
		adminRoleProxy.save(newAdminRole);
		QueryObject<AdminRole> query = new QueryObject<AdminRole>();
		query.addFilterBean(new FilterBean<AdminRole>("rolename", name, FilterBeanType.EQUAL));
		AdminRole adminRole = adminRoleProxy.searchAll(query).get(0);
		for (int i = 0; i < roleIds.length; i++) {
			logger.info(roleIds[i]);
			AdminPrivilege temp = new AdminPrivilege();
			temp.setRoleId(adminRole.getId());
			temp.setResourceId(Integer.parseInt(roleIds[i]));
			admin_group += adminResourceProxy.getById(Integer.parseInt(roleIds[i])).getLeftBarName()+",";
			adminPrivilegeProxy.save(temp);
		}
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "添加管理组", "添加管理组: "+name+"["+admin_group+"]", "AddRoleAction.class", 
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
