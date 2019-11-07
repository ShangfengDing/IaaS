package appcloud.vmscheduler.disk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import appcloud.common.model.VmVolume;
import appcloud.vmscheduler.impl.DBAgent;

public class DevAllocUtil {
	private static DBAgent dbAgent = DBAgent.getInstance();
	private static Logger logger = Logger.getLogger(DevAllocUtil.class);
	
	private Map<Character, Integer> devList;
	//public List<String> devListTest = new ArrayList<String>();
	private char maxUsedKey;
	private String devPre = null;
	private char limit;
	private boolean usedOver = false;
	
	private DevAllocUtil(String pre) {
		devList = new HashMap<Character, Integer>();
		for (char a = 'b'; a < 'z'; a++) {
			devList.put(a, 0);
			//devListTest.add(pre+a);
		}
		//目前已经使用了的character
		maxUsedKey = 'a';
		limit = 'z';
		devPre = pre;
	}
	
	private DevAllocUtil(String instanceUUID, String pre) throws Exception {
		//初始化DevAllocUtil
		this(pre);
		
		//获取该instance的所有attached volume
		List<VmVolume> volumeList = new ArrayList<VmVolume>();//dbAgent.getAttachedVolumeList(instanceUUID);
		logger.info("volume list : " + volumeList);

		for (VmVolume vo : volumeList) {
			/*
			 * 当volume个数不为1：attach一个新盘的场景 ，先设置已经占用的盘符
			 */
			if (vo.getMountPoint() != null) {
				this.setUsedDev(vo.getMountPoint());	
			}
			else {
				throw new Exception("volume: " + vo.getVolumeUuid() + "  , mountpoint is null");
			}
		}
	}
	
	/**
	 * 获取盘符分配工具的实例，根据传入的volume的个数：
	 * 1）当volume个数为1：初始创建虚拟机的场景
	 * 2）当volume个数长于1：attach一个新盘的场景
	 * 3）当volume为空：不符要求
	 * @param instanceUUID
	 * @param pre
	 * @return
	 * @throws Exception
	 */
	public static DevAllocUtil getDevAllocUtil(String instanceUUID, String pre) throws Exception {
		//初始化DevAllocUtil
		DevAllocUtil service = new DevAllocUtil(pre);
		
		//获取该instance的所有attached volume
		List<VmVolume> volumeList = dbAgent.getAttachedVolumeList(instanceUUID);
		logger.info("attached volume list : " + volumeList);

		for (VmVolume vo : volumeList) {
			/*
			 * 当volume个数不为1：attach一个新盘的场景 ，先设置已经占用的盘符
			 */
			if (vo.getMountPoint() != null) {
				service.setUsedDev(vo.getMountPoint());	
			}
			else {
				throw new Exception("volume: " + vo.getVolumeUuid() + "  , mountpoint is null");
			}
		}
		
		return service;
	}
	
	/**
	 * 设置已经使用的dev
	 * @return String：返回分配好的dev盘符，包括了前缀，如hda/vdb等
	 */
	public String allocADev() throws Exception {
		String dev = null;
		
		if (usedOver == false) {
			++maxUsedKey;
			if ( maxUsedKey <= limit) {
				if (devList.get(maxUsedKey) == 0) {
					dev = devPre + maxUsedKey;
					devList.put(maxUsedKey, 1);
				}
				return dev;
			} else {
				usedOver = true;
				throw new Exception("磁盘空间已分配完");
			}
		} else {
			throw new Exception("磁盘空间已分配完");
		}	
	}
	
	/**
	 * 设置已经使用的dev
	 * @param mountPoint：从数据库中获取出来的mountPoint
	 * @return
	 */
	public void setUsedDev(String mountPoint) {
		if (mountPoint.length() < 3)
			return;
		char mount = mountPoint.charAt(2);//XXX don't use fix num
		devList.put(mount, 1);
		
		if(maxUsedKey < mount) {
			maxUsedKey = mount;
		}
	}
	
	//查询下一个为空的dev,进行分配
//	public String findUnusedDev() {
//		String dev = null;
//		/*
//		 * 遍历,找到一个为空的值
//		 * 这个地方,先空缺吧...先使用按需获取即可,拿到当前的最大值
//		 */
//		
//		
//		return dev;
//	}

}
