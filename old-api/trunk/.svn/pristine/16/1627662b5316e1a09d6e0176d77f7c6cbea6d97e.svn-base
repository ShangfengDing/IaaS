package appcloud.api.manager.fake;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import appcloud.api.beans.AcService;
import appcloud.api.enums.AcServiceStatusEnum;
import appcloud.api.enums.AcServiceTypeEnum;
import appcloud.api.manager.AcServiceManager;

public class FakeAcServiceManager implements AcServiceManager {

	@Override
	public List<AcService> getAll(String tenantId) throws Exception {
		System.out.println("fake getall");
		List<AcService> allServices = new ArrayList<AcService>();
		allServices.add(new AcService(1, 1, "host1", 1, "192.168.1.1", 8888,
				AcServiceTypeEnum.IMAGE_SERVER, new Date(), new Date(), new Date(), 
				AcServiceStatusEnum.INIT));
		allServices.add(new AcService(2, 2, "host2", 1, "192.168.1.2", 9999,
				AcServiceTypeEnum.DHCP_CONTROLLER, new Date(), new Date(), new Date(), 
				AcServiceStatusEnum.INIT));
		allServices.add(new AcService(3, 3, "host3", 1, "192.168.1.3", 7777,
				AcServiceTypeEnum.NODE_MONITOR, new Date(), new Date(), new Date(), 
				AcServiceStatusEnum.INIT));
		return allServices;
	}

	@Override
	public List<AcService> getHostServices(String tenantId, String hostId)
			throws Exception {
		List<AcService> hostServices = new ArrayList<AcService>();
		hostServices.add(new AcService(1, 1, hostId, 1, "192.168.1.1", 5555,
				AcServiceTypeEnum.RESOURCE_SCHEDULER, new Date(), new Date(), new Date(), 
				AcServiceStatusEnum.INIT));
		hostServices.add(new AcService(2, 1, hostId, 1, "192.168.1.2", 6666,
				AcServiceTypeEnum.VOLUME_PROVIDER, new Date(), new Date(), new Date(), 
				AcServiceStatusEnum.INIT));
		return hostServices;
	}

	@Override
	public void startService(String tenantId, String hostId,
			List<AcServiceTypeEnum> serviceTypes) throws Exception {
		System.out.println("starting " + tenantId + "'s host " + hostId + "'s service");
		for(AcServiceTypeEnum type : serviceTypes)
			System.out.print(type + " ");
		System.out.println();
	}

	@Override
	public void stopService(String tenantId, String hostId,
			List<AcServiceTypeEnum> serviceTypes) throws Exception {
		System.out.println("stoping " + tenantId + "'s host " + hostId + "'s service");
		for(AcServiceTypeEnum type : serviceTypes)
			System.out.print(type + " ");
		System.out.println();
	}

}
