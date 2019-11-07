/**
 * File: MySQLClusterProxyTest.java
 * Author: weed
 * Create Time: 2013-4-17
 */
package appcloud.dbproxy.mysql.junit;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import appcloud.common.model.Cluster;
import appcloud.common.model.VmSnapshot;
import appcloud.common.model.VmSnapshot.VmSnapshotStatusEnum;
import appcloud.common.model.VmVolume;
import appcloud.common.util.UuidUtil;
import appcloud.dbproxy.mysql.MySQLClusterProxy;
import appcloud.dbproxy.mysql.MySQLVmSnapshotProxy;
import appcloud.dbproxy.mysql.MySQLVmVolumeProxy;

/**
 * @author weed
 *
 */
public class VmVolumeSnapshotProxyTest {
	static private Logger logger = Logger.getLogger(VmVolumeSnapshotProxyTest.class);
	static MySQLVmSnapshotProxy proxy = null;
	static String testName = "testVmVolume";
	String vuuid;
	String uuid;
	Integer zoneid = 1;
	Integer userId = 1;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		proxy = new MySQLVmSnapshotProxy();
		
	}
	
	@AfterClass
	public static void tearDownAfterClass(){
		
	}
	
	@Before
	public void setUp() throws Exception {
		vuuid = UuidUtil.getRandomUuid();
		uuid = UuidUtil.getRandomUuid();
	}
	
	private VmVolume newVolume() throws Exception{
		VmVolume v = new VmVolume();
		v.setVolumeUuid(vuuid);
		v.setName(testName);
		v.setAvailabilityZoneId(zoneid);
		v.setUserId(userId);
		return v;
	}
	
	private VmSnapshot newSnapshot(Integer volumeid) throws Exception{
		VmSnapshot v = new VmSnapshot();
		v.setCreatedTime(new Timestamp(System.currentTimeMillis()));
		v.setVolumeId(volumeid);
		v.setUuid(uuid);
		v.setName(testName);
		v.setStatus(VmSnapshotStatusEnum.DEFINED);
		v.setUserId(userId);
		return v;
	}
	
	@Test
	public void testSaveAndDelete() throws Exception {
		VmSnapshot v = newSnapshot(null);
		VmVolume vv = newVolume();
		MySQLVmVolumeProxy vvproxy = new MySQLVmVolumeProxy();
		vvproxy.save(vv);
		vv = vvproxy.getByUUID(vuuid, false, false, false, false);
		v.setVolumeId(vv.getId());
		
		proxy.save(v);
		v = proxy.getByUuid(uuid, true);
		assertNotNull(v);
		assertEquals(testName, v.getName());
		assertEquals(vv.getId(), v.getVolumeId());
		proxy.deleteById(v.getId());
		vvproxy.deleteById(vv.getId());

	}

// TODO
//	@Test
//	public void testFindAll() throws Exception {
//		
//		VmVolume v = newVolume();
//		proxy.save(v);
//		v = proxy.getByUUID(uuid, false, false, false, false);
//		List<? extends VmVolume> volumes = proxy.findAll(false, false, false,false);
//		assertTrue("findAll", volumes.size() != 0);
//		
//		boolean found = false; 
//		for (VmVolume vmVolume : volumes) {
//			logger.info(vmVolume.getVolumeUuid());
//			if(vmVolume.getVolumeUuid().equals(uuid)){
//				found = true;
//			}
//		}
//		assertTrue("findAll", found);
//		
//		v = proxy.getByUUID(uuid, false, false, false, false);
//		proxy.deleteById(v.getId());
//
//	}


	@Test
	public void testUpdate() throws Exception {
		VmSnapshot v = newSnapshot(null);
		VmVolume vv = newVolume();
		MySQLVmVolumeProxy vvproxy = new MySQLVmVolumeProxy();
		vvproxy.save(vv);
		vv = vvproxy.getByUUID(vuuid, false, false, false, false);
		v.setVolumeId(vv.getId());
		
		proxy.save(v);
		v = proxy.getByUuid(uuid, true);
		assertNotNull(v);
		assertEquals(testName, v.getName());
		String newName = "name";
		v.setName(newName);
		proxy.update(v);
		v = proxy.getByUuid(uuid, true);
		assertNotNull(v);
		assertEquals(newName, v.getName());
		
		proxy.deleteById(v.getId());
		vvproxy.deleteById(vv.getId());
	}


}
