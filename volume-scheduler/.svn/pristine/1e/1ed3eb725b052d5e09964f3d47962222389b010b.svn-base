package appcloud.vs;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.VmImage;
import appcloud.common.model.VmImageBack;
import appcloud.common.model.VmVolume;
import appcloud.common.model.VmVolume.VmVolumeStatusEnum;
import appcloud.common.model.VmVolume.VmVolumeUsageTypeEnum;
import appcloud.common.model.VmZone;
import appcloud.common.proxy.VmImageProxy;
import appcloud.common.proxy.VmVolumeProxy;
import appcloud.common.proxy.VmZoneProxy;
import appcloud.common.service.VolumeSchedulerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.vs.impl.DBUtil;
import org.apache.log4j.Logger;

import static org.junit.Assert.assertEquals;

public class VolumeSchedulerServerTest {
	private static Logger logger = Logger.getLogger(VolumeSchedulerServerTest.class);
	
	private static DBUtil dbUtil = DBUtil.getInstance();
	
	public void testCreate() throws Exception {
		Integer userid = 1;
		Integer size = 20;

//		List<? extends Cluster> clusters = ConnectionFactory.getClusterProxy().findAll(false, false, false);
//		Cluster cluster = clusters.get(0);
//		List<? extends VmZone> clusters = ConnectionFactory.getDbProxy(VmZoneP).findAll(false, false, false);
		VmZoneProxy vzp = (VmZoneProxy) ConnectionFactory.getDBProxy(VmZoneProxy.class);
		VmZone zone = vzp.findAll().get(0);
		
		VolumeSchedulerService vss = (VolumeSchedulerService) ConnectionFactory.getAMQPService(VolumeSchedulerService.class, RoutingKeys.VOLUME_SCHEDULER);
		VmImageProxy imageProxy = (VmImageProxy) ConnectionFactory.getDBProxy(VmImageProxy.class);
		VmVolumeProxy volumeProxy = (VmVolumeProxy) ConnectionFactory.getDBProxy(VmVolumeProxy.class);
		VmImage image = imageProxy.findAll().get(0);
		
		VmVolume volume = vss.defineVolume(VmVolumeUsageTypeEnum.SYSTEM,userid,size,zone,null,null,null);
		Integer id = volume.getId();
		assertEquals(VmVolumeStatusEnum.DEFINED, volume.getStatus());
		logger.info("defined ");
		
		volume = vss.createVolume(volume.getVolumeUuid(),null);
		logger.info("created ");
		
		volume = volumeProxy.getById(id, true, true, true, true);
		assertEquals(VmVolumeStatusEnum.AVAILABLE, volume.getStatus());
		
		volume = vss.deleteVolume(volume.getVolumeUuid(),null);
		logger.info("deleted");
		volume = volumeProxy.getById(id, true, true, true, true);
		assertEquals(VmVolumeStatusEnum.DELETED, volume.getStatus());
		
		volumeProxy.deleteById(id);
	}
	public static void main(String[] args) throws Exception {
//		VolumeSchedulerServerTest test = new VolumeSchedulerServerTest();
//		test.testCreate();
		VmImageBack imageBack = dbUtil.getImageBack("41165684d91245afaa6dc172bec89353",true);
		System.out.println(imageBack.isTop());

	}
}
