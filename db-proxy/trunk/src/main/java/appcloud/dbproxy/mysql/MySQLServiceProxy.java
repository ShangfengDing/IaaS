package appcloud.dbproxy.mysql;

import java.util.Iterator;
import java.util.List;
import appcloud.common.model.Service;
import appcloud.common.model.Service.ServiceTypeEnum;
import appcloud.common.proxy.ServiceProxy;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.HostDAO;
import appcloud.dbproxy.mysql.dao.ServiceDAO;
import appcloud.dbproxy.mysql.model.ServiceTable;

public class MySQLServiceProxy implements ServiceProxy {

	private static final ServiceDAO serviceDao = new ServiceDAO();
	private static HostDAO hostDao = new HostDAO();
	
	@Override
	public void save(Service service) {
		ServiceTable table = new ServiceTable(service);
		serviceDao.save(table);
		service.setId(table.getId());
	}

	@Override
	public Service update(Service service) {
		return serviceDao.update(new ServiceTable(service));
	}
	
	@Override
	public void delete(Integer id) {
		serviceDao.deleteByPrimaryKey(id);
	}
	
	@Override
	public Service getById(Integer id, boolean withHost) {
		Service service = serviceDao.findById(id);
		if (service != null && withHost) {
			fillHost(service);
		}
		
		return service;
	}

	@Override
	public Service getUniqueService(String hostIp, Integer monitorPort, boolean withHost) {
		
		List<ServiceTable> list = serviceDao.findByProperty2("hostIp", hostIp, "monitorPort", monitorPort);
		if (list != null && !list.isEmpty()) {
			ServiceTable service = list.get(0);
			if (withHost) {
				fillHost(service);
			}
			return service;
		}
		
		return null;
	}

	@Override
	public List<? extends Service> getServiceByType(ServiceTypeEnum type, boolean withHost) {
		
		
		List<ServiceTable> list = serviceDao.findByProperty("type", type);
		
		//添加host信息
		if (list != null && !list.isEmpty() && withHost) {
			Iterator<ServiceTable> itr = list.iterator();
			while (itr.hasNext()) {
				fillHost(itr.next());
			}
		}
		
		return list;
	}
	
	@Override
	public List<? extends Service> getHostServicesById(Integer hostId) {
		return serviceDao.findByProperty("hostId", hostId);
	}

	@Override
	public List<? extends Service> getHostServicesByUuid(String hostUuid) {
		return serviceDao.findByProperty("hostUuid", hostUuid);
	}

	public List<? extends Service> findAll(boolean withHost) {
		List<ServiceTable> list = serviceDao.findAll();
		if (list != null && !list.isEmpty() && withHost) {
			Iterator<ServiceTable> itr = list.iterator();
			while (itr.hasNext()) {
				fillHost(itr.next());
			}
		}
		return list;
	}

	@Override
	public List<? extends Service> searchAll(QueryObject<Service> queryObject,
			boolean withHost) {
		List<ServiceTable> list =serviceDao.findByProperties(queryObject, 0, 0);
		
		//填充Host信息
		if (list != null && !list.isEmpty() && withHost) {
			Iterator<ServiceTable> itr = list.iterator();
			while (itr.hasNext()) {
				fillHost(itr.next());
			}
		}
		return list;
	}

	private void fillHost(Service service) {
		service.setHost(hostDao.findById(service.getHostId()));
	}

	@Override
	public Service getLatestHostService(Integer hostId) {
		return serviceDao.getLatestHostService(hostId);
	}



}
