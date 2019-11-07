package appcloud.admin.action.vm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AvailabilityZone;
import appcloud.api.beans.Server;
import appcloud.api.client.AcAggregateClient;
import appcloud.api.client.ServerClient;

import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.util.ClientFactory;

public class VmListAction extends BaseAction{
	private Logger logger = Logger.getLogger(VmListAction.class);
	private ServerClient serverClient = ClientFactory.getServerClient();
	private AcAggregateClient zoneClient = ClientFactory.getAggregateClient();
	
	private List<Server> servers = new ArrayList<Server>();
	private HashMap<Integer, String> zoneIdNameMap = new HashMap<Integer, String>();
	
	public String execute() {
		servers = serverClient.getAllTenantsServers(Constants.ADMIN_TENANT_ID, true);
		logger.info("获得所有server成功");
		if(servers == null){
			return SUCCESS;
		}
		List<AvailabilityZone> zones = zoneClient.getZones();
		for (AvailabilityZone zone : zones) {
			zoneIdNameMap.put(zone.id, zone.name);
		}
		return SUCCESS;
	}

	public List<Server> getServers() {
		return servers;
	}

	public HashMap<Integer, String> getZoneIdNameMap() {
		return zoneIdNameMap;
	}

}
