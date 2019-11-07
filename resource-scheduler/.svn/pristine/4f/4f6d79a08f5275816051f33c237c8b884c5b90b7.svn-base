package appcloud.resourcescheduler.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.common.constant.HostCapacities;
import appcloud.common.model.Cluster;
import appcloud.common.model.Host.HostTypeEnum;
import appcloud.common.model.Instance.InstanceTypeEnum;
import appcloud.common.model.ResrcStrategy;
import appcloud.common.model.Host;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;
import appcloud.resourcescheduler.model.Resource;

public class HostSelector {
	private static Logger logger = Logger.getLogger(HostSelector.class);
	
	@SuppressWarnings("unchecked")
	// 算法模块，抛就抛吧
	public static List<Host> selectNodes(ResrcStrategy strategy, int num, Resource minRequest, InstanceTypeEnum type) throws Exception {
		List<Host> selectedHosts = new ArrayList<Host>();
		
		List<Cluster> clusters = (List<Cluster>) ConnectionFactory.getClusterProxy().findAll(true, false, true);
		List<Cluster> qualifiedClusters = ClusterSelector.selectQualifiedClusters(clusters, strategy, num);
		
		if (qualifiedClusters.size() > 0) {
			for (Cluster cluster : qualifiedClusters) {
				if (cluster.getHosts() == null) {
					QueryObject<Host> queryObject = new QueryObject<Host>();
					queryObject.addFilterBean(new FilterBean<Host>("type", HostTypeEnum.COMPUTE_NODE, FilterBeanType.EQUAL));
					queryObject.addFilterBean(new FilterBean<Host>("clusterId", cluster.getId(), FilterBeanType.EQUAL));
					selectedHosts = (List<Host>) ConnectionFactory.getHostProxy().searchAll(queryObject, false, true, true);
				}
				logger.debug("selected hosts num before filtering:" + selectedHosts.size());
				
				// filter by type, j2ee or vm
				selectedHosts = filterByType(selectedHosts, type);
				logger.debug("selected hosts num after filtering:" + selectedHosts.size());
				
				// sorted by selected strategy
				Collections.sort(selectedHosts, HostComparatorFactory.getHostComparator(strategy, selectedHosts));
				logger.debug("selected hosts sorted are:" + selectedHosts);
				
				// check if meet the needs
				if(!meetTheNeeds(selectedHosts, minRequest, num))
					break;				
			}
		} else {
			logger.warn("no hosts with strategy " + strategy + " and num " + num + "found");
		}
		
		return selectedHosts;
	}

	/**
	 * currently, we just check the num, not the resources
	 * 
	 * @param selectedHosts
	 * @param minRequest
	 * @param num
	 * @return
	 */
	private static boolean meetTheNeeds(List<Host> selectedHosts,
			Resource minRequest, int num) {
		if (selectedHosts != null && selectedHosts.size() >= num)
			return true;
		else
			return false;
	}

	private static List<Host> filterByType(List<Host> selectedHosts,
			InstanceTypeEnum type) {
		List<Host> typedHosts = new ArrayList<Host>();
		
		for (Host host : selectedHosts) {
			// !!!!!!! need fixed!!!!!!!
//			if(type == InstanceTypeEnum.J2EE && host.getCapability().toUpperCase().contains(HostCapacities.J2EE)) {
//				typedHosts.add(host);
//			}
//			if(type == InstanceTypeEnum.VM && host.getCapability().toUpperCase().contains(HostCapacities.VM)) {
//				typedHosts.add(host);
//			}
		}
		
		return typedHosts;
	}

}
