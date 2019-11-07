package appcloud.openapi.check.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Component;

import appcloud.common.model.VmVolume;
import appcloud.common.model.VmSnapshot.VmSnapshotStatusEnum;
import appcloud.common.model.VmVolume.VmVolumeStatusEnum;
import appcloud.common.proxy.VmZoneProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.openapi.check.SnapshotParamsCheck;
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
public class SnapshotParamsCheckImpl implements SnapshotParamsCheck{

	private static Logger logger = Logger.getLogger(SnapshotParamsCheckImpl.class);
	private static SnapshotParamsCheckImpl instanceParamsCheck = new SnapshotParamsCheckImpl();
	public static SnapshotParamsCheckImpl getInstance() {
		return instanceParamsCheck;
	}
	private static CommonGenerator commonGenerator  = new CommonGenerator();
	private static VmZoneProxy vmZoneProxy;
	
	private SnapshotParamsCheckImpl() {
		super();
		vmZoneProxy =(VmZoneProxy)ConnectionFactory.getTipProxy(
				VmZoneProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}
	
	public Map<String, String> checkCreateParams(Map<String, String> paramsMap) throws Exception {
		logger.info("Action: "+paramsMap.get(Constants.ACTION)+" check create snapshot params, snapshotName=" + paramsMap.get(Constants.SNAPSHOT_NAME) + 
				"; description=" + paramsMap.get(Constants.DESCRIPTION) + "; VOLUMEID=" + paramsMap.get(Constants.VOLUME_ID) );
		
		if (StringUtil.isEmpty(paramsMap.get(Constants.DISK_ID)))
			return commonGenerator.missing(null, Constants.DISK_ID);
		if (!StringUtil.isUUID(paramsMap.get(Constants.DISK_ID)))
			return commonGenerator.inValid(null, Constants.DISK_ID);
		
		if (!StringUtil.isEmpty(paramsMap.get(Constants.DESCRIPTION)) && 
				!StringUtil.isValidInSizeAndChinese(paramsMap.get(Constants.DESCRIPTION), 
						Constants.MIN_DESCRIPTION, Constants.MAX_DESCRIPTION))
			return commonGenerator.inValid(null, Constants.DESCRIPTION);
		
		if (!StringUtil.isEmpty(paramsMap.get(Constants.SNAPSHOT_NAME)) && 
				!StringUtil.isValidInSizeAndChinese(paramsMap.get(Constants.SNAPSHOT_NAME), 
						Constants.MIN_NAME, Constants.MAX_NAME))
			return commonGenerator.inValid(null, Constants.SNAPSHOT_NAME);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check Create Snapshot params successfully!");
		return resultMap;
	}

	@Override
	public Map<String, String> checkDeleteParams(Map<String, String> paramsMap)
			throws Exception {
		
		if (StringUtil.isEmpty(paramsMap.get(Constants.SNAPSHOT_ID)))
			return commonGenerator.missing(null, Constants.SNAPSHOT_ID);
		if (!StringUtil.isUUID(paramsMap.get(Constants.SNAPSHOT_ID)))
			return commonGenerator.inValid(null, Constants.SNAPSHOT_ID);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check Create Snapshot params successfully!");
		return resultMap;
	}

	@Override
	public Map<String, String> checkDescribeParams(Map<String, String> paramsMap)
			throws Exception {
		if(!StringUtil.isEmpty(paramsMap.get(Constants.SNAPSHOT_IDS))) {
			try {
				JSONArray array = new JSONArray(paramsMap.get(Constants.SNAPSHOT_IDS));
				for (int i = 0; i < array.length(); i++) {
					String snapshotId = (String) array.get(i);
					if (!StringUtil.isUUID(snapshotId))
						return commonGenerator.inValid("snapshotId="+snapshotId+" not valid", Constants.SNAPSHOT_IDS);
				}
			} catch (JSONException e) {
				logger.warn("json format is wrong!",e);
				return commonGenerator.inValid(null, Constants.SNAPSHOT_IDS);
			}
		}
		
		if(null==paramsMap.get(Constants.REGION_ID)) {
			return commonGenerator.missing(null, Constants.REGION_ID);
		}else {
			if(paramsMap.get(Constants.REGION_ID).equalsIgnoreCase("") ||
					null==vmZoneProxy.getByRegionId(paramsMap.get(Constants.REGION_ID))) {
				return commonGenerator.inValid(null, Constants.REGION_ID);
			}
		}
		
		if (!StringUtil.isEmpty(paramsMap.get(Constants.DISK_ID)) &&
				!StringUtil.isUUID(paramsMap.get(Constants.DISK_ID)))
			return commonGenerator.inValid(null, Constants.DISK_ID);
		
		if (!StringUtil.isEmpty(paramsMap.get(Constants.DESCRIPTION)) && 
				!StringUtil.isValidInSizeAndChinese(paramsMap.get(Constants.DESCRIPTION), 
						Constants.MIN_DESCRIPTION, Constants.MAX_DESCRIPTION))
			return commonGenerator.inValid(null, Constants.DESCRIPTION);
		
		if (!StringUtil.isEmpty(paramsMap.get(Constants.SNAPSHOT_NAME)) && 
				!StringUtil.isValidInSizeAndChinese(paramsMap.get(Constants.SNAPSHOT_NAME), 
						Constants.MIN_NAME, Constants.MAX_NAME))
			return commonGenerator.inValid(null, Constants.SNAPSHOT_NAME);
		
		if (!StringUtil.isEmpty(paramsMap.get(Constants.SNAPSHOT_STATUS))) {
			try {
				VmSnapshotStatusEnum.valueOf(paramsMap.get(Constants.SNAPSHOT_STATUS).toUpperCase());
			} catch (Exception e) {
				return commonGenerator.inValid(null, Constants.SNAPSHOT_STATUS);
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
		logger.info("Check Describe Snapshots params successfully!");
		return resultMap;
	}

}
