package appcloud.openapi.check.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import org.springframework.stereotype.Component;

import appcloud.common.model.VmVolume;
import appcloud.common.model.VmVolume.VmVolumeStatusEnum;
import appcloud.openapi.check.VolumeParamsCheck;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.util.CommonGenerator;
import appcloud.openapi.manager.util.StringUtil;

/**
 * 镜像参数检查实现类
 * @author sujingwei
 *
 */
@Component
public class VolumeParamsCheckImpl implements VolumeParamsCheck{

	private static Logger logger = Logger.getLogger(VolumeParamsCheckImpl.class);
	private static VolumeParamsCheckImpl instanceParamsCheck = new VolumeParamsCheckImpl();
	public static VolumeParamsCheckImpl getInstance() {
		return instanceParamsCheck;
	}
	private static CommonGenerator commonGenerator  = new CommonGenerator();
	private VolumeParamsCheckImpl() {
		super();
	}
	
	@Override
	public Map<String, String> checkCreateParams(Map<String, String> paramsMap)
			throws Exception {
		logger.info(paramsMap);
		
		if (!StringUtil.isNullOrNumeric(paramsMap.get(Constants.REGION_ID)))
			return commonGenerator.inValid(null, Constants.REGION_ID);
		if (StringUtil.isEmpty(paramsMap.get(Constants.ZONE_ID)))
			return commonGenerator.missing(null, Constants.ZONE_ID);
		
		if (StringUtil.isEmpty(paramsMap.get(Constants.DISK_SIZE)))
			return commonGenerator.missing(null, Constants.DISK_SIZE);
		if (!StringUtil.isNumeric(paramsMap.get(Constants.DISK_SIZE)))
			return commonGenerator.inValid(null, Constants.DISK_SIZE);
		
		if (!StringUtil.isEmpty(paramsMap.get(Constants.DESCRIPTION)) && 
				!StringUtil.isValidInSizeAndChinese(paramsMap.get(Constants.DESCRIPTION), 
						Constants.MIN_DESCRIPTION, Constants.MAX_DESCRIPTION))
			return commonGenerator.inValid(null, Constants.DESCRIPTION);
		
		if (!StringUtil.isEmpty(paramsMap.get(Constants.DISK_NAME)) && 
				!StringUtil.isValidInSizeAndChinese(paramsMap.get(Constants.DISK_NAME), 
						Constants.MIN_NAME, Constants.MAX_NAME))
			return commonGenerator.inValid(null, Constants.DISK_NAME);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check Create Disk params successfully!");
		return resultMap;
	}

	@Override
	public Map<String, String> checkAttachParams(Map<String, String> paramsMap)
			throws Exception {
		logger.info(paramsMap);
		
		if (StringUtil.isEmpty(paramsMap.get(Constants.DISK_ID)))
			return commonGenerator.missing(null, Constants.DISK_ID);
		if (!StringUtil.isUUID(paramsMap.get(Constants.DISK_ID)))
			return commonGenerator.inValid(null, Constants.DISK_ID);
		if (StringUtil.isEmpty(paramsMap.get(Constants.INSTANCE_ID)))
			return commonGenerator.missing(null, Constants.INSTANCE_ID);
		if (!StringUtil.isUUID(paramsMap.get(Constants.INSTANCE_ID)))
			return commonGenerator.inValid(null, Constants.INSTANCE_ID);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check Attach Disk params successfully!");
		return resultMap;
	}

	@Override
	public Map<String, String> checkDetachParams(Map<String, String> paramsMap)
			throws Exception {
		logger.info(paramsMap);
		
		if (StringUtil.isEmpty(paramsMap.get(Constants.DISK_ID)))
			return commonGenerator.missing(null, Constants.DISK_ID);
		if (!StringUtil.isUUID(paramsMap.get(Constants.DISK_ID)))
			return commonGenerator.inValid(null, Constants.DISK_ID);
		if (StringUtil.isEmpty(paramsMap.get(Constants.INSTANCE_ID)))
			return commonGenerator.missing(null, Constants.INSTANCE_ID);
		if (!StringUtil.isUUID(paramsMap.get(Constants.INSTANCE_ID)))
			return commonGenerator.inValid(null, Constants.INSTANCE_ID);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check Attach Disk params successfully!");
		return resultMap;
	}

	@Override
	public Map<String, String> checkDeleteParams(Map<String, String> paramsMap)
			throws Exception {
		logger.info(paramsMap);
		
		if (StringUtil.isEmpty(paramsMap.get(Constants.DISK_ID)))
			return commonGenerator.missing(null, Constants.DISK_ID);
		if (!StringUtil.isUUID(paramsMap.get(Constants.DISK_ID)))
			return commonGenerator.inValid(null, Constants.DISK_ID);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check Attach Disk params successfully!");
		return resultMap;
	}

	@Override
	public Map<String, String> checkDescribeParams(Map<String, String> paramsMap)
			throws Exception {
		if(!StringUtil.isEmpty(paramsMap.get(Constants.DISK_IDS))) {
			try {
				JSONArray array = new JSONArray(paramsMap.get(Constants.DISK_IDS));
				for (int i = 0; i < array.length(); i++) {
					String diskId = (String) array.get(i);
					if (!StringUtil.isUUID(diskId))
						return commonGenerator.inValid("diskId="+diskId+" not valid", Constants.DISK_IDS);
				}
			} catch (JSONException e) {
				logger.warn("json format is wrong!",e);
				return commonGenerator.inValid(null, Constants.DISK_IDS);
			}
		}

		if (!StringUtil.isEmpty(paramsMap.get(Constants.INSTANCE_ID)) &&
				!StringUtil.isUUID(paramsMap.get(Constants.INSTANCE_ID)))
			return commonGenerator.inValid(null, Constants.INSTANCE_ID);
		
		if (!StringUtil.isEmpty(paramsMap.get(Constants.DISK_TYPE))) {
			try {
				VmVolume.VmVolumeUsageTypeEnum.valueOf(paramsMap.get(Constants.DISK_TYPE).toUpperCase());
			} catch (Exception e) {
				return commonGenerator.inValid(null, Constants.DISK_TYPE);
			}
		}
		
		if (!StringUtil.isEmpty(paramsMap.get(Constants.DESCRIPTION)) && 
				!StringUtil.isValidInSizeAndChinese(paramsMap.get(Constants.DESCRIPTION), 
						Constants.MIN_DESCRIPTION, Constants.MAX_DESCRIPTION))
			return commonGenerator.inValid(null, Constants.DESCRIPTION);
		
		if (!StringUtil.isEmpty(paramsMap.get(Constants.DISK_NAME)) && 
				!StringUtil.isValidInSizeAndChinese(paramsMap.get(Constants.DISK_NAME), 
						Constants.MIN_NAME, Constants.MAX_NAME))
			return commonGenerator.inValid(null, Constants.DISK_NAME);
		
		if (!StringUtil.isEmpty(paramsMap.get(Constants.DISK_STATUS))) {
			try {
				VmVolumeStatusEnum.valueOf(paramsMap.get(Constants.DISK_STATUS));
			} catch (Exception e) {
				return commonGenerator.inValid(null, Constants.DISK_STATUS);
			}
		}
		
		if (!StringUtil.isEmpty(paramsMap.get(Constants.DISK_ATTACH_STATUS))) {
			try {
				VmVolume.VmVolumeAttachStatusEnum.valueOf(paramsMap.get(Constants.DISK_ATTACH_STATUS));
			} catch (Exception e) {
				return commonGenerator.inValid(null, Constants.DISK_ATTACH_STATUS);
			}
		}
		
		if (!StringUtil.isEmpty(paramsMap.get(Constants.PAGE_NUMBER))) {
			if(!StringUtil.isNumeric(paramsMap.get(Constants.PAGE_NUMBER))) {
				return commonGenerator.inValid(null, Constants.PAGE_NUMBER);
			}
			
			if(Integer.parseInt(paramsMap.get(Constants.PAGE_NUMBER)) < 1) {
				return commonGenerator.inValid(null, Constants.PAGE_NUMBER);
			}
		}
		
		if (!StringUtil.isEmpty(paramsMap.get(Constants.PAGE_SIZE))) {
			if(!StringUtil.isNumeric(paramsMap.get(Constants.PAGE_SIZE))) {
				return commonGenerator.inValid(null, Constants.PAGE_SIZE);
			}
			
			if(Integer.parseInt(paramsMap.get(Constants.PAGE_SIZE)) < 1) {
				return commonGenerator.inValid(null, Constants.PAGE_NUMBER);
			}
			
			if(Integer.parseInt(paramsMap.get(Constants.PAGE_SIZE)) > Constants.MAX_PAGE_SIZE) {
				return commonGenerator.inValid(null, Constants.PAGE_NUMBER);
			}
		}
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check Describe Disks params successfully!");
		return resultMap;
	}

	@Override
	public Map<String, String> checkResetParams(Map<String, String> paramsMap)
			throws Exception {
		
		if (StringUtil.isEmpty(paramsMap.get(Constants.DISK_ID)))
			return commonGenerator.missing(null, Constants.DISK_ID);
		if (!StringUtil.isUUID(paramsMap.get(Constants.DISK_ID)))
			return commonGenerator.inValid(null, Constants.DISK_ID);
		if (StringUtil.isEmpty(paramsMap.get(Constants.SNAPSHOT_ID)))
			return commonGenerator.missing(null, Constants.SNAPSHOT_ID);
		if (!StringUtil.isUUID(paramsMap.get(Constants.SNAPSHOT_ID)))
			return commonGenerator.inValid(null, Constants.SNAPSHOT_ID);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check Reset Disk params successfully!");
		return resultMap;
	}
	
}
