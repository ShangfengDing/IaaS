package appcloud.dbproxy.mysql.junit;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Assert;
import org.junit.Test;

import appcloud.common.model.VmZone;
import appcloud.dbproxy.mysql.MySQLVmZoneProxy;

public class MySQLVmZoneProxyTest {
	private static MySQLVmZoneProxy proxy;
	private static Random rnd = new Random();
	private Map<String, VmZone> zones;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		proxy = new MySQLVmZoneProxy();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	private VmZone genAZone() {
		return new VmZone(null, String.valueOf(rnd.nextInt(1000)),
				null, null, new Timestamp(System.currentTimeMillis()-100),
				new Timestamp(System.currentTimeMillis()));
	}

	@Before
	public void setUp() throws Exception {		
		zones = new HashMap<String, VmZone>();
	}

	@After
	public void tearDown() throws Exception {
		for (VmZone zone : zones.values()) {
			zone = proxy.getByName(zone.getName());
			proxy.deleteById(zone.getId());
		}
		
		zones.clear();
	}
	
	private void insertZones(int num) throws Exception {
		zones = new HashMap<String, VmZone>();
		
		VmZone zone = null;
		
		for (int i = 0; i < num; i++) {
			zone = genAZone();
			zones.put(zone.getName(), zone);
		}
		
		for (VmZone z : zones.values()) {
			proxy.save(z);
		}
	}
	
	private VmZone findZoneInResults(List<VmZone> results, String name) throws RuntimeException{
		for (VmZone zone : results) {
			if (zone.getName().equals(name)) {
				return zone;
			}
		}
		
		throw new RuntimeException("Not found");		
	}

	@Test
	public void testFindAll() {
		try {
			long before = proxy.countAll();
			insertZones(3);
			List<VmZone> results = (List<VmZone>) proxy.findAll();
			Assert.assertEquals("Size should be equal", before+zones.size(), results.size());
			
			for (VmZone zone : zones.values()) {
				Assert.assertNotNull("Should have id", 
						findZoneInResults(results, zone.getName()).getId());
			}
		} catch (Exception e) {
			fail("Shit Happens");
			e.printStackTrace();
		}
	}

	@Test
	public void testFindAllIntegerInteger() {
		//fail("Not yet implemented");
	}

	@Test
	public void testCountAll() {
		try {
			long before = proxy.countAll();
			int increase = rnd.nextInt(5) + 1;
			insertZones(increase);
			long after = proxy.countAll();
			
			Assert.assertEquals("Size should be equal", before+increase, after);
		} catch (Exception e) {
			fail("Shit Happens");
			e.printStackTrace();
		}
	}

	@Test
	public void testGetById() {
		try {
			VmZone zone = genAZone();
			zones.put(zone.getName(), zone);
			proxy.save(zone);
			
			VmZone tmpZone = proxy.getByName(zone.getName());
			Integer id = tmpZone.getId();
			Assert.assertNotNull("should have id", id);
			
			VmZone byIdZone = proxy.getById(id);
			
			Assert.assertEquals(id, byIdZone.getId());
			Assert.assertEquals(zone.getName(), byIdZone.getName());
			Assert.assertEquals(zone.getCreatedTime().getTime()/1000,
					byIdZone.getCreatedTime().getTime()/1000);			
		} catch (Exception e) {
			fail("Shit Happens");
			e.printStackTrace();
		}
	}

	@Test
	public void testGetByName() {
		try {
			VmZone zone = genAZone();
			zones.put(zone.getName(), zone);
			proxy.save(zone);
			
			VmZone byNameZone = proxy.getByName((zone.getName()));
			
			Assert.assertNotNull("should have id", byNameZone.getId());
			Assert.assertEquals(zone.getName(), byNameZone.getName());
			Assert.assertEquals(zone.getCreatedTime().getTime()/1000,
					byNameZone.getCreatedTime().getTime()/1000);			
		} catch (Exception e) {
			fail("Shit Happens");
			e.printStackTrace();
		}
	}

	@Test
	public void testSave() {
		try {
			VmZone zone = genAZone();
			zones.put(zone.getName(), zone);
			proxy.save(zone);
		} catch (Exception e) {
			fail("Shit Happens");
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdate() {
		try {
			VmZone zone = genAZone();
			zones.put(zone.getName(), zone);
			proxy.save(zone);
			
			zone = proxy.getByName(zone.getName());
			zones.remove(zone.getName());
			
			int id = zone.getId();
			
			// update name
			zone.setName("another");
			zone.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
			zones.put(zone.getName(), zone);
			
			proxy.update(zone);
			
			VmZone updated = proxy.getById(id);
			
			Assert.assertEquals(zone.getName(), updated.getName());
			Assert.assertEquals(zone.getUpdatedTime().getTime()/1000,
					updated.getUpdatedTime().getTime()/1000);
			Assert.assertEquals(zone.getCreatedTime().getTime()/1000,
					updated.getCreatedTime().getTime()/1000);
		} catch (Exception e) {
			fail("Shit Happens");
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteById() {
		try {
			VmZone zone = genAZone();
			zones.put(zone.getName(), zone);
			proxy.save(zone);
			
			zone = proxy.getByName(zone.getName());
			
			int id = zone.getId();
			String name = zone.getName();
			
			proxy.deleteById(id);
			
			zone = proxy.getById(id);
			
			Assert.assertNull("Should have been deleted", zone);
			// remove from map to TearDown
			zones.remove(name);
		} catch (Exception e) {
			fail("Shit Happens");
			e.printStackTrace();
		}
	}

}
