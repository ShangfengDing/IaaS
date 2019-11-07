/**
 * File: VmDiskManager.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.resourcescheduler.vmdisk;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import appcloud.common.model.*;
import appcloud.common.proxy.VmImageBackProxy;
import appcloud.common.proxy.VmImageProxy;
import org.apache.log4j.Logger;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.VmVolume.VmVolumeUsageTypeEnum;
import appcloud.common.proxy.ClusterProxy;
import appcloud.common.proxy.VmVolumeProxy;
import appcloud.common.service.VolumeSchedulerService;
import appcloud.common.transaction.ContextHandler;
import appcloud.common.util.LolLogUtil;
import appcloud.resourcescheduler.common.ConnectionManager;
import appcloud.resourcescheduler.transaction.rollback.resource.VmVolumeResource;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public class VmDiskManager {
	private static VmDiskManager instance = new VmDiskManager();
	
	private static Logger logger = Logger
			.getLogger(VmDiskManager.class);
	VolumeSchedulerService volumeScheduler = (VolumeSchedulerService) ConnectionManager.getInstance().getAMQPService(VolumeSchedulerService.class, RoutingKeys.VOLUME_SCHEDULER);
	
	private static VmVolumeProxy vmVolumeProxy = (VmVolumeProxy) ConnectionManager.getInstance()
			.getDBProxy(VmVolumeProxy.class);
	private static ClusterProxy clusterProxy = (ClusterProxy) ConnectionManager.getInstance().getDBProxy(ClusterProxy.class);


	public List<VmVolume> createDisk(String vmUuid, Integer userId,  String imageUUID, VmZone zone, Host vmHost, Integer localGb, Integer imageSizeGb, ContextHandler context, RpcExtra rpcExtra)
	throws RpcException{
		logger.info("localGb: " + localGb + ", imageSizeGb: " + imageSizeGb + ", vmHost: " + vmHost + ", imageUUID: " + imageUUID);
		
		Integer sum = Math.max(localGb, imageSizeGb);
		logger.info("sum: "+sum);
		Host volumeHost = null;
		List<Host> volumeHosts = null;
		try {
			Cluster cluster = clusterProxy.getById(vmHost.getClusterId(), false, false, false);
			logger.info(cluster);
			volumeHosts = volumeScheduler.selectHost(cluster, sum, vmHost, rpcExtra);
			if (volumeHosts == null || volumeHosts.size() == 0) {
				logger.error("volume select host fail");
				throw new RpcException("volume select host fail");
			}
			volumeHost = volumeHosts.get(0);

			logger.info("volumeHost: " + volumeHost);
			List<VmVolume> volumes = new LinkedList<VmVolume>();
			VmVolume tmpVolume0 = volumeScheduler.defineVolume(VmVolumeUsageTypeEnum.SYSTEM, userId, imageSizeGb, zone, imageUUID, volumeHost, rpcExtra);
			logger.info("System volume uuid: "+tmpVolume0.getVolumeUuid()+" System volume:" + tmpVolume0);
//            Thread.sleep(10000);
			tmpVolume0 = volumeScheduler.createVolume(tmpVolume0.getVolumeUuid(), rpcExtra);
			logger.info("System create success");
//			Thread.sleep(10000);
//            VmVolume tmpVolume1 = volumeScheduler.defineVolume(VmVolumeUsageTypeEnum.NETWORK, userId, sum-imageSizeGb, zone, null, volumeHost, rpcExtra);
//            logger.info("Data volume uuid: "+tmpVolume1.getVolumeUuid()+" Data volume:" + tmpVolume1);
//            Thread.sleep(10000);
//            tmpVolume1 = volumeScheduler.createVolume(tmpVolume1.getVolumeUuid(), rpcExtra);
//            logger.info("Data create success");
			
			if (tmpVolume0  != null){
				if (imageUUID == null){
					try {
						VmVolume savedVolume = vmVolumeProxy.getByUUID(tmpVolume0.getVolumeUuid(), false, false, false, false);
						savedVolume.setImageUuid("only-one");
						vmVolumeProxy.update(savedVolume);
						
						tmpVolume0.setImageUuid("only-one");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				logger.info("System volume update success: " + tmpVolume0);
				volumes.add(tmpVolume0);
				context.addRollbackResource(new VmVolumeResource(tmpVolume0));
			}
			else{
				return null;
			}
			
			if (sum - imageSizeGb > 0){
                VmVolume tmpVolume1 = volumeScheduler.defineVolume(VmVolumeUsageTypeEnum.NETWORK, userId, sum - imageSizeGb, zone, null, volumeHost, rpcExtra);
                logger.info("Data volume uuid: "+tmpVolume1.getVolumeUuid()+" Data volume:" + tmpVolume1);
				tmpVolume1 = volumeScheduler.createVolume(tmpVolume1.getVolumeUuid(), rpcExtra);
                logger.info("Data create success");
                if (tmpVolume1 != null){
                    volumes.add(tmpVolume1);
                    logger.info("Data volume create success");
                    context.addRollbackResource(new VmVolumeResource(tmpVolume1));
                }
                else{
                    return null;
                }
            }
			return volumes;
		} catch (RpcException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RpcException(e);
		}
	}

	/**
	 * @return the instance
	 */
	public static VmDiskManager getInstance() {
		return instance;
	}
}
