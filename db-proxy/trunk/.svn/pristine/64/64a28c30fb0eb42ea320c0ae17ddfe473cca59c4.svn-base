/**
 * File: LoadDAO.java
 * Author: weed
 * Create Time: 2012-11-25
 */
package appcloud.dbproxy.mysql.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Level;

import appcloud.common.model.Load;
import appcloud.common.util.ConnectionFactory;
import appcloud.dbproxy.mysql.model.LoadTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

/**
 * @author weed
 *
 */
public class LoadDAO extends AbstractDAO<LoadTable> {
	static final String PU_NAME = "AppcloudPU";

	/* (non-Javadoc)
	 * @see appcloud.dbproxy.util.sql.AbstractDAO#getEntityClass()
	 */
	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return LoadTable.class;
	}

	/* (non-Javadoc)
	 * @see appcloud.dbproxy.util.sql.AbstractDAO#getPUName()
	 */
	@Override
	public String getPUName() {
		// TODO Auto-generated method stub
		return PU_NAME;
	}
	
	@Override
	public void save(LoadTable load){
		try {
			ConnectionFactory.getLoadProxy().save(load);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see appcloud.dbproxy.util.sql.AbstractDAO#getEntityManagerHelper()
	 */
	@Override
	public IEntityManagerHelper getEntityManagerHelper() {
		// TODO Auto-generated method stub
		return new NoCacheEntityManagerHelper();
	}

	public Load getCurLoadByUuid(String uuid) {
		try {
			return ConnectionFactory.getLoadProxy().getCurLoadByUuid(uuid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
