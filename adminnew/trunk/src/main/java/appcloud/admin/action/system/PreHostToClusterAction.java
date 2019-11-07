package appcloud.admin.action.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcAggregate;
import appcloud.api.client.AcAggregateClient;

import com.appcloud.vm.fe.util.ClientFactory;

public class PreHostToClusterAction extends BaseAction {
	private static final long serialVersionUID = -8978894090535415113L;
	private static final Logger logger = Logger.getLogger(PreHostToClusterAction.class);
	
	private AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();
	
	private String hostId;
	private int zoneId;
	private List<AcAggregate> aggregates= new ArrayList<AcAggregate>();
	
	//TODO:查找和host相同router的cluster
	public String execute() {
		aggregates = aggregateClient.getAggregatesOfZone(zoneId);
		return SUCCESS;
	}

	public List<AcAggregate> getAggregates() {
		return aggregates;
	}

	public void setAggregates(List<AcAggregate> aggregates) {
		this.aggregates = aggregates;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

}
