package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.VmUserTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class VmUserDAO extends AbstractDAO<VmUserTable>{
	static final String PU_NAME = "AppcloudPU";
	
	@Override
	public Class<VmUserTable> getEntityClass() {
		return VmUserTable.class;
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
