package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.Instance;
import appcloud.common.model.VMApp;
import appcloud.common.proxy.VMAppProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.DeveloperDAO;
import appcloud.dbproxy.mysql.dao.InstanceDAO;
import appcloud.dbproxy.mysql.dao.VMAppDAO;

public class MySQLVMAppProxy implements VMAppProxy{

	private static VMAppDAO dao = new VMAppDAO();
	
	@Override
	public List<? extends VMApp> getByDevId(Integer devId, boolean withDev, boolean withInstance) throws Exception {
		List<? extends VMApp> vmApps = dao.findByProperty("devId", devId);
		for (VMApp vmApp : vmApps) {
			fillUpVMApp(vmApp, withDev, withInstance);
		}
		return vmApps;
	}
	
	@Override
	public long countByDevId(Integer devId) throws Exception {
		return dao.countByProperty("devId", devId);
	}
	
	@Override
	public VMApp getByUuid(String uuid, boolean withDev, boolean withInstance) throws Exception {
		List<? extends VMApp> vmApps = dao.findByProperty("uuid", uuid);
		if (vmApps.isEmpty()) {
			return null;
		} else {
			VMApp vmApp = vmApps.get(0);
			fillUpVMApp(vmApp, withDev, withInstance);
			return vmApp;	
		}	
	}
	
	@Override
	public void deleteByUuid(String uuid) throws Exception {
		dao.deleteByProperty("uuid", uuid);
	}

	@Override
	public List<? extends VMApp> searchAll(QueryObject<VMApp> queryObject,
			boolean withDev, boolean withInstance) throws Exception {
		List<? extends VMApp> vms = searchAll(queryObject,withDev,withInstance,0,0);
		return vms;
	}

	@Override
	public List<? extends VMApp> searchAll(QueryObject<VMApp> queryObject,
			boolean withDev, boolean withInstance, Integer page, Integer size)
			throws Exception {
		List<? extends VMApp> vms = dao.findByProperties(queryObject, page, size);
		for(VMApp vm:vms){
			fillUpVMApp(vm, withDev, withInstance);
		}
		return vms;
	}
	
	private void fillUpVMApp(VMApp app, boolean withDev, boolean withInstance) {
		if (withDev) {
			app.setDeveloper(new DeveloperDAO().findByPrimaryKey(app.getDevId()));
		}
		if (withInstance) {
			List<? extends Instance> instances = new InstanceDAO().findByProperty("appUuid", app.getUuid());
			Instance instance = (instances.isEmpty()) ? null : instances.get(0);
			app.setInstance(instance);
		}
	}
}