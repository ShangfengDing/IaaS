package appcloud.nodeagent.dao;

import java.sql.Timestamp;

import appcloud.common.model.Host;
import appcloud.common.model.Service;
import appcloud.common.model.Service.ServiceStatus;
import appcloud.common.model.Service.ServiceTypeEnum;
import appcloud.common.proxy.ServiceProxy;
import appcloud.nodeagent.util.AppUtils;
import appcloud.nodeagent.util.ServiceUnit;

public class ServiceDAO {

	private Host host;
	private ServiceProxy serviceProxy;
	
	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public ServiceProxy getServiceProxy() {
		return serviceProxy;
	}

	public void setServiceProxy(ServiceProxy serviceProxy) {
		this.serviceProxy = serviceProxy;
	}

	public Service setStarted(Service service) {
		service = serviceProxy.getUniqueService(service.getHostIp(), service.getMonitorPort(), false);
		service.setServiceStatus(ServiceStatus.RUNNING);
		service.setLastStartTime(new Timestamp(System.currentTimeMillis()));
		service = serviceProxy.update(service);
		return service;
	}
	
	public Service setStoped(Service service) {
		service = serviceProxy.getUniqueService(service.getHostIp(), service.getMonitorPort(), false);
		service.setServiceStatus(ServiceStatus.STOPED);
		service.setLastStopTime(new Timestamp(System.currentTimeMillis()));
		service = serviceProxy.update(service);
		return service;
	}
	
	public Service getService(ServiceUnit su) {
		
		Service service = serviceProxy.getUniqueService(host.getIp(), su.getMonitorPort(), false);
		if (service == null) {
			service = new Service();
			service.setHostId(host.getId());
			service.setHostUuid(host.getUuid());
			service.setHostIp(host.getIp());
			service.setMonitorPort(su.getMonitorPort());
			service.setType(ServiceTypeEnum.toEnum(su.getName()));
			service.setZoneId(host.getAvailabilityZoneId());
			service.setClusterId(host.getClusterId());
			serviceProxy.save(service);
		}
		return service;
	}
}
