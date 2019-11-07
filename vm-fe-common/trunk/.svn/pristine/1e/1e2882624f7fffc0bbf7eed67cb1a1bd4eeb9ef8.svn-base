package com.appcloud.vm.fe.billing;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

public class RateManager {

	private static final Logger logger = Logger.getLogger(RateManager.class);
	
	public List<Billingrate> findAllRates(String item, Integer clusterid,
			Integer cpu, Integer memory, Integer harddisk, Integer bandwidth){
		List<Billingrate> allrates = BillingAPI.getRate(item, clusterid, cpu, memory, 
				harddisk, bandwidth);
		if(allrates.size() > 0){
			return allrates;
		}else{
			logger.info("getRate failed");
			return Collections.emptyList();
		}
	}
}
