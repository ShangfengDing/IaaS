package appcloud.admin.action.runtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.common.model.AdminResource;
import appcloud.common.proxy.AdminResourceProxy;
import appcloud.common.proxy.AdminRoleProxy;
import appcloud.common.util.ConnectionFactory;

public class PreAddRoleAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(PreAddRoleAction.class);
	private List<Integer> top_bar_ids = new ArrayList<Integer>();
	private AdminResourceProxy adminResourceProxy = (AdminResourceProxy) ConnectionFactory.getTipProxy(AdminResourceProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private Map<AdminResource, ArrayList<AdminResource>> adminResourceMap = new HashMap<AdminResource, ArrayList<AdminResource>>();
	
	@Override
	public String execute() throws Exception {
		List<AdminResource> adminResources = (List<AdminResource>) adminResourceProxy.findAll();
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
	
	public Map<AdminResource, ArrayList<AdminResource>> getAdminResourceMap() {
		return adminResourceMap;
	}
	public void setAdminResourceMap(
			Map<AdminResource, ArrayList<AdminResource>> adminResourceMap) {
		this.adminResourceMap = adminResourceMap;
	}
}
