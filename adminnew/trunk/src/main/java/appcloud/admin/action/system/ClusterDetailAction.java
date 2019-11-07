package appcloud.admin.action.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;

import appcloud.common.model.Host;
import appcloud.common.proxy.HostProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcAggregate;
import appcloud.api.beans.AcGroup;
import appcloud.api.beans.AcHost;
import appcloud.api.beans.AcResourceStrategy;
import appcloud.api.beans.AddressPool;
import appcloud.api.beans.AvailabilityZone;
import appcloud.api.beans.Flavor;
import appcloud.api.beans.Resource;
import appcloud.api.beans.Server;
import appcloud.api.client.AcAggregateClient;
import appcloud.api.client.AcGroupClient;
import appcloud.api.client.AcResourceStrategyClient;
import appcloud.api.client.AddressPoolClient;
import appcloud.api.client.FlavorClient;
import appcloud.api.client.ServerClient;

import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.util.ClientFactory;

public class ClusterDetailAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3273282514712524331L;
	private static final Logger logger = Logger.getLogger(ClusterDetailAction.class);
	private AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();
	private AcResourceStrategyClient acResourceStrategyClient = ClientFactory.getAcResourceStrategyClient();
	private AcGroupClient acGroupClient = ClientFactory.getAcGroupClient();
	private AddressPoolClient addressPoolClient = ClientFactory.getAddressPoolClient();
	private FlavorClient flavorClient = ClientFactory.getFlavorClient();
	private ServerClient serverClient = ClientFactory.getServerClient();
	private List<AddressPool> addressPools = new ArrayList<AddressPool>();
	private HashMap<String, String> addrTypeMap = new HashMap<String, String>();
	private HashMap<Integer, AcAggregate> aggregateMap = new HashMap<Integer, AcAggregate>();
	private HashMap<Integer, List<AddressPool>> addrPoolByCluster = new HashMap<Integer, List<AddressPool>>();
	private HashMap<Integer, HashMap<String, Integer>> ipNumMap = new HashMap<Integer, HashMap<String,Integer>>();
	private HashMap<AcGroup, List<AcAggregate>> groupClusterMap = new HashMap<AcGroup, List<AcAggregate>>();
	private int zid = 1;
	private String zname = "";
	
	/*
	 * 用于统计总IP数量和总IP剩余量
	 */
	private Integer allTotalPublicNum = 0;
	private Integer allPublicNum = 0;
	private Integer allTotalPrivateNum = 0;
	private Integer allPrivateNum = 0;
	/*
	 * 用于统计超售比例
	 */
	private Integer cpu_sell = 0;
	private Integer memory_sell = 0;
	private Integer disk_sell = 0;
	private Integer cpu_conf = 0;
	private Integer memory_conf = 0;
	private Integer disk_conf = 0;
	private Integer cpu_all = 0;
	private Integer memory_all = 0;
	private Integer disk_all = 0;
	private String cpu_sell_rate;
	private String memory_sell_rate;
	private String disk_sell_rate;
	
	private Integer id;
	private AcAggregate aggregate;
	private List<AcResourceStrategy> curRSList = new ArrayList<AcResourceStrategy>();
	private List<AcHost> hostList = new ArrayList<AcHost>();
	private List<Host> hosts=new ArrayList<>();
	private HostProxy hostProxy= ConnectionFactory.getHostProxy();
	@Override
	public String execute() throws Exception {
		/**
		 * 获取资源调度策略
		 */
		aggregate = aggregateClient.get(id);
		curRSList = acResourceStrategyClient.getByUuid(aggregate.resrcStrategyUuid);
		logger.info("获取集群资源调度策略,id:" + id);
		hostList = aggregate.acHosts;
		logger.info("hostList.size() = " + hostList.size());
		/**
		 * 统计超售比例
		 */
		cpu_all = 0;
		memory_all = 0;
		disk_all = 0;
		cpu_sell = 0;
		memory_sell = 0;
		disk_sell = 0;
		for(AcHost acHost : hostList) { 
			/*总量			
			已用			
			销售比例(%)			
			超售比例(%)*/
			Integer host_cpu_sell = 0;
			Integer host_memory_sell = 0;
			Integer host_disk_sell = 0;
			Integer hostMem = 0;
			Integer hostCPU = 0;
			Integer hostDisk = 0;
			List<Server> servers = serverClient.searchByProperties("admin", "", "", "", "",
					1, null, acHost.id, "", null, null, null, null);
			for(Server server : servers){
				Flavor flavor = flavorClient.get(Constants.ADMIN_TENANT_ID, server.flavor.id);
				host_cpu_sell += flavor.vcpus;
				host_memory_sell += flavor.ram * 1024;
				host_disk_sell += flavor.disk;
			}
			for (Resource resource : acHost.resources) {
				if (resource.project.equals("(total)")) {
					hostMem = resource.memoryMb;
					hostCPU = resource.cpu;
					hostDisk = resource.diskGb;
					break;
				}
			}
			cpu_sell += host_cpu_sell;
			memory_sell += host_memory_sell;
			disk_sell += host_disk_sell;
			//统计硬件配置
			cpu_conf += hostCPU ;
			memory_conf += hostMem - 4 * 1024;
			disk_conf += hostDisk;
			//统计总量
			cpu_all += hostCPU * aggregate.cpu_oversell / 100;
			memory_all += (hostMem - 4 * 1024) * aggregate.memory_oversell / 100 ;
			disk_all += hostDisk * aggregate.disk_oversell / 100;

		}
		cpu_sell_rate = new Formatter().format("%.2f", (double)cpu_sell * 100 / cpu_conf).toString();
		memory_sell_rate = new Formatter().format("%.2f", (double)memory_sell * 100 / memory_conf).toString();
		disk_sell_rate = new Formatter().format("%.2f", (double)disk_sell * 100 / disk_conf).toString();
		/**
		 * 获取网络资源
		 */
		addrTypeMap.put("public", "外网");
		addrTypeMap.put("private", "内网");
		//此处返回的ip池集合，已用ip池列表List<IPUsage> ips没能返回(为空)，api此部分待修改
		addressPools = addressPoolClient.getAddressPoolsOfZone(
				Constants.ADMIN_TENANT_ID, zid);
		logger.info("获取所有网络地址段成功");
		ComparatoClusterName comparatorCluster = new ComparatoClusterName();
		Collections.sort(addressPools, comparatorCluster);
		
		//提取集群id-集群name
		for (AddressPool addressPool : addressPools) {
			Integer aggregateId = addressPool.aggregateId;
			if (!aggregateMap.containsKey(aggregateId)) {
				aggregateMap.put(aggregateId, aggregateClient.get(aggregateId));
			}
		}
		
		//按集群id分类ip池
		Iterator iter = aggregateMap.entrySet().iterator(); 
		while (iter.hasNext()) { 
			Map.Entry entry = (Map.Entry) iter.next(); 
		    Integer clusterid = (Integer) entry.getKey();
			if (clusterid.equals(id)) {
				List<AddressPool> list = new ArrayList<AddressPool>();
				for (AddressPool addressPool : addressPools) {
					if (addressPool.aggregateId == clusterid) {
						list.add(addressPool);
					}
				}
				addrPoolByCluster.put(clusterid, list);
				break;
			}
		}
		
		//按集群id计算ip剩余量
		Iterator iter2 = addrPoolByCluster.entrySet().iterator(); 
		while (iter2.hasNext()) { 
			Map.Entry entry = (Map.Entry) iter2.next(); 
		    Integer clusterid = (Integer) entry.getKey();
		    HashMap<String, Integer> ipMap = new HashMap<String, Integer>();
		    Integer totalPublicNum = 0, publicNum = 0, totalPrivateNum = 0, privateNum = 0;
		    for (AddressPool addr : (List<AddressPool>)entry.getValue()) {
		    	//再次调用api的原因是，在通过数据中心和集群查询的ip池，api并没有将已用ip列表返回，api中只有通过ip池id才能得到已用ip列表
		    	addr = addressPoolClient.get(Constants.ADMIN_TENANT_ID, addr.id);
		    	if ("public".equals(addr.type)) {
		    		totalPublicNum += countTotalIpNum(addr);
		    		publicNum += countIpNum(addr);
		    	} else {
		    		privateNum += countIpNum(addr);
		    		totalPrivateNum += countTotalIpNum(addr);
		    	}
		    }
		    ipMap.put("totalPublic", totalPublicNum);
		    ipMap.put("public", publicNum);
		    ipMap.put("totalPrivate", totalPrivateNum);
		    ipMap.put("private", privateNum);
		    ipNumMap.put(clusterid, ipMap);
		    
		    allTotalPublicNum += totalPublicNum;
		    allPublicNum += publicNum;
		    allTotalPrivateNum += totalPrivateNum;
		    allPrivateNum += privateNum;
		}
		
		
		AvailabilityZone zone = aggregateClient.getZoneById(zid);
		zname = zone.name;
		/**
		 * 获取所在群组详情
		 */
		List<AcGroup> acGroups = acGroupClient.getByClusterId(id);
		for(AcGroup group : acGroups) {
			List<AcAggregate> list = new ArrayList<AcAggregate>();
			for(Integer tmp : group.availableClusters) {
				 list.add(aggregateClient.get(tmp));
			}
			groupClusterMap.put(group, list);
		}
		QueryObject<Host> queryObject=new QueryObject<>();
		FilterBean<Host> filter2 = new FilterBean<Host>("clusterId",id, FilterBean.FilterBeanType.EQUAL);
		queryObject.addFilterBean(filter2);
		hosts=(List<Host>)hostProxy.searchAll(queryObject,false,false,false);
		return SUCCESS;
	}
	
	private int countTotalIpNum(AddressPool arg) {
		String ipStart = arg.ipStart;
		String ipEnd = arg.ipEnd;
		Integer totalCount = Integer.parseInt(ipEnd.substring(ipEnd.lastIndexOf('.')+1)) - Integer.parseInt(ipStart.substring(ipStart.lastIndexOf('.')+1)) + 1;
		return totalCount;
	}
	
	private int countIpNum(AddressPool arg) {
		return (countTotalIpNum(arg) - arg.ips.size());
	}
	
	private class ComparatoClusterName implements Comparator{
		public int compare(Object arg0, Object arg1) {
			AddressPool addrPool1 = (AddressPool)arg0;
			AddressPool addrPool2 = (AddressPool)arg1;
			int nameFlag = aggregateClient.get(addrPool1.aggregateId).name.compareTo(aggregateClient.get(addrPool2.aggregateId).name);
			if (nameFlag == 0) {
				return addrPool2.type.compareTo(addrPool1.type);
			}
			return nameFlag;
		}
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AcAggregate getAggregate() {
		return aggregate;
	}

	public void setAggregate(AcAggregate aggregate) {
		this.aggregate = aggregate;
	}

	public List<AcResourceStrategy> getCurRSList() {
		return curRSList;
	}

	public void setCurRSList(List<AcResourceStrategy> curRSList) {
		this.curRSList = curRSList;
	}
	public HashMap<Integer, HashMap<String, Integer>> getIpNumMap() {
		return ipNumMap;
	}

	public void setIpNumMap(HashMap<Integer, HashMap<String, Integer>> ipNumMap) {
		this.ipNumMap = ipNumMap;
	}

	public HashMap<Integer, List<AddressPool>> getAddrPoolByCluster() {
		return addrPoolByCluster;
	}

	public void setAddrPoolByCluster(
			HashMap<Integer, List<AddressPool>> addrPoolByCluster) {
		this.addrPoolByCluster = addrPoolByCluster;
	}

	public List<AddressPool> getAddressPools() {
		return addressPools;
	}

	public HashMap<String, String> getAddrTypeMap() {
		return addrTypeMap;
	}

	public HashMap<Integer, AcAggregate> getAggregateMap() {
		return aggregateMap;
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

	public List<AcHost> getHostList() {
		return hostList;
	}

	public void setHostList(List<AcHost> hostList) {
		this.hostList = hostList;
	}

	public HashMap<AcGroup, List<AcAggregate>> getGroupClusterMap() {
		return groupClusterMap;
	}

	public void setGroupClusterMap(
			HashMap<AcGroup, List<AcAggregate>> groupClusterMap) {
		this.groupClusterMap = groupClusterMap;
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

	public Integer getCpu_all() {
		return cpu_all;
	}

	public void setCpu_all(Integer cpu_all) {
		this.cpu_all = cpu_all;
	}

	public Integer getMemory_all() {
		return memory_all;
	}

	public void setMemory_all(Integer memory_all) {
		this.memory_all = memory_all;
	}

	public Integer getDisk_all() {
		return disk_all;
	}

	public void setDisk_all(Integer disk_all) {
		this.disk_all = disk_all;
	}

	public String getCpu_sell_rate() {
		return cpu_sell_rate;
	}

	public void setCpu_sell_rate(String cpu_sell_rate) {
		this.cpu_sell_rate = cpu_sell_rate;
	}

	public String getMemory_sell_rate() {
		return memory_sell_rate;
	}

	public void setMemory_sell_rate(String memory_sell_rate) {
		this.memory_sell_rate = memory_sell_rate;
	}

	public String getDisk_sell_rate() {
		return disk_sell_rate;
	}

	public void setDisk_sell_rate(String disk_sell_rate) {
		this.disk_sell_rate = disk_sell_rate;
	}

	public Integer getCpu_conf() {
		return cpu_conf;
	}

	public void setCpu_conf(Integer cpu_conf) {
		this.cpu_conf = cpu_conf;
	}

	public Integer getMemory_conf() {
		return memory_conf;
	}

	public void setMemory_conf(Integer memory_conf) {
		this.memory_conf = memory_conf;
	}

	public Integer getDisk_conf() {
		return disk_conf;
	}

	public void setDisk_conf(Integer disk_conf) {
		this.disk_conf = disk_conf;
	}

	public List<Host> getHosts() {
		return hosts;
	}

	public void setHosts(List<Host> hosts) {
		this.hosts = hosts;
	}
}
