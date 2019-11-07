package com.appcloud.vm.fe.manager;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.appcloud.vm.fe.common.Bill_Constants;
import com.appcloud.vm.fe.model.Billingrate;
import com.appcloud.vm.fe.model.BillingrateDAO;
import com.appcloud.vm.fe.util.ConvertBillingrate;

public class BillingrateManager {
	/**
	 * 原vm-billingrate工程的com.appcloud.vm.billingrate.manager.BillingrateManager类
	 * 合并至此 
	 */
	private static BillingrateDAO billingrateDao = new BillingrateDAO();
	private static final Logger logger = Logger.getLogger(BillingrateManager.class);
	
	public static List<Billingrate> findByPtypeAndClusterid(String ptype,int clusterid)
	{
		Map<String,Object> params = new HashMap<String,Object>();
		params.put(Bill_Constants.BILLING_PTYPE, ptype);
		params.put(Bill_Constants.BILLING_CLUSTERID, clusterid);
		return billingrateDao.findByProperty(params, null, null, null, false);
	}
	
	/**
	 * 新增一个billingRate实例
	 * @param rate
	 * @return
	 */
	public static Billingrate addBillingRate( Billingrate rate ){
		try {
			int clusterid = rate.getClusterid();
			String type = rate.getPtype();
			int cpuNum = rate.getCpu();
			int memNum = rate.getMemory();
			int hdNum = rate.getHarddisk();
			int bdNum = rate.getBandwidth();
			if (findBillingRates(type, cpuNum, memNum, hdNum, bdNum, clusterid) != null) {
				logger.info(type+"已有同样配置，添加失败！");
				return null;
			}
			billingrateDao.save(rate);
            return rate;
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            ex.printStackTrace(System.err);
            return null;
        }
	}
	
	/**
	 * 新增多个billingRate实例
	 * @param billingRateList
	 * @return
	 */
	public static List<Billingrate> addBillingRuleList(List<Billingrate> billingRateList) {
		if(billingRateList.size() > 0){
			for(Billingrate bRate: billingRateList){
				try {
					billingrateDao.save(bRate);
		        } catch (Exception ex) {
		            logger.warn(ex.getMessage());
		            ex.printStackTrace(System.err);
		        }
			}
			return billingRateList;
		}
		return null;
	}
	
	/**
	 * 更新计费规则信息
	 * @param rate
	 * @return
	 */
	public static Billingrate updateBillingrate( Billingrate rate ){
		try {
			int clusterid = rate.getClusterid();
			String type = rate.getPtype();
			int cpuNum = rate.getCpu();
			int memNum = rate.getMemory();
			int hdNum = rate.getHarddisk();
			int bdNum = rate.getBandwidth();
			Billingrate anotherRate = findBillingRates(type, cpuNum, memNum, hdNum, bdNum, clusterid);
			if (anotherRate != null && anotherRate.getId() != rate.getId()) {
				logger.info(type+"已有同样配置，更新失败！");
				return null;
			}
			billingrateDao.update(rate);
            return rate;
        } catch (Exception ex) {
        	logger.warn(ex.getMessage());
            ex.printStackTrace(System.err);
            return null;
        }
	}
	
	/**
	 * 更新多个计费规则
	 * @param billingRateList
	 * @return
	 */
	public static List<Billingrate> updateBillingRuleList(List<Billingrate> billingRateList) {
		if(billingRateList.size() > 0){
			for(Billingrate bRate: billingRateList){
				try {
					billingrateDao.update(bRate);
		        } catch (Exception ex) {
		        	logger.warn(ex.getMessage());
		            ex.printStackTrace(System.err);
		        }
			}
			return billingRateList;
		}
		return Collections.emptyList();
	}
	
	/**
	 * 删除计费规则
	 * @param rateId
	 * @return
	 */
	public static boolean delBillingrate ( int rateId ){
		if( rateId >= 0 ){
			billingrateDao.deleteByPrimaryKey(rateId);
			return true;
		}
		return false;
	}
	
	/**
	 * 查询计费规则，可根据计费对象筛选
	 * @param item
	 * @param itemNames
	 * @param itemValues
	 * @param clusterId
	 * @param name
	 * @return
	 */
	public static Billingrate findBillingRates ( String item,int cpuValue,int memoryValue,
			int hardDiskValue,int bandWidthValue, int clusterId){
		Billingrate result=new Billingrate();
		logger.info(item);
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			if(item.equals("cpu")){
				result=billingrateDao.findByProperty3("cpu", cpuValue,"ptype","CPU","clusterid",clusterId).get(0);
			}
			if(item.equals("mem")){
				result=billingrateDao.findByProperty3("memory", memoryValue,"ptype","MEM","clusterid",clusterId).get(0);
			}
			if(item.equals("hd")){
				result=billingrateDao.findByProperty3("harddisk", hardDiskValue,"ptype","HD","clusterid",clusterId).get(0);
			}
			if(item.equals("bw")){
				result=billingrateDao.findByProperty3("bandwidth", bandWidthValue,"ptype","BW","clusterid",clusterId).get(0);
			}
			if(item.equals("charge")){
				result=billingrateDao.findByProperty2("clusterid",clusterId,"ptype","CHARGE").get(0);
			}
			if(item.equals("vmpackage")){
				params.put("cpu", cpuValue);
				params.put("memory", memoryValue);
				params.put("harddisk", hardDiskValue);
				params.put("bandwidth", bandWidthValue);
				params.put("ptype", "VMPACKAGE");
				if(clusterId!=-1)
					params.put("clusterid", clusterId);
				result = billingrateDao.findByProperty(params,null,null,null,false).get(0);
				//result=billingrateDao.findByProperty6("cpu",cpuValue,"memory",memoryValue,"harddisk",hardDiskValue,"bandwidth",bandWidthValue,"ptype","VMPACKAGE","clusterid",clusterId).get(0);
			}if(item.equals("instancetype")){
				params.put("cpu", cpuValue);
				params.put("memory", memoryValue);
				params.put("harddisk", hardDiskValue);
				params.put("bandwidth", bandWidthValue);
				params.put("ptype", "INSTANCETYPE");
				if(clusterId!=-1)
					params.put("clusterid", clusterId);
				result = billingrateDao.findByProperty(params,null,null,null,false).get(0);
				//result=billingrateDao.findByProperty6("cpu",cpuValue,"memory",memoryValue,"harddisk",hardDiskValue,"bandwidth",bandWidthValue,"ptype","INSTANCETYPE","clusterid",clusterId).get(0);
			}
			if(item.equals("flavor")){
				Billingrate cpuRate=billingrateDao.findByProperty3("cpu", cpuValue,"ptype","CPU","clusterid",clusterId).get(0);
				Billingrate memoryRate=billingrateDao.findByProperty3("memory", memoryValue,"ptype","MEM","clusterid",clusterId).get(0);
				Billingrate bandWidthRate=billingrateDao.findByProperty3("bandwidth", bandWidthValue,"ptype","BW","clusterid",clusterId).get(0);
				result.setClusterid(cpuRate.getClusterid());
				result.setCpu(cpuRate.getCpu());
				result.setMemory(memoryRate.getMemory());
				result.setBandwidth(bandWidthRate.getBandwidth());
				result.setHourprice(cpuRate.getHourprice() + memoryRate.getHourprice() + bandWidthRate.getHourprice());
				result.setDayprice(cpuRate.getDayprice() + memoryRate.getDayprice() + bandWidthRate.getDayprice());
				result.setMonthprice(cpuRate.getMonthprice() + memoryRate.getMonthprice() + bandWidthRate.getMonthprice());
				result.setYearprice(cpuRate.getYearprice() + memoryRate.getYearprice() + bandWidthRate.getYearprice());
			}
			if(item.equals("vm")){
				Billingrate cpuRate=billingrateDao.findByProperty3("cpu", cpuValue,"ptype","CPU","clusterid",clusterId).get(0);
				Billingrate memoryRate=billingrateDao.findByProperty3("memory", memoryValue,"ptype","MEM","clusterid",clusterId).get(0);
				Billingrate hardDiskRate=billingrateDao.findByProperty3("harddisk", hardDiskValue,"ptype","HD","clusterid",clusterId).get(0);
				Billingrate bandWidthRate=billingrateDao.findByProperty3("bandwidth", bandWidthValue,"ptype","BW","clusterid",clusterId).get(0);
				result.setClusterid(cpuRate.getClusterid());
				result.setCpu(cpuRate.getCpu());
				result.setMemory(memoryRate.getMemory());
				result.setHarddisk(hardDiskRate.getHarddisk());
				result.setBandwidth(bandWidthRate.getBandwidth());
				result.setHourprice(cpuRate.getHourprice()+memoryRate.getHourprice()+hardDiskRate.getHourprice()+bandWidthRate.getHourprice());
				result.setMonthprice(cpuRate.getMonthprice()+memoryRate.getMonthprice()+hardDiskRate.getMonthprice()+bandWidthRate.getMonthprice());
				result.setDayprice(cpuRate.getDayprice()+memoryRate.getDayprice()+hardDiskRate.getDayprice()+bandWidthRate.getDayprice());
				result.setYearprice(cpuRate.getYearprice()+memoryRate.getYearprice()+hardDiskRate.getYearprice()+bandWidthRate.getYearprice());
			}
			return result;
		} catch (Exception ex) {
			// TODO: handle exception
			logger.warn(ex.getMessage());
            //ex.printStackTrace(System.err);
			return null;
		}
	}
	
	/**
	 * 按集群查找
	 * @param clusterId
	 * @return
	 */
	public static List<Billingrate> findBillingRateList(int clusterId) {
		if(clusterId == -1){
			return billingrateDao.findAll();
		}
		return billingrateDao.findByProperty("clusterid",clusterId);
	}

	/**
	 * 按名称和pType查找billgrate
	 * @param name pType
	 * @return
	 */
	public static List<Billingrate> findBillingRateByNameAndPType(String name, String pType) {
		if(name == null || name.equalsIgnoreCase("")){
			return null;
		}
		return billingrateDao.findByProperty2("name", name, "ptype", pType);
	}
	
	/**
	 * 费用计算
	 * @param item
	 * @param itemNames
	 * @param itemValues
	 * @param clusterId
	 * @param name
	 * @param payment
	 * @param count
	 * @return
	 */
	public static int getAmount(String item,int cpuValue,int memoryValue,int hardDiskValue,int bandWidthValue,int clusterId,int payment,double count){
		int price=0;
		Billingrate result=findBillingRates(item,cpuValue,memoryValue,hardDiskValue,bandWidthValue,clusterId);
		switch(payment){
		case 1:
			price = ConvertBillingrate.round2Int(result.getYearprice()*count);
			break;
		case 2:
			price = ConvertBillingrate.round2Int(result.getMonthprice()*count);
			break;
		case 3:
			price = ConvertBillingrate.round2Int(result.getDayprice()*count);
			break;
		case 4:
			price = ConvertBillingrate.round2Int(result.getHourprice()*count);
			break;
		default:
			return -1;
		}
		return price;
	}
	
}
