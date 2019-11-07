package appcloud.dbproxy.mysql.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.apache.log4j.Level;
import appcloud.common.model.HostLoad;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public abstract class CommonLoadDAO<T> extends AbstractDAO<T> {

	public static final long ONE_DAY_MILLISECONDS = 24*3600*1000;
	static final String PU_NAME = "AppcloudPU";
	
	@Override
	public String getPUName() {
		return PU_NAME;
	}

	@Override
	public IEntityManagerHelper getEntityManagerHelper() {
		return new NoCacheEntityManagerHelper();
	}

	/**
	 * 获取一定时间范围内的load
	 * @param uuid
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<? extends HostLoad> getLoads(String uuid, Date startTime, Date endTime) {
		Query query = getEntityManager().createQuery("select model from " + getClassName() + " model where model.uuid=:uuid and model.time between :startTime and :endTime");
		
		query.setParameter("uuid", uuid);
    	query.setParameter("startTime", new Timestamp(startTime.getTime()), TemporalType.TIMESTAMP);
    	query.setParameter("endTime", new Timestamp(endTime.getTime()), TemporalType.TIMESTAMP);
    	
		return query.getResultList();
	}
	
	/**
	 * 获取某个host或虚拟机的平均负载
	 * @param uuid
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    public HostLoad getAvgLoad(String uuid, Date startTime, Date endTime) {
    	Query query = getEntityManager().createQuery("select new appcloud.common.model.HostLoad(avg(model.cpuPercent), avg(model.avgLoad), avg(model.memPercent), avg(model.diskPercent), avg(model.netInPercent), avg(model.netOutPercent), avg(model.diskReadRate), avg(model.diskWriteRate)) from " + getClassName() + " model where model.uuid=:uuid and model.time between :startTime and :endTime");
    	
    	query.setParameter("uuid", uuid);
    	
    	query.setParameter("startTime", new Timestamp(startTime.getTime()), TemporalType.TIMESTAMP);
    	query.setParameter("endTime", new Timestamp(endTime.getTime()), TemporalType.TIMESTAMP);

    	HostLoad load = (HostLoad) query.getSingleResult();
    	load.setUuid(uuid);
    	load.setTime(new Timestamp(startTime.getTime()));
    	return load;
    }

    /**
     * 获取全部host或虚拟机的平均负载
     * @param startTime
     * @param endTime
     * @return
     */
    public List<? extends HostLoad> getAvgLoads(Date startTime, Date endTime) {
    	Query query = getEntityManager().createQuery("select new appcloud.common.model.HostLoad(uuid, avg(model.cpuPercent), avg(model.avgLoad), avg(model.memPercent), avg(model.diskPercent), avg(model.netInPercent), avg(model.netOutPercent), avg(model.diskReadRate), avg(model.diskWriteRate)) from " + getClassName() + " model where model.time between :startTime and :endTime group by model.uuid");
    	
    	query.setParameter("startTime", new Timestamp(startTime.getTime()), TemporalType.TIMESTAMP);
    	query.setParameter("endTime", new Timestamp(endTime.getTime()), TemporalType.TIMESTAMP);

    	List<? extends HostLoad> list = query.getResultList();
    	Iterator<? extends HostLoad> itr = list.iterator();
    	while (itr.hasNext()) {
    		HostLoad load = itr.next();
    		load.setTime(new Timestamp(startTime.getTime()));
    	}
    	
    	return list;
    }
    
    public int deleteBefore(String uuid, Date time) {
    	EntityManager em = getEntityManager();

    	if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
			getLogger().log(Level.ERROR, "A transaction is still active before another begin, we have to roll back it!",
					null);
		}
    	
    	try {
			em.getTransaction().begin();

			Query query = em.createQuery("delete from " + getClassName() + " model where model.uuid = :uuid and model.time <= :time");
			query.setParameter("uuid", uuid);
			query.setParameter("time", time, TemporalType.TIMESTAMP);

			int count = query.executeUpdate();

			em.getTransaction().commit();
			
			return count;
    	} catch (RuntimeException re) {
    		getLogger().log(Level.ERROR, "save failed", re);
			em.getTransaction().rollback();
			throw re;
    	}

	}
    
    public int deleteBefore(Date time) {
    	EntityManager em = getEntityManager();

    	if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
			getLogger().log(Level.ERROR, "A transaction is still active before another begin, we have to roll back it!",
					null);
		}
    	
    	try {
			em.getTransaction().begin();
			
			Query query = em.createQuery("delete from " + getClassName() + " model where model.time <= :time");
			query.setParameter("time", time, TemporalType.TIMESTAMP);
			
			int count = query.executeUpdate();
			
			em.getTransaction().commit();
			
			return count;
    	} catch (RuntimeException re) {
    		getLogger().log(Level.ERROR, "save failed", re);
			em.getTransaction().rollback();
			throw re;
    	}

	}
    
    public int delete(Date time) {
    	
    	int count = 0;
    	EntityManager em = getEntityManager();

        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
            getLogger().log(Level.ERROR, "A transaction is still active before another begin, we have to roll back it!", null);
        }
        em.getTransaction().begin();
        
        try {
            final String queryString = "delete from " + getClassName() + " model where model.time = :time";
            Query query = em.createQuery(queryString);
            query.setParameter("time", new Timestamp(time.getTime()), TemporalType.TIMESTAMP);
            count = query.executeUpdate();
            em.getTransaction().commit();
        } catch (RuntimeException re) {
            getLogger().log(Level.ERROR, "delete by time failed", re);
            em.getTransaction().rollback();
            throw re;
        }
        
        return count;
    }
    
    public static void clearTime(Date date) {
    	date.setTime(date.getTime()/1000*1000);
    	date.setHours(0);
    	date.setMinutes(0);
    	date.setSeconds(0);
    }
}
