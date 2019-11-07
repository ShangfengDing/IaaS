package appcloud.dbproxy.mysql.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.apache.log4j.Level;

import appcloud.common.model.HostLoad;
import appcloud.dbproxy.mysql.model.VmLoadTable;

public class VmLoadDao extends CommonLoadDAO<VmLoadTable> {

	@Override
	public Class<VmLoadTable> getEntityClass() {
		return VmLoadTable.class;
	}

	public HostLoad getLatestLoad(String uuid) {
		List<VmLoadTable> list = getLatestLoad(uuid, 1);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		
		return null;
	}
	
	public List<VmLoadTable> getLatestLoad(String uuid, int num) {
		Query query = getEntityManager().createQuery("select model from " + getClassName() + " model where model.uuid=:uuid order by model.time desc");
		query.setParameter("uuid", uuid);
		query.setFirstResult(0);
		query.setMaxResults(num);
		return query.getResultList();
	}
	
	public List<VmLoadTable> getOneDayLoad(String uuid, Date date) {
		
		Date startTime = new Date(date.getTime());
		clearTime(startTime);
		
		Date endTime = new Date(startTime.getTime() + ONE_DAY_MILLISECONDS);
		
		return (List<VmLoadTable>) getLoads(uuid, startTime, endTime);
	}
	
    public HostLoad getOneDayAvgLoad(String uuid, Date date) {

    	clearTime(date);
    	Timestamp startTime = new Timestamp(date.getTime());
    	Timestamp endTime = new Timestamp(date.getTime() + ONE_DAY_MILLISECONDS);
    	return getAvgLoad(uuid, startTime, endTime);
    }
    
	@Override
	public List<VmLoadTable> findByProperty2(String name1, Object value1,
			String name2, Object value2) {
		getLogger().log(
				Level.DEBUG,
				"finding " + getClassName() + " instance with property1: "
						+ name1 + ", value1: " + value1 + "; propety2: "
						+ name2 + ", value2: " + value2, null);
		try {
			final String queryString = "select model from " + getClassName()
					+ " model where model." + name1 + "= :value1 and model."
					+ name2 + " like :value2%";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("value1", value1);
			query.setParameter("value2", value2);
			return query.getResultList();
		} catch (RuntimeException re) {
			getLogger().log(Level.ERROR, "find by property name2 failed", re);
			throw re;
		}
	}

}
