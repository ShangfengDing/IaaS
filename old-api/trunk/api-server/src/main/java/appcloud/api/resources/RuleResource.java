package appcloud.api.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import appcloud.api.beans.Rule;
import appcloud.api.beans.securitygroup.RuleCreateReq;
import appcloud.api.manager.SecurityGroupManager;

@Path("{tenantId}/os-security-group-rules")
public class RuleResource {
	private SecurityGroupManager securityGroupManager;
	
	public SecurityGroupManager getSecurityGroupManager() {
		return securityGroupManager;
	}

	public void setSecurityGroupManager(SecurityGroupManager securityGroupManager) {
		this.securityGroupManager = securityGroupManager;
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Rule create(@PathParam("tenantId") String tenantId,RuleCreateReq cReq) throws Exception{
		return securityGroupManager.createSecurityGroupRule(tenantId, cReq);
	}
	
	@DELETE
	@Path("/{securityGroupRuleId}")
	public void delete(@PathParam("tenantId") String tenantId, @PathParam("securityGroupRuleId") Integer ruleId) throws Exception{
		securityGroupManager.deleteSecurityGroupRule(tenantId, ruleId);
	}
}
