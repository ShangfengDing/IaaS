package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.VmHostUsedTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class VmHostUsedDAO extends AbstractDAO<VmHostUsedTable> {
	static final String PU_NAME = "AppcloudPU";

	@Override
	public Class<VmHostUsedTable> getEntityClass() {
		return VmHostUsedTable.class;
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
