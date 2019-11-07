package com.appcloud.vm.action.vm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.billing.BillingAPI;
import com.appcloud.vm.fe.billing.Billingrate;
import com.appcloud.vm.fe.common.Constants;

public class VmPriceAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	/**
	 * 申请虚拟机准备
	 */
	private Logger logger = Logger.getLogger(VmPriceAction.class);

	private int clusterid = 0;
	private List<Billingrate> bandwidthPrice = new ArrayList<Billingrate>();
	private List<Billingrate> cpuPrice = new ArrayList<Billingrate>();
	private List<Billingrate> harddiskPrice = new ArrayList<Billingrate>();
	private List<Billingrate> memoryPrice = new ArrayList<Billingrate>();
	private List<Billingrate> packagePrice = new ArrayList<Billingrate>();
	private HashMap<Integer, String> yearDiscounts = new HashMap<Integer, String>();
	private HashMap<Integer, String> monthDiscounts = new HashMap<Integer, String>();
	
	@SuppressWarnings("unchecked")
	public String execute() {
		//前端：查询所有套餐信息，以及自定义的各种硬件配置信息
		
		List<Billingrate> all = BillingAPI.getRate(Constants.BILLING_ALL, clusterid, -1, -1, -1, -1);
		
		if (all != null) {
			logger.info("获取计费规则成功");
		} else {
			logger.info("获取计费规则失败");
		}
		
		for (int i = 0; i < all.size(); i++) {
			Billingrate rate = new Billingrate();
			rate = all.get(i);
			if (rate.getPtype().equals(Constants.BILLING_CPU_ABBR)) {
				cpuPrice.add(rate);
			} else if (rate.getPtype().equals(Constants.BILLING_BW_ABBR)) {
				bandwidthPrice.add(rate);
			} else if (rate.getPtype().equals(Constants.BILLING_MEM_ABBR)) {
				memoryPrice.add(rate);
			} else if (rate.getPtype().equals(Constants.BILLING_HD_ABBR)) {
				harddiskPrice.add(rate);
			} else if (rate.getPtype().equals(Constants.BILLING_VMPACKAGE)){
				packagePrice.add(rate);
			}
		}
		
		ComparatorPackage comparatorPackage = new ComparatorPackage();
		Collections.sort(packagePrice, comparatorPackage);
		ComparatorCpu comparatorCpu = new ComparatorCpu();
		Collections.sort(cpuPrice, comparatorCpu);
		ComparatorMemory comparatorMemory = new ComparatorMemory();
		Collections.sort(memoryPrice, comparatorMemory);
		ComparatorHarddisk comparatorHarddisk = new ComparatorHarddisk();
		Collections.sort(harddiskPrice, comparatorHarddisk);
		ComparatorBandwidth comparatorBandwidth = new ComparatorBandwidth();
		Collections.sort(bandwidthPrice, comparatorBandwidth);

		//计算套餐的折扣
		for(Billingrate p : packagePrice){
			if(!yearDiscounts.containsKey(p.getId())){
				double count = Double.parseDouble(p.getYearPrice()) / (Double.parseDouble(p.getDayPrice())*Constants.yearDays10);
				BigDecimal bd = new BigDecimal(String.valueOf(count));
				bd = bd.setScale(1,BigDecimal.ROUND_HALF_UP);
				String discount = bd.toString();
				yearDiscounts.put(p.getId(), discount);
			}
			if(!monthDiscounts.containsKey(p.getId())){
				double count = Double.parseDouble(p.getMonthPrice()) / (Double.parseDouble(p.getDayPrice())*Constants.monthDays10);
				BigDecimal bd = new BigDecimal(String.valueOf(count));
				bd = bd.setScale(1,BigDecimal.ROUND_HALF_UP);
				String discount = bd.toString();
				monthDiscounts.put(p.getId(), discount);
			}
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public class ComparatorPackage implements Comparator{
		public int compare(Object arg0, Object arg1) {
			Billingrate price0=(Billingrate)arg0;
			Billingrate price1=(Billingrate)arg1;
			int flag = price0.getCpu().compareTo(price1.getCpu());
			if(flag == 0){
				int flag1 = price0.getMemory().compareTo(price1.getMemory());
				if(flag1 == 0){
					int flag2 = price0.getHarddisk().compareTo(price1.getHarddisk());
					if(flag2 == 0){
						return price0.getBandwidth().compareTo(price1.getBandwidth());
					}else{
						return flag2;
					}
				}else{
					return flag1;
				}
			}else{
				return flag;
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public class ComparatorCpu implements Comparator{
		public int compare(Object arg0, Object arg1) {
			Billingrate price0=(Billingrate)arg0;
			Billingrate price1=(Billingrate)arg1;
			return price0.getCpu().compareTo(price1.getCpu());
		}
	}
	
	@SuppressWarnings("rawtypes")
	public class ComparatorMemory implements Comparator{
		public int compare(Object arg0, Object arg1) {
			Billingrate price0=(Billingrate)arg0;
			Billingrate price1=(Billingrate)arg1;
			return price0.getMemory().compareTo(price1.getMemory());
		}
	}
	
	@SuppressWarnings("rawtypes")
	public class ComparatorHarddisk implements Comparator{
		public int compare(Object arg0, Object arg1) {
			Billingrate price0=(Billingrate)arg0;
			Billingrate price1=(Billingrate)arg1;
			return price0.getHarddisk().compareTo(price1.getHarddisk());
		}
	}
	
	@SuppressWarnings("rawtypes")
	public class ComparatorBandwidth implements Comparator{
		public int compare(Object arg0, Object arg1) {
			Billingrate price0=(Billingrate)arg0;
			Billingrate price1=(Billingrate)arg1;
			return price0.getBandwidth().compareTo(price1.getBandwidth());
		}
	}

	public int getClusterid() {
		return clusterid;
	}

	public void setClusterid(int clusterid) {
		this.clusterid = clusterid;
	}

	public List<Billingrate> getBandwidthPrice() {
		return bandwidthPrice;
	}

	public void setBandwidthPrice(List<Billingrate> bandwidthPrice) {
		this.bandwidthPrice = bandwidthPrice;
	}

	public List<Billingrate> getCpuPrice() {
		return cpuPrice;
	}

	public void setCpuPrice(List<Billingrate> cpuPrice) {
		this.cpuPrice = cpuPrice;
	}

	public List<Billingrate> getHarddiskPrice() {
		return harddiskPrice;
	}

	public void setHarddiskPrice(List<Billingrate> harddiskPrice) {
		this.harddiskPrice = harddiskPrice;
	}

	public List<Billingrate> getMemoryPrice() {
		return memoryPrice;
	}

	public void setMemoryPrice(List<Billingrate> memoryPrice) {
		this.memoryPrice = memoryPrice;
	}

	public List<Billingrate> getPackagePrice() {
		return packagePrice;
	}

	public void setPackagePrice(List<Billingrate> packagePrice) {
		this.packagePrice = packagePrice;
	}

	public HashMap<Integer, String> getYearDiscounts() {
		return yearDiscounts;
	}

	public void setYearDiscounts(HashMap<Integer, String> yearDiscounts) {
		this.yearDiscounts = yearDiscounts;
	}

	public HashMap<Integer, String> getMonthDiscounts() {
		return monthDiscounts;
	}

	public void setMonthDiscounts(HashMap<Integer, String> monthDiscounts) {
		this.monthDiscounts = monthDiscounts;
	}

}
