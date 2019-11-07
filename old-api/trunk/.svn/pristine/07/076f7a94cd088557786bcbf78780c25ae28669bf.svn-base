package appcloud.api.client;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import appcloud.api.beans.Rule;
import appcloud.api.beans.securitygroup.RuleCreateReq;

import com.sun.jersey.test.framework.JerseyTest;

public class TestRuleClient extends JerseySpringTest {
	public TestRuleClient() {
		super("appcloud.api.resources");
	}
	
	private RuleClient client = new RuleClient("http://127.0.0.1:9998/");
	
	@Test
	public void testCreate() {
		Rule rule = client.createRule("xuan", new RuleCreateReq("tcp", 12, 45, "10.10.10.10/12", "group1", 1));
		assertEquals("tcp", rule.ipProtocal);
		assertEquals("10.10.10.10/12", rule.ipRange.cidr);
	}
	
	@Test 
	public void testDelete() {
		client.deleteRule("xuan", 123);
	}
}
