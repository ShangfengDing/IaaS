package appcloud.resourcescheduler.strategy;

import java.util.List;

import appcloud.common.model.Host;
import appcloud.common.util.ConnectionFactory;

public class MemoryHostSorter implements IHostSorter{

	public int scoreHost(Host host) throws Exception {
		// simple sorter, just score by free memory
		if (host.getLoad() == null) {
			host.setLoad(ConnectionFactory.getLoadProxy().getCurLoadByUuid(host.getUuid()));			
		}
		
		return host.getMemoryMb() - host.getLoad().getMemoryUseMb();
	}

	public void setHosts(List<Host> hosts) {
		// this sorter is very simple and does not care about other hosts
	}

}
