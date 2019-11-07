package appcloud.dbproxy.mysql;

import java.util.ArrayList;
import java.util.List;
import appcloud.common.model.Developer;
import appcloud.common.model.Instance;
import appcloud.common.model.J2EEApp;
import appcloud.common.model.J2EEInfo;
import appcloud.common.model.VMApp;
import appcloud.common.proxy.DeveloperProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.DeveloperDAO;
import appcloud.dbproxy.mysql.dao.InstanceDAO;
import appcloud.dbproxy.mysql.dao.J2EEAppDAO;
import appcloud.dbproxy.mysql.dao.J2EEInfoDAO;
import appcloud.dbproxy.mysql.dao.VMAppDAO;
import appcloud.dbproxy.mysql.model.DeveloperTable;


public class MySQLDeveloperProxy implements DeveloperProxy {
	private static DeveloperDAO dao = new DeveloperDAO(); 

	@Override
	public List<? extends Developer> findAll(boolean withNum) throws Exception {
		return findAll(withNum, 0, 0);
	}

	@Override
	public List<? extends Developer> findAll(boolean withNum, Integer page,
			Integer size) throws Exception {
		List<? extends Developer> developers = new ArrayList<Developer>();
		developers = dao.findAll(page,size);
		for(Developer developer:developers){
			fillUpDeveloper(developer,withNum);
		}
		return developers;
	}

	public Developer getById(Integer id, boolean withNum) {
		Developer developer = dao.findById(id);
		if (developer != null) {
			fillUpDeveloper(developer, withNum);
		}
		return developer;
	}
	
	@Override
	public long countAll() throws Exception {
		return dao.countAll();
	}

	@Override
	public List<? extends Developer> searchAll(
			QueryObject<Developer> queryObject, boolean withNum)
			throws Exception {
		List<? extends Developer> developers = searchAll(queryObject,withNum,0,0);
		return developers;
	}

	@Override
	public List<? extends Developer> searchAll(
			QueryObject<Developer> queryObject, boolean withNum, Integer page,
			Integer size) throws Exception {
		List<? extends Developer> developers = dao.findByProperties(queryObject, page, size);
		for (Developer developer : developers) {
			fillUpDeveloper(developer, withNum);
		}
		return developers;
	}

	@Override
	public long countSearch(QueryObject<Developer> queryObject)
			throws Exception {
		return dao.countByProperties(queryObject);
	}

	@Override
	public void save(Developer dev) throws Exception {
		dao.save(new DeveloperTable(dev));
	}
	
	/**
	 * 填充developer的其他相关信息，用户返回前端显示
	 * @param developer
	 * @param withAppNum
	 */
	private void fillUpDeveloper(Developer developer, boolean withNum) {
		
		if (withNum) {
			List<? extends J2EEInfo> info = new J2EEInfoDAO().findByProperty(
					"devId", developer.getId());
					
			List<? extends J2EEApp> apps = new J2EEAppDAO().findByProperty(
					"devId", developer.getId());
			List<? extends VMApp> vms = new VMAppDAO().findByProperty("devId", developer.getId());
			
			int instanceSize = 0;
			List<? extends Instance> instances;
			for(J2EEApp app:apps){
				instances = new InstanceDAO().findByProperty("appUuid", app.getUuid());
				if(instances != null){
					instanceSize += instances.size();
				}
			}
			developer.setJ2eeInfoNum(info.size());
			developer.setJ2eeAppNum(apps.size());
			developer.setVmNum(vms.size());
			developer.setJ2eeInstanceNum(instanceSize);			
		}		
	}
}
