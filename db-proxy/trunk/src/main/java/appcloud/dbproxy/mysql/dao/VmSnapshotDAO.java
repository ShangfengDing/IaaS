package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.VmSnapshotTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

/**
 * @author XuanJiaxing
 *
 */
public class VmSnapshotDAO extends AbstractDAO<VmSnapshotTable>{
static final String PU_NAME = "AppcloudPU";
	
	@Override
	public Class<VmSnapshotTable> getEntityClass() {
		return VmSnapshotTable.class;
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
