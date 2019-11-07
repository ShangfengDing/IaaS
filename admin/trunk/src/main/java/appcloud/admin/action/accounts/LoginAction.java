package appcloud.admin.action.accounts;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import appcloud.admin.action.base.BaseAction;
import appcloud.admin.common.Constants;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import appcloud.common.model.Admin;
import appcloud.common.model.AdminResource;
import appcloud.common.proxy.AdminProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.Md5Tool;
import appcloud.common.util.UuidUtil;

import com.opensymphony.xwork2.ActionContext;

public class LoginAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(LoginAction.class);
	private static final long serialVersionUID = 3874649220563709692L;
	private AdminProxy adminProxy = ConnectionFactory.getAdminProxy();
	private String username;
	private String password;
	private String url;
	private boolean rememberMe;

	@Override
	public String execute() {
		Admin admin = null;
		try {
			admin = adminProxy.getByUsername(username.trim(), true, true, true);
		} catch (Exception e) {
			logger.error("获取用户名异常");
			e.printStackTrace();
		}
		logger.info("输入得到的密码："+Md5Tool.getMd5(username+password));
		if(admin == null){
			logger.info("输入的用户名不存在");
		} else{
			logger.info("数据库中的密码："+admin.getPassword());
		}
		if (admin == null || !admin.getPassword().equals(Md5Tool.getMd5(username+password))) {
			addFieldError("error", "用户名或密码错误!");
			return INPUT;
		}
		if (rememberMe) {
			addLoginCookie( admin.getId(), admin.getUsername(), admin.getPassword());
		}
		if (checkPrivilege(admin)) {
			addFieldError("error", "您还没有相应的管理权限！");
			logger.info("角色错误。。。");
			return INPUT;
		}
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put(Constants.ADMIN_ID_KEY, admin.getId());
		session.put(Constants.ADMIN_USERNAME_KEY, admin.getUsername());
		session.put(Constants.ADMIN_DISPLAY_NAME_KEY, admin.getDisplayName());
		session.put(Constants.ADMIN_RESOURCE, admin.getAdminResources());
		logger.debug(username + "," + password + " login.");
//		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
//				this.getUserId(), null, "登录", "用户["+getUsername()+"]登录", "LoginAction.class",
//				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		String type = admin.getType().toString();
		session.put(Constants.TYPE_KEY, type);//管理员类型
		url = getStartUrl(admin.getAdminResources());
		logger.info("startUrl: "+url);
		return SUCCESS;
	} 
	public String getStartUrl(List<AdminResource> adminResources) {
		String[] urls = {
				"",
				"/system/infrastructure",
				"/system/zone",
				"/img/imglist?type=IMAGE",
				"/user/userlist",
				"/user/usermanage.jsp",
				"/group/acGroupList",
				"/vm/vmlist",
				"/vm/presearchvm",
				"/hd/hdlist",
				"/hd/hdmanage",
				"/price/getrule?itemproducts=vm,vmpackage",
				"/price/income.jsp",
				"/runtime/log.jsp",
				"/runtime/mailconf",
				"/runtime/admin_manage.jsp",
				"/runtime/role_manage.jsp",
				"/runtime/admin_log.jsp"};
		int min = adminResources.get(0).getTopBarId();
		AdminResource minAdminResource = adminResources.get(0);
		for (AdminResource adminResource : adminResources) {
			if (min > adminResource.getTopBarId()) {
				min = adminResource.getTopBarId();
				minAdminResource = adminResource;
			};
		}
		return urls[minAdminResource.getId()];
	}
	public boolean checkPrivilege(Admin admin){
		if (admin.getRoleId() == null || admin.getAdminRole() == null || 
				admin.getAdminPrivileges() == null || admin.getAdminPrivileges().size() == 0 || 
				admin.getAdminResources() == null || admin.getAdminResources().size() ==0) {
			logger.info(admin.getAdminRole());
			return true;
		}
		return false;
	}
	
	public void addLoginCookie(Integer adminId, String username, String password) {
		Cookie cookie = new Cookie(Constants.AUTH_COOKIE_ID_NAME, adminId.toString());
		cookie.setPath("/");
		cookie.setMaxAge(Integer.MAX_VALUE);
	    ServletActionContext.getResponse().addCookie(cookie);
	    cookie = new Cookie(Constants.AUTH_COOKIE_NAME, DigestUtils.md5Hex(username + password));
	    cookie.setPath("/");
	    cookie.setMaxAge(Integer.MAX_VALUE);
	    ServletActionContext.getResponse().addCookie(cookie);
	    logger.debug(username + "," + password + " add login cookie.");
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
