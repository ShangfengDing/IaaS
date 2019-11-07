package appcloud.admin.action.runtime;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.opensymphony.xwork2.ActionContext;
import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import appcloud.common.model.Admin;
import appcloud.common.proxy.AdminProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.Md5Tool;

public class ChangePasswordAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(ChangePasswordAction.class);
	private AdminProxy adminProxy = (AdminProxy) ConnectionFactory.getTipProxy(AdminProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private int id;
	private String name;
	private String password;
	private String result = "fail";
	Map<String, Object> session = ActionContext.getContext().getSession();
	@Override
	public String execute() throws Exception {
		logger.info(name+", "+password);
		logger.info("id"+id);
		Admin admin = adminProxy.getById(id, false, false, false);
		admin.setUsername(name);
		admin.setPassword(Md5Tool.getMd5(name+password));
		adminProxy.update(admin);
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "修改管理员名称和密码", "修改管理员名称和密码组: "+name, "ChangePasswordAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		this.result = SUCCESS;
		return SUCCESS;
	}

	public String change() throws Exception {
		logger.info(name+", "+password);
		logger.info("id"+id);
		Admin admin = adminProxy.getById(id, false, false, false);
		name = admin.getUsername();
		session.put("username",name);
		admin.setPassword(Md5Tool.getMd5(name+password));
		adminProxy.update(admin);
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "修改管理员名称和密码", "修改管理员名称和密码组: "+name, "ChangePasswordAction.class",
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		this.result = SUCCESS;
		return SUCCESS;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
