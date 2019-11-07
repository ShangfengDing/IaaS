package appcloud.admin.action.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AvailabilityZone;
import appcloud.api.client.AcAggregateClient;

import com.appcloud.vm.fe.util.ClientFactory;

public class ZoneAction extends BaseAction {
	private static final long serialVersionUID = -8978894090535415113L;
	private static final Logger logger = Logger.getLogger(ZoneAction.class);
	
	private AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();
	private List<AvailabilityZone> zones= new ArrayList<AvailabilityZone>();
	
	public String execute() {
		zones = aggregateClient.getZones();
		return SUCCESS;
	}

	public List<AvailabilityZone> getZones() {
		return zones;
	}

	public void setZones(List<AvailabilityZone> zones) {
		this.zones = zones;
	}

}
