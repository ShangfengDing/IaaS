package com.appcloud.vm.fe.model;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

public class ServiceNumDAO extends AbstractDAO<ServiceNum>{

	public static final String PU_NAME = "VMC_PU";

	@Override
	public Class<ServiceNum> getEntityClass() {
		return ServiceNum.class;
	}

	@Override
	public String getPUName() {
		return PU_NAME;
	}

	@Override
	public IEntityManagerHelper getEntityManagerHelper() {
		return new NoCacheEntityManagerHelper();
	}
	
/*	@Test
	public void test() {
		HashMap<Integer,Integer> hm = new HashMap<>();
		AppkeyDAO appkeyDAO = new AppkeyDAO();
		ServiceNumDAO serviceDAO = new ServiceNumDAO();
		List<Appkey> list = appkeyDAO.findAll();
		for(Appkey ak: list) {
			ServiceNum serviceNum = new ServiceNum();
			serviceNum.setUserId(ak.getUserId());
			if(hm.containsKey(ak.getUserId())) {
				System.out.println("重复数据");
			}
			else {
				hm.put(ak.getUserId(), 1);			
				serviceDAO.save(serviceNum);
				System.out.println("save "+ ServiceNum.class.getName()+"success");
			}
		}
	}*/
	
}
