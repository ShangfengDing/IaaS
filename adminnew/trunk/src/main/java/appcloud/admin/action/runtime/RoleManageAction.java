package appcloud.admin.action.runtime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;

import appcloud.admin.action.base.BaseAction;
import appcloud.admin.common.Constants;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import appcloud.common.model.Admin;
import appcloud.common.model.AdminRole;
import appcloud.common.proxy.AdminProxy;
import appcloud.common.proxy.AdminRoleProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;

public class RoleManageAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(RoleManageAction.class);
	private AdminProxy adminProxy = (AdminProxy) ConnectionFactory.getTipProxy(AdminProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private AdminRoleProxy adminRoleProxy = (AdminRoleProxy) ConnectionFactory.getTipProxy(AdminRoleProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private String name;
	private String result;
	private String adminId;
	private String roleid;
	private List<AdminRole> adminRoles = new ArrayList<AdminRole>();
	private int page = 1;
	private int total = 0;
	private int lastpage = 1;
	private final static int PAGESIZE = 10;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> session = ActionContext.getContext().getSession();
		this.adminId = session.get(Constants.ADMIN_ID_KEY).toString();
		this.roleid = adminProxy.getById(Integer.parseInt(adminId), false, false, false).getRoleId()+"";
		logger.info(adminId);
		QueryObject<AdminRole> queryObject = new QueryObject<AdminRole>();
		if (name != "") {
			queryObject.addFilterBean(new FilterBean<AdminRole>("rolename", name, FilterBeanType.BOTH_LIKE));
		}
		adminRoles = (List<AdminRole>) adminRoleProxy.searchAll(queryObject, page, PAGESIZE);
		total = (int) adminRoleProxy.countSearch(queryObject);
		if (adminRoles != null && adminRoles.size() != 0) {
			if(total % PAGESIZE == 0){
				lastpage = total / PAGESIZE;
			}else{
				lastpage = total / PAGESIZE + 1;
			}
		}
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "查看管理组", "查看管理组 ", "RoleManageAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		return SUCCESS;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
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

	public List<AdminRole> getAdminRoles() {
		return adminRoles;
	}

	public void setAdminRoles(List<AdminRole> adminRoles) {
		this.adminRoles = adminRoles;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getLastpage() {
		return lastpage;
	}

	public void setLastpage(int lastpage) {
		this.lastpage = lastpage;
	}
}
