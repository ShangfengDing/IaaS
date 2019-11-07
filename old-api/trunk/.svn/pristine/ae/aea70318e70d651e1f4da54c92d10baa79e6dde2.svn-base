package appcloud.api.manager;

import java.util.List;

import appcloud.api.beans.AcService;
import appcloud.api.enums.AcServiceTypeEnum;

public interface AcServiceManager {

	
	public List<AcService> getAll(String tenantId) throws Exception;
	
	public List<AcService> getHostServices(String tenantId, String hostId) throws Exception;
	
	public void startService(String tenantId, String hostId, List<AcServiceTypeEnum> serviceTypes) throws Exception;
	
	public void stopService(String tenantId, String hostId, List<AcServiceTypeEnum> serviceTypes) throws Exception;
	
}
