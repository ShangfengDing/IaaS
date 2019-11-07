package com.appcloud.vm.fe.model;

import com.free4lab.utils.sql.AbstractDAO;
import com.free4lab.utils.sql.IEntityManagerHelper;
import com.free4lab.utils.sql.entitymanager.NoCacheEntityManagerHelper;

@Deprecated
public class UserDAO extends AbstractDAO<User>{
	
	public String getClassName(){
		return getEntityClass().getName();
	}
	
	@Override
	public Class<User> getEntityClass() {
		return User.class;
	}
	
	public static final String PU_NAME = "VMC_PU";
	
	@Override
	public String getPUName() {
		return PU_NAME;
	}

	@Override
	public IEntityManagerHelper getEntityManagerHelper() {
		return new NoCacheEntityManagerHelper();
	}
	
}
