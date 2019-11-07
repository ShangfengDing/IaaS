package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.VmSecurityGroupTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class VmSecurityGroupDAO  extends AbstractDAO<VmSecurityGroupTable>{
	static final String PU_NAME = "AppcloudPU";
	
	@Override
	public Class<VmSecurityGroupTable> getEntityClass() {
		return VmSecurityGroupTable.class;
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
