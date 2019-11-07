package appcloud.openapi.manager.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.exception.OperationFailedException;
import appcloud.openapi.check.CommonParamsCheck;
import appcloud.openapi.check.VolumeOperateCheck;
import appcloud.openapi.check.VolumeParamsCheck;
import appcloud.openapi.constant.ActionConstants;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.CommonManager;

@Component
public class VolumeUnifiedCheckAndAuth {
	private static final Logger logger = Logger.getLogger(VolumeUnifiedCheckAndAuth.class);
	
	@Autowired
	private CommonParamsCheck commonParamsCheck;
	@Autowired
	private VolumeParamsCheck volumeParamsCheck;
	@Autowired
	private VolumeOperateCheck volumeOperateCheck;
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
			case ActionConstants.CREATE_DISK:
				resultMap = volumeParamsCheck.checkCreateParams(paramsMap);
				break;
			case ActionConstants.ATTACH_DISK:
				resultMap = volumeParamsCheck.checkAttachParams(paramsMap);
				break;
			case ActionConstants.DETACH_DISK:
				resultMap = volumeParamsCheck.checkDetachParams(paramsMap);
				break;
			case ActionConstants.DELETE_DISK:
				resultMap = volumeParamsCheck.checkDeleteParams(paramsMap);
				break;
			case ActionConstants.DESCRIBE_DISKS:
				resultMap = volumeParamsCheck.checkDescribeParams(paramsMap);
				break;
			case ActionConstants.RESET_DISK:
				resultMap = volumeParamsCheck.checkResetParams(paramsMap);
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
			case ActionConstants.CREATE_DISK:
				resultMap = volumeOperateCheck.checkCreate(paramsMap);
				break;
			case ActionConstants.ATTACH_DISK:
				resultMap = volumeOperateCheck.checkAttach(paramsMap);
				break;
			case ActionConstants.DETACH_DISK:
				resultMap = volumeOperateCheck.checkDetach(paramsMap);
				break;
			case ActionConstants.DELETE_DISK:
				resultMap = volumeOperateCheck.checkDelete(paramsMap);
				break;
			case ActionConstants.DESCRIBE_DISKS:
				resultMap = volumeOperateCheck.checkDescribeParams(paramsMap);
				break;
			case ActionConstants.RESET_DISK:
				resultMap = volumeOperateCheck.checkResetParams(paramsMap);
				break;
			default :
				resultMap = null;
				break;
			}
			
			if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
					!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
				logger.warn("Check the operate volume permission is failed!");
				return resultMap;
			}

			
		} catch (ItemNotFoundException e) {
			logger.warn("ItemNotFoundException exist in creating volume! reason is "+e.getMessage());
			resultMap.put(Constants.ERRORCODE, Constants.INVALID_PARAMETER);
			resultMap.put(Constants.ERRORMESSAGE,e.getMessage());
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
			return resultMap;
		} catch (OperationFailedException e) {
			logger.warn("OperationFailedException exist in creating volume! reason is "+e.getMessage());
			resultMap.put(Constants.ERRORCODE, Constants.OPERATIONDENIED);
			resultMap.put(Constants.ERRORMESSAGE,e.getMessage());
			resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_FORBIDDEN+"");
			return resultMap;
		}  catch (Exception e) {
			logger.warn("exception exist in creating volume!",e);
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
