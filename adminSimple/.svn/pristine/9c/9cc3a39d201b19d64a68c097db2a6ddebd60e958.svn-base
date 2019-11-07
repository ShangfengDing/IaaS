package appcloud.admin.action.system;

import java.util.HashMap;
import java.util.List;

import com.appcloud.vm.fe.util.ClientFactory;

import appcloud.admin.action.base.BaseAction;

import appcloud.api.beans.AcService;
import appcloud.api.client.AcServiceClient;
import appcloud.api.enums.AcServiceTypeEnum;

public class HostServiceAction extends BaseAction{
	private String hostId;
	private String hostMem;
	private String hostIp;
	private Integer hostCPU;
	private Integer hostDisk;
	
	private HashMap<String, String> serviceStatusMap;
	private AcServiceClient serviceClient = ClientFactory.getServiceClient();
	private String NOT_USED = "NOT USED";
	
	public String execute() {
		System.out.println(hostId);
		serviceStatusMap = new HashMap<String, String>();
		AcServiceTypeEnum[] services = AcServiceTypeEnum.values();
		List<AcService> usedServices = serviceClient.getHostService(hostId);
		System.out.println("usedServices.size() = " + usedServices.size());
		for(AcService usedService : usedServices) {
			System.out.println("usedService.type.toString() = " + usedService.type.toString()
					+ ",usedService.serviceStatus.toString() = " + usedService.serviceStatus.toString());
			serviceStatusMap.put(usedService.type.toString(), usedService.serviceStatus.toString());
		}
		
		for(AcServiceTypeEnum service : services) {
			if(!serviceStatusMap.containsKey(service.toString())) {
				serviceStatusMap.put(service.toString(), NOT_USED);
			}
		}
		
		return SUCCESS;
	}
	
	public String getHostId() {
		return hostId;
	}
	
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getHostMem() {
		return hostMem;
	}

	public void setHostMem(String hostMem) {
		this.hostMem = hostMem;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	public Integer getHostCPU() {
		return hostCPU;
	}

	public void setHostCPU(Integer hostCPU) {
		this.hostCPU = hostCPU;
	}

	public Integer getHostDisk() {
		return hostDisk;
	}

	public void setHostDisk(Integer hostDisk) {
		this.hostDisk = hostDisk;
	}

	public HashMap<String, String> getServiceStatusMap() {
		return serviceStatusMap;
	}

	public void setServiceStatusMap(HashMap<String, String> serviceStatusMap) {
		this.serviceStatusMap = serviceStatusMap;
	}
}


