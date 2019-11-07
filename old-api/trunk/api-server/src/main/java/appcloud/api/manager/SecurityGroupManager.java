package appcloud.api.manager;

import java.util.List;

import appcloud.api.beans.Rule;
import appcloud.api.beans.SecurityGroup;
import appcloud.api.beans.securitygroup.SecurityGroupCreateReq;
import appcloud.api.beans.securitygroup.RuleCreateReq;

public interface SecurityGroupManager {
	public List<SecurityGroup> getList(String tenantId, boolean detailed) throws Exception;
	
	public SecurityGroup get(String tenantId, Integer groupId) throws Exception;
	
	public SecurityGroup create(String tenantId, SecurityGroupCreateReq cReq) throws Exception;
	
	public void delete(String tenantId, Integer groupId) throws Exception;
	
	public Rule createSecurityGroupRule(String tenantId, RuleCreateReq cReq) throws Exception;
	
	public void deleteSecurityGroupRule(String tenantId, Integer ruleId) throws Exception;
	
	public SecurityGroup updateSecurityGroup(String tenantId, Integer groupId, SecurityGroup securityGroup)
			throws Exception;
	
}
