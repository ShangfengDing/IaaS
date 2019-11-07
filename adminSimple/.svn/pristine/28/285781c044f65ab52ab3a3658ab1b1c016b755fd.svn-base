package appcloud.admin.action.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import appcloud.common.model.Cluster;
import appcloud.common.model.Host;
import appcloud.common.model.VmInstance;
import appcloud.common.proxy.ClusterProxy;
import appcloud.common.proxy.HostProxy;
import appcloud.common.proxy.VmInstanceProxy;
import appcloud.common.proxy.VmVolumeProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import com.opensymphony.xwork2.Action;
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

	private List<Cluster> clusters=new ArrayList<>();
	private ClusterProxy clusterProxy= ConnectionFactory.getClusterProxy();

	private long servercount;
	private long volumecount;
	private long hostcount;
	private Integer id;
	private String result;
	private HostProxy hostProxy=ConnectionFactory.getHostProxy();
	private VmInstanceProxy vmInstanceProxy=ConnectionFactory.getVmInstanceProxy();

	private long count;

	public String execute() throws Exception {
		logger.info(zid);
		aggregates = aggregateClient.getAggregates();//cluster
		
		for(AcAggregate ac : aggregates){
			Integer hostNum = ac.acHosts.size();//vm_host 有多少个
			Integer serverNum = 0; //vm_instance
			for (AcHost acHost : ac.acHosts) {
				List<Server> servers = serverClient.searchByProperties("admin",
						"", "", "", "", 1, null, acHost.id, "", null, null,
						null, null);//find in vm_instance search by zoneid,hostid
				System.out.println(servers.size()+"+"+acHost.id);
				serverNum += servers.size(); //add
			}
			Long volNum =  volumeClient.countByProperties("admin", null, 
					null, null, "network", null, null, false,
					null, null, ac.id, null);//search in vm_volume search by usage_type,cluster_id
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(hostNum);
			list.add(serverNum);
			list.add((int)(long)volNum);
			HashMap<AcAggregate, List<Integer>> clusterMap = new HashMap<AcAggregate, List<Integer>>();
			clusterMap.put(ac, list);
			clusterList.add(clusterMap);
		}
		
		AvailabilityZone zone = aggregateClient.getZoneById(zid);
		zname = zone.name; //search in vm_zone by id=1;
		return SUCCESS;
	}


	public String findAllCluster(){
		try {
			clusters = (List<Cluster>)clusterProxy.findAll(false, false, false);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("find all clusters error");
			return Action.ERROR;
		}
		return Action.SUCCESS;
	}

	public String countClusterDevices(){
		volumecount =  volumeClient.countByProperties("admin", null,
				null, null, "network", null, null, false,
				null, null, id, null);
		try {
			FilterBean<Host> filterBean=new FilterBean<Host>("clusterId",id, FilterBean.FilterBeanType.EQUAL);
			QueryObject<Host> queryObject=new QueryObject<Host>();
			queryObject.addFilterBean(filterBean);
			//hostcount = hostProxy.countSearch(queryObject);
			List<Host> hostlist=(List<Host>)hostProxy.searchAll(queryObject,false,false,false);
			hostcount=hostlist.size();
			servercount=0;
//			FilterBean<VmInstance> filter2 = new FilterBean<VmInstance>("availabilityClusterId",id, FilterBean.FilterBeanType.EQUAL);
//			QueryObject<VmInstance> query = new QueryObject<VmInstance>();
//			query.addFilterBean(filter2);
//			servercount += vmInstanceProxy.countSearch(query);
			for(Host host:hostlist) {
//				FilterBean<VmInstance> filter = new FilterBean<VmInstance>("hostUuid",host.getUuid(), FilterBean.FilterBeanType.EQUAL);
//				FilterBean<VmInstance> filter2 = new FilterBean<VmInstance>("availabilityZoneId",1, FilterBean.FilterBeanType.EQUAL);
//				QueryObject<VmInstance> query = new QueryObject<VmInstance>();
//				query.addFilterBean(filter);
//				query.addFilterBean(filter2);
//				servercount += vmInstanceProxy.countSearch(query);
				List<Server> servers = serverClient.searchByProperties("admin",
						"", "", "", "", 1, null, host.getUuid(), "", null, null,
						null, null);//find in vm_instance search by zoneid,hostid
				servercount += servers.size(); //add
			}

		}catch(Exception e){
			e.printStackTrace();
			result="error";
			return Action.ERROR;
		}
		result="success";
		return Action.SUCCESS;
	}
	public String countAll(){
		return Action.SUCCESS;
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

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<Cluster> getClusters() {
		return clusters;
	}

	public void setClusters(List<Cluster> clusters) {
		this.clusters = clusters;
	}

	public long getServercount() {
		return servercount;
	}

	public void setServercount(long servercount) {
		this.servercount = servercount;
	}

	public long getVolumecount() {
		return volumecount;
	}

	public void setVolumecount(long volumecount) {
		this.volumecount = volumecount;
	}

	public long getHostcount() {
		return hostcount;
	}

	public void setHostcount(long hostcount) {
		this.hostcount = hostcount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
