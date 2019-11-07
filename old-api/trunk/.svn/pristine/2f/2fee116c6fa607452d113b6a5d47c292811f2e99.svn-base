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

import appcloud.api.beans.AcUser;
import appcloud.api.manager.AcUserManager;

@Path("{adminId}/ac-users")
public class AcUserResource {
private AcUserManager acUserManager;
	
	public AcUserManager getAcUserManager() {
		return acUserManager;
	}

	public void setAcUserManager(AcUserManager userManager) {
		this.acUserManager = userManager;
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<AcUser> index(@PathParam("adminId") String adminId) throws Exception {
		return acUserManager.getList(adminId);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{userId}")
	public AcUser show(@PathParam("adminId") String adminId, @PathParam("userId") String userId)
			throws Exception {
		return acUserManager.get(adminId, userId);
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/enterprise/{userId}")
	public AcUser showAccount(@PathParam("adminId") String adminId, @PathParam("userId") String userId)
			throws Exception {
		return acUserManager.getAccount(adminId, userId);
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public AcUser create(@PathParam("adminId") String adminId, AcUser user) throws Exception {
		return acUserManager.create(adminId, user);
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{userId}")
	public AcUser update(
			@PathParam("adminId") String adminId, 
			@PathParam("userId") String userId, 
			@DefaultValue("") @QueryParam("group_id") String groupIdStr, 
			@DefaultValue("") @QueryParam("enterprise_id") String enterpriseIdStr,
			AcUser user) throws Exception {
		System.out.println("userID:" + userId + "  user:" + user);
		if(!groupIdStr.equals("")){
			Integer groupId = null;
			
			try {
				groupId = Integer.valueOf(groupIdStr);
			}catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
			return acUserManager.addUserToGroup(adminId, userId, groupId);
		}
		
		if(!enterpriseIdStr.equals("")) {
			Integer enterpriseId = null;
			
			try {
				enterpriseId = Integer.valueOf(enterpriseIdStr);
			}catch(Exception ex) {
				ex.printStackTrace();
				return null;
			}
			return acUserManager.addUserToEnterprise(adminId, userId, enterpriseId);
		}
			
		else if(user != null) 
			return acUserManager.update(adminId, userId, user);
		return null;
	}
	
	@DELETE
	@Path("/{userId}")
	public void delete(@PathParam("adminId") String adminId, @PathParam("userId") String userId) throws Exception {
		acUserManager.delete(adminId, userId);
	}

	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/search")
	public List<AcUser> searchByProperties(@PathParam("adminId") String adminId, 
			@DefaultValue("") @QueryParam("group_id") String groupIdStr, 
			@DefaultValue("") @QueryParam("user_email") String userEmailStr, 
			@DefaultValue("") @QueryParam("is_active") String isActiveStr, 
			@DefaultValue("") @QueryParam("enterprise_id") String enterpriseIdStr,
			@DefaultValue("") @QueryParam("page") String pageStr,
			@DefaultValue("") @QueryParam("size") String sizeStr)
			throws Exception{
		Integer groupId = null;
		Boolean isActive = null;
		if(!"".equals(groupIdStr)) {
			try {
				groupId = Integer.valueOf(groupIdStr);
			}catch (Exception ex) {
				;
			}
		}
		if(userEmailStr.equals("")) {
			userEmailStr = null;
		}
		if(!"".equals(isActiveStr)) {
			try {
				isActive = Boolean.valueOf(isActiveStr);
			}catch (Exception ex) {
				;
			}
		}
		if("".equals(enterpriseIdStr)) {
			enterpriseIdStr = null;
		}
		
		Integer page = 0;
		if(!pageStr.equals("")){
			try {
				page = Integer.valueOf(pageStr);
			}catch (Exception e){
				;
			}
		}
		Integer size = 0;
		if(!sizeStr.equals("")) {
			try {
				size = Integer.valueOf(sizeStr);
			}catch (Exception e) {
				;
			}
		}
		//return acUserManager.searchByProperties(adminId, groupId, userEmailStr, isActive, enterpriseId, page, size);
		return acUserManager.searchByProperties(adminId, groupId, userEmailStr, isActive, enterpriseIdStr, page, size);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/search/count")
	public String countByProperties(@PathParam("adminId") String adminId, 
			@DefaultValue("") @QueryParam("group_id") String groupIdStr, 
			@DefaultValue("") @QueryParam("email") String email,
			@DefaultValue("") @QueryParam("is_active") String isActiveStr,
			@DefaultValue("") @QueryParam("enterprise_id") String enterpriseIdsStr)
			throws Exception{
		Integer groupId = null;
		Boolean isActive = null;
		if(!"".equals(groupIdStr)) {
			try {
				groupId = Integer.valueOf(groupIdStr);
			}catch (Exception ex) {
				;
			}
		}
		if(!"".equals(isActiveStr)) {
			try {
				isActive = Boolean.valueOf(isActiveStr);
			}catch (Exception ex) {
				;
			}
		}
		if("".equals(enterpriseIdsStr)) {
			enterpriseIdsStr = null;
		}
		//return acUserManager.countByProperties(adminId, groupId, email, isActive, enterpriseId).toString();
		return acUserManager.countByProperties(adminId, groupId, email, isActive, enterpriseIdsStr).toString();
	}
	
}
