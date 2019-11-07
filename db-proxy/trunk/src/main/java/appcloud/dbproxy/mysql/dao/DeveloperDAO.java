package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.DeveloperTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class DeveloperDAO extends AbstractDAO<DeveloperTable>{
	static final String PU_NAME = "AppcloudPU";
	
	@Override
	public Class<DeveloperTable> getEntityClass() {
		return DeveloperTable.class;
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
