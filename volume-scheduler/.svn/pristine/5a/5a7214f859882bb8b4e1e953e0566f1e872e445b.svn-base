package appcloud.vs.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import appcloud.common.exception.IllegalRpcArgumentException;
import appcloud.common.model.Host;
import appcloud.common.model.VmImage;
import appcloud.common.model.VmVolume;
import appcloud.common.model.VmVolume.VmVolumeUsageTypeEnum;
import appcloud.common.model.VmZone;
import appcloud.common.proxy.HostProxy;
import appcloud.common.proxy.VmImageProxy;
import appcloud.common.proxy.VmVolumeProxy;
import appcloud.common.proxy.VmZoneProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.rpc.tools.RpcException;

public class VolumeSchedulerTest {
	Integer userid = 1;
	Integer size = 20;
	Integer imageId = 1;
	Integer zoneid = 1;
	String imageuuid = "abcd";
	VmZone zone = null;
	VmImage image = null;
	Host host = null;
	
	@Before
	public void setup() throws Exception{
		VmZoneProxy vzp = (VmZoneProxy) ConnectionFactory.getDBProxy(VmZoneProxy.class);
		zone = vzp.findAll().get(0);
		VmImageProxy vip = (VmImageProxy) ConnectionFactory.getDBProxy(VmImageProxy.class);
		image = vip.findAll().get(0);
		HostProxy hp = (HostProxy) ConnectionFactory.getDBProxy(HostProxy.class);
		host = hp.findAll(false,false,false).get(0);
	}
	
	@Test
	public void testDefine() throws Exception {
		VolumeScheduler vs = new VolumeScheduler();		
		VmVolume v1;
		v1 = vs.defineVolume(VmVolumeUsageTypeEnum.SYSTEM, userid, size, zone, null, null, null);
		VmVolume v2 = vs.defineVolume(VmVolumeUsageTypeEnum.DATA, userid, size, zone, image.getUuid(), host,null);
		VmVolumeProxy vvp = (VmVolumeProxy) ConnectionFactory.getDBProxy(VmVolumeProxy.class);
		vvp.deleteById(v1.getId());
		vvp.deleteById(v2.getId());
		
	}
	
	@Test
	public void testException() {
		VolumeScheduler vs = new VolumeScheduler();		
		try {
			VmVolume v1 = vs.defineVolume(VmVolumeUsageTypeEnum.SYSTEM, -1, -1, null, null, null, null);
		} catch (IllegalRpcArgumentException e) {
			e.printStackTrace();
		} catch (RpcException e) {
			assertTrue(false);
		}
	}
}
