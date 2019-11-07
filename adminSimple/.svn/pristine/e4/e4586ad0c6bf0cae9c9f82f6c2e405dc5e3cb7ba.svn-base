package appcloud.admin.action.system;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.opensymphony.xwork2.Action;
import javassist.bytecode.Descriptor.Iterator;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcAggregate;
import appcloud.api.beans.AcHost;
import appcloud.api.beans.AcService;
import appcloud.api.beans.AvailabilityZone;
import appcloud.api.beans.IP;
import appcloud.api.beans.Network;
import appcloud.api.beans.Resource;
import appcloud.api.beans.Server;
import appcloud.api.client.AcAggregateClient;
import appcloud.api.client.AcHostClient;
import appcloud.api.client.AcServiceClient;
import appcloud.api.client.AvailabilityZoneClient;
import appcloud.api.client.ServerClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.fe.users.UsersAPI;
import com.appcloud.vm.fe.util.ClientFactory;



public class SearchHostAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String tenantId = "admin";
	private static Logger logger = Logger.getLogger(SearchHostAction.class);
	private AcServiceClient serviceClient = ClientFactory.getServiceClient();
	private AcHostClient hostClient = ClientFactory.getHostClient();
	private AcAggregateClient zoneClient = ClientFactory.getAggregateClient();
	
	
	private int page = 1;
    private final static int PAGESIZE = 10;
    private long total = 0;
    private int lastpage = 1;

    private String uid = null;
    private String ip = null; 
    private String type = null; 
    private String status = null; 
	private String zoneId = null;
	private String aggregateId = null;
	private String service = null; 
	private String cpuCount = null;
	private String memoryCount = null;
	private String diskCount = null;
	int cpuOperator = 0;
	int memoryOperator = 0;
	int diskOperator = 0;
    
	private List<AcHost> hosts = new ArrayList<AcHost>();
	private HashMap<String, Resource> hostIdResourceMap = 
			new HashMap<String, Resource>();		//host的资源配置详情
	private HashMap<String, List<AcService>> hostIdServiceMap = 
			new HashMap<String, List<AcService>>();	//host上开启的service服务列表
	
	private StringBuffer searchContion = new StringBuffer("");

	private Integer id = null;
	private String name = null;

	
	public String execute() {
		// 准备条件
		Integer cpuCountInt = null;
		Integer memoryCountInt = null;
		Integer diskCountInt = null;
		Integer zid = null;
		Integer aid = null;


		
		try {

			if(cpuCount != null && !cpuCount.equals("")) {
				cpuCountInt = Integer.valueOf(cpuCount);
				searchContion.append("CPU核数："+cpuCount+", ");
			}
			if(memoryCount != null && !memoryCount.equals("")) {
				memoryCountInt = Integer.valueOf(memoryCount);
				searchContion.append("内存大小:"+memoryCount+", ");
			}
			if(diskCount != null && !diskCount.equals("")) {
				diskCountInt = Integer.valueOf(diskCount)*1024;
				searchContion.append("硬盘大小:"+diskCount+",");
			}
			if(zoneId != null && !zoneId.equals("")) {
				zid = Integer.valueOf(zoneId);
				searchContion.append("数据中心:"+zoneClient.getZoneById(zid).name+", ");
			}
			if(aggregateId != null && !aggregateId.equals("")) {
				aid = Integer.valueOf(aggregateId);
				searchContion.append("集群:"+zoneClient.get(aid).name+", ");
			}
		} catch(Exception e) {
			logger.error("把String参数转换为Integer时出错", e);
		}
		search(searchContion, ip, "主机IP");
		search(searchContion, type, "主机类型");
		search(searchContion, status, "主机状态");
		search(searchContion, service,"运行服务");
		total = Long.valueOf(hostClient.countByProperties
				(tenantId, uid, ip, type, status, zid, aid,
				 service, Integer.valueOf(cpuOperator), cpuCountInt,
				 Integer.valueOf(memoryOperator), memoryCountInt,
				 Integer.valueOf(diskOperator), diskCountInt));
		//返回
		if(total % PAGESIZE == 0){
    		lastpage = (int)(total / PAGESIZE);
    	}else{
    		lastpage = (int)(total / PAGESIZE) + 1;
    	}

		hosts = hostClient.getHostsByProperties(
				 tenantId, uid, ip, type, status, zid, aid, service,
				 Integer.valueOf(cpuOperator), cpuCountInt,
				 Integer.valueOf(memoryOperator), memoryCountInt,
				 Integer.valueOf(diskOperator), diskCountInt,
				 Integer.valueOf(page), Integer.valueOf(PAGESIZE));
		if(hosts == null || hosts.size() == 0){
			return SUCCESS;
		}

		for (AcHost host : hosts) {
			for (Resource resource : host.resources) {
				if (resource.project.equals("(total)")) {
					hostIdResourceMap.put(host.id, resource);
					break;
				}
			}
		}
		logger.info("查找host列表成功");

		for(AcHost host : hosts) {
			Resource r = hostIdResourceMap.get(host.id);
		}

		//查找平台上开启的所有service
		List<AcService> services = null;


		for(AcHost host : hosts) {
			services = serviceClient.getHostService(host.id);
			if(services != null) {
				hostIdServiceMap.put(host.id, services);
			}
		}


		Set<String> keys = hostIdServiceMap.keySet();
		logger.info("查找平台所有service成功");
		if (searchContion.length() == 0) {
			searchContion.append("查询全部");
		}
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "节点查询", "查询条件:"+searchContion, "SearchHostAction.class",
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		return SUCCESS;
	}
//	public String searchAll(){
//	VmSummaryProxy vm=ConnectionFactory.getVmSummaryProxy();
//
//		return Action.SUCCESS;
//	}

	private void search(StringBuffer sb, String name, String des) {
		if (name != null && !name.equals("")) {
			sb.append(des+":"+name+", ");
		}
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getLastpage() {
		return lastpage;
	}

	public void setLastpage(int lastpage) {
		this.lastpage = lastpage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

/*	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}*/

	/*public ServerClient getServerClient() {
		return serverClient;
	}

	public void setServerClient(ServerClient serverClient) {
		this.serverClient = serverClient;
	}*/

	public AcServiceClient getServiceClient() {
		return serviceClient;
	}

	public void setServiceClient(AcServiceClient serviceClient) {
		this.serviceClient = serviceClient;
	}

	public AcHostClient getHostClient() {
		return hostClient;
	}

	public void setHostClient(AcHostClient hostClient) {
		this.hostClient = hostClient;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAggregateId() {
		return aggregateId;
	}

	public void setAggregateId(String aggregateId) {
		this.aggregateId = aggregateId;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getCpuCount() {
		return cpuCount;
	}

	public void setCpuCount(String cpuCount) {
		this.cpuCount = cpuCount;
	}

	public String getMemoryCount() {
		return memoryCount;
	}

	public void setMemoryCount(String memoryCount) {
		this.memoryCount = memoryCount;
	}

	public String getDiskCount() {
		return diskCount;
	}

	public void setDiskCount(String diskCount) {
		this.diskCount = diskCount;
	}

	public int getCpuOperator() {
		return cpuOperator;
	}

	public void setCpuOperator(int cpuOperator) {
		this.cpuOperator = cpuOperator;
	}

	public int getMemoryOperator() {
		return memoryOperator;
	}

	public void setMemoryOperator(int memoryOperator) {
		this.memoryOperator = memoryOperator;
	}

	public int getDiskOperator() {
		return diskOperator;
	}

	public void setDiskOperator(int diskOperator) {
		this.diskOperator = diskOperator;
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


	public static String getTenantid() {
		return tenantId;
	}

	public static int getPagesize() {
		return PAGESIZE;
	}
}
