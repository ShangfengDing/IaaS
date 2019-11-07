package appcloud.vmscheduler.disk;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.common.model.VmInstance;
import appcloud.common.model.VmVolume;
import appcloud.common.model.VmVolume.VmVolumeAttachStatusEnum;
import appcloud.common.model.VmVolume.VmVolumeTypeEnum;
import appcloud.common.model.VmVolume.VmVolumeUsageTypeEnum;
import appcloud.vmscheduler.impl.DBAgent;

/*
 * 构造一个volume管理类：
 * 暂时没想好这个类如何抽象，就简单的把几个方法统一管理在这个类里面
 */
public class VolumeAttachManager {
	
	private static DBAgent dbAgent = DBAgent.getInstance();
	private static Logger logger = Logger.getLogger(VolumeAttachManager.class);
	private static final String DEV_PREFIX = "vd";
	private static  String VOLUME_BUS = "virtio";
	
	/**
	 * 设置volume的attachment信息
	 * 场景1：用于已经创建的虚拟机，增加新盘
	 * 场景2：用于初始创建的虚拟机，分配各个初始卷的盘符（包括系统盘）
	 * 动作：
	 * 1）分配盘符
	 * 2）持久化数据库信息
	 * 3）返回分配好盘符的卷信息
	 * @param instanceUUID
	 * @return List<VmVolume> 返回分配好盘符的卷信息 
	 * @throws Exception 
	 */
	public static List<VmVolume> attachVolumes(VmInstance instance, List<VmVolume> volumes) throws Exception {
	
		/*
		 * 1. 分配盘符，写入到volumes里
		 * 判断是否是系统盘：isSystemDisk
		 * 1）如果是系统盘：devPrefix+a即可
		 * 2）如果非系统盘，统计已经使用的盘符，分配剩余盘符
		 */
		allocTargetDev(instance, volumes, DEV_PREFIX);
				
		/*
		 * 2. 将该volume相关的attachment信息，持久化数据库
		 */
		List<VmVolume> results = new ArrayList<VmVolume>();
		for (VmVolume vo : volumes) {
			if(isIso(vo)){
				VOLUME_BUS = "ide";
			}
			VmVolume newVolume = dbAgent.setVolumeAttachment(vo.getVolumeUuid(), 
															 VOLUME_BUS, 										 
															 vo.getMountPoint(), 
															 instance.getUuid(), 
															 VmVolumeAttachStatusEnum.ATTACHED);
			results.add(newVolume);
		
		}
		logger.info("attached volume: " + results);
		return results;
	}
	
	
	/**
	 * 根据volume信息，判断该盘是否是系统盘
	 * @param volume
	 * @return
	 */
	public static boolean isSystemDisk(VmVolume volume) {
		//TODO:check whether the judgment rule is right
		if (volume.getUsageType().equals(VmVolumeUsageTypeEnum.SYSTEM)) {
			return true;
		} else {
			return false;	
		}
	}
	
	/**
	 * 将整个分配盘符动作，封装起来，使用该接口即可
	 * 分配volume的target dev：hda、hdb。。。
	 * 场景：该instance已经创建，并且已有一系列volume占用盘符
	 * @param instanceUUID
	 * @param volume
	 * @param devPrefix
	 * @return
	 * @throws Exception 
	 */
	public static void allocTargetDev(VmInstance instance, 
									  List<VmVolume> volumes, 
									  String devPrefix) throws Exception {
		DevAllocUtil allocDevService = DevAllocUtil.getDevAllocUtil(instance.getUuid(), devPrefix);
		
		/*
		 * TODO:尚未处理ISO
		 */
		for (VmVolume vo : volumes) {
			String dev = null;
		
			if (isSystemDisk(vo)) {
				/*
				 * 系统盘：
				 * 1）一般启动盘：分配盘符后缀为a
				 * 2）iso：任意分配盘符
				 */
				dev = devPrefix + "a";
				
				//更新instance中的rootdevicelocation
				if (instance.getRootDeviceLocation() == null ||
					(instance.getRootDeviceLocation() != null &&
					 !instance.getRootDeviceLocation().equals("cdrom"))) {
					
					instance.setRootDeviceLocation(dev);
					dbAgent.updateVmInstance(instance);	
				}
			} else {
				//非系统盘，使用devAlloc工具进行分配
				try {
					dev = allocDevService.allocADev();
				} catch (Exception e) {
					logger.error("alloc dev error", e);
					throw e;
				}
				if (vo.getVolumeType().equals(VmVolumeTypeEnum.ISO)) {
					//更新instance中的rootdevicelocation，用于设置boot dev
					dbAgent.setVMRootDevice(instance, "cdrom");
				}
			}
		
			vo.setMountPoint(dev);
			logger.info("alloc target dev for volume: " + vo.getVolumeUuid());
			logger.info("alloc target dev: " + vo.getMountPoint());
			logger.info("root device location: " + instance.getRootDeviceLocation());
		}
	}
	
	private static boolean isIso(VmVolume vo) {
		logger.info("VmVolume's type is " + vo.getVolumeType());
		if(vo.getVolumeType() != null && vo.getVolumeType().equals(VmVolumeTypeEnum.ISO)){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 设置volume的detach信息
	 * 场景1：用于卸载盘，系统盘、数据盘
	 * 场景2：用于卸载iso
	 * 动作：
	 * 1）回收盘符等资源，修改机器启动状态
	 * 2）持久化数据库信息
	 * 3）返回分配好盘符的卷信息
	 * @param instanceUUID
	 * @return List<VmVolume> 返回该instance当前应当挂载的卷列表 
	 * @throws Exception 
	 */
	public static List<VmVolume> detachVolume(VmInstance instance, VmVolume volume) throws Exception {
		dbAgent.setVolumeAttachment(volume.getVolumeUuid(), 
									null,
									null,
									instance.getUuid(), 
									VmVolumeAttachStatusEnum.DETACHED);
		
		List<VmVolume> attachedVolumes = dbAgent.getAttachedVolumeList(instance.getUuid());
		
		//detach iso: change the vm root device location from 'cdrom' to 'hda...vda...'
		if (volume.getVolumeType().equals(VmVolumeTypeEnum.ISO)) {
			for (VmVolume vo : attachedVolumes) {
				if (isSystemDisk(vo)) {
					dbAgent.setVMRootDevice(instance, vo.getMountPoint());	
				} else if (vo.getVolumeType().equals(VmVolumeTypeEnum.ISO)) {
					dbAgent.setVMRootDevice(instance, "cdrom");
					break;
				}
			}
		}
		
		//return attached volumes, exclude detached volume just now
		return attachedVolumes;
	}

}
