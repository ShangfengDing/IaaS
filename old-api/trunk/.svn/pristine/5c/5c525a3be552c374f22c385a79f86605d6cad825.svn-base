package appcloud.api.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import appcloud.api.beans.AcVlan;
import appcloud.api.manager.AcVlanManager;

@Path("{tenantId}/ac-vlan")
public class AcVlanResource {
	private AcVlanManager acVlanManager;

	public AcVlanManager getAcVlanManager() {
		return acVlanManager;
	}

	public void setAcVlanManager(AcVlanManager acVlanManager) {
		this.acVlanManager = acVlanManager;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<AcVlan> index(@PathParam("tenantId") String tenantId) throws Exception {
		List<AcVlan> hosts = acVlanManager.getList(tenantId);
		return hosts;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{vlanId}")
	public AcVlan show(@PathParam("tenantId") String tenantId, @PathParam("vlanId") Integer vlanId) 
			throws Exception {
		AcVlan host = acVlanManager.get(tenantId, vlanId);
		return host;
	}
}
