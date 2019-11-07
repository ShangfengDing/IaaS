package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.AlertSettingTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class AlertSettingDAO extends AbstractDAO<AlertSettingTable>{
	static final String PU_NAME = "AppcloudPU";
	
	@Override
	public Class<AlertSettingTable> getEntityClass() {
		return AlertSettingTable.class;
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
