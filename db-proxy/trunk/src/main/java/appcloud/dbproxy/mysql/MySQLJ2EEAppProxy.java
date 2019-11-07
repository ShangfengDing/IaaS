package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.Instance;
import appcloud.common.model.J2EEApp;
import appcloud.common.proxy.J2EEAppProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.DeveloperDAO;
import appcloud.dbproxy.mysql.dao.InstanceDAO;
import appcloud.dbproxy.mysql.dao.J2EEAppDAO;
import appcloud.dbproxy.mysql.model.J2EEAppTable;

public class MySQLJ2EEAppProxy implements J2EEAppProxy{
	private static J2EEAppDAO dao = new J2EEAppDAO();

	@Override
	public List<? extends J2EEApp> findAll(boolean withDev,
			boolean withInstance, boolean withNum) throws Exception {
		return findAll(withDev, withInstance, withNum, 0, 0);
	}

	@Override
	public List<? extends J2EEApp> findAll(boolean withDev,
			boolean withInstance, boolean withNum, Integer page, Integer size)
			throws Exception {
		List<? extends J2EEApp> apps = dao.findAll(page, size);
		for (J2EEApp app : apps) {
			fillUpJ2EEApp(app, withDev, withInstance, withNum);
		}
		return apps;
	}

	@Override
	public long countAll() throws Exception {
		return dao.countAll();
	}

	@Override
	public List<? extends J2EEApp> getByJ2EEInfoId(Integer j2eeInfoId,
			boolean withDev, boolean withInstance, boolean withNum)
			throws Exception {
		return getByJ2EEInfoId(j2eeInfoId, withDev, withInstance, withNum, 0, 0);
	}

	@Override
	public List<? extends J2EEApp> getByJ2EEInfoId(Integer j2eeInfoId,
			boolean withDev, boolean withInstance, boolean withNum,
			Integer page, Integer size) throws Exception {
		List<? extends J2EEApp> apps = dao.findByProperty("j2eeInfoId", j2eeInfoId, page, size);
		for (J2EEApp app : apps) {
			fillUpJ2EEApp(app, withDev, withInstance, withNum);
		}
		return apps;
	}

	@Override
	public long countByJ2EEInfoId(Integer j2eeInfoId) throws Exception {
		return dao.countByProperty("j2eeInfoId", j2eeInfoId);
	}

	@Override
	public List<? extends J2EEApp> searchAll(QueryObject<J2EEApp> queryObject,
			boolean withDev, boolean withInstance, boolean withNum)
			throws Exception {
		return searchAll(queryObject, withDev, withInstance, withNum, 0, 0);
	}

	@Override
	public List<? extends J2EEApp> searchAll(QueryObject<J2EEApp> queryObject,
			boolean withDev, boolean withInstance, boolean withNum,
			Integer page, Integer size) throws Exception {
		List<? extends J2EEApp> apps = dao.findByProperties(queryObject, page, size);
		for (J2EEApp app : apps) {
			fillUpJ2EEApp(app, withDev, withInstance, withNum);
		}
		return apps;
	}

	@Override
	public long countSearch(QueryObject<J2EEApp> queryObject) throws Exception {
		return dao.countByProperties(queryObject);
	}

	@Override
	public J2EEApp getByUuid(String uuid, boolean withDev,
			boolean withInstance, boolean withNum) throws Exception {
		List<? extends J2EEApp> apps = dao.findByProperty("uuid", uuid);
		if (apps.size() == 0) {
			return null;
		} else {
			J2EEApp app = apps.get(0);
			fillUpJ2EEApp(app, withDev, withInstance, withNum);
			return app;
		}
	}
	
	@Override
	public J2EEApp getById(Integer id, boolean withDev,
			boolean withInstance, boolean withNum) throws Exception {
		J2EEApp app = dao.findById(id);
		if (app != null) {
			fillUpJ2EEApp(app, withDev, withInstance, withNum);
		}
		return app;
	}

	@Override
	public Integer save(J2EEApp app) throws Exception {
		J2EEAppTable appTable = new J2EEAppTable(app);
		dao.save(appTable);
		return appTable.getId();
	}

	@Override
	public void update(J2EEApp app) throws Exception {
		dao.update(new J2EEAppTable(app));
	}

	@Override
	public void deleteByUuid(String uuid) throws Exception {
		dao.deleteByProperty("uuid", uuid);
	}
	
	@Override
	public void deleteById(Integer id) throws Exception {
		dao.deleteByPrimaryKey(id);
	}

	private void fillUpJ2EEApp(J2EEApp app, boolean withDev, boolean withInstance, boolean withNum) {
		if (withDev) {
			app.setDeveloper(new DeveloperDAO().findByPrimaryKey(app.getDevId()));
		}
		if (withInstance) {
			List<? extends Instance> instances = new InstanceDAO().findByProperty("appUuid", app.getUuid());
			app.setInstances((List<Instance>)instances);
		}
		if (withNum) {
			Integer instanceNum = (int) new InstanceDAO().countByProperty("appUuid", app.getUuid());
			app.setInstanceNum(instanceNum);
		}
	}
}
