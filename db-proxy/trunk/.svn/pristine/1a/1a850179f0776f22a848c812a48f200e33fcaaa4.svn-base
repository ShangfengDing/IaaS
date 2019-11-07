package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.VmSecurityGroupRuleTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class VmSecurityGroupRuleDAO  extends AbstractDAO<VmSecurityGroupRuleTable>{
	static final String PU_NAME = "AppcloudPU";
	
	@Override
	public Class<VmSecurityGroupRuleTable> getEntityClass() {
		return VmSecurityGroupRuleTable.class;
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
