package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.AdminURLTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class AdminURLDAO extends AbstractDAO<AdminURLTable> {
	static final String PU_NAME = "AppcloudPU";

	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return AdminURLTable.class;
	}

	@Override
	public String getPUName() {
		// TODO Auto-generated method stub
		return PU_NAME;
	}

	@Override
	public IEntityManagerHelper getEntityManagerHelper() {
		return new NoCacheEntityManagerHelper();
	}

}
