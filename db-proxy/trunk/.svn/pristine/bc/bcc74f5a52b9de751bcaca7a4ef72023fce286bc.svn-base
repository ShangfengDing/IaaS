package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.AdminResourceTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class AdminResourceDAO extends AbstractDAO<AdminResourceTable> {
	static final String PU_NAME = "AppcloudPU";
	
	@Override
	public Class getEntityClass() {
		return AdminResourceTable.class;
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
