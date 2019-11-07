package appcloud.admin.action.network;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcAggregate;
import appcloud.api.beans.AddressPool;
import appcloud.api.beans.AddressPool.IPUsage;
import appcloud.api.beans.AvailabilityZone;
import appcloud.api.client.AcAggregateClient;
import appcloud.api.client.AddressPoolClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.fe.billing.Billingrate;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.util.ClientFactory;

public class NetListAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(NetListAction.class);
	private AddressPoolClient addressPoolClient = ClientFactory.getAddressPoolClient();
	private AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();
	
	private List<AddressPool> addressPools = new ArrayList<AddressPool>();
	private HashMap<String, String> addrTypeMap = new HashMap<String, String>();
	private HashMap<Integer, AcAggregate> aggregateMap = new HashMap<Integer, AcAggregate>();
	private HashMap<Integer, List<AddressPool>> addrPoolByCluster = new HashMap<Integer, List<AddressPool>>();
	private HashMap<Integer, HashMap<String, Integer>> ipNumMap = new HashMap<Integer, HashMap<String,Integer>>();
	private int zid;
	private String zname = "";
	
	/*
	 * 用于统计总IP数量和总IP剩余量
	 */
	private Integer allTotalPublicNum = 0;
	private Integer allPublicNum = 0;
	private Integer allTotalPrivateNum = 0;
	private Integer allPrivateNum = 0;
	
	public String execute() {
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
		    List<AddressPool> list = new ArrayList<AddressPool>();
		    for (AddressPool addressPool : addressPools) {
		    	if (addressPool.aggregateId == clusterid) {
		    		list.add(addressPool);
		    	}
		    }
		    addrPoolByCluster.put(clusterid, list);
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
		return SUCCESS;
	}

	public List<Integer> countAllIpNum(){
		zid = 1;
		execute();
		List<Integer> list = new ArrayList<Integer>();
		list.add(allTotalPublicNum);
		list.add(allPublicNum);
		list.add(allTotalPrivateNum);
		list.add(allPrivateNum);
		return list;
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

}
