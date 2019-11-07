package com.appcloud.vm.action.hd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.api.beans.Volume;
import appcloud.api.client.ServerClient;
import appcloud.api.client.VolumeClient;
import appcloud.api.enums.AcVolumeTypeEnum;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.util.ClientFactory;

public class HdListByUidAction extends BaseAction{
	/**
	 * 获取当前时刻该用户的所有硬盘名称和状态，返回作为比较硬盘列表是否需要刷新的依据
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(HdListByUidAction.class);
	private VolumeClient volumeClient = ClientFactory.getVolumeClient();
	/*private AcAggregateClient zoneClient = ClientFactory.getAggregateClient();*/
	private ServerClient serverClient = ClientFactory.getServerClient();
	
	private List<Volume> volumes = new ArrayList<Volume>();
	/*private HashMap<String, String> volumeIdServerNameMap = new HashMap<String, String>();
	private HashMap<String, String> zoneIdNameMap = new HashMap<String, String>();
	private HashMap<String, VmHdEndtime> endtimeMap = new HashMap<String, VmHdEndtime>();
	
	private HashMap<String, List<String>> pricesMap = new HashMap<String, List<String>>();
	private HashMap<String, HashMap<String, List<String>>> pricesMapByClusterid = new HashMap<String, HashMap<String, List<String>>>();
	private List<String> clusterIds = new ArrayList<String>();*/
	
	private String newvalue = "";
	
	public String execute() {
		String userId = this.getSessionUserID().toString();
		volumes = volumeClient.getByAcVolumeType(userId, AcVolumeTypeEnum.NETWORK);
		logger.info("读取用户硬盘列表成功");
		
		HashMap<String, String> serverIdNameMap = new HashMap<String, String>();
		serverIdNameMap.put(null, "无");
		for (Volume volume : volumes) {
			//查询硬盘所属的数据中心名称,id与name的键值对
			/*String zoneId = volume.availabilityZone;
			if (!zoneIdNameMap.containsKey(zoneId)) {
				String zoneName = zoneClient.getZoneById(Integer.valueOf(zoneId)).name;
				zoneIdNameMap.put(zoneId, zoneName);
			}
			//查询硬盘所属集群
			String clusterId = volume.metadata.get(VolumeMetadata.AGGREGATE_ID);
			clusterIds.add(clusterId);*/
			//查询硬盘挂载的虚拟机,硬盘id与虚拟机名称的键值对
			String device = volume.attachments.device;
			String serverName = "无";
			if (!device.contains("null")) {	//硬盘现在是挂载状态
				String serverId = volume.attachments.serverId;
				serverName = serverIdNameMap.get(serverId); 
				if (serverName == null) {
					serverName = serverClient.get(userId, serverId).name;
					serverIdNameMap.put(serverId, serverName);
				}
			}
			/*volumeIdServerNameMap.put(volume.id, serverName);*/
			newvalue += volume.displayName+serverName;
		}
		logger.info("读取硬盘所属虚拟机名称成功"+newvalue);
		
		//读取硬盘截止时间
		/*List<VmHdEndtime> endtimes = new VmHdEndtimeManager().getHdEndtimeByUserId(this.getSessionUserID());
		for (VmHdEndtime endtime : endtimes) {
			endtimeMap.put(endtime.getUuid(), endtime);
		}*/

		//读取硬盘的价格,先按集群划分，再按硬盘大小划分
		/*@SuppressWarnings("rawtypes")
		Iterator iter = zoneIdNameMap.entrySet().iterator(); 
		while (iter.hasNext()) { 
		    @SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) iter.next(); 
		    String key = (String) entry.getKey();
		    int clusterid = Integer.parseInt(key);
		    List<Billingrate> all = BillingAPI.getRate(Constants.BILLING_ALL, clusterid, -1, -1, -1, -1);
		    for (Billingrate rate : all) {
		    	List<String> prices = new ArrayList<String>();
		    	double count;
		    	BigDecimal yearDiscount;
				BigDecimal monthDiscount;
				if (rate.getDayPrice() != null && !rate.getDayPrice().equals("0")
						&& rate.getYearPrice() != null && !rate.getYearPrice().equals("0")) {
		    		count = (Double.parseDouble(rate.getYearPrice()) / (Double.parseDouble(rate.getDayPrice())*Constants.yearDays10));
		    		yearDiscount = new BigDecimal(String.valueOf(count));
		    		yearDiscount = yearDiscount.setScale(1,BigDecimal.ROUND_HALF_UP);
		    	}else{
		    		yearDiscount = new BigDecimal("0");
		    	}
				if (rate.getDayPrice() != null && !rate.getDayPrice().equals("0")
						&& rate.getMonthPrice() != null && !rate.getMonthPrice().equals("0")) {
		    		count = (Double.parseDouble(rate.getMonthPrice()) /(Double.parseDouble(rate.getDayPrice())*Constants.monthDays10));
		    		monthDiscount = new BigDecimal(String.valueOf(count));
		    		monthDiscount = monthDiscount.setScale(1,BigDecimal.ROUND_HALF_UP);
		    	}else{
		    		monthDiscount = new BigDecimal("0");
		    	}
				if (rate.getHourPrice() != null){
					prices.add(rate.getHourPrice());
				}else{
					prices.add("");
				}
				if (rate.getDayPrice() != null){
					prices.add(rate.getDayPrice());
				}else{
					prices.add("");
				}
				if (rate.getMonthPrice() != null){
					prices.add(rate.getMonthPrice());
				}else{
					prices.add("");
				}
				if (rate.getYearPrice() != null){
					prices.add(rate.getYearPrice());
				}else{
					prices.add("");
				}
		    	prices.add(yearDiscount.toString());
		    	prices.add(monthDiscount.toString());
		    	if (rate.getHarddisk() != null){
		    		pricesMap.put(rate.getHarddisk().toString(), prices);
		    	}
		    }
		    pricesMapByClusterid.put(key, pricesMap);
		} 
		if (pricesMapByClusterid != null) {
			logger.info("显示成功");
		}else{
			logger.info("显示失败");
		}*/
		return SUCCESS;
	}

/*	public List<String> getClusterIds() {
		return clusterIds;
	}

	public void setClusterIds(List<String> clusterIds) {
		this.clusterIds = clusterIds;
	}

	public List<Volume> getVolumes() {
		return volumes;
	}

    public HashMap<String, String> getVolumeIdServerNameMap() {
		return volumeIdServerNameMap;
	}

	public HashMap<String, String> getZoneIdNameMap() {
		return zoneIdNameMap;
	}

	public HashMap<String, VmHdEndtime> getEndtimeMap() {
		return endtimeMap;
	}

	public HashMap<String, List<String>> getPricesMap() {
		return pricesMap;
	}

	public void setPricesMap(HashMap<String, List<String>> pricesMap) {
		this.pricesMap = pricesMap;
	}

	public HashMap<String, HashMap<String, List<String>>> getPricesMapByClusterid() {
		return pricesMapByClusterid;
	}

	public void setPricesMapByClusterid(
			HashMap<String, HashMap<String, List<String>>> pricesMapByClusterid) {
		this.pricesMapByClusterid = pricesMapByClusterid;
	}*/

	public String getNewvalue() {
		return newvalue;
	}

	public void setNewvalue(String newvalue) {
		this.newvalue = newvalue;
	}
}
