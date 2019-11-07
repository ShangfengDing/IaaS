package appcloud.admin.action.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcHost;
import appcloud.api.beans.AcService;
import appcloud.api.beans.AvailabilityZone;
import appcloud.api.beans.Resource;
import appcloud.api.client.AcAggregateClient;
import appcloud.api.client.AcHostClient;
import appcloud.api.client.AcServiceClient;

import com.appcloud.vm.fe.util.ClientFactory;

public class HostAction extends BaseAction {
	private static final long serialVersionUID = -8978894090535415113L;
	private static final Logger logger = Logger.getLogger(HostAction.class);
	private AcHostClient hostClient = ClientFactory.getHostClient();
	private AcServiceClient serviceClient = ClientFactory.getServiceClient();
	private AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();
	//查找所有host列表
	private List<AcHost> hosts = new ArrayList<AcHost>();
	private HashMap<String, Resource> hostIdResourceMap = 
			new HashMap<String, Resource>();		//host的资源配置详情
	private HashMap<String, List<AcService>> hostIdServiceMap = 
			new HashMap<String, List<AcService>>();	//host上开启的service服务列表
	private int zid;
	private String zname = "";
	private List<AvailabilityZone> zones = null;
	
	
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() {
		zones = aggregateClient.getZones();
		return SUCCESS;
	}
//	public String execute() {
//		logger.info(zid);
//		hosts = hostClient.getAcHostOfZone(zid);
//		for (AcHost host : hosts) {
//			for (Resource resource : host.resources) {
//				if (resource.project.equals("(total)")) {
//					hostIdResourceMap.put(host.id, resource);
//					break;
//				}
//			}
//		}
//		logger.info("查找所有host列表成功");
//		
//		//查找平台上开启的所有service
//		List<AcService> allServices = serviceClient.getAllServices();
//		for (AcService service : allServices) {
//			String hostId = service.hostUuid;
//			List<AcService> hostServices = hostIdServiceMap.get(hostId);
//			if (hostServices == null) {
//				hostServices = new ArrayList<AcService>();
//			}
//			hostServices.add(service);
//			hostIdServiceMap.put(hostId, hostServices);
//		}
//		logger.info("查找平台所有service成功");
//		
//		AvailabilityZone zone = aggregateClient.getZoneById(zid);
//		zname = zone.name;
//		return SUCCESS;
//	}
	
	public List<AvailabilityZone> getZones() {
		return zones;
	}

	public void setZones(List<AvailabilityZone> zones) {
		this.zones = zones;
	}

	public List<AcHost> getHosts() {
		return hosts;
	}
	public void setHosts(List<AcHost> hosts) {
		this.hosts = hosts;
	}

	public HashMap<String, Resource> getHostIdResourceMap() {
		return hostIdResourceMap;
	}

	public void setHostIdResourceMap(HashMap<String, Resource> hostIdResourceMap) {
		this.hostIdResourceMap = hostIdResourceMap;
	}

	public HashMap<String, List<AcService>> getHostIdServiceMap() {
		return hostIdServiceMap;
	}

	public void setHostIdServiceMap(
			HashMap<String, List<AcService>> hostIdServiceMap) {
		this.hostIdServiceMap = hostIdServiceMap;
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
}
