package appcloud.openapi.resources;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import appcloud.api.beans.Snapshot;
import appcloud.common.util.UuidUtil;
import appcloud.core.sdk.http.FormatType;
import appcloud.openapi.constant.ActionConstants;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.util.ConstructResponse;
import appcloud.openapi.manager.util.SnapshotUnifiedCheckAndAuth;
import appcloud.openapi.operate.SnapshotOperate;
import appcloud.openapi.response.BaseResponse;
import appcloud.openapi.response.CreateSnapshotResponse;
import appcloud.openapi.response.DisksDetailReponse;
import appcloud.openapi.response.SnapshotsDetailReponse;

@RestController
public class SnapshotResource extends BaseController {

	private static Logger logger = Logger.getLogger(SnapshotResource.class);
	
	@Autowired
	private SnapshotUnifiedCheckAndAuth snapshotUnifiedCheckAndAuth;
	@Autowired
	private SnapshotOperate snapshotOperate;
	@Autowired
	private ConstructResponse constructResponse;
	
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.CREATE_SNAPSHOT, produces = { "application/json","application/xml"})
	@ResponseBody
	public CreateSnapshotResponse create(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(value = Constants.SNAPSHOT_NAME, required = false) String snapshotName,
			@RequestParam(value = Constants.DESCRIPTION, required = false) String description,
			@RequestParam(Constants.DISK_ID) String diskId,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(value = Constants.VERSION) String version,
			@RequestParam(value = Constants.APPKEY_ID) String appKeyId,
			@RequestParam(value = Constants.SIGNATURE) String signature,
			@RequestParam(value = Constants.TIMESTAMP) String timestamp,
			@RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) throws Exception {
		
			//检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put(Constants.ACTION, action);
			paramsMap.put(Constants.SNAPSHOT_NAME, snapshotName);
			paramsMap.put(Constants.DESCRIPTION, description);
			paramsMap.put(Constants.DISK_ID, diskId);
			if(null!=format) {
				paramsMap.put(Constants.FORMAT, format);
			}
			paramsMap.put(Constants.VERSION, version);
			paramsMap.put(Constants.APPKEY_ID, appKeyId);
			paramsMap.put(Constants.SIGNATURE, signature);
			paramsMap.put(Constants.TIMESTAMP, timestamp);
			if(null!=userEmail) {
				paramsMap.put(Constants.USER_EMAIL, userEmail);
			}
			
			//将所有的默认参数值放到defaultParamsMap中，并且defaultParamsMap中的参数只不参与签名认证
			Map<String, String> defaultParamsMap = new HashMap<String, String>();
			if(null==paramsMap.get(Constants.FORMAT)) {
				defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
			}
			defaultParamsMap.put(Constants.SNAPSHOT_NAME, "unknow-name");
			defaultParamsMap.put(Constants.DESCRIPTION, null);
			
			//首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
			String requestId = UuidUtil.getRandomUuid();
			
			try {
				Map<String, String> resultMap = snapshotUnifiedCheckAndAuth.checkAndAuth(paramsMap, defaultParamsMap);
				if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new CreateSnapshotResponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
				
				Snapshot snapshot = new Snapshot();
				snapshot.displayName = paramsMap.get(Constants.SNAPSHOT_NAME);
				snapshot.displayDescription = paramsMap.get(Constants.DESCRIPTION);
				snapshot.volumeId = diskId;
				snapshot.force = true;
				
				snapshot = snapshotOperate.create(appKeyId, snapshot, requestId);
				
				return new CreateSnapshotResponse(requestId, snapshot.id);
			} catch (Exception e) {
				logger.warn("执行操作抛出异常",e);
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new CreateSnapshotResponse(requestId, Constants.INTERNAL_ERROR, 
						"The request processing has failed due to some unknown error.");
			}
			
	}

	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.DELETE_SNAPSHOT, produces = { "application/json","application/xml"})
	@ResponseBody
	public BaseResponse delete(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(Constants.SNAPSHOT_ID) String snapshotId,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) {
		
			//检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put(Constants.ACTION, action);
			paramsMap.put(Constants.SNAPSHOT_ID, snapshotId);
			if(null!=format) {
				paramsMap.put(Constants.FORMAT, format);
			}
			paramsMap.put(Constants.VERSION, version);
			paramsMap.put(Constants.APPKEY_ID, appKeyId);
			paramsMap.put(Constants.SIGNATURE, signature);
			paramsMap.put(Constants.TIMESTAMP, timestamp);
			if(null!=userEmail) {
				paramsMap.put(Constants.USER_EMAIL, userEmail);
			}
			//将所有的默认参数值放到defaultParamsMap中，并且defaultParamsMap中的参数只不参与签名认证
			Map<String, String> defaultParamsMap = new HashMap<String, String>();
			if(null==paramsMap.get(Constants.FORMAT)) {
				defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
			}
			//首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
			String requestId = UuidUtil.getRandomUuid();
			
			try {
				Map<String, String> resultMap = snapshotUnifiedCheckAndAuth.checkAndAuth(paramsMap, defaultParamsMap);
				if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new BaseResponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
				
				
				Boolean result = snapshotOperate.delete(appKeyId, snapshotId, requestId);
				
				if (result)
					return new BaseResponse(requestId);
				else {
					response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
					return new BaseResponse(requestId, Constants.INTERNAL_ERROR, "挂载失败，请稍后尝试!");
				}
				
			} catch (Exception e) {
				logger.warn("执行操作抛出异常",e);
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new BaseResponse(requestId, Constants.INTERNAL_ERROR, "The request processing has failed due to some unknown error.");
			}
	}
	
	@RequestMapping(method = RequestMethod.GET,  params="Action="+ActionConstants.DESCRIBE_SNAPSHOTS, produces = { "application/json","application/xml"})
	@ResponseBody
	public SnapshotsDetailReponse describeDisks(@RequestParam(Constants.ACTION) String action, 
			@RequestParam(value=Constants.SNAPSHOT_IDS, required=false) String snapshotIds,
			@RequestParam(value=Constants.PAGE_NUMBER, required=false) String pageNumber,
			@RequestParam(value=Constants.PAGE_SIZE, required=false) String pageSize,
			@RequestParam(value=Constants.DISK_ID, required=false) String diskId,
			@RequestParam(value=Constants.SNAPSHOT_NAME, required=false) String snapshotName,
			@RequestParam(value=Constants.DESCRIPTION, required=false) String description,
			@RequestParam(value=Constants.SNAPSHOT_STATUS, required=false) String snapshotStatus,
			@RequestParam(value=Constants.FORMAT, required=false) String format,
			@RequestParam(Constants.VERSION) String version,
			@RequestParam(Constants.APPKEY_ID) String appKeyId,
			@RequestParam(Constants.SIGNATURE) String signature,
			@RequestParam(Constants.TIMESTAMP) String timestamp,
			@RequestParam(value = Constants.USER_EMAIL, required = false) String userEmail) {
		
			//检查接口参数，非必填参数若为空并有默认值时，为其设置默认值，但不用于签名检查
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put(Constants.ACTION, action);
			
			paramsMap.put(Constants.SNAPSHOT_IDS, snapshotIds);
			paramsMap.put(Constants.PAGE_NUMBER, pageNumber);
			paramsMap.put(Constants.PAGE_SIZE, pageSize);
			paramsMap.put(Constants.DISK_ID, diskId);
			paramsMap.put(Constants.SNAPSHOT_NAME, snapshotName);
			paramsMap.put(Constants.DESCRIPTION, description);
			paramsMap.put(Constants.SNAPSHOT_STATUS, snapshotStatus);
			if(null!=format) {
				paramsMap.put(Constants.FORMAT, format);
			}
			paramsMap.put(Constants.VERSION, version);
			paramsMap.put(Constants.APPKEY_ID, appKeyId);
			paramsMap.put(Constants.SIGNATURE, signature);
			paramsMap.put(Constants.TIMESTAMP, timestamp);
			if(null!=userEmail) {
				paramsMap.put(Constants.USER_EMAIL, userEmail);
			}
			
			//首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
			String requestId = UuidUtil.getRandomUuid();
			
			//将所有的默认参数值放到defaultParamsMap中，并且defaultParamsMap中的参数只不参与签名认证
			Map<String, String> defaultParamsMap = new HashMap<String, String>();
			if(null==paramsMap.get(Constants.FORMAT)) {
				defaultParamsMap.put(Constants.FORMAT, FormatType.XML.toString());
			}
			if(null==paramsMap.get(Constants.PAGE_NUMBER)) {
				defaultParamsMap.put(Constants.PAGE_NUMBER, "1");
			}
			if(null==paramsMap.get(Constants.PAGE_SIZE)) {
				defaultParamsMap.put(Constants.PAGE_SIZE, Constants.DEFAULT_PAGE_SIZE.toString());
			}
			
			try {
				Map<String, String> resultMap = snapshotUnifiedCheckAndAuth.checkAndAuth(paramsMap, defaultParamsMap);
				if(null == resultMap || null == resultMap.get(Constants.HTTP_CODE) ||
						!resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
					response.setStatus(constructResponse.getResponseStatus(resultMap));
					return new SnapshotsDetailReponse(requestId, 
							resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
				}
				
				
				SnapshotsDetailReponse result = snapshotOperate.describe(appKeyId, snapshotIds, requestId, diskId, snapshotName, description, snapshotStatus,paramsMap.get(Constants.PAGE_NUMBER), 
						paramsMap.get(Constants.PAGE_SIZE));
				
				return result;
				
			} catch (Exception e) {
				logger.warn("执行操作抛出异常",e);
				response.setStatus(Integer.parseInt(HttpConstants.STATUS_INTERNAL_SERVER_ERROR));
				return new SnapshotsDetailReponse(requestId, Constants.INTERNAL_ERROR, "The request processing has failed due to some unknown error.");
			}
	}
}
