package appcloud.api.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.NotFoundException;

import appcloud.api.beans.AvailabilityZone;
import appcloud.api.manager.AcAggregateManager;

@Path("{adminId}/ac-availability_zones")
public class AvailabilityZoneResource {
	private AcAggregateManager acAggregateManager;
	
	public AcAggregateManager getAcAggregateManager() {
		return acAggregateManager;
	}

	public void setAcAggregateManager(AcAggregateManager acAggregateManager) {
		this.acAggregateManager = acAggregateManager;
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<AvailabilityZone> index(@PathParam("adminId") String adminId) throws Exception {
		return acAggregateManager.listAVZones(adminId);
	}
	
	@GET
	@Path("/{zoneId}")
	@Produces(MediaType.APPLICATION_XML)
	public AvailabilityZone show(@PathParam("adminId") String adminId, @PathParam("zoneId") Integer zoneId) throws Exception {
		AvailabilityZone zone = null;
		zone = acAggregateManager.getAvailabilityZoneById(adminId, zoneId);
		
		if (zone != null) {
			return zone;
		} else {
			throw new NotFoundException(); 
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_XML)
	public AvailabilityZone create(@PathParam("adminId") String adminId, AvailabilityZone zone) throws Exception {
		return acAggregateManager.createAvailabilityZone(adminId, zone);
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{zoneId}")
	public AvailabilityZone update(@PathParam("adminId") String adminId,@PathParam("zoneId") Integer zoneId, 
			AvailabilityZone zone) throws Exception {
		return acAggregateManager.updateAvailabilityZone(adminId, zoneId, zone);
	}
}