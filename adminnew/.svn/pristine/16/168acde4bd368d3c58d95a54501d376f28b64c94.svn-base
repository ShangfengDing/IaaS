package appcloud.admin.action.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcAggregate;
import appcloud.api.beans.AcHost;
import appcloud.api.client.AcAggregateClient;
import appcloud.api.client.AcHostClient;

import com.appcloud.vm.fe.util.ClientFactory;

public class ClusterAndHostByZidAction extends BaseAction {
	private static final long serialVersionUID = -8978894090535415113L;
	private static final Logger logger = Logger.getLogger(ClusterAndHostByZidAction.class);
	
	private AcHostClient hostClient = ClientFactory.getHostClient();
	private AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();
	private List<AcAggregate> aggregates= new ArrayList<AcAggregate>();
	private List<AcHost> hosts = new ArrayList<AcHost>();
	private String zoneId = "1";
	
	public String execute() throws Exception {
		logger.info(zoneId);
			aggregates = aggregateClient.getAggregatesOfZone(null);
			hosts = hostClient.getAcHosts();
		System.out.println("aggregates.size() = " + aggregates.size());
		return SUCCESS;
	}

	public List<AcAggregate> getAggregates() {
		return aggregates;
	}

	public void setAggregates(List<AcAggregate> aggregates) {
		this.aggregates = aggregates;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public List<AcHost> getHosts() {
		return hosts;
	}

	public void setHosts(List<AcHost> hosts) {
		this.hosts = hosts;
	}

}
