package appcloud.vmscheduler.strategy;

import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.common.model.Host;


public class HostComparator implements Comparator<Host>  {
	private static Logger logger = Logger.getLogger(HostComparator.class);
	private IHostSorter sorter;
	
	public HostComparator(IHostSorter sorter, List<Host> hosts) {
		this.sorter = sorter;
		this.sorter.setHosts(hosts);
	}
	public int compare(Host host1, Host host2) {
		// to sort by score in descending order
		try {
			return sorter.scoreHost(host2) - sorter.scoreHost(host1);
		} catch (Exception e) {
			logger.error("ATTENTION: DB Error" + e.getMessage());
			return 0;
		}
	}

//	@Override
//	public int compare(Host o1, Host o2) {
//		return scoreHost(o1) - scoreHost(o2);
//	}
	
//	private int scoreHost(Host host) {
//		switch (host.getStatus()) {
//		case HIGH_LOAD:
//			return 3;
//		case NORMAL_LOAD:
//			return 2;
//		case LOW_LOAD:
//			return 1;
//		}
//		return 0;
//	}

}
