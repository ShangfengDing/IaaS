package appcloud.vmscheduler.strategy;

import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.common.model.Host;
import appcloud.common.model.ResrcStrategy;
import appcloud.common.util.ConnectionFactory;

public class HostComparatorFactory {
	private static Logger logger = Logger.getLogger(HostSorter.class);
	public static Comparator<Host> getHostComparator(Integer clusterID, List<Host> hosts) {
		// only memory sorter was implemented now
		IHostSorter sorter = null; 
		try {
			sorter = new HostSorter();
			return new HostComparator(sorter, hosts);
		} catch (Exception e) {
			logger.error("获取集群失败，"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}
