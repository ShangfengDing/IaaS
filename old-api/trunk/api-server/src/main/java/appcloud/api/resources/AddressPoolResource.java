package appcloud.api.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.NotFoundException;

import appcloud.api.beans.AddressPool;
import appcloud.api.manager.AddressPoolManager;

@Path("{adminId}/ac-address_pools")
public class AddressPoolResource {
	private AddressPoolManager addressPoolManager;
	
	public AddressPoolManager getAddressPoolManager() {
		return addressPoolManager;
	}

	public void setAddressPoolManager(AddressPoolManager addressPoolManager) {
		this.addressPoolManager = addressPoolManager;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public AddressPool create(@PathParam("adminId") String adminId, AddressPool createReq) throws Exception {
		return addressPoolManager.create(adminId, createReq);
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<AddressPool> index(@PathParam("adminId") String adminId, 
			@DefaultValue("") @QueryParam("zone_id") String zoneIdStr, 
			@DefaultValue("") @QueryParam("aggregate_id") String aggregateIdStr) throws Exception {
		Integer zoneId = null;
		if(!zoneIdStr.equals("")){
			try {
				zoneId = Integer.valueOf(zoneIdStr);
			}catch (Exception e){
				;
			}
		}
		Integer aggregateId = null;
		if(!aggregateIdStr.equals("")) {
			try {
				aggregateId = Integer.valueOf(aggregateIdStr);
			}catch (Exception e) {
				;
			}
		}
		return addressPoolManager.getList(adminId, zoneId, aggregateId);
	}
	
	@GET
	@Path("/{poolId}")
	@Produces(MediaType.APPLICATION_XML)
	public AddressPool show(@PathParam("adminId") String adminId, @PathParam("poolId") Integer poolId) throws Exception {
		AddressPool pool = null;
		pool = addressPoolManager.get(adminId, poolId);
		
		if (pool != null) {
			return pool;
		} else {
			throw new NotFoundException(); 
		}
	}
	@DELETE
	@Path("/{poolId}")
	public void delete (@PathParam("adminId") String adminId, @PathParam("poolId") Integer poolId)
			throws Exception{
		addressPoolManager.delete(adminId, poolId);
	}
}