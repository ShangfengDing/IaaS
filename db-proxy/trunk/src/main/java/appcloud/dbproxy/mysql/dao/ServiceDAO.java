package appcloud.dbproxy.mysql.dao;

import java.util.List;

import javax.persistence.Query;

import appcloud.common.model.Service;
import appcloud.dbproxy.mysql.model.ServiceTable;
import appcloud.dbproxy.util.sql.AbstractDAO;
import appcloud.dbproxy.util.sql.IEntityManagerHelper;
import appcloud.dbproxy.util.sql.entitymanager.NoCacheEntityManagerHelper;

public class ServiceDAO extends AbstractDAO<ServiceTable> {

	static final String PU_NAME = "AppcloudPU";
	
	@Override
	public Class<ServiceTable> getEntityClass() {
		return ServiceTable.class;
	}

	@Override
	public String getPUName() {
		return PU_NAME;
	}

	@Override
	public IEntityManagerHelper getEntityManagerHelper() {
		return new NoCacheEntityManagerHelper();
	}

	/**
	 * 获取最后一次更新的service
	 * @param hostId
	 * @return
	 */
	public Service getLatestHostService(Integer hostId) {
		Query query = getEntityManager().createQuery("select model from " + getClassName() + " model where model.hostId=:hostId order by model.updateTime desc");
		query.setParameter("hostId", hostId);
		query.setMaxResults(1);
		List<ServiceTable> list = query.getResultList();
		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
