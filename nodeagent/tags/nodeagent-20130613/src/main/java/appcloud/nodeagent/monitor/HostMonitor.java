package appcloud.nodeagent.monitor;

import org.apache.log4j.Logger;
import appcloud.common.model.Host;
import appcloud.common.model.HostLoad;
import appcloud.common.proxy.HostLoadProxy;
import appcloud.common.proxy.HostProxy;
import appcloud.nodeagent.info.LoadSummary;
import appcloud.nodeagent.info.NodeAgent;
import appcloud.nodeagent.util.SystemUtils;

/**
 * 
 * @author wylazy
 *
 */
public class HostMonitor implements Runnable {

    private static Logger logger = Logger.getLogger(HostMonitor.class);
    
	private NodeAgent nodeAgent;
    private HostProxy hostProxy;
    private HostLoadProxy hostLoadProxy;
    private LoadSummary loadSummary;
    private Host host;
    private int maxDelay;
	
    private int count;
    
	public HostMonitor() {
		count = -1;
		nodeAgent = NodeAgent.getInstance();
	}
	
	/**
	 * 获取Host最新的负载
	 */
	public void run() {
		if (count < 0) {
			count++;
			return;
		}
		HostLoad hostLoad = SystemUtils.currentHostLoad(host);
		loadSummary.addLoad(hostLoad);
		
		logger.debug("update host info : " + hostLoad);
		if (count++ % maxDelay == 0) {
			hostLoadProxy.save(loadSummary.asHostLoad());
			logger.debug("save host load : " + loadSummary.asHostLoad());
			loadSummary.clear();
		}
	}

    public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}
	
    public NodeAgent getNodeAgent() {
		return nodeAgent;
	}

	public void setNodeAgent(NodeAgent nodeAgent) {
		this.nodeAgent = nodeAgent;
	}

	public HostProxy getHostProxy() {
		return hostProxy;
	}

	public void setHostProxy(HostProxy hostProxy) {
		this.hostProxy = hostProxy;
	}

	public HostLoadProxy getHostLoadProxy() {
		return hostLoadProxy;
	}

	public void setHostLoadProxy(HostLoadProxy hostLoadProxy) {
		this.hostLoadProxy = hostLoadProxy;
	}

	public LoadSummary getLoadSummary() {
		return loadSummary;
	}

	public void setLoadSummary(LoadSummary loadSummary) {
		this.loadSummary = loadSummary;
	}

	public int getMaxDelay() {
		return maxDelay;
	}

	public void setMaxDelay(int maxDelay) {
		this.maxDelay = maxDelay;
	}

}
