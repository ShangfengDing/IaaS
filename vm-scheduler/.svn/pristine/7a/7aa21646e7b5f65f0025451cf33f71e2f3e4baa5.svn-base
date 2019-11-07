package appcloud.vmscheduler.strategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import appcloud.common.model.Host;
import appcloud.common.model.VmInstanceType;
import appcloud.common.model.Service.ServiceTypeEnum;
import appcloud.common.model.VmInstance;
import appcloud.vmschduler.utils.Constants;
import appcloud.vmschduler.utils.PackageUtil;
import appcloud.vmscheduler.strategy.filter.BaseHostFilter;

public class HostFilterHandler {

	private static final Logger logger = Logger
			.getLogger(HostFilterHandler.class);
	private static final String PACKAGE_PRE = "appcloud.vmscheduler.strategy.filter.";
	private static final String PACKAGE_NAME = "appcloud.vmscheduler.strategy.filter";
	private String[] filterNameList;
	private ArrayList<BaseHostFilter> hostFilters;
//	private Set<String> filterSet = new LinkedHashSet<String>();

//	static {
//		if (Constants.config.getStringArray(Constants.SCHEDULER_NAME) == null) {
//			filterNameList = new String[] { "AvailablityClusterFilter" };
//		} else {
//			filterNameList = Constants.config
//					.getStringArray(Constants.SCHEDULER_NAME);
//		}
//		for (int i = 0; i < filterNameList.length; i++) {
//			filterSet.add(filterNameList[i]);
//		}
//		logger.info(filterSet.toString());
//		filterSet.add("AvailablityClusterFilter");
//	}

	public HostFilterHandler(Set<String> filterNameSet) {
		super();
		hostFilters = new ArrayList<BaseHostFilter>();
		init(filterNameSet);
	}

	private void init(Set<String> filterNameSet) {
		filterNameSet = getMatchingClasses(filterNameSet);
		for (String filterName : filterNameSet) {
			try {
				hostFilters.add((BaseHostFilter) Class.forName(
						PACKAGE_PRE + filterName).newInstance());
			} catch (InstantiationException e) {
				logger.error(e.getMessage());
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage());
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage());
			}
		}

	}

	/**
	 * 容错，排除因管理员疏忽导致填错的类
	 * 
	 * @param filterNameList
	 */

	private Set<String> getMatchingClasses(Set<String> filterNameSet) {
		Set<String> result = new LinkedHashSet<String>();
		List<String> packageList = PackageUtil.getClassName(PACKAGE_NAME);
		Iterator<String> ite = filterNameSet.iterator();
		while (ite.hasNext()) {
			String filterName = ite.next();
			if (packageList.contains(filterName)) {
				result.add(filterName);
			}
		}
		return (Set<String>) result;
	}

	public List<Host> getAvailHost(VmInstance instance, VmInstanceType instanceType,
			 Integer clusterID, ServiceTypeEnum serviceTypeEnum, List<Host> hostList) {
		logger.info("before filter");
		showHosts(hostList);
		List<Host> resultHostList = hostFilters.get(0).filterAll(hostList,
				instance, instanceType, clusterID, serviceTypeEnum);
		logger.info("after filter" + hostFilters.get(0).getClass().getCanonicalName());
		showHosts(resultHostList);
		for (int i = 1; i < hostFilters.size(); i++) {
			resultHostList = hostFilters.get(i).filterAll(resultHostList,
					instance, instanceType, clusterID, serviceTypeEnum);
			logger.info("after filter" + hostFilters.get(i).getClass().getCanonicalName());
			showHosts(resultHostList);
		}
		return resultHostList;
	}
	
	public void showHosts(List<Host> hosts) {
		for(Host h : hosts) {
			logger.info(h);
		}
	}
	// 选择可以配置的过滤规则对象
	// 使用ConcreteFilter的算法，并在运行时刻动态动态设置应用哪个或那几个算法。
	// allFilter();
	// standardFilter();

}
