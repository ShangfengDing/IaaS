package appcloud.admin.action.accounts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import appcloud.admin.action.base.BaseAction;
import appcloud.admin.common.Constants;
import appcloud.common.model.Admin;
import appcloud.common.model.AdminResource;
import appcloud.common.proxy.AdminProxy;
import appcloud.common.util.ConnectionFactory;

public class GetResourceList extends BaseAction implements SessionAware{
	private static final Logger logger = Logger.getLogger(GetResourceList.class);
	private Map<String, Object> session;
	private List<Integer> topBarList = new ArrayList<Integer>();
	private List<Integer> leftBarList = new ArrayList<Integer>();
	private Map<Integer, List<Integer>> leftBarListByTopBar = new HashMap<Integer, List<Integer>>();
	private Integer topBarId;
//	private AdminProxy adminProxy = ConnectionFactory.getAdminProxy();

	@Override
	public String execute() throws Exception {
		Cmp cmp = new Cmp();
		List<AdminResource> adminResources = (List<AdminResource>) session.get(Constants.ADMIN_RESOURCE);
		for (AdminResource adminResource : adminResources) {
			if (!topBarList.contains(adminResource.getTopBarId()))
				topBarList.add(adminResource.getTopBarId());
		}
		Collections.sort(topBarList, cmp);
		for (Integer id : topBarList) {
			List<Integer> leftBarList = new ArrayList<Integer>();
			for (AdminResource adminResource : adminResources) {
				if (id.equals(adminResource.getTopBarId()) && !leftBarList.contains(adminResource.getLeftBarId()))
					leftBarList.add(adminResource.getLeftBarId());
			}
			Collections.sort(leftBarList);
			leftBarListByTopBar.put(id, leftBarList);
		}
		for (AdminResource adminResource : adminResources) {
			if (this.topBarId.equals(adminResource.getTopBarId()) && !leftBarList.contains(adminResource.getLeftBarId())) {
				leftBarList.add(adminResource.getLeftBarId());
			}
		}
		Collections.sort(leftBarList, cmp);
		
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
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public List<Integer> getTopBarList() {
		return topBarList;
	}
	public void setTopBarList(List<Integer> topBarList) {
		this.topBarList = topBarList;
	}
	public Integer getTopBarId() {
		return topBarId;
	}
	public void setTopBarId(Integer topBarId) {
		this.topBarId = topBarId;
	}
	public List<Integer> getLeftBarList() {
		return leftBarList;
	}
	public void setLeftBarList(List<Integer> leftBarList) {
		this.leftBarList = leftBarList;
	}
	public Map<Integer, List<Integer>> getLeftBarListByTopBar() {
		return leftBarListByTopBar;
	}
	public void setLeftBarListByTopBar(
			Map<Integer, List<Integer>> leftBarListByTopBar) {
		this.leftBarListByTopBar = leftBarListByTopBar;
	}
}
