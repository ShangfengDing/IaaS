package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.VmUsedIpTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;


public class VmUsedIpDAO extends AbstractDAO<VmUsedIpTable> {
	static final String PU_NAME = "AppcloudPU";
	
	
	@Override
	public Class<VmUsedIpTable> getEntityClass() {
		return VmUsedIpTable.class;
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
