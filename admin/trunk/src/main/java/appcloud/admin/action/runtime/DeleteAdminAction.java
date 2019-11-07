package appcloud.admin.action.runtime;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;

import appcloud.admin.action.base.BaseAction;
import appcloud.admin.common.Constants;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import appcloud.common.model.Admin;
import appcloud.common.proxy.AdminProxy;
import appcloud.common.util.ConnectionFactory;

public class DeleteAdminAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(DeleteAdminAction.class);
	private AdminProxy adminProxy = (AdminProxy) ConnectionFactory.getTipProxy(AdminProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private int id;
	private String result = "fail";
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> session = ActionContext.getContext().getSession();
		Integer adminId = (Integer) session.get(Constants.ADMIN_ID_KEY);
		Admin admin = adminProxy.getById(adminId, false, false, false);
		if (admin == null) {
			this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
					this.getUserId(), null, "删除管理员", "管理员"+adminProxy.getById(id, false, false, false).getUsername()+"已被删除。。。", "DeleteAdminAction.class", 
					null, AcLogLevelEnum.WARN, new Date(System.currentTimeMillis()));
			this.setResult("deleted");
			return SUCCESS;
		}
		String name = adminProxy.getById(id, false, false, false).getUsername();
		adminProxy.deleteById(id);
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "删除管理员", "删除管理员: "+name, "DeleteAdminAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		this.setResult(SUCCESS);
		return SUCCESS;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
