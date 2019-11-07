package appcloud.dbproxy.mysql.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import appcloud.common.model.HostLoad;
import appcloud.dbproxy.mysql.model.HostLoadTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class HostLoadDao extends CommonLoadDAO<HostLoadTable> {

    static final String PU_NAME = "AppcloudPU";

    @Override
    public Class<HostLoadTable> getEntityClass() {
        return HostLoadTable.class;
    }

    @Override
    public String getPUName() {
        return PU_NAME;
    }

    @Override
    public IEntityManagerHelper getEntityManagerHelper() {
        return new NoCacheEntityManagerHelper();
    }

    public List<? extends HostLoad> getLatestLoad(String uuid, int nums) {
    	Query query = getEntityManager().createQuery("select model from " + getClassName() + " model where model.uuid=:uuid order by time desc");
    	query.setParameter("uuid", uuid);
    	query.setMaxResults(nums);
    	List<? extends HostLoad> list = query.getResultList();
    	return list;
    }
    
    public HostLoad getLatestLoad(String uuid) {
    	List<? extends HostLoad> list = getLatestLoad(uuid, 1);
    	if (!list.isEmpty()) {
    		return list.get(0);
    	}
    	
    	return null;
    }
	
}
