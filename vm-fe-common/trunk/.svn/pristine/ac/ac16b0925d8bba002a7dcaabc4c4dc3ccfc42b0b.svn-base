package com.appcloud.vm.fe.model;

import java.util.List;

import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;
/**
 * @author hgm
 */
public class AppkeyDAO extends AbstractDAO<Appkey>{
	@Override
	public Class<Appkey> getEntityClass() {
		return Appkey.class;
	}

	@Override
	public IEntityManagerHelper getEntityManagerHelper() {
		return new NoCacheEntityManagerHelper();
	}

	public static final String PU_NAME = "VMC_PU";
	@Override
	public String getPUName() {
		return PU_NAME;
	}

	public static void main(String args[]){
		AppkeyDAO dao = new AppkeyDAO();
		List<Appkey> appkeys = dao.findByProperty("userEmail", "yumike18@126.com");
		System.out.print(appkeys.size());
	}
}
