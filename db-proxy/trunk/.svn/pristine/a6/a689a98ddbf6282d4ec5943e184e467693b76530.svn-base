package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.VmMacSequenceTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;


public class VmMacSequenceDAO extends AbstractDAO<VmMacSequenceTable> {
	static final String PU_NAME = "AppcloudPU";
	
	
	@Override
	public Class<VmMacSequenceTable> getEntityClass() {
		return VmMacSequenceTable.class;
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
