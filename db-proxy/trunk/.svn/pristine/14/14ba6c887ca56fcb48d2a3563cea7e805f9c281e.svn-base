package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.AlertMsgTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class AlertMsgDAO extends AbstractDAO<AlertMsgTable>{
	static final String PU_NAME = "AppcloudPU";
	
	@Override
	public Class<AlertMsgTable> getEntityClass() {
		return AlertMsgTable.class;
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
