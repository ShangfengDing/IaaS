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

import appcloud.api.beans.Enterprise;
import appcloud.api.manager.EnterpriseManager;

@Path("{adminId}/enterprises")
public class EnterpriseResource {
	private EnterpriseManager enterpriseManager;
	public EnterpriseManager getEnterpriseManager() throws Exception {
		return enterpriseManager;
	}

	public void setEnterpriseManager(EnterpriseManager enterpriseManager) throws Exception {
		this.enterpriseManager = enterpriseManager;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Enterprise> index(@PathParam("adminId") String adminId) throws Exception {
		return enterpriseManager.getList(adminId);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{enterpriseId}")
	public Enterprise show(
			@PathParam("adminId") String adminId, 
			@PathParam("enterpriseId") Integer enterpriseId) throws Exception {
		return enterpriseManager.get(adminId, enterpriseId);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Enterprise create(@PathParam("adminId") String adminId, Enterprise enterprise) throws Exception {
		return enterpriseManager.create(adminId, enterprise);
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{enterpriseId}")
	public Enterprise update(@PathParam("adminId") String adminId, @PathParam("enterpriseId") Integer enterpriseId, 
			Enterprise enterprise) throws Exception {
		return enterpriseManager.update(adminId, enterpriseId, enterprise);
	}
	
	@DELETE
	@Path("/{enterpriseId}")
	public void delete(@PathParam("adminId") String adminId, @PathParam("enterpriseId") Integer enterpriseId) throws Exception {
		enterpriseManager.delete(adminId, enterpriseId);
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_XML)
	public List<Enterprise> getEnterpriseByProperties(
			@PathParam("admin_id") String adminId, 
			@DefaultValue("") @QueryParam("id") String id, 
			@DefaultValue("") @QueryParam("owner_id") String ownerId,
			@DefaultValue("") @QueryParam("is_active") String isActive,
			@DefaultValue("") @QueryParam("name") String name,
			@DefaultValue("") @QueryParam("description") String description,
			@DefaultValue("") @QueryParam("phone") String phone, 
			@DefaultValue("") @QueryParam("email") String email,
			@DefaultValue("") @QueryParam("address") String address,
			@DefaultValue("") @QueryParam("postcode") String postcode,
			@DefaultValue("") @QueryParam("homepage") String homepage,
			@DefaultValue("") @QueryParam("parentCompany") String parentCompany,
			@DefaultValue("") @QueryParam("page") String page,
			@DefaultValue("") @QueryParam("size") String size) throws Exception{
		
		Integer idInt=null, ownerIdInt=null, parentCompanyInt = null, pageInt=null, sizeInt=null;
		Boolean isActiveBool = null; 
		if(!id.equals("")) {
			idInt = Integer.valueOf(id);
		} 
		if(!ownerId.equals("")) {
			ownerIdInt = Integer.valueOf(ownerId);
		} 
		if(isActive != null && !isActive.equals("")) {
			if(isActive.equals(Boolean.TRUE.toString()))
				isActiveBool = Boolean.TRUE;
			else
				isActiveBool = Boolean.FALSE;
		}
			
		if(!parentCompany.equals("")) {
			parentCompanyInt = Integer.valueOf(parentCompany);
		} 
		if(!page.equals("")) {
			pageInt = Integer.valueOf(page);
		}
		if(!size.equals("")) {
			sizeInt = Integer.valueOf(size);
		} 
		
		return enterpriseManager.searchByProperties(adminId, idInt, ownerIdInt, 
				isActiveBool, name, description, phone, email, address, postcode,
				homepage, parentCompanyInt, pageInt, sizeInt);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/search/count")
	public String countEnterpriseByProperties(
			@PathParam("admin_id") String adminId, 
			@DefaultValue("") @QueryParam("id") String id, 
			@DefaultValue("") @QueryParam("owner_id") String ownerId,
			@DefaultValue("") @QueryParam("is_active") String isActive,
			@DefaultValue("") @QueryParam("name") String name,
			@DefaultValue("") @QueryParam("description") String description,
			@DefaultValue("") @QueryParam("phone") String phone, 
			@DefaultValue("") @QueryParam("email") String email,
			@DefaultValue("") @QueryParam("address") String address,
			@DefaultValue("") @QueryParam("postcode") String postcode,
			@DefaultValue("") @QueryParam("homepage") String homepage,
			@DefaultValue("") @QueryParam("parentCompany") String parentCompany) throws Exception{
		
		Integer idInt=null, ownerIdInt=null, parentCompanyInt = null;
		Boolean isActiveBool = null; 
		if(!id.equals("")) {
			idInt = Integer.valueOf(id);
		} 
		if(!ownerId.equals("")) {
			ownerIdInt = Integer.valueOf(ownerId);
		} 
		if(isActive !=null && !isActive.equals("")) {
			if(isActive.equals(Boolean.TRUE.toString()))
				isActiveBool = Boolean.TRUE;
			else
				isActiveBool = Boolean.FALSE;
		}
			
		if(!parentCompany.equals("")) {
			parentCompanyInt = Integer.valueOf(parentCompany);
		} 
		
		return enterpriseManager.countByProperties(adminId, idInt, ownerIdInt, 
				isActiveBool, name, description, phone, email, address, postcode,
				homepage, parentCompanyInt);
				   
	}
	
}
