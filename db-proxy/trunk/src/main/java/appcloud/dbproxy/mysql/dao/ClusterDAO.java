package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.ClusterTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class ClusterDAO extends AbstractDAO<ClusterTable>{
	static final String PU_NAME = "AppcloudPU";
	
	@Override
	public Class<ClusterTable> getEntityClass() {
		return ClusterTable.class;
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
