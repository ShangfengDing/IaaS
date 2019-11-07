package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.ResourceStrategyTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class ResourceStrategyDAO extends AbstractDAO<ResourceStrategyTable> {
	static final String PU_NAME = "AppcloudPU";

	@Override
	public Class<ResourceStrategyTable> getEntityClass() {
		return ResourceStrategyTable.class;
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
