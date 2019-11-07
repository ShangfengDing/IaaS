package appcloud.api.client;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import appcloud.api.beans.Aggregate;
import appcloud.api.beans.AvailabilityZone;

import com.sun.jersey.test.framework.JerseyTest;

public class TestAggregateClient extends JerseySpringTest {
	public TestAggregateClient() {
		super("appcloud.api.resources");
	}
	
	private AggregateClient client = new AggregateClient("http://127.0.0.1:9998/");
	
	
	@Test
	public void testGetList() {
		List<Aggregate> aggregates = client.getAggregates("xuan");
		assertEquals(3, aggregates.size());
	}
	
	@Test
	public void testGet() {
		Aggregate aggregate = client.get("xuan", 12);
		assertEquals("hehe", aggregate.availabilityZone);
		assertEquals("12nnn", aggregate.name);
	}

	@Test
	public void testUpdate() {
		Aggregate a = new Aggregate();
		a.availabilityZone = "zonea";
		Aggregate aggregate = client.updateAggregate("xuan", 90, a);
		assertEquals(new Integer(90), aggregate.id);
		assertEquals("zonea", aggregate.availabilityZone);
	}
	
	@Test
	public void testCreate() {
		Aggregate a = new Aggregate();
		a.availabilityZone = "zoneb";
		a.name = "nameb";
		Aggregate aggregate = client.createAggregate("xuan", a);
		assertEquals("zoneb", aggregate.availabilityZone);
		assertEquals("nameb", aggregate.name);
	}
	
	@Test
	public void testAddHost () {
		Aggregate aggregate = client.addHost("xuan", 3, "hostAdded");
		assertEquals(new Integer(3), aggregate.id);
		assertEquals(4, aggregate.hosts.size());
		assertEquals("hostAdded", aggregate.hosts.get(3));
	}
	
	@Test
	public void testRemoveHost() {
		Aggregate aggregate = client.removeHost("xuan", 7, "host");
		assertEquals(new Integer(7), aggregate.id);
		assertEquals(3, aggregate.hosts.size());
	}
	
	@Test
	public void testGetZones() {
		List<AvailabilityZone> zones = client.getZones();
		Assert.assertTrue(zones.size() > 0);
	}
	
	@Test
	public void testGetZoneById() {
		AvailabilityZone zone = client.getZoneById(23);
		Assert.assertEquals(Integer.valueOf(23), zone.id);
	}
}
