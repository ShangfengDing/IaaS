package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.VmVlanTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class VmVlanDAO extends AbstractDAO<VmVlanTable> {
	static final String PU_NAME = "AppcloudPU";
	
	
	@Override
	public Class<VmVlanTable> getEntityClass() {
		return VmVlanTable.class;
	}


	@Override
	public String getPUName() {	
		return PU_NAME;
	}

	
	@Override
	public IEntityManagerHelper getEntityManagerHelper() {
		return new NoCacheEntityManagerHelper();
	}

}
