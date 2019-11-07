/**
 * File: J2EEInfoDAO.java
 * Author: weed
 * Create Time: 2012-11-25
 */
package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.J2EEInfoTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

/**
 * @author weed
 *
 */
public class J2EEInfoDAO extends AbstractDAO<J2EEInfoTable> {
	static final String PU_NAME = "AppcloudPU";

	/* (non-Javadoc)
	 * @see appcloud.dbproxy.util.sql.AbstractDAO#getEntityClass()
	 */
	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return J2EEInfoTable.class;
	}

	/* (non-Javadoc)
	 * @see appcloud.dbproxy.util.sql.AbstractDAO#getPUName()
	 */
	@Override
	public String getPUName() {
		// TODO Auto-generated method stub
		return PU_NAME;
	}

	/* (non-Javadoc)
	 * @see appcloud.dbproxy.util.sql.AbstractDAO#getEntityManagerHelper()
	 */
	@Override
	public IEntityManagerHelper getEntityManagerHelper() {
		// TODO Auto-generated method stub
		return new NoCacheEntityManagerHelper();
	}

}
