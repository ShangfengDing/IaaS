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
import appcloud.common.proxy.VmZoneProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.openapi.check.SecurityGroupParamsCheck;
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
public class SecurityGroupParamsCheckImpl implements SecurityGroupParamsCheck{

	private static Logger logger = Logger.getLogger(SecurityGroupParamsCheckImpl.class);
	private static SecurityGroupParamsCheckImpl instanceParamsCheck = new SecurityGroupParamsCheckImpl();
	private static VmZoneProxy vmZoneProxy;
	public static SecurityGroupParamsCheckImpl getInstance() {
		return instanceParamsCheck;
	}
	private static CommonGenerator commonGenerator  = new CommonGenerator();
	private SecurityGroupParamsCheckImpl() {
		super();
		vmZoneProxy = (VmZoneProxy)ConnectionFactory.getTipProxy(
				VmZoneProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}
	
	enum IpProtocol{
		TCP,UDP,ICMP,GRE,ALL;
		
		public static boolean contains(String value) {
			try {
				IpProtocol.valueOf(value.toUpperCase());
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		
	}
	
	enum Policy{
		ACCEPT,DROP;
		
		public static boolean contains(String value) {
			try {
				Policy.valueOf(value.toUpperCase());
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		
	}
	
	@Override
	public Map<String, String> checkCreateParams(Map<String, String> paramsMap)
			throws Exception {
		logger.info(paramsMap);
		
		if (StringUtil.isEmpty(paramsMap.get(Constants.REGION_ID)))
			return commonGenerator.missing(null, Constants.REGION_ID);
		else if (null==vmZoneProxy.getByRegionId(paramsMap.get(Constants.REGION_ID)))
			return commonGenerator.inValid(null, Constants.REGION_ID);
		
		if (!StringUtil.isEmpty(paramsMap.get(Constants.DESCRIPTION)) && 
				!StringUtil.isValidInSizeAndChinese(paramsMap.get(Constants.DESCRIPTION), 
						Constants.MIN_DESCRIPTION, Constants.MAX_DESCRIPTION))
			return commonGenerator.inValid(null, Constants.DESCRIPTION);
		
		if (!StringUtil.isEmpty(paramsMap.get(Constants.SECURITY_GROUP_NAME)) && 
				!StringUtil.isValidInSizeAndChinese(paramsMap.get(Constants.SECURITY_GROUP_NAME), 
						Constants.MIN_NAME, Constants.MAX_NAME))
			return commonGenerator.inValid(null, Constants.SECURITY_GROUP_NAME);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check Create SECURITY_GROUP params successfully!");
		return resultMap;
	}

	@Override
	public Map<String, String> checkDeleteParams(Map<String, String> paramsMap)
			throws Exception {
		if (StringUtil.isEmpty(paramsMap.get(Constants.REGION_ID)))
			return commonGenerator.missing(null, Constants.REGION_ID);
		else if (null==vmZoneProxy.getByRegionId(paramsMap.get(Constants.REGION_ID)))
			return commonGenerator.inValid(null, Constants.REGION_ID);
		
		if (StringUtil.isEmpty(paramsMap.get(Constants.SECURITY_GROUP_ID)))
			return commonGenerator.missing(null, Constants.SECURITY_GROUP_ID);
		if (!StringUtil.isNumeric(paramsMap.get(Constants.SECURITY_GROUP_ID)))
			return commonGenerator.inValid(null, Constants.SECURITY_GROUP_ID);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		logger.info("Check Delete SECURITY_GROUP params successfully!");
		return resultMap;
	}

	@Override
	public Map<String, String> checkAuthorize(Map<String, String> paramsMap)
			throws Exception {
		if (StringUtil.isEmpty(paramsMap.get(Constants.REGION_ID)))
			return commonGenerator.missing(null, Constants.REGION_ID);
		else if (null==vmZoneProxy.getByRegionId(paramsMap.get(Constants.REGION_ID)))
			return commonGenerator.inValid(null, Constants.REGION_ID);
		
		if (StringUtil.isEmpty(paramsMap.get(Constants.SECURITY_GROUP_ID)))
			return commonGenerator.missing(null, Constants.SECURITY_GROUP_ID);
		if (!StringUtil.isNumeric(paramsMap.get(Constants.SECURITY_GROUP_ID)))
			return commonGenerator.inValid(null, Constants.SECURITY_GROUP_ID);
		
		if (StringUtil.isEmpty(paramsMap.get(Constants.IP_PROTOCOL)))
			return commonGenerator.missing(null, Constants.IP_PROTOCOL);
		if (!IpProtocol.contains(paramsMap.get(Constants.IP_PROTOCOL))) {
			return commonGenerator.inValid(null, Constants.IP_PROTOCOL);
		}
		
		if (StringUtil.isEmpty(paramsMap.get(Constants.PORT_RANGE)))
			return commonGenerator.missing(null, Constants.PORT_RANGE);
		try {
			String[] split = paramsMap.get(Constants.PORT_RANGE).split("/");
			if (split.length != 2) {
				return commonGenerator.inValid(null, Constants.PORT_RANGE);
			} else {
				Integer stPort = Integer.valueOf(split[0]);
				Integer etPort = Integer.valueOf(split[1]);
				if (stPort > etPort) {
					return commonGenerator.inValid(null, Constants.PORT_RANGE);
				}
			}
		} catch (Exception e) {
			return commonGenerator.inValid(null, Constants.PORT_RANGE);
		}
		
		if (!StringUtil.isEmpty(paramsMap.get(Constants.SOURCE_CIDR_IP))) {
			String ip = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}"; 
			String cidrIp = ip+"\\/([1-9]|[1-2]\\d|3[0-2])?"+"|(0\\.){3}0\\/0";
			if(!Pattern.matches(cidrIp, paramsMap.get(Constants.SOURCE_CIDR_IP))) {
				return commonGenerator.inValid(null, Constants.SOURCE_CIDR_IP);
			}
		} 
		
		if (!StringUtil.isEmpty(paramsMap.get(Constants.POLICY))) {
			if (!Policy.contains(paramsMap.get(Constants.POLICY))) {
				return commonGenerator.inValid(null, Constants.POLICY,paramsMap);
			}
		}
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}
	
	@Override
	public Map<String, String> checkDescribeSGsParams(
			Map<String, String> paramsMap) throws Exception {
		if (StringUtil.isEmpty(paramsMap.get(Constants.REGION_ID)))
			return commonGenerator.missing(null, Constants.REGION_ID);
		else if (null==vmZoneProxy.getByRegionId(paramsMap.get(Constants.REGION_ID)))
			return commonGenerator.inValid(null, Constants.REGION_ID);
		
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
		return resultMap;
	}

	@Override
	public Map<String, String> checkRevoke(Map<String, String> paramsMap)
			throws Exception {
		return checkAuthorize(paramsMap);
	}

	@Override
	public Map<String, String> checkDescribeAttitude(
			Map<String, String> paramsMap) throws Exception {
		if (StringUtil.isEmpty(paramsMap.get(Constants.REGION_ID)))
			return commonGenerator.missing(null, Constants.REGION_ID);
		else if (null==vmZoneProxy.getByRegionId(paramsMap.get(Constants.REGION_ID)))
			return commonGenerator.inValid(null, Constants.REGION_ID);
		
		if (StringUtil.isEmpty(paramsMap.get(Constants.SECURITY_GROUP_ID)))
			return commonGenerator.missing(null, Constants.SECURITY_GROUP_ID);
		if (!StringUtil.isNumeric(paramsMap.get(Constants.SECURITY_GROUP_ID)))
			return commonGenerator.inValid(null, Constants.SECURITY_GROUP_ID);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}

	@Override
	public Map<String, String> checkModifyAttitude(Map<String, String> paramsMap)
			throws Exception {
		if (StringUtil.isEmpty(paramsMap.get(Constants.REGION_ID)))
			return commonGenerator.missing(null, Constants.REGION_ID);
		else if (null==vmZoneProxy.getByRegionId(paramsMap.get(Constants.REGION_ID)))
			return commonGenerator.inValid(null, Constants.REGION_ID);
		
		if (StringUtil.isEmpty(paramsMap.get(Constants.SECURITY_GROUP_ID)))
			return commonGenerator.missing(null, Constants.SECURITY_GROUP_ID);
		if (!StringUtil.isNumeric(paramsMap.get(Constants.SECURITY_GROUP_ID)))
			return commonGenerator.inValid(null, Constants.SECURITY_GROUP_ID);
		
		if (!StringUtil.isEmpty(paramsMap.get(Constants.DESCRIPTION)) && 
				!StringUtil.isValidInSizeAndChinese(paramsMap.get(Constants.DESCRIPTION), 
						Constants.MIN_DESCRIPTION, Constants.MAX_DESCRIPTION))
			return commonGenerator.inValid(null, Constants.DESCRIPTION);
		
		if (!StringUtil.isEmpty(paramsMap.get(Constants.SECURITY_GROUP_NAME)) && 
				!StringUtil.isValidInSizeAndChinese(paramsMap.get(Constants.SECURITY_GROUP_NAME), 
						Constants.MIN_NAME, Constants.MAX_NAME))
			return commonGenerator.inValid(null, Constants.SECURITY_GROUP_NAME);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}
	
}
