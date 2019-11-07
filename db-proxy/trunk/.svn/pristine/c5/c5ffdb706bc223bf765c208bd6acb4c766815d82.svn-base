package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.AdminPrivilegeTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class AdminPrivilegeDAO extends AbstractDAO<AdminPrivilegeTable> {
	static final String PU_NAME = "AppcloudPU";
	
	@Override
	public Class getEntityClass() {
		return AdminPrivilegeTable.class;
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
