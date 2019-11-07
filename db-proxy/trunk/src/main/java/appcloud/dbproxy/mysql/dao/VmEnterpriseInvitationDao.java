package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.VmEnterpriseInvitationTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class VmEnterpriseInvitationDao extends AbstractDAO<VmEnterpriseInvitationTable> {
	static final String PU_NAME = "AppcloudPU";
	
	@Override
	public Class<VmEnterpriseInvitationTable> getEntityClass() {
		return VmEnterpriseInvitationTable.class;
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
