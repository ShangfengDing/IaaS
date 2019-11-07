package appcloud.vs.strategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import appcloud.common.model.Cluster;
import appcloud.common.model.Host;
import appcloud.common.model.VmInstanceType;
import appcloud.common.model.Service.ServiceTypeEnum;
import appcloud.common.model.VmInstance;
import appcloud.common.proxy.ClusterProxy;
import appcloud.vs.Conf;
import appcloud.vs.impl.PackageUtil;
import appcloud.vs.strategy.filter.BaseFilter;
import appcloud.vs.strategy.filter.BaseHostFilter;
import appcloud.vs.strategy.filter.SameHostFilter;

public class HostFilterHandler {

	private static final Logger logger = Logger.getLogger(HostFilterHandler.class);
	private static final String PACKAGE_PRE = "appcloud.vs.strategy.filter.";
	private static final String PACKAGE_NAME = "appcloud.vs.strategy.filter";
	private List<BaseHostFilter> hostFilters = new ArrayList<BaseHostFilter>();
	private Set<String> filterNameSet = new LinkedHashSet<String>();

//	static {
//		if (Conf.config.getStringArray(Conf.SCHEDULER_NAME) == null) {
//			filterNameList = null;
//		} else {
//			filterNameList = Conf.config.getStringArray(Conf.SCHEDULER_NAME);
//		}
//		for (int i = 0; i < filterNameList.length; i++) {
//			filterSet.add(filterNameList[i]);
//		}
//		logger.info(filterSet.toString());
//	}

	public HostFilterHandler(String[] filterNameList) {
		for (String name : filterNameList) {
			this.filterNameSet.add(name);
		}
		init();
	}
	
	private void init(){
		this.filterNameSet = getMatchingClasses(this.filterNameSet);
		for (String filterName : this.filterNameSet) {
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

	private static HashSet<String> getMatchingClasses(
			Set<String> filterNameSet) {
		Set<String> result = new HashSet<String>();
		List<String> packageList = PackageUtil.getClassName(PACKAGE_NAME);
		Iterator<String> ite = filterNameSet.iterator();
		while (ite.hasNext()) {
			String filterName = ite.next();
			if (packageList.contains(filterName)) {
				result.add(filterName);
			}
		}
		return (HashSet<String>) result;
	}

	public List<Host> getAvailHost(Cluster cluster, Integer size, Host exHost, ServiceTypeEnum serviceTypeEnum, List<Host> hostList) {
		List<Host> resultHostList = null;
		logger.info(hostFilters);
		for (BaseFilter filter : hostFilters) {
			resultHostList = filter.filterAll(hostList, cluster, size, serviceTypeEnum, exHost);
		}
		return resultHostList;
	}
	
	public List<Host> getSameHost(Cluster cluster, Integer size , Host exHost, ServiceTypeEnum serviceTypeEnum, List<Host> hostList) {
		BaseFilter filter = new SameHostFilter();
		List<Host> resultHostList = filter.filterAll(hostList, cluster, size, serviceTypeEnum, exHost);
		return resultHostList;
	}
}
