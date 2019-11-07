package appcloud.admin.action.runtime;

/**
 * @author zydoer
 * 增加管理员
 */
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

public class AddAdminAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(AddAdminAction.class);
	private AdminProxy adminProxy = (AdminProxy) ConnectionFactory.getTipProxy(AdminProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private String name;
	private String password;
	private Integer roleid;
	private String des;
	private String mobile;
	private String email;
	private String result = "fail";
	
	@Override
	public String execute() throws Exception {
		Admin admin = new Admin();
		admin.setUsername(name);
		admin.setPassword(Md5Tool.getMd5(name+password));
		admin.setRoleId(roleid);
		admin.setDisplayName(des);
		admin.setEmail(email);
		admin.setMobile(mobile);
		admin.setType(AdminRoleEnum.PLATADMIN);
		adminProxy.save(admin);
		logger.info(admin+" 添加成功!");
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "添加管理员", "添加管理员: "+name, "AddAdminAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		this.setResult(SUCCESS);
		return SUCCESS;
	}
	
	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
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

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getRoleid() {
		return roleid;
	}
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
}
