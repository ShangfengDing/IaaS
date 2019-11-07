package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.VmGroupTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class VmGroupDAO extends AbstractDAO<VmGroupTable>{
	static final String PU_NAME = "AppcloudPU";
	
	@Override
	public Class<VmGroupTable> getEntityClass() {
		return VmGroupTable.class;
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
