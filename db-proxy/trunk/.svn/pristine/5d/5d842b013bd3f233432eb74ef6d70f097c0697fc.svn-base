package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.HostTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class HostDAO extends AbstractDAO<HostTable>{
	static final String PU_NAME = "AppcloudPU";
	
	@Override
	public Class<HostTable> getEntityClass() {
		return HostTable.class;
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
