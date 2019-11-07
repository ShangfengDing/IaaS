package appcloud.api.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import appcloud.api.beans.SecurityGroup;
import appcloud.api.beans.securitygroup.SecurityGroupCreateReq;
import appcloud.api.manager.SecurityGroupManager;

@Path("{tenantId}/os-security-group")
public class SecurityGroupResource {
	private SecurityGroupManager securityGroupManager;
	
	public SecurityGroupManager getSecurityGroupManager() {
		return securityGroupManager;
	}

	public void setSecurityGroupManager(SecurityGroupManager securityGroupManager) {
		this.securityGroupManager = securityGroupManager;
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<SecurityGroup> index(@PathParam("tenantId") String tenantId) throws Exception {
		List<SecurityGroup> securityGroups = securityGroupManager.getList(tenantId, true);
		return securityGroups;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{securityGroupId}")
	public SecurityGroup show(@PathParam("tenantId") String tenantId, @PathParam("securityGroupId") Integer groupId) throws Exception {
		return securityGroupManager.get(tenantId, groupId);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public SecurityGroup create(@PathParam("tenantId") String tenantId, SecurityGroupCreateReq cReq) throws Exception {
		return securityGroupManager.create(tenantId, cReq);
	}
	
	@DELETE
	@Path("/{securityGroupId}")
	public void delete(@PathParam("tenantId") String tenantId, @PathParam("securityGroupId") Integer groupId) throws Exception{
		securityGroupManager.delete(tenantId, groupId);
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{securityGroupId}")
	public SecurityGroup update(
			@PathParam("tenantId") String tenantId, 
			@PathParam("securityGroupId") Integer groupId, 
			SecurityGroup securityGroup) throws Exception {
		return securityGroupManager.updateSecurityGroup(tenantId, groupId, securityGroup);
	}
}
