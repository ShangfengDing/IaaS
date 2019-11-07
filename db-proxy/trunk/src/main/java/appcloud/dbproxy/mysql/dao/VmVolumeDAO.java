package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.VmVolumeTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

/**
 * @author XuanJiaxing
 *
 */
public class VmVolumeDAO extends AbstractDAO<VmVolumeTable>{
	static final String PU_NAME = "AppcloudPU";
	
	@Override
	public Class<VmVolumeTable> getEntityClass() {
		return VmVolumeTable.class;
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
