/**
 * File: VmDiskManager.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.resourcescheduler.vmdisk;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.common.model.Host;
import appcloud.common.model.VmInstanceType;
import appcloud.common.model.VmVolume;
import appcloud.common.model.VmZone;
import appcloud.common.service.VolumeSchedulerService;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public class VmDiskManager {
	private static VmDiskManager instance = new VmDiskManager();
	
	private static Logger logger = Logger
			.getLogger(VmDiskManager.class);
	VolumeSchedulerService volumeScheduler;
	
	public List<VmVolume> createDisk(Integer userId,  String imageUUID, VmZone zone, Host vmHost, Integer localGb, Integer imageSizeGb){
		Host volumeHost = null;
		try {
			volumeHost = volumeScheduler.selectHost(zone, localGb, vmHost);
		} catch (RpcException e) {
			logger.error(e.getMessage());
		}
		
		List<VmVolume> volumes = new LinkedList<VmVolume>();
		
		try {
			VmVolume tmpVolume0 = volumeScheduler.defineVolume(userId, imageSizeGb, zone, imageUUID, volumeHost);
			volumes.add(volumeScheduler.createVolume(tmpVolume0.getVolumeUuid()));
			
			VmVolume tmpVolume1 = volumeScheduler.defineVolume(userId, localGb - imageSizeGb, zone, imageUUID, volumeHost);
			volumes.add(volumeScheduler.createVolume(tmpVolume1.getVolumeUuid()));
			
			return volumes;
		} catch (RpcException e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	/**
	 * @return the instance
	 */
	public static VmDiskManager getInstance() {
		return instance;
	}
}
