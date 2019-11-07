package appcloud.api.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import appcloud.api.beans.SecurityGroup;
import appcloud.api.beans.securitygroup.SecurityGroupCreateReq;

import com.sun.jersey.test.framework.JerseyTest;

public class TestSecurityGroupClient extends JerseySpringTest {
	
	public TestSecurityGroupClient() {
		super("appcloud.api.resources");
	}
	
	private SecurityGroupClient client = new SecurityGroupClient("http://127.0.0.1:9998/");
	
	@Test
	public void testGetList() {
		List<SecurityGroup> groups = client.getSecurityGroups("xuan");
		assertTrue(groups.size() != 0);
		for(SecurityGroup group : groups) {
			System.out.println(group.id);
			assertEquals("xuan", group.tenantId);
		}
	}
	
	@Test
	public void testGet() {
		SecurityGroup group = client.get("xuan", 12);
		assertEquals("xuan", group.tenantId);
		assertEquals("group2", group.name);
	}
	
	@Test
	public void testCreate() {
		SecurityGroupCreateReq cReq = new SecurityGroupCreateReq();
		cReq.name = "name!!!!!!";
		cReq.description = "des";
		SecurityGroup group = client.createSecurityGroup("xuan", cReq);
		assertEquals("name!!!!!!", group.name);
		assertEquals("des", group.description);
	}
	
	@Test
	public void testDelete() {
		client.deleteSecurityGroup("xuan", 23);
	}

	@Test 
	public void testUpate() {
		SecurityGroup group = client.updateSecurityGroup("110", 5, "daye", "description", null);
		assertEquals("110", group.tenantId);
		assertEquals("daye", group.name);
	}
}
