package appcloud.resourcescheduler.strategy;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.common.model.Cluster;
import appcloud.common.model.ResrcStrategy;

public class ClusterSelector {
	private static Logger logger = Logger.getLogger(ClusterSelector.class);
	
	public static List<Cluster> selectQualifiedClusters(List<Cluster> clusters, ResrcStrategy strategy, int minNum) {
		logger.info("select cluster with: " + strategy + " min num: " + minNum);
		List<Cluster> qualifiedClusters = new ArrayList<Cluster>();
		
		for (Cluster cluster : clusters) {
			logger.debug("cluster: " + cluster);
			if ((cluster.getHostNum() >= minNum) && cluster.getResrcStrategyUuid().equals(strategy.getId())) {
				qualifiedClusters.add(cluster);
			}
		}
		
		logger.info("qualified clusters are:" + qualifiedClusters);
		
		return qualifiedClusters;
	}

}
