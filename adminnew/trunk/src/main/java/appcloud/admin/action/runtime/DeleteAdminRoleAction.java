package appcloud.admin.action.runtime;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import appcloud.common.model.Admin;
import appcloud.common.model.AdminPrivilege;
import appcloud.common.proxy.AdminPrivilegeProxy;
import appcloud.common.proxy.AdminProxy;
import appcloud.common.proxy.AdminRoleProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;

public class DeleteAdminRoleAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(DeleteAdminRoleAction.class);
	private AdminProxy adminProxy = (AdminProxy) ConnectionFactory.getTipProxy(AdminProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private AdminRoleProxy adminRoleProxy = (AdminRoleProxy) ConnectionFactory.getTipProxy(AdminRoleProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private AdminPrivilegeProxy adminPrivilegeProxy = (AdminPrivilegeProxy) ConnectionFactory.getTipProxy(AdminPrivilegeProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private int id;
	private String result = "fail";
	
	@Override
	public String execute() throws Exception {
		logger.info(id);
		String deleteRoleName = adminRoleProxy.getById(id).getRolename();
		QueryObject<Admin> queryObject1 = new QueryObject<Admin>();
		queryObject1.addFilterBean(new FilterBean<Admin>("roleId", id, FilterBeanType.EQUAL));
		List<? extends Admin> admins = adminProxy.searchAll(queryObject1, false, false, false);
		logger.info(admins);
		if (admins != null && admins.size() != 0) {
			this.result = "noPrivilege";
			this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
					this.getUserId(), null, "删除管理组", "删除管理组"+adminRoleProxy.getById(id).getRolename()+"失败，正在使用中。。。", "DeleteAdminRoleAction.class", 
					null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
			logger.info("使用中，无法删除。。。");
			return SUCCESS;
		}
		adminRoleProxy.deleteById(id);
		QueryObject<AdminPrivilege> queryObject = new QueryObject<AdminPrivilege>();
		queryObject.addFilterBean(new FilterBean<AdminPrivilege>("roleId", id, FilterBeanType.EQUAL));
		List<AdminPrivilege> adminPrivileges = (List<AdminPrivilege>) adminPrivilegeProxy.searchAll(queryObject);
		for (AdminPrivilege temp:adminPrivileges) {
			adminPrivilegeProxy.deleteById(temp.getId());
		}
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "删除管理组", "删除管理组 "+deleteRoleName, "DeleteAdminRoleAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		result = SUCCESS;
		return SUCCESS;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
