package appcloud.resourcescheduler.strategy;

import java.util.Comparator;
import java.util.List;

import appcloud.common.model.Host;

public class HostComparator implements Comparator<Host> {
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
				System.err.println("ATTENTION: DB Error" + e.getMessage());
				return 0;
			}
		}
		
	}