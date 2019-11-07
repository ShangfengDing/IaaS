package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.AdminRoleTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class AdminRoleDAO extends AbstractDAO<AdminRoleTable> {
	static final String PU_NAME = "AppcloudPU";

	@Override
	public Class<AdminRoleTable> getEntityClass() {
		return AdminRoleTable.class;
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
