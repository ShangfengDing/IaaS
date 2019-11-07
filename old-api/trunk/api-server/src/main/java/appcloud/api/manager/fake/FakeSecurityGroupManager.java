package appcloud.api.manager.fake;

import java.util.ArrayList;
import java.util.List;

import appcloud.api.beans.IpRange;
import appcloud.api.beans.Rule;
import appcloud.api.beans.SecurityGroup;
import appcloud.api.beans.securitygroup.SecurityGroupCreateReq;
import appcloud.api.beans.securitygroup.RuleCreateReq;
import appcloud.api.manager.SecurityGroupManager;

public class FakeSecurityGroupManager implements SecurityGroupManager {
	
	@Override
	public List<SecurityGroup> getList(String tenantId, boolean detailed) {
		List<SecurityGroup> groups = new ArrayList<SecurityGroup>();
		
		IpRange ipRange1 = new IpRange("10.108.120.0/8");
		//ipRange1.add("10.108.120.0/8");
		List<Rule> rules1 = new ArrayList<Rule>();
		rules1.add(new Rule(1, 1, 23, 24, "tcp", new SecurityGroup(), ipRange1));
		groups.add(new SecurityGroup(1, "group1", tenantId, rules1, "the first security group"));

		List<Rule> rules2 = new ArrayList<Rule>();
		IpRange ipRange2 = new IpRange("10.108.30.0/9");
		//ipRange2.add("10.108.30.0/9");
		rules2.add(new Rule(2, 2, 88, 89, "udp", null, ipRange2));

		IpRange ipRange3 = new IpRange("10.108.50.0/10");
		//ipRange3.add("10.108.50.0/10");
		rules2.add(new Rule(3, 2, 98, 99, "tcp", new SecurityGroup(), ipRange3));
		groups.add(new SecurityGroup(2, "group2", tenantId, rules2, "the second security group"));
		//groups.add(new SecurityGroup());
			return groups;
	}
	
	@Override
	public SecurityGroup create(String tenantId, SecurityGroupCreateReq cReq){
		SecurityGroup group = new SecurityGroup(1, cReq.name, tenantId, new ArrayList<Rule>(), cReq.description);
		//System.out.println("name=" + cReq.name + " description=" + cReq.description);
		return group;
	}
	
	@Override
	public void delete(String tenantId, Integer groupId){
		//TODO
		System.out.println("deleting " + tenantId + "'s group:" + groupId);
	}
	
	@Override
	public Rule createSecurityGroupRule(String tenantId, RuleCreateReq cReq) {
		return new Rule(1, cReq.parentGroupId, cReq.fromPort, cReq.toPort, cReq.ipProtocal, new SecurityGroup(), new IpRange(cReq.cidr));
	}
	
	@Override
	public void deleteSecurityGroupRule(String tenantId, Integer ruleId){
		System.out.println("deleting " + tenantId + "'s rule:" + ruleId);
	}
	
	@Override
	public SecurityGroup get(String tenantId, Integer groupId){

		List<Rule> rules = new ArrayList<Rule>();
		IpRange ipRange1 = new IpRange("10.108.30.0/9");
		//ipRange2.add("10.108.30.0/9");
		rules.add(new Rule(2, 2, 88, 89, "udp", null, ipRange1));
		IpRange ipRange2 = new IpRange("10.108.50.0/10");
		//ipRange3.add("10.108.50.0/10");
		rules.add(new Rule(3, 2, 98, 99, "tcp", new SecurityGroup(), ipRange2));
		return new SecurityGroup(2, "group2", tenantId, rules, "the second security group");
	}
	@Override
	public SecurityGroup updateSecurityGroup(String tenantId, Integer groupId,
			SecurityGroup securityGroup) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
