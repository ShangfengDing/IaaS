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
import org.jboss.netty.handler.codec.replay.VoidEnum;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import appcloud.common.model.Cluster;
import appcloud.common.model.VmSnapshot.VmSnapshotStatusEnum;
import appcloud.common.model.VmVolume;
import appcloud.common.model.VmVolume.VmVolumeAttachStatusEnum;
import appcloud.common.model.VmVolume.VmVolumeStatusEnum;
import appcloud.common.model.VmVolume.VmVolumeTypeEnum;
import appcloud.common.model.VmVolume.VmVolumeUsageTypeEnum;
import appcloud.common.proxy.VmVolumeProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.UuidUtil;
import appcloud.dbproxy.mysql.MySQLClusterProxy;
import appcloud.dbproxy.mysql.MySQLVmVolumeProxy;

/**
 * @author weed
 *
 */
public class VmVolumeProxyTest {
	static private Logger logger = Logger.getLogger(VmVolumeProxyTest.class);
	static VmVolumeProxy proxy = null;
	static String testName = "testVmVolume";
	String uuid;
	Integer zoneid = 1;
	Integer userId = 1;
	String imageUuid = "ImageTestReserved1";
	String hostUuid = "HomeTestReserved1";
	String instanceUuid = "ReservedVmTest1";
	String providerLocation = "192.168.1.1:/srv/nfs4:images/a/z/uuid.img";
	String providerLocation1 = "192.168.1.1:/srv/nfs4:images/a/x/uuid.img";
	String backupUuid = "VolumeTestReserved1";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		proxy = new MySQLVmVolumeProxy();
//		proxy = (VmVolumeProxy) ConnectionFactory.getDBProxy(VmVolumeProxy.class);
	}
	
	@AfterClass
	public static void tearDownAfterClass(){
		
	}
	
	@Before
	public void setUp() throws Exception {
		uuid = UuidUtil.getRandomUuid();
		
	}
	
	private VmVolume newVolume() throws Exception{
		VmVolume v = new VmVolume();
		
		
		Timestamp t = new Timestamp(System.currentTimeMillis());
		v.setAttachedTime(t);
		v.setAttachStatus(VmVolumeAttachStatusEnum.DETACHED);
		v.setAvailabilityZoneId(zoneid);
		v.setAvailabilityClusterId(1);
		v.setBus("bus");
		v.setCreatedTime(t);
		v.setDeletedTime(t);
		
		v.setDisplayDescription(testName);
		v.setDisplayName(testName);
		v.setInstanceUuid(instanceUuid);
		v.setMountPoint("/dev/vda");
		v.setName(testName);
		v.setProviderLocation(providerLocation);
		v.setSize(20);
		v.setStatus(VmVolumeStatusEnum.DEFINED);
		v.setUpdatedTime(t);
		v.setUserId(userId);
		v.setVolumeType(VmVolumeTypeEnum.QCOW2);
		v.setUsageType(VmVolumeUsageTypeEnum.NETWORK);
		
		v.setImageUuid(imageUuid);
		v.setHostUuid(hostUuid);
		
		
		v.setBackupVolumeUuid(backupUuid);
		v.setVolumeUuid(uuid);
		
		return v;
	}
	
	@Test
	public void testSaveAndDelete() throws Exception {

		VmVolume v = newVolume();
		logger.info(v);
		proxy.save(v);

		v = proxy.getByUUID(uuid, false, false, false, false);
		assertEquals(testName, v.getName());
		assertEquals(zoneid, v.getAvailabilityZoneId());
		assertEquals(providerLocation, v.getProviderLocation());
		proxy.deleteById(v.getId());
		v = proxy.getByUUID(uuid, false, false, false, false);
		assertEquals(null, v);
	}
	
	@Test
	public void testFindAll() throws Exception {
		
		VmVolume v = newVolume();
		proxy.save(v);
		v = proxy.getByUUID(uuid, false, false, false, false);
		List<? extends VmVolume> volumes = proxy.findAll(false, false, false,false);
		assertTrue("findAll", volumes.size() != 0);
		
		boolean found = false; 
		for (VmVolume vmVolume : volumes) {
			logger.info(vmVolume.getVolumeUuid());
			if(vmVolume.getVolumeUuid().equals(uuid)){
				found = true;
			}
		}
		assertTrue("findAll", found);
		
		v = proxy.getByUUID(uuid, false, false, false, false);
		proxy.deleteById(v.getId());

	}

	@Test
	public void testCountAll() throws Exception {
		long count = proxy.countAll();
		VmVolume v = newVolume();
		proxy.save(v);
		long count1 = proxy.countAll();
		assertEquals(count+1, count1);
		Integer id = proxy.getByUUID(uuid, false, false, false, false).getId();
		proxy.deleteById(id);
	}

	@Test
	public void testFindAllBooleanBooleanBooleanIntegerInteger() throws Exception {
		VmVolume v = newVolume();
		proxy.save(v);
		v = proxy.getByUUID(uuid, false, false, false, false);
		
		List<? extends VmVolume> volumes = proxy.findAll(true, true, true,true);
		assertTrue("findAll", volumes.size() != 0);
		 
		for (VmVolume vmVolume : volumes) {
			if(vmVolume.getVolumeUuid().equals(uuid)){
				assertNotNull(vmVolume.getImage());
				assertNotNull(vmVolume.getId().toString(), vmVolume.getZone());
			}
		}

		v = proxy.getByUUID(uuid, false, false, false, false);
		proxy.deleteById(v.getId());
		
	}

	@Test
	public void testUpdate() throws Exception {
		VmVolume v = newVolume();
		proxy.save(v);
		v = proxy.getByUUID(uuid, false, false, false, false);
		assertEquals(providerLocation, v.getProviderLocation());
		logger.info(v);
		
		Timestamp time = v.getUpdatedTime();
		String newName = "hello";
		v.setName(newName);
		v.setProviderLocation(providerLocation1);
		proxy.update(v);
		v = proxy.getByUUID(uuid, false, false, false, false);
//		assertTrue("updated",v.getUpdatedTime().after(time));
		assertEquals(newName,v.getName());
		assertEquals(providerLocation1, v.getProviderLocation());
		proxy.deleteById(v.getId());
	}
	public static void main(String[] args) throws Exception {
		proxy = (VmVolumeProxy) ConnectionFactory.getDBProxy(VmVolumeProxy.class);
		VmVolume v = proxy.getById(28, false, false, false, false);
		System.out.println(v);
	}

}
