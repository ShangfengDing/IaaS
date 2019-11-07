package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.VmEnterpriseTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class VmEnterpriseDao extends AbstractDAO<VmEnterpriseTable>{
	static final String PU_NAME = "AppcloudPU";
	
	@Override
	public Class<VmEnterpriseTable> getEntityClass() {
		return VmEnterpriseTable.class;
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
