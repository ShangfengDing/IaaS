package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.J2EEApp;
import appcloud.common.model.J2EEInfo;
import appcloud.common.model.RoutingEntry;
import appcloud.common.proxy.J2EEInfoProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.DeveloperDAO;
import appcloud.dbproxy.mysql.dao.J2EEInfoDAO;
import appcloud.dbproxy.mysql.dao.RoutingEntryDAO;
import appcloud.dbproxy.mysql.model.J2EEInfoTable;

public class MySQLJ2EEInfoProxy implements J2EEInfoProxy{
	private static J2EEInfoDAO dao = new J2EEInfoDAO();

	@Override
	public List<? extends J2EEInfo> getByDevId(Integer devId)
			throws Exception {
		return getByDevId(devId, 0, 0);
	}

	@Override
	public List<? extends J2EEInfo> getByDevId(Integer devId, Integer page,
			Integer size) throws Exception {
		List<? extends J2EEInfo> j2eeInfos = dao.findByProperty("devId", devId);
		return j2eeInfos;
	}

	@Override
	public long countByDevId(Integer devId) throws Exception {
		return dao.countByProperty("devId", devId);
	}

	@Override
	public J2EEInfo getById(Integer id) throws Exception {
		J2EEInfo j2eeInfo = dao.findById(id);
		return j2eeInfo;
	}

	@Override
	public Integer save(J2EEInfo j2eeInfo) throws Exception {
		J2EEInfoTable j2eeInfoTable = new J2EEInfoTable(j2eeInfo);
		dao.save(j2eeInfoTable);
		return j2eeInfoTable.getId();
	}

	@Override
	public void update(J2EEInfo j2eeInfo) throws Exception {
		dao.update(new J2EEInfoTable(j2eeInfo));
	}

	@Override
	public void deleteById(Integer j2eeInfoId) throws Exception {
		dao.deleteByPrimaryKey(j2eeInfoId);
	}
	
	//five functions added by XuanJiaxing
	@Override
	public List<? extends J2EEInfo> findAll(boolean withDev,
			boolean withRoutingEntry) throws Exception {
		return findAll(withDev, withRoutingEntry, 0, 0);
	}

	@Override
	public List<? extends J2EEInfo> findAll(boolean withDev,
			boolean withRoutingEntry, Integer page, Integer size)
			throws Exception {
		List<? extends J2EEInfo> apps = dao.findAll(page, size);
		for (J2EEInfo app : apps) {
			fillUpJ2EEInfo(app, withDev, withRoutingEntry);
		}
		return apps;
	}
	@Override
	public List<? extends J2EEInfo> searchAll(QueryObject<J2EEInfo> queryObject,
			boolean withDev, boolean withRoutingEntry)
			throws Exception {
		return searchAll(queryObject, withDev, withRoutingEntry, 0, 0);
	}

	@Override
	public List<? extends J2EEInfo> searchAll(QueryObject<J2EEInfo> queryObject,
			boolean withDev, boolean withRoutingEntry,
			Integer page, Integer size) throws Exception {
		List<? extends J2EEInfo> apps = dao.findByProperties(queryObject, page, size);
		for (J2EEInfo app : apps) {
			fillUpJ2EEInfo(app, withDev, withRoutingEntry);
		}
		return apps;
	}
	
	private void fillUpJ2EEInfo(J2EEInfo app, boolean withDev, boolean withRoutingEntry) {
		if(withRoutingEntry){

			List<? extends RoutingEntry> routingEntries = new RoutingEntryDAO().findByProperty("srcPrefix",  app.getDomainPrefix(), 0, 0);
			app.setRoutingEntries(routingEntries);
		}
		if (withDev) {
			app.setDeveloper(new DeveloperDAO().findByPrimaryKey(app.getDevId()));
		}
	}
	
	@Override
	public long countAll() throws Exception {
		return dao.countAll();
	}
	
	@Override
	public long countSearch(QueryObject<J2EEInfo> queryObject) throws Exception {
		return dao.countByProperties(queryObject);
	}
}

