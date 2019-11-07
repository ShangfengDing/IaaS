
package appcloud.api.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import appcloud.api.beans.Hypervisor;
import appcloud.api.manager.HypervisorManager;

@Path("{tenantId}/os-hypervisors")
public class HypervisorResource {
	private HypervisorManager hypervisorManager;
	
	public HypervisorManager getHypervisorManager() {
		return hypervisorManager;
	}

	public void setHypervisorManager(HypervisorManager hypervisorManager) {
		this.hypervisorManager = hypervisorManager;
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Hypervisor> index(@PathParam("tenantId") String tenantId) throws Exception{
		List<Hypervisor> hypervisor = hypervisorManager.getList(tenantId, false);
		return hypervisor;
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{HypervisorHostname}/servers")
	public Hypervisor show(@PathParam("tenantId") String tenantId,
			@PathParam("hypervisorName") String hypervisorName) throws Exception{
		
		return hypervisorManager.get(tenantId, hypervisorName);
	}
}