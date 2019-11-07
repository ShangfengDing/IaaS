package appcloud.openapi.manager.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import appcloud.openapi.check.BillingOperateCheck;
import appcloud.openapi.check.BillingParamsCheck;
import appcloud.openapi.check.CommonParamsCheck;
import appcloud.openapi.constant.ActionConstants;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.CommonManager;

@Component
public class BillingUnifiedCheckAndAuth {

private static final Logger logger = Logger.getLogger(VolumeUnifiedCheckAndAuth.class);
	
	@Autowired
	private CommonParamsCheck commonParamsCheck;
	@Autowired
	private BillingParamsCheck billingParamsCheck;
	@Autowired
	private BillingOperateCheck billingOperateCheck;
	@Autowired
	private CommonManager commonManager;
	
	public  Map<String, String> checkAndAuth (Map<String, String> paramsMap, final Map<String, String> defaultParamsMap) {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			resultMap = commonParamsCheck.checkCommonParams(paramsMap);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				logger.warn("The request common params was not authenticated successfully!");
				return resultMap;
			}
			
			//检查接口自身参数
			switch (paramsMap.get(Constants.ACTION)) {
			case ActionConstants.DESCRIBE_INSTANCE_TYPES:
				resultMap = billingParamsCheck.checkDescribeInstanceTypesParams(paramsMap);
				break;
			case ActionConstants.DESCRIBE_DISK_TYPES:
				resultMap = billingParamsCheck.checkDescribeDiskTypesParams(paramsMap);
				break;
			case ActionConstants.DESCRIBE_INTERNET_TYPES:
				resultMap = billingParamsCheck.checkDescribeInternetTypesParams(paramsMap);
				break;
			case ActionConstants.DESCRIBE_CPU_TYPES:
				resultMap = billingParamsCheck.checkDescribeCpuTypesParams(paramsMap);
				break;
			case ActionConstants.DESCRIBE_MEMORY_TYPES:
				resultMap = billingParamsCheck.checkDescribeMemoryTypesParams(paramsMap);
				break;
			default :
				resultMap = null;
				break;
			}
			
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				logger.warn("The request speical params was not authenticated successfully!");
				return resultMap;
			}
			
			//签名认证
			resultMap = commonManager.getAuthenticate(paramsMap);
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				logger.warn("The request signature was not authenticated successfully!");
				return resultMap;
			}
			
			if(null!=defaultParamsMap && !defaultParamsMap.isEmpty()) {
				logger.info("Transfer default params to paramsMap.");
				for(Map.Entry<String, String> entry : defaultParamsMap.entrySet()) {
					if (StringUtil.isEmpty(paramsMap.get(entry.getKey()))) {
						paramsMap.put(entry.getKey(), entry.getValue());
					}
				}
				logger.info("Transfer default params successfully.");
			}
			
			//check operation auth
			switch (paramsMap.get(Constants.ACTION)) {
			case ActionConstants.DESCRIBE_INSTANCE_TYPES:
				resultMap = billingOperateCheck.checkDescribeInstanceTypes(paramsMap);
				break;
			case ActionConstants.DESCRIBE_DISK_TYPES:
				resultMap = billingOperateCheck.checkDescribeDiskTypes(paramsMap);
				break;
			case ActionConstants.DESCRIBE_INTERNET_TYPES:
				resultMap = billingOperateCheck.checkDescribeInternetTypes(paramsMap);
				break;
			case ActionConstants.DESCRIBE_CPU_TYPES:
				resultMap = billingOperateCheck.checkDescribeCpuTypes(paramsMap);
				break;
			case ActionConstants.DESCRIBE_MEMORY_TYPES:
				resultMap = billingOperateCheck.checkDescribeMemoryTypes(paramsMap);
				break;
			default :
				resultMap = null;
				break;
			}
			
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				logger.warn("Check the billing operate permission is failed!");
				return resultMap;
			}
		}  catch (Exception e) {
			logger.error(e.getMessage(),e);
			resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
			resultMap.put(Constants.ERRORMESSAGE,"The request processing has failed due to some unknown error.");
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR+"");
			return resultMap;
		}
		logger.info("Check the params and the operation permission successfully, and result is OK!");
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}
}
