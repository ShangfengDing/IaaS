package appcloud.vmscheduler.strategy;

import java.util.List;

import appcloud.common.model.Host;

public interface IHostSorter {
	/**
	 * @param host	host to get a score;
	 * @return		the overall score this host wins, 
	 * 				greater score means better host.
	 * @throws Exception 
	 */
	public int scoreHost(Host host) throws Exception;
	
	public void setHosts(List<Host> hosts);
	
	public void setParams(String params);

}
