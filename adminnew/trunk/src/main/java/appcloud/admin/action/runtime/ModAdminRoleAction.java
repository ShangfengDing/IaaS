package appcloud.admin.action.runtime;
/**
 * @author zydoer
 */
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import appcloud.common.model.Admin;
import appcloud.common.proxy.AdminProxy;
import appcloud.common.proxy.AdminRoleProxy;
import appcloud.common.util.ConnectionFactory;

public class ModAdminRoleAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(ModAdminRoleAction.class);
	private AdminProxy adminProxy = (AdminProxy) ConnectionFactory.getTipProxy(AdminProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private AdminRoleProxy adminRoleProxy = (AdminRoleProxy) ConnectionFactory.getTipProxy(AdminRoleProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private int roleId;
	private int id;
	private String result = "fail";
	
	@Override
	public String execute() throws Exception {
		Admin admin = adminProxy.getById(id, false, false, false);
		admin.setRoleId(roleId);
		adminProxy.update(admin);
		this.result = SUCCESS;
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "修改管理员的管理组", "修改管理员的管理组为"+adminRoleProxy.getById(roleId).getRolename(), "ModAdminRoleAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		return SUCCESS;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
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
