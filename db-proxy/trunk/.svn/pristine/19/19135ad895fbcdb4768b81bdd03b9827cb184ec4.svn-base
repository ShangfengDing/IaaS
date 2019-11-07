/**
 * File: NetworkLoadDAO.java
 * Author: weed
 * Create Time: 2012-11-25
 */
package appcloud.dbproxy.mysql.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Level;

import appcloud.common.model.Load;
import appcloud.common.model.NetworkLoad;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.model.NetworkLoadTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.JPAInterpreter;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

/**
 * @author weed
 *
 */
public class NetworkLoadDAO extends AbstractDAO<NetworkLoadTable> {
	static final String PU_NAME = "AppcloudPU";

	/* (non-Javadoc)
	 * @see appcloud.dbproxy.util.sql.AbstractDAO#getEntityClass()
	 */
	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return NetworkLoadTable.class;
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

	public NetworkLoad getCurNetLoadByUuid(String uuid) {
		getLogger().log(Level.INFO, "finding latest " + getClassName() + " instance with uuid: " + uuid, null);
		try {
			final String queryString = "select model from " + getClassName()
					+ " model where model.fatherUuid = :uuid order by model.id DESC";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("uuid", uuid).setMaxResults(1);
			return (NetworkLoad) query.getSingleResult();
		} catch (NoResultException re) {
			return null;
		} catch (RuntimeException re) {
			getLogger().log(Level.ERROR, "find latest " + getClassName() + " instance with uuid: " + uuid + "failed", re);
			throw re;
		}
	}
	
	public NetworkLoad getNetLoadByUuid(String uuid, Calendar st, Calendar ed) {
		getLogger().log(Level.INFO, "finding latest " + getClassName() + " instance with uuid: " + uuid, null);
		try {
			final String queryString = "select new appcloud.dbproxy.mysql.model.NetworkLoadTable(sum(model.netInByte) , sum(model.netOutByte) , sum(model.requestNum) , avg(model.averageCostSec)) from " + getClassName()
					+ " model where model.fatherUuid = :uuid and :st < model.startTime and model.endTime < :ed";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("st", st, TemporalType.TIMESTAMP);
			query.setParameter("ed", ed, TemporalType.TIMESTAMP);
			query.setParameter("uuid", uuid).setMaxResults(1);
			return (NetworkLoad) query.getSingleResult();
		} catch (NoResultException re) {
			return new NetworkLoad();
		} catch (RuntimeException re) {
			getLogger().log(Level.ERROR, "find latest " + getClassName() + " instance with uuid: " + uuid + "failed", re);
			throw re;
		}
	}
	
	public NetworkLoad getVersionNetLoad(String appUuid, Calendar st, Calendar ed) {
		getLogger().log(Level.INFO, "finding latest " + getClassName() + " instance with appUuid: " + appUuid + " " +  date2String(st) + " -> " + date2String(ed), null);

		try {
			final String queryString = "select new appcloud.dbproxy.mysql.model.NetworkLoadTable(sum(model.netInByte) , sum(model.netOutByte) , sum(model.requestNum) , avg(model.averageCostSec)) from " + getClassName()
					+ " model where model.appUuid = :appUuid and :st < model.startTime and model.endTime < :ed";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("st", st, TemporalType.TIMESTAMP);
			query.setParameter("ed", ed, TemporalType.TIMESTAMP);
			query.setParameter("appUuid", appUuid).setMaxResults(1);
			return (NetworkLoad) query.getSingleResult();
		} catch (NoResultException re) {
			return new NetworkLoad();
		} catch (RuntimeException re) {
			getLogger().log(Level.ERROR, "find latest " + getClassName() + " instance with appUuid: " + appUuid + " failed", re);
			throw re;
		}
	}
	
	public NetworkLoad getAppNetLoad(Integer j2eeinfoId, Calendar st, Calendar ed) {
		getLogger().log(Level.INFO, "finding latest " + getClassName() + " instance with j2eeinfoId: " + j2eeinfoId + " " +  date2String(st) + " -> " + date2String(ed), null);

		if (j2eeinfoId == null) {
			return new NetworkLoad();
		}
		
		try {
			final String queryString = "select new appcloud.dbproxy.mysql.model.NetworkLoadTable(sum(model.netInByte) , sum(model.netOutByte) , sum(model.requestNum) , avg(model.averageCostSec)) from " + getClassName()
					+ " model where model.j2eeinfoId = :j2eeinfoId and :st < model.startTime and model.endTime < :ed";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("st", st, TemporalType.TIMESTAMP);
			query.setParameter("ed", ed, TemporalType.TIMESTAMP);
			query.setParameter("j2eeinfoId", j2eeinfoId).setMaxResults(1);
			return (NetworkLoad) query.getSingleResult();
		} catch (NoResultException re) {
			return new NetworkLoad();
		} catch (RuntimeException re) {
			getLogger().log(Level.INFO, "finding latest " + getClassName() + " instance with j2eeinfoId: " + j2eeinfoId + " " +  date2String(st) + " -> " + date2String(ed), null);
			throw re;
		}
	}
	
	public NetworkLoad getNetLoad(QueryObject<NetworkLoad> queryObject, Calendar st, Calendar ed) {
		
		getLogger().log(Level.INFO, "finding  " + getClassName() + " instance with " + queryObject + date2String(st) + " -> " + date2String(ed), null);

		if (queryObject == null) {
			return new NetworkLoad();
		}

		try {
			final String queryString = "select new appcloud.dbproxy.mysql.model.NetworkLoadTable(sum(model.netInByte) , sum(model.netOutByte) , sum(model.requestNum) , avg(model.averageCostSec)) from " + getClassName()
					+ " model " + queryObject.generateQueryString(new JPAInterpreter()) + " and :st < model.startTime and model.endTime < :ed";
			Query query = getEntityManager().createQuery(queryString);
			
			Map<String, Object> parameters = queryObject.generateParameters();
			for (Entry<String, Object> parameter : parameters.entrySet()) {
				query.setParameter(parameter.getKey(), parameter.getValue());
			}
			
			query.setParameter("st", st, TemporalType.TIMESTAMP);
			query.setParameter("ed", ed, TemporalType.TIMESTAMP);
			query.setMaxResults(1);
			return (NetworkLoad) query.getSingleResult();
		} catch (NoResultException re) {
			return new NetworkLoad();
		} catch (RuntimeException re) {
			getLogger().log(Level.INFO, "finding  " + getClassName() + " instance with " + queryObject + date2String(st) + " -> " + date2String(ed), null);
			throw re;
		}
	}
	
	private static String date2String(Calendar c) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(c.getTime());
	}
}
