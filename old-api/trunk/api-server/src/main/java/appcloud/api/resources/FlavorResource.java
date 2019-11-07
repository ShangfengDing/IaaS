package appcloud.api.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import appcloud.api.beans.Flavor;
import appcloud.api.manager.FlavorManager;

@Path("{tenantId}/flavors")
public class FlavorResource {
	private FlavorManager flavorManager;
	
	public FlavorManager getFlavorManager() {
		return flavorManager;
	}

	public void setFlavorManager(FlavorManager flavorManager) {
		this.flavorManager = flavorManager;
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Flavor> index(@PathParam("tenantId") String tenantId) throws Exception {
		return flavorManager.getList(tenantId, false);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/detail")
	public List<Flavor> detail(@PathParam("tenantId") String tenantId) throws Exception {
		return flavorManager.getList(tenantId, true);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Flavor create(@PathParam("tenantId") String tenantId, Flavor flavor) throws Exception {
		return flavorManager.create(tenantId, flavor);
	}
	
	@GET
	@Path("/{flavorId}")
	@Produces(MediaType.APPLICATION_XML)
	public Flavor get(@PathParam("tenantId") String tenantId, @PathParam("flavorId") Integer flavorId) throws Exception {
		return flavorManager.get(tenantId, flavorId);
	}
	
}
