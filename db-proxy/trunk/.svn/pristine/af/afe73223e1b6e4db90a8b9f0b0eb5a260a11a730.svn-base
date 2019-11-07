package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.VmImageTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

/**
 * @author XuanJiaxing
 *
 */
public class VmImageDAO extends AbstractDAO<VmImageTable>{
	static final String PU_NAME = "AppcloudPU";
	
	@Override
	public Class<VmImageTable> getEntityClass() {
		return VmImageTable.class;
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
