package appcloud.api.client;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.test.framework.JerseyTest;

import appcloud.api.beans.Load;
import appcloud.api.beans.Server;
import appcloud.api.beans.server.ServerActionReboot;
import appcloud.api.beans.server.ServerActionRebuild;
import appcloud.api.beans.server.ServerCreateReq;

public class TestServerClient extends JerseySpringTest {
	public TestServerClient() {
		super("appcloud.api.resources");
	}
	
	private ServerClient client = new ServerClient("http://127.0.0.1:9998/");
	
	@Test
	public void testDelete() {
		client.terminateServer("ddd", "bbb");
	}
	
	@Test
	public void testGet() {
		Server s = client.get("ddd", "bbb");
		//assertTrue(s.);
		assertEquals("bbb", s.id);
		assertTrue(s.status != null);
		assertEquals("default", s.securityGroup.name);
	}
	
	@Test
	public void testGetList() {
		List<Server> servers = client.getServers("hehe", false);
		assertTrue(servers.size() > 0);
		
		for (Server server : servers) {
			assertEquals("hehe", server.tenantId);
			assertEquals(null, server.addresses);
		}
	}
	
	@Test
	public void testGetListDetailed() {
		List<Server> servers = client.getServers("123", true);
		assertTrue(!servers.isEmpty());
		
		for(Server server : servers){
			assertTrue(server.metadata != null);
			assertEquals("123", server.tenantId);
			assertEquals("default", server.securityGroup.name);
		}
	}

	@Test
	public void testCreate(){
		Server server = client.createServer("123", new ServerCreateReq());
		
		assertTrue(server.metadata != null);
		assertEquals("123", server.tenantId);
	}
	
	@Test
	public void testOsStart(){
		client.osStart("123", "test");
	}
	
	@Test
	public void testOsStop(){
		client.osStop("123", "test");
	}

	@Test
	public void testResume(){
		client.resume("123", "test");
	}

	@Test
	public void testReboot(){
		client.reboot("123", "test", new ServerActionReboot("hard"));
	}

	@Test
	public void testRebuild(){
		ServerActionRebuild rebuild = new ServerActionRebuild();
		rebuild.imageRef = "newimage";
		
		client.rebuild("123", "abcd", rebuild);
	}

	@Test
	public void testResize(){
		client.resize("123", "abcd", "1");
	}

	@Test
	public void testSuspend(){
		client.suspend("123", "test");
	}

	@Test
	public void testForceDelete(){
		client.forceDelete("123", "test");
	}
	
	@Test
	public void testGetLoads() {
		Date eDate = new Date();
		Date sDate = new Date(eDate.getTime() - 1000*30*30);
		String uuid = "adabaadfa";
		//System.out.println("sdate:" + sDate);
		//System.out.println("edate:" + eDate);
		List<Load> loads = client.getLoads("123", uuid, "day", sDate, eDate);
		Assert.assertTrue(loads.size() > 0);
		for (Load load : loads) {
			//Assert.assertTrue(load.time.getTime() >= sDate.getTime());
			//System.out.println("back: " + load.time);
		}
	}
	
	@Test
	public void testChangeSecurityGroup() {
		client.changeSecurityGroup("1", "abcd", 32);
	}

	@Test 
	public void testGetMetadata() {
		try {
			Map <String, String> metas = client.getMetadata("110", "qunidaye");
			assertEquals("default", metas.get("securityGroupName"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateMetadata() {
		try {
			Map<String, String> metas = new HashMap<String, String>();
			metas.put("key1", "value1");
			Map <String, String> metasGot = client.updateMetadata("110", "qunidaye", metas);
			assertEquals("v1", metasGot.get("ori_meta_1"));
			assertEquals("value1", metasGot.get("key1"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}

	@Test
	public void testSetMetadata () {
		try {
			Map<String, String> metas = new HashMap<String, String>();
			metas.put("key1", "value1");
			Map <String, String> metasGot = client.setMetadata("110", "qunidaye", metas);
			assertEquals("value1", metasGot.get("key1"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
	
	@Test
	public void testGetMeta () {
		String value = client.getMetadataItem("110", "sever210", "key");
		assertEquals("value_getItem", value);
	}
	
	/*@Test
	public void testUpdateMeta() {
		String value = client.updateMetadataItem("110", "sever210", "keysand", "visot");
		assertEquals("visot", value);
	}*/
	
	@Test 
	public void testSetMeta() {
		String value = client.setMetadataItem("110", "sever210", "keywoieru", "dddddd");
		assertEquals("dddddd", value);
	}

	@Test
	public void testForceRefresh(){
		String status = client.forceRefresh("123", "test");
		assertEquals("OK", status);
	}
	
	
	@Test
	public void testSearch() {
		List<Server> servers = client.searchByProperties("110", "36", "", "name", "status",
				1, 2, null, null, new Date(), new Date(), 1, 1);
		assertEquals(2,servers.size());
	}
	
	@Test
	public void testCount() {
		Long c = client.countByProperties("110", "36", "", "name", "status",
				1, 2, null, null, new Date(), new Date());
		assertEquals(Long.valueOf(5),c);
	}
}
