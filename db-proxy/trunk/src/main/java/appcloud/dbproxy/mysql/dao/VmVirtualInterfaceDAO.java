package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.VmVirtualInterfaceTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;


public class VmVirtualInterfaceDAO extends AbstractDAO<VmVirtualInterfaceTable> {
	static final String PU_NAME = "AppcloudPU";
	
	
	@Override
	public Class<VmVirtualInterfaceTable> getEntityClass() {
		return VmVirtualInterfaceTable.class;
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
