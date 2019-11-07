package appcloud.api.manager;

import java.util.List;

import appcloud.api.beans.Hypervisor;

public interface HypervisorManager {

	public List<Hypervisor> getList(String tenantId, boolean detailed) throws Exception;
	
	public Hypervisor get(String tenantId, String hypervisorName) throws Exception;
}
