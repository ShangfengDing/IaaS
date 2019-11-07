package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.VmIpSegmentTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;


public class VmIpSegmentDAO extends AbstractDAO<VmIpSegmentTable> {
	static final String PU_NAME = "AppcloudPU";
	
	
	@Override
	public Class<VmIpSegmentTable> getEntityClass() {
		return VmIpSegmentTable.class;
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