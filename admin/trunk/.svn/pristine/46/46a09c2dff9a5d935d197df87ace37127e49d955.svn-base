package appcloud.admin.action.vm;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcAggregate;
import appcloud.api.beans.AcHost;
import appcloud.api.beans.AvailabilityZone;
import appcloud.api.client.AcAggregateClient;
import appcloud.api.client.AcHostClient;

import com.appcloud.vm.fe.util.ClientFactory;

public class PreSearchVmAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(PreSearchVmAction.class);
	private AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();
	private AcHostClient hostClient = ClientFactory.getHostClient();
	
	private List<AcAggregate> aggregates= new ArrayList<AcAggregate>();
	private List<AvailabilityZone> zones= new ArrayList<AvailabilityZone>();
	private List<AcHost> hosts = new ArrayList<AcHost>();
	
	private String hostId = "";
	private int zoneId = -1;
	private int clusterId = -1;
	
	public String execute() throws Exception {
		aggregates = aggregateClient.getAggregatesOfZone(null);
		zones = aggregateClient.getZones();
		hosts = hostClient.getAcHosts();
		return SUCCESS;
	}

	public List<AcAggregate> getAggregates() {
		return aggregates;
	}

	public void setAggregates(List<AcAggregate> aggregates) {
		this.aggregates = aggregates;
	}

	public List<AvailabilityZone> getZones() {
		return zones;
	}

	public void setZones(List<AvailabilityZone> zones) {
		this.zones = zones;
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

	public List<AcHost> getHosts() {
		return hosts;
	}

	public void setHosts(List<AcHost> hosts) {
		this.hosts = hosts;
	}

	public int getClusterId() {
		return clusterId;
	}

	public void setClusterId(int clusterId) {
		this.clusterId = clusterId;
	}
	
}
