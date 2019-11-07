package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.AdminLogTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class AdminLogDAO extends AbstractDAO<AdminLogTable>{
	static final String PU_NAME = "AppcloudPU";
	
	@Override
	public Class<AdminLogTable> getEntityClass() {
		return AdminLogTable.class;
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
