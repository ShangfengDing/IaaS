package com.appcloud.vm.action.hd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.billing.BillingAPI;
import com.appcloud.vm.fe.billing.Billingrate;
import com.appcloud.vm.fe.common.Constants;

public class HdPriceAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(HdPriceAction.class);
	
	private List<Billingrate> harddiskPrice = new ArrayList<Billingrate>();
	private int clusterid = -1;
	
	@SuppressWarnings("unchecked")
	public String execute() {
		logger.info("clusterid: " + clusterid);
		//显示价格
		List<Billingrate> all = BillingAPI.getRate(Constants.BILLING_ALL, clusterid, -1, -1, -1, -1);
		
		if (all != null) {
			logger.info("获取计费规则成功");
		} else {
			logger.info("获取计费规则失败");
		}
		
		for (int i = 0; i < all.size(); i++) {
			Billingrate rate = new Billingrate();
			rate = all.get(i);
			if (rate.getPtype().equals(Constants.BILLING_HD_ABBR)) {
				harddiskPrice.add(rate);
			}
		}
		ComparatorHarddisk comparatorHarddisk = new ComparatorHarddisk();
		Collections.sort(harddiskPrice, comparatorHarddisk);
		
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public class ComparatorHarddisk implements Comparator{
		public int compare(Object arg0, Object arg1) {
			Billingrate price0=(Billingrate)arg0;
			Billingrate price1=(Billingrate)arg1;
			return price0.getHarddisk().compareTo(price1.getHarddisk());
		}
	}
	
	public int getClusterid() {
		return clusterid;
	}

	public void setClusterid(int clusterid) {
		this.clusterid = clusterid;
	}

	public List<Billingrate> getHarddiskPrice() {
		return harddiskPrice;
	}

	public void setHarddiskPrice(List<Billingrate> harddiskPrice) {
		this.harddiskPrice = harddiskPrice;
	}
	
}
