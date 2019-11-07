package appcloud.api.client;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import appcloud.api.beans.Snapshot;

public class TestSnapshotClient extends JerseySpringTest{

	public TestSnapshotClient () {
		super("appcloud.api.resources");
	}

	private SnapshotClient client = new SnapshotClient("http://127.0.0.1:9998/");
	

	@Test
	public void testGetListDetail() {
		List <Snapshot> snapshots = client.getSnapshots("xuan");
		assertTrue(snapshots.size() != 0);
		for(Snapshot snapshot : snapshots) {
			assertEquals("xuan", snapshot.tenantId);
			assertTrue(snapshot.createdAt != null);
		}
	}
	
	@Test
	public void testGet() {
		Snapshot snapshot = client.get("xuan", "daye");
		assertEquals("xuan", snapshot.tenantId);
		assertEquals("id_get", snapshot.id);
		assertEquals("name_get", snapshot.displayName);
	}
	
	@Test
	public void testCreate() {
		Snapshot snapshot = client.createSnapshot("xuan", new Snapshot());
		assertEquals("xuan", snapshot.tenantId);
		assertEquals("id_create", snapshot.id);
		assertEquals("name_create", snapshot.displayName);
	}
	
	@Test
	public void testDelete() {
		client.deleteSnapshot("xuan", "right");
	}
	
	@Test
	public void testUpdate() {
		Snapshot snapshot = client.updateSnapshot("110", "upID", "upName", "upDescription", null);
		assertEquals("110", snapshot.tenantId);
		assertEquals("upID", snapshot.id);
		assertEquals("new", snapshot.status);
	}
}
