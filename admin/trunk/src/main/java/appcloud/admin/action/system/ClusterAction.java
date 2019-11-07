package appcloud.admin.action.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcAggregate;
import appcloud.api.beans.AcHost;
import appcloud.api.beans.AvailabilityZone;
import appcloud.api.beans.Flavor;
import appcloud.api.beans.Resource;
import appcloud.api.beans.Server;
import appcloud.api.client.AcAggregateClient;
import appcloud.api.client.ServerClient;
import appcloud.api.client.VolumeClient;

import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.util.ClientFactory;

public class ClusterAction extends BaseAction {
	private static final long serialVersionUID = -8978894090535415113L;
	private static final Logger logger = Logger.getLogger(ClusterAction.class);
	private ServerClient serverClient = ClientFactory.getServerClient();
	private AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();
	private VolumeClient volumeClient = ClientFactory.getVolumeClient();
	private List<AcAggregate> aggregates= new ArrayList<AcAggregate>();
	private ArrayList<HashMap<AcAggregate, List<Integer>>> clusterList = new ArrayList<HashMap<AcAggregate, List<Integer>>>();
	private int zid = 1;
	private String zname = "";
	
	public String execute() throws Exception {
		logger.info(zid);
		aggregates = aggregateClient.getAggregates();
		
		for(AcAggregate ac : aggregates){
			Integer hostNum = ac.acHosts.size();
			Integer serverNum = 0;
			for (AcHost acHost : ac.acHosts) {
				List<Server> servers = serverClient.searchByProperties("admin",
						"", "", "", "", 1, null, acHost.id, "", null, null,
						null, null);
				serverNum += servers.size();
			}
			Long volNum =  volumeClient.countByProperties("admin", null, 
					null, null, "network", null, null, false,
					null, null, ac.id, null);
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(hostNum);
			list.add(serverNum);
			list.add((int)(long)volNum);
			HashMap<AcAggregate, List<Integer>> clusterMap = new HashMap<AcAggregate, List<Integer>>();
			clusterMap.put(ac, list);
			clusterList.add(clusterMap);
		}
		
		AvailabilityZone zone = aggregateClient.getZoneById(zid);
		zname = zone.name;
		return SUCCESS;
	}

	public List<AcAggregate> getAggregates() {
		return aggregates;
	}

	public void setAggregates(List<AcAggregate> aggregates) {
		this.aggregates = aggregates;
	}

	public int getZid() {
		return zid;
	}

	public void setZid(int zid) {
		this.zid = zid;
	}
	
	public String getZname() {
		return zname;
	}

	public void setZname(String zname) {
		this.zname = zname;
	}

	public ArrayList<HashMap<AcAggregate, List<Integer>>> getClusterList() {
		return clusterList;
	}

	public void setClusterList(
			ArrayList<HashMap<AcAggregate, List<Integer>>> clusterList) {
		this.clusterList = clusterList;
	}

}
