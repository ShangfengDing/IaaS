package appcloud.admin.action.runtime;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import appcloud.common.model.Admin;
import appcloud.common.model.Admin.AdminRoleEnum;
import appcloud.common.proxy.AdminProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.Md5Tool;

public class ModAdminAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(ModAdminAction.class);
	private AdminProxy adminProxy = (AdminProxy) ConnectionFactory.getTipProxy(AdminProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private Integer roleid;
	private String mobile;
	private String email;
	private String result = "fail";
	private Integer id;
	
	@Override
	public String execute() throws Exception {
		logger.info(id+", "+roleid+", "+email+", "+mobile);
		Admin admin = adminProxy.getById(id, false, false, false);
		admin.setRoleId(roleid);
		admin.setEmail(email);
		admin.setMobile(mobile);
		admin.setType(AdminRoleEnum.PLATADMIN);
		logger.info(admin);
		adminProxy.update(admin);
		logger.info(admin+"  修改成功!");
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "修改管理员", "修改管理员 "+admin, "ModAdminAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		this.setResult(SUCCESS);
		return SUCCESS;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getRoleid() {
		return roleid;
	}
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
}
