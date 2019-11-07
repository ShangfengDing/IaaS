package appcloud.admin.action.system;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcAggregate;
import appcloud.api.beans.AcHost;
import appcloud.api.beans.AcService;
import appcloud.api.beans.Flavor;
import appcloud.api.beans.Resource;
import appcloud.api.beans.Server;
import appcloud.api.client.AcAggregateClient;
import appcloud.api.client.AcHostClient;
import appcloud.api.client.AcServiceClient;
import appcloud.api.client.FlavorClient;
import appcloud.api.client.ServerClient;
import appcloud.api.enums.AcHostTypeEnum;
import appcloud.api.enums.AcServiceTypeEnum;

import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.util.ClientFactory;

public class HostDetailAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -915719876932863057L;
	private Logger logger = Logger.getLogger(HostDetailAction.class);
	private AcHostClient hostClient = ClientFactory.getHostClient();	
	private AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();
	private FlavorClient flavorClient = ClientFactory.getFlavorClient();
	private ServerClient serverClient = ClientFactory.getServerClient();
	private AcServiceClient serviceClient = ClientFactory.getServiceClient();
	private HashMap<String, String> serviceStatusMap;
	private String NOT_USED = "NOT USED";
	private String hostName;	//host的id
	private Integer hostMem;
	private String hostIp;
	private Integer hostCPU;
	private Integer hostDisk;
	private String hostLocation;
	private String hostType;
	private String hostAggregate;
	private Integer cpu_oversell;
	private Integer memory_oversell;
	private Integer disk_oversell;
	private Integer cpu_sell;
	private Integer memory_sell;
	private Integer disk_sell;
	
	@Override
	public String execute() throws Exception {

		AcHost acHost = hostClient.get(hostName);
		hostAggregate = acHost.aggregateName;
		hostIp = acHost.ip;
		for (Resource resource : acHost.resources) {
			if (resource.project.equals("(total)")) {
				hostMem = resource.memoryMb;
				hostCPU = resource.cpu;
				hostDisk = resource.diskGb;
				break;
			}
		}
		if(acHost.location.equals("PhysicalNode")) {
			hostLocation = "物理节点";
		} else {
			hostLocation = "虚拟节点";
		}
		if (acHost.hostType.toString().equals(
				AcHostTypeEnum.COMPUTE_NODE.toString())) {
			hostType = "计算节点";
		} else if (acHost.hostType.toString().equals(
				AcHostTypeEnum.STORAGE_NODE.toString())) {
			hostType = "存储节点";
		} else {
			hostType = "功能节点";
		}
		/*超售比例
		可供销售
		实际销售
		剩余资源
		销售比例 */
		AcAggregate aggregate = aggregateClient.get(acHost.aggregateId);
		cpu_oversell = aggregate.cpu_oversell;
		memory_oversell = aggregate.memory_oversell;
		disk_oversell = aggregate.disk_oversell;
		//tenantId=admin,userIds.toString()=,uuid=,name=,status=,zid=null,cid=2,hostId=,ip,start=null,end=null,page=1,Ps=10
		//获取实际销售，
		List<Server> servers = serverClient.searchByProperties("admin", "", "", "", "",
				1, null, acHost.id, "", null, null, null, null);
		cpu_sell = 0;
		memory_sell = 0;
		disk_sell = 0;
		for(Server server : servers){
			Flavor flavor = flavorClient.get(Constants.ADMIN_TENANT_ID, server.flavor.id);
			cpu_sell += flavor.vcpus;
			memory_sell += flavor.ram * 1024;
			disk_sell += flavor.disk;
		}
		//启动停止服务
		serviceStatusMap = new HashMap<String, String>();
		AcServiceTypeEnum[] services = AcServiceTypeEnum.values();
		List<AcService> usedServices = serviceClient.getHostService(hostName);
		System.out.println("usedServices.size() = " + usedServices.size());
		for(AcService usedService : usedServices) {
			serviceStatusMap.put(usedService.type.toString(), usedService.serviceStatus.toString());
		}
		
		for(AcServiceTypeEnum service : services) {
			if(!serviceStatusMap.containsKey(service.toString())) {
				serviceStatusMap.put(service.toString(), NOT_USED);
			}
		}
		return SUCCESS;
	}
	
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getHostLocation() {
		return hostLocation;
	}
	public void setHostLocation(String hostLocation) {
		this.hostLocation = hostLocation;
	}
	public String getHostType() {
		return hostType;
	}
	public void setHostType(String hostType) {
		this.hostType = hostType;
	}
	public String getHostAggregate() {
		return hostAggregate;
	}
	public void setHostAggregate(String hostAggregate) {
		this.hostAggregate = hostAggregate;
	}
	public Integer getCpu_oversell() {
		return cpu_oversell;
	}
	public void setCpu_oversell(Integer cpu_oversell) {
		this.cpu_oversell = cpu_oversell;
	}
	public Integer getMemory_oversell() {
		return memory_oversell;
	}
	public void setMemory_oversell(Integer memory_oversell) {
		this.memory_oversell = memory_oversell;
	}
	public Integer getDisk_oversell() {
		return disk_oversell;
	}
	public void setDisk_oversell(Integer disk_oversell) {
		this.disk_oversell = disk_oversell;
	}
	public Integer getCpu_sell() {
		return cpu_sell;
	}
	public void setCpu_sell(Integer cpu_sell) {
		this.cpu_sell = cpu_sell;
	}
	public Integer getMemory_sell() {
		return memory_sell;
	}
	public void setMemory_sell(Integer memory_sell) {
		this.memory_sell = memory_sell;
	}
	public Integer getDisk_sell() {
		return disk_sell;
	}
	public void setDisk_sell(Integer disk_sell) {
		this.disk_sell = disk_sell;
	}
	public HashMap<String, String> getServiceStatusMap() {
		return serviceStatusMap;
	}
	public void setServiceStatusMap(HashMap<String, String> serviceStatusMap) {
		this.serviceStatusMap = serviceStatusMap;
	}
	
	public Integer getHostMem() {
		return hostMem;
	}

	public void setHostMem(Integer hostMem) {
		this.hostMem = hostMem;
	}

	public String getHostIp() {
		return hostIp;
	}
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}
	public Integer getHostCPU() {
		return hostCPU;
	}
	public void setHostCPU(Integer hostCPU) {
		this.hostCPU = hostCPU;
	}
	public Integer getHostDisk() {
		return hostDisk;
	}
	public void setHostDisk(Integer hostDisk) {
		this.hostDisk = hostDisk;
	}
	
}
