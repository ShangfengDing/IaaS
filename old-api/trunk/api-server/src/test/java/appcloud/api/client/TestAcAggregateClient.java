package appcloud.api.client;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import appcloud.api.beans.AcAggregate;
import appcloud.api.beans.AvailabilityZone;

public class TestAcAggregateClient extends JerseySpringTest {
	public TestAcAggregateClient() {
		super("appcloud.api.resources");
	}
	
	private AcAggregateClient client = new AcAggregateClient("http://127.0.0.1:9998/");
	
	@Test
	public void testGetList() {
		List<AcAggregate> aggregates = client.getAggregates();
		assertEquals(3, aggregates.size());
	}
	
	@Test
	public void testGet() {
		AcAggregate aggregate = client.get(12);
		assertEquals("hehe", aggregate.availabilityZoneName);
		assertEquals("12nnn", aggregate.name);
	}

	@Test
	public void testUpdate() {
		AcAggregate a = new AcAggregate();
		a.availabilityZoneName = "zonea";
		AcAggregate aggregate = client.updateAggregate(90, a);
		assertEquals(new Integer(90), aggregate.id);
		assertEquals("zonea", aggregate.availabilityZoneName);
	}
	
	@Test
	public void testCreate() {
		AcAggregate a = new AcAggregate();
		a.availabilityZoneName = "zoneb";
		a.name = "nameb";
		AcAggregate aggregate = client.createAggregate(a);
		assertEquals("zoneb", aggregate.availabilityZoneName);
		assertEquals("nameb", aggregate.name);
	}
	
	@Test
	public void testAddHost () {
		AcAggregate aggregate = client.addHost( 3, "hostAdded");
		assertEquals(new Integer(3), aggregate.id);
		/*
		assertEquals(4, aggregate.acHosts.size());
		assertEquals("hostAdded", aggregate.acHosts.get(3));
		*/
	}
	
	@Test
	public void testRemoveHost() {
		AcAggregate aggregate = client.removeHost(7, "host");
		assertEquals(new Integer(7), aggregate.id);
		
		//assertEquals(3, aggregate.acHosts.size());
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
	
	@Test
	public void testCreateZone() {
		AvailabilityZone zone = client.createAvailabilityZone("98765");
		Assert.assertEquals("98765", zone.name);
	}
	
}
