package appcloud.api.resources;

import java.sql.Timestamp;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import appcloud.api.beans.AcGroup;
import appcloud.api.beans.AcResourceStrategy;
import appcloud.api.manager.AcGroupManager;
import appcloud.common.model.ResourceStrategy.StrategyTypeEnum;

@Path("{adminId}/ac-groups")
public class AcGroupResource {
private AcGroupManager acGroupManager;
	
	public AcGroupManager getAcGroupManager() throws Exception {
		return acGroupManager;
	}

	public void setAcGroupManager(AcGroupManager groupManager) throws Exception {
		this.acGroupManager = groupManager;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<AcGroup> index(@PathParam("adminId") String adminId) throws Exception {
		return acGroupManager.getList(adminId);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{groupId}")
	public AcGroup show(@PathParam("adminId") String adminId, @PathParam("groupId") Integer groupId)
			throws Exception {
		return acGroupManager.get(adminId, groupId);
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_XML)
	public List<AcGroup> getByClusterId(@PathParam("adminId") String adminId,
			@DefaultValue("") @QueryParam("clusterId") String clusterId) throws Exception

	{
		return acGroupManager.getByClusterId(adminId, Integer.valueOf(clusterId));
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public AcGroup create(@PathParam("adminId") String adminId, AcGroup group) throws Exception {
		return acGroupManager.create(adminId, group);
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{groupId}")
	public AcGroup update(@PathParam("adminId") String adminId, @PathParam("groupId") Integer groupId, 
			AcGroup group) throws Exception {
		return acGroupManager.update(adminId, groupId, group);
	}
	
	@DELETE
	@Path("/{groupId}")
	public void delete(@PathParam("adminId") String adminId, @PathParam("groupId") Integer groupId) throws Exception {
		acGroupManager.delete(adminId, groupId);
	}
	
}
