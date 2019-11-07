package appcloud.dbproxy.mysql;

import appcloud.common.model.VmSecurityGroup;
import appcloud.common.model.VmSecurityGroupRule;

public class TestMySQLVmSecurityGroup {
	
	void testGroup() throws Exception{
		MySQLVmSecurityGroupProxy groupProxy = new MySQLVmSecurityGroupProxy();
		VmSecurityGroup group = groupProxy.getById(1, false);
		System.out.println(group.toString());
		
		group = groupProxy.getByUuid("uuid2", true);
		System.out.println(group.toString());
	}
	
	void testRule() throws Exception{

		MySQLVmSecurityGroupRuleProxy ruleProxy = new MySQLVmSecurityGroupRuleProxy();
		VmSecurityGroupRule rule = ruleProxy.getById(3, false);
		System.out.println(rule.toString());
		
		rule = ruleProxy.getByUuid("uuid5", true);
		System.out.println(rule.toString());
	}
	
	public static void main(String[] args) {
		TestMySQLVmSecurityGroup testCase = new TestMySQLVmSecurityGroup();
		try {
			System.err.println("test group");
			testCase.testGroup();
			System.err.println("test rule");
			testCase.testRule();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
