package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.Host;
import appcloud.common.model.Instance;
import appcloud.common.model.Instance.InstanceTypeEnum;
import appcloud.common.model.Load;
import appcloud.common.proxy.InstanceProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.HostDAO;
import appcloud.dbproxy.mysql.dao.InstanceDAO;
import appcloud.dbproxy.mysql.dao.LoadDAO;
import appcloud.dbproxy.mysql.model.InstanceTable;

public class MySQLInstanceProxy implements InstanceProxy{
	private static InstanceDAO dao = new InstanceDAO(); 

	@Override
	public List<? extends Instance> findAll(boolean withApp, boolean withHost,
			boolean withLoad) throws Exception {
		return findAll(withApp, withHost, withLoad, 0, 0);
	}

	@Override
	public List<? extends Instance> findAll(boolean withApp, boolean withHost,
			boolean withLoad, Integer page, Integer size) throws Exception {
		List<? extends Instance> instances = dao.findAll(page, size);
		for (Instance instance : instances) {
			fillUpInstance(instance, withApp, withHost, withLoad);
		}
		return instances;
	}

	@Override
	public long countAll() throws Exception {
		return dao.countAll();
	}

	@Override
	public List<? extends Instance> getByType(InstanceTypeEnum type,
			boolean withApp, boolean withHost, boolean withLoad)
			throws Exception {
		return getByType(type, withApp, withHost, withLoad, 0, 0);
	}

	@Override
	public List<? extends Instance> getByType(InstanceTypeEnum type,
			boolean withApp, boolean withHost, boolean withLoad, Integer page,
			Integer size) throws Exception {
		List<? extends Instance> instances = dao.findByProperty("type", type, page, size);
		for (Instance instance : instances) {
			fillUpInstance(instance, withApp, withHost, withLoad);
		}
		return instances;
	}

	@Override
	public long countByType(InstanceTypeEnum type) throws Exception {
		return dao.countByProperty("type", type);
	}

	@Override
	public List<? extends Instance> searchAll(
			QueryObject<Instance> queryObject, boolean withApp,
			boolean withHost, boolean withLoad) throws Exception {
		return searchAll(queryObject, withApp, withHost, withLoad, 0, 0);
	}

	@Override
	public List<? extends Instance> searchAll(
			QueryObject<Instance> queryObject, boolean withApp,
			boolean withHost, boolean withLoad, Integer page, Integer size)
			throws Exception {
		List<? extends Instance> instances = dao.findByProperties(queryObject, page, size);
		for (Instance instance : instances) {
			fillUpInstance(instance, withApp, withHost, withLoad);
		}
		return instances;
	}

	@Override
	public long countSearch(QueryObject<Instance> queryObject)
			throws Exception {
		return dao.countByProperties(queryObject);
	}
	
	@Override
	public Instance getByUuid(String uuid, boolean withApp, boolean withHost,
			boolean withLoad) throws Exception {
		List<? extends Instance> instances = dao.findByProperty("uuid", uuid);
		if (instances.size() != 0) {
			Instance instance = instances.get(0);
			fillUpInstance(instance, withApp, withHost, withLoad);
			return instance;
		} else {
			return null;
		}
	}

	@Override
	public void save(Instance instance) throws Exception {
		dao.save(new InstanceTable(instance));
	}

	@Override
	public void update(Instance instance) throws Exception {
		dao.update(new InstanceTable(instance));
	}

	@Override
	public void deleteByUuid(String uuid) throws Exception {
		dao.deleteByProperty("uuid", uuid);
	}
	
	
	/**
	 * 填充instance的其他相关实体信息，用于返回前端显示
	 * @throws Exception 
	 */
	private void fillUpInstance(Instance instance, boolean withApp, boolean withHost, boolean withLoad){
		if (withApp) {
			if (instance.getType().equals(InstanceTypeEnum.J2EE)) {
				try {
					instance.setJ2eeApp(new MySQLJ2EEAppProxy().getByUuid(instance.getAppUuid(), true, false, false));
				} catch (Exception e) {
					instance.setJ2eeApp(null);
				}
			} else {
				try {
					instance.setVmApp(new MySQLVMAppProxy().getByUuid(instance.getAppUuid(),true, false));
				} catch (Exception e) {
					instance.setVmApp(null);
				}
			}
		}
		if (withHost) {
			List<? extends Host> hosts = new HostDAO().findByProperty("uuid", instance.getHostUuid());
			Host host = (hosts.size() == 0) ? null : hosts.get(0);
			instance.setHost(host);
		}
		if (withLoad) {
			Load load = new LoadDAO().getCurLoadByUuid(instance.getUuid());
			instance.setLoad(load);
		}
	}

}
