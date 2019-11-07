package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.AdminTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class AdminDAO extends AbstractDAO<AdminTable>{
	static final String PU_NAME = "AppcloudPU";
	
	@Override
	public Class<AdminTable> getEntityClass() {
		return AdminTable.class;
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
