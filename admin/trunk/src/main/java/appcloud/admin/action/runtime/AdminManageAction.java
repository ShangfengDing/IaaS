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
import appcloud.common.proxy.AdminProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import appcloud.common.util.query.FilterBean.FilterBeanType;

public class AdminManageAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(AdminManageAction.class);
	private AdminProxy adminProxy = (AdminProxy) ConnectionFactory.getTipProxy(AdminProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private List<Admin> admins = new ArrayList<Admin>();
	private String adminId;
	private String name;
	private String email;
	private int page = 1;
	private int total = 0;
	private int lastpage = 1;
	private final static int PAGESIZE = 10;
	
	@Override
	public String execute() throws Exception {
		logger.info("adminManageAction: " + name+ ", "+email);
		Map<String, Object> session = ActionContext.getContext().getSession();
		this.adminId = session.get(Constants.ADMIN_ID_KEY).toString();
		logger.info(adminId);
		QueryObject<Admin> queryObject = new QueryObject<Admin>();
		if (!name.equals("")) {
			queryObject.addFilterBean(new FilterBean<Admin>("username", name, FilterBeanType.BOTH_LIKE));
		}
		if (!email.equals("")) {
			queryObject.addFilterBean(new FilterBean<Admin>("email", email, FilterBeanType.BOTH_LIKE));
		}
		this.admins = (List<Admin>) adminProxy.searchAll(queryObject, true, true, true, page, PAGESIZE);
		this.total = (int) adminProxy.countSearch(queryObject);
		if (admins != null && admins.size() != 0) {
			if(total % PAGESIZE == 0){
				lastpage = total / PAGESIZE;
			}else{
				lastpage = total / PAGESIZE + 1;
			}
		}
		logger.info("total: "+total+", page: "+page+", lastpage: "+lastpage);
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "查看管理员", "查询管理员 ", "AdminManageAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		return SUCCESS;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public List<Admin> getAdmins() {
		return admins;
	}

	public void setAdmins(List<Admin> admins) {
		this.admins = admins;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
