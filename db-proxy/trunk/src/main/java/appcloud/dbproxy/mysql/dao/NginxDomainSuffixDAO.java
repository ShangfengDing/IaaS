/**
 * File: NginxDomainSuffixDAO.java
 * Author: weed
 * Create Time: 2012-11-24
 */
package appcloud.dbproxy.mysql.dao;

import appcloud.dbproxy.mysql.model.NginxToSuffixTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

/**
 * @author weed
 *
 */
public class NginxDomainSuffixDAO extends AbstractDAO<NginxToSuffixTable> {
	static final String PU_NAME = "AppcloudPU";
	
	/* (non-Javadoc)
	 * @see appcloud.dbproxy.util.sql.AbstractDAO#getEntityClass()
	 */
	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return NginxToSuffixTable.class;
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
