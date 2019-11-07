package appcloud.resourcescheduler.strategy;

import java.util.Comparator;
import java.util.List;

import appcloud.common.model.Host;
import appcloud.common.model.ResrcStrategy;

public class HostComparatorFactory {
	public static Comparator<Host> getHostComparator(ResrcStrategy strategy, List<Host> hosts) {
		// only memory sorter was implemented now
		
		IHostSorter sorter = null; 
		
		if (strategy == null) {
			sorter = new MemoryHostSorter();
		} else if (strategy.getAlgoCode() == "") {
			sorter = new MemoryHostSorter();
		} else {
			sorter = new MemoryHostSorter();
		}
		
		return new HostComparator(sorter, hosts);
	}
	
	

}
