package appcloud.api.resources;

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

import appcloud.api.beans.EnterpriseInvitation;
import appcloud.api.manager.EnterpriseInvitationManage;
import appcloud.common.model.VmEnterpriseInvitation.VmEnterpriseInvitationStatus;

@Path("{adminId}/enterprise_invitation")
public class EnterpriseInvitationResource {
	private EnterpriseInvitationManage enterpriseInvitationManager;
	public EnterpriseInvitationManage getEnterpriseInvitationManager() throws Exception {
		return enterpriseInvitationManager;
	}

	public void setEnterpriseInvitationManager(EnterpriseInvitationManage enterpriseInvitationManager) throws Exception {
		this.enterpriseInvitationManager = enterpriseInvitationManager;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<EnterpriseInvitation> index(
			@PathParam("adminId") String adminId, 
			@DefaultValue("") @QueryParam("enterprise_id") String enterpriseId,
			@DefaultValue("") @QueryParam("user_id") String userId,
			@DefaultValue("") @QueryParam("status") String status) throws Exception {
		Integer enterpriseIdInt = null, userIdInt = null;
		VmEnterpriseInvitationStatus statusEnum = null;
		if(! enterpriseId.equals("")) {
			try{
				enterpriseIdInt = Integer.valueOf(enterpriseId);
			}catch(NumberFormatException e) {
				;
			}
		}
		if(! userId.equals("")) {
			try{
				userIdInt = Integer.valueOf(userId);
			}catch(NumberFormatException e) {
				;
			}
		}
		if(! status.equals("")) {
			for(VmEnterpriseInvitationStatus s : VmEnterpriseInvitationStatus.values()) {
				if(s.toString().equals(status))
					statusEnum = s;
			}
		}
		List<EnterpriseInvitation> enterpriseInvitationts = 
				enterpriseInvitationManager.searchByProperties(adminId, null, enterpriseIdInt, userIdInt, statusEnum, 0, 0);
		
		return enterpriseInvitationts;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public EnterpriseInvitation create(
			@PathParam("adminId") String adminId, EnterpriseInvitation enterpriseInvitation) throws Exception {
		return enterpriseInvitationManager.create(adminId, enterpriseInvitation);
	}
	
	@GET
	@Path("/{enterpriseInvitationId}")
	public EnterpriseInvitation getById(
			@PathParam("adminId") String adminId, 
			@PathParam("enterpriseInvitationId") Integer enterpriseInvitationId) throws Exception {
		Integer enterpriseInvitationIdInt = null;
		if(enterpriseInvitationId != null)
			enterpriseInvitationIdInt = Integer.valueOf(enterpriseInvitationId);
		return enterpriseInvitationManager.get(adminId, enterpriseInvitationIdInt);
	}
	
	@DELETE
	@Path("/{enterpriseInvitationId}")
	public void delete(
			@PathParam("adminId") String adminId, 
			@PathParam("enterpriseInvitationId") Integer enterpriseInvitationId) throws Exception {
		enterpriseInvitationManager.delete(adminId, enterpriseInvitationId);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{enterpriseInvitationId}")
	public EnterpriseInvitation show(
			@PathParam("adminId") String adminId, 
			@PathParam("enterpriseInvitationId") String enterpriseInvitationId) throws Exception {
		EnterpriseInvitation enterpriseInvitation = enterpriseInvitationManager.get(adminId, Integer.valueOf(enterpriseInvitationId));
		return enterpriseInvitation;
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{enterpriseInvitationId}")
	public EnterpriseInvitation update(
			@PathParam("adminId") String adminId, 
			@PathParam("enterpriseInvitationId") Integer enterpriseInvitationId, 
			EnterpriseInvitation enterpriseInvitation) throws Exception {
		return enterpriseInvitationManager.update(adminId, enterpriseInvitationId, enterpriseInvitation);
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_XML)
	public List<EnterpriseInvitation> getEnterpriseInvitationsByProperties(
			@PathParam("admin_id") String adminId, 
			@DefaultValue("") @QueryParam("id") String id, 
			@DefaultValue("") @QueryParam("enterprise_id") String enterpriseId,
			@DefaultValue("") @QueryParam("user_id") String userId,
			@DefaultValue("") @QueryParam("status") String status,
			@DefaultValue("") @QueryParam("page") String page, 
			@DefaultValue("") @QueryParam("size") String size) throws Exception{
		
		Integer idInt=null, enterpriseIdInt=null, userIdInt=null, pageInt=null, sizeInt=null;
		VmEnterpriseInvitationStatus statusEnum=null; 
		if(!id.equals("")) {
			idInt = Integer.valueOf(id);
		} 
		if(!enterpriseId.equals("")) {
			enterpriseIdInt = Integer.valueOf(enterpriseId);
		} 
		
		if(!userId.equals("")) {
			userIdInt = Integer.valueOf(userId);
		} 
		if(!status.equals("")) {
			for(VmEnterpriseInvitationStatus s : VmEnterpriseInvitationStatus.values()) {
				if(s.toString().equals(status)) {
					statusEnum = s;
					break;
				}
			}
		} 
		if(!page.equals("")) {
			pageInt = Integer.valueOf(page);
		} 
		if(!size.equals("")) {
			sizeInt = Integer.valueOf(size);
		} 
		
		return enterpriseInvitationManager.searchByProperties(
				adminId, idInt, enterpriseIdInt, userIdInt, statusEnum, pageInt, sizeInt);
				   
	}
	
	@GET
	@Path("/search/count")
	@Produces(MediaType.APPLICATION_XML)
	public String countInvitationByProperties(
			@PathParam("admin_id") String adminId, 
			@DefaultValue("") @QueryParam("id") String id, 
			@DefaultValue("") @QueryParam("enterprise_id") String enterpriseId,
			@DefaultValue("") @QueryParam("user_id") String userId,
			@DefaultValue("") @QueryParam("status") String status) throws Exception{
		
		Integer idInt=null, enterpriseIdInt=null, userIdInt=null;
		VmEnterpriseInvitationStatus statusEnum=null; 
		if(!id.equals("")) {
			idInt = Integer.valueOf(id);
		} 
		if(!enterpriseId.equals("")) {
			enterpriseIdInt = Integer.valueOf(enterpriseId);
		} 
		
		if(!userId.equals("")) {
			userIdInt = Integer.valueOf(userId);
		} 
		if(!status.equals("")) {
			for(VmEnterpriseInvitationStatus s : VmEnterpriseInvitationStatus.values()) {
				if(s.toString().equals(status)) {
					statusEnum = s;
					break;
				}
			}
		} 
	
		return enterpriseInvitationManager.countByProperties
				(adminId, idInt, enterpriseIdInt, userIdInt, statusEnum);
	}
}
