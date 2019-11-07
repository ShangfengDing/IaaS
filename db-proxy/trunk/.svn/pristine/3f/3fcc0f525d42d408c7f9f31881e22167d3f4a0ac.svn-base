/**
 * File: DomainDAO.java
 * Author: weed
 * Create Time: 2012-11-22
 */
package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.DomainTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

/**
 * @author weed
 *
 */
public class DomainDAO extends AbstractDAO<DomainTable> {
	static final String PU_NAME = "AppcloudPU";

	/* (non-Javadoc)
	 * @see com.free4lab.utils.sql.AbstractDAO#getEntityClass()
	 */
	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return DomainTable.class;
	}

	/* (non-Javadoc)
	 * @see com.free4lab.utils.sql.AbstractDAO#getEntityManagerHelper()
	 */
	@Override
	public IEntityManagerHelper getEntityManagerHelper() {
		// TODO Auto-generated method stub
		return new NoCacheEntityManagerHelper();
	}

	/* (non-Javadoc)
	 * @see com.free4lab.utils.sql.AbstractDAO#getPUName()
	 */
	@Override
	public String getPUName() {
		// TODO Auto-generated method stub
		return PU_NAME;
	}

}
