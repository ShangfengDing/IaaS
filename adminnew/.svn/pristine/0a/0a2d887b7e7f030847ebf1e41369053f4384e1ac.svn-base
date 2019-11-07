package appcloud.admin.action.runtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import appcloud.common.model.AdminPrivilege;
import appcloud.common.model.AdminResource;
import appcloud.common.proxy.AdminPrivilegeProxy;
import appcloud.common.proxy.AdminResourceProxy;
import appcloud.common.proxy.AdminRoleProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;

public class ShowRoleActionold extends BaseAction {
	private static final Logger logger = Logger.getLogger(ShowRoleActionold.class);
	private AdminRoleProxy adminRoleProxy = (AdminRoleProxy) ConnectionFactory.getTipProxy(AdminRoleProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private AdminPrivilegeProxy adminPrivilegeProxy = (AdminPrivilegeProxy) ConnectionFactory.getTipProxy(AdminPrivilegeProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private AdminResourceProxy adminResourceProxy = (AdminResourceProxy) ConnectionFactory.getTipProxy(AdminResourceProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private List<Integer> top_bar_ids = new ArrayList<Integer>();
	private Map<AdminResource, ArrayList<AdminResource>> adminResourceMap = new HashMap<AdminResource, ArrayList<AdminResource>>();
	List<AdminResource> adminResources = new ArrayList<AdminResource>();
	private int roleid;
	private String name;
	private String result = "fail";
	
	@Override
	public String execute() throws Exception {
		this.name = adminRoleProxy.getById(roleid).getRolename();
		logger.info(name+", "+roleid);
		QueryObject<AdminPrivilege> queryObject = new QueryObject<AdminPrivilege>();
		queryObject.addFilterBean(new FilterBean<AdminPrivilege>("roleId", roleid, FilterBeanType.EQUAL));
		List<? extends AdminPrivilege> adminPrivileges = adminPrivilegeProxy.searchAll(queryObject);
		for (AdminPrivilege adminPrivilege : adminPrivileges) {
			AdminResource temp = adminResourceProxy.getById(adminPrivilege.getResourceId());
			adminResources.add(temp);
		}
		for (AdminResource adminResource : adminResources) {
			if (!top_bar_ids.contains(adminResource.getTopBarId())) {
				top_bar_ids.add(adminResource.getTopBarId());
			}
		}
		Collections.sort(top_bar_ids, new Cmp());
		for (Integer id : top_bar_ids) {
			ArrayList<AdminResource> temps = new ArrayList<AdminResource>();
			for (AdminResource adminResource : adminResources) {
				if (id.equals(adminResource.getTopBarId()) && !temps.contains(adminResource)) {
					temps.add(adminResource);
				}
			}
			adminResourceMap.put(temps.get(0), temps);
		}
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "查看管理组", "查看管理组 "+name, "ShowRoleAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		return SUCCESS;
	}
	public class Cmp implements Comparator{
		@Override
		public int compare(Object o1, Object o2) {
			Integer arg1 = (Integer)o1;
			Integer arg2 = (Integer)o2;
			return arg1 - arg2;
		}
		
	}
	
	public List<AdminResource> getAdminResources() {
		return adminResources;
	}
	public void setAdminResources(List<AdminResource> adminResources) {
		this.adminResources = adminResources;
	}
	public Map<AdminResource, ArrayList<AdminResource>> getAdminResourceMap() {
		return adminResourceMap;
	}
	public void setAdminResourceMap(
			Map<AdminResource, ArrayList<AdminResource>> adminResourceMap) {
		this.adminResourceMap = adminResourceMap;
	}
	public int getRoleid() {
		return roleid;
	}
	public void setRoleid(int roleid) {
		this.roleid = roleid;
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
}
