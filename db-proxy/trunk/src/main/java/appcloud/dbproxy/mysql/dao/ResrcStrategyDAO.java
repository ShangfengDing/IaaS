package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.ResrcStrategyTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class ResrcStrategyDAO extends AbstractDAO<ResrcStrategyTable>{
	static final String PU_NAME = "AppcloudPU";
	
	@Override
	public Class<ResrcStrategyTable> getEntityClass() {
		return ResrcStrategyTable.class;
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
