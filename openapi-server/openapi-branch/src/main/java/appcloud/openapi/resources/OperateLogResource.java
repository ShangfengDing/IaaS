package appcloud.openapi.resources;

import appcloud.common.model.OperateLogType;
import appcloud.common.util.UuidUtil;
import appcloud.openapi.constant.ActionConstants;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.datatype.OperateLogItem;
import appcloud.openapi.manager.util.ConstructResponse;
import appcloud.openapi.manager.util.OperateLogUnifiedCheckAndAuth;
import appcloud.openapi.operate.LogOperate;
import appcloud.openapi.operate.impl.LogOperateImpl;
import appcloud.openapi.response.BaseResponse;
import appcloud.openapi.response.SearchOperateLogResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zouji on 2017/11/1.
 */
@RestController
public class OperateLogResource extends BaseController{
//
//    @Autowired
//    private CommonParamsCheck commonParamsCheck;
//    @Autowired
//    private CommonManager commonManager;
//    @Autowired
    @Autowired
    private LogOperate logOperate = new LogOperateImpl();

    private ConstructResponse constructResponse = new ConstructResponse();
    private OperateLogUnifiedCheckAndAuth operateLogUnifiedCheckAndAuth = new OperateLogUnifiedCheckAndAuth();
//    private CommonGenerator commonGenerator;

    private static Logger logger = Logger.getLogger(OperateLogResource.class);

    @RequestMapping(method = RequestMethod.GET, params="Action="+ ActionConstants.SAVE_OPERATE_LOG, produces = { "application/json", "application/xml"})
    @ResponseBody
    public BaseResponse SavaOperateLog(@RequestParam(Constants.ACTION) String action,
           @RequestParam(value = Constants.LOG_RESULT, required=false) String result,
           @RequestParam(value = Constants.LOG_DEVICE, required=false) String device,
           @RequestParam(value = Constants.LOG_DEVICEID, required=false) String deviceId,
           @RequestParam(value = Constants.LOG_OPERATETYPE, required=false) String operateType,
           @RequestParam(value = Constants.LOG_PROVIDER, required=false) String provider,
           @RequestParam(value = Constants.LOG_USERID, required=false) String userId,
           @RequestParam(value = Constants.LOG_INFOTYPE, required=false) String infoType,
           @RequestParam(value = Constants.LOG_CREATEDTIME, required=false) String createdTime,
           @RequestParam(value = Constants.APPKEY_ID) String appKeyId,
           @RequestParam(value = Constants.VERSION) String version,
           @RequestParam(value = Constants.SIGNATURE) String signature,
           @RequestParam(value = Constants.TIMESTAMP) String timestamp)throws Exception {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put(Constants.ACTION, action);
        logger.info("result" + result);
        Timestamp created = null;
        if (null != result) {
            paramsMap.put(Constants.LOG_RESULT, result);
        }
        if (null != appKeyId) {
            paramsMap.put(Constants.LOG_APPKEYID, appKeyId);
        }
        if (null != device) {
            paramsMap.put(Constants.LOG_DEVICE, device);
        }
        if (null != deviceId) {
            paramsMap.put(Constants.LOG_DEVICEID, deviceId);
        }
        if (null != operateType) {
            paramsMap.put(Constants.LOG_OPERATETYPE, operateType);
        }
        if (null != provider) {
            paramsMap.put(Constants.LOG_PROVIDER, provider);
        }
        if (null != userId) {
            paramsMap.put(Constants.LOG_USERID, userId);
        }
        if (null != infoType) {
            paramsMap.put(Constants.LOG_INFOTYPE, infoType);
        }
        if (null != createdTime) {
            paramsMap.put(Constants.LOG_CREATEDTIME, createdTime);
            created = new Timestamp(Long.valueOf(createdTime));
        }
        OperateLogType operateLogType = new OperateLogType(result, appKeyId, userId, device, provider, operateType, deviceId, infoType, created);
        paramsMap.put(Constants.VERSION, version);
        paramsMap.put(Constants.APPKEY_ID, appKeyId);
        paramsMap.put(Constants.SIGNATURE, signature);
        paramsMap.put(Constants.TIMESTAMP, timestamp);
        String requestId = UuidUtil.getRandomUuid();

        try {
            //首先对操作权限进行统一检查和认证
            Map<String, String> resultMap = operateLogUnifiedCheckAndAuth.checkAndAuth(paramsMap, new HashMap<String, String>());
            if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                response.setStatus(constructResponse.getResponseStatus(resultMap));
                return constructResponse.getBaseResponse(requestId, resultMap);
            }
            //resultMap = operate
            logger.info("will save operate log successfully!");
            resultMap = logOperate.saveOperateLog(operateLogType, requestId);
            logger.info("Save operate log successfully!");
            response.setStatus(Integer.parseInt(HttpConstants.STATUS_OK));
            return new BaseResponse(requestId);
        } catch (Exception e) {
            logger.warn("执行操作抛出异常", e);
            response.setStatus(constructResponse.getResponseStatus(null));
            return constructResponse.getBaseResponse(requestId, null);
        }
    }

    @RequestMapping(method = RequestMethod.GET, params="Action="+ActionConstants.SEARCH_OPERATE_LOG, produces = { "application/json", "application/xml"})
    @ResponseBody
    public SearchOperateLogResponse searchOperateLog(
            @RequestParam(Constants.ACTION) String action,
            @RequestParam(value = Constants.SIZE, required=false) String size,
            @RequestParam(value = Constants.ENDTIME, required=false) String etime,
            @RequestParam(value = Constants.BEGINTIME, required=false) String btime,
            @RequestParam(value = Constants.TIMEASC, required=false) String timeasc,
            @RequestParam(value = Constants.LOG_RESULT, required=false) String result,
            @RequestParam(value = Constants.LOG_DEVICE, required=false) String device,
            @RequestParam(value = Constants.LOG_DEVICEID, required=false) String deviceId,
            @RequestParam(value = Constants.LOG_OPERATETYPE, required=false) String operateType,
            @RequestParam(value = Constants.LOG_PROVIDER, required=false) String provider,
            @RequestParam(value = Constants.LOG_USERID, required=false) String userId,
            @RequestParam(value = Constants.LOG_INFOTYPE, required=false) String infoType,
            @RequestParam(value = Constants.LOG_CREATEDTIME, required=false) String createdTime,
            @RequestParam(value = Constants.APPKEY_ID) String appKeyId,
            @RequestParam(value = Constants.VERSION) String version,
            @RequestParam(value = Constants.SIGNATURE) String signature,
            @RequestParam(value = Constants.TIMESTAMP) String timestamp) {
        Map<String, String> paramsMap = new HashMap<>();
        Timestamp created = null;
        Timestamp startTime = null;
        Timestamp endTime = null;
        Integer count = null;
        Boolean order = false;
        if (null != size) {
            paramsMap.put(Constants.SIZE, size);
            count = Integer.valueOf(size);
        }
        if (null != timeasc) {
            paramsMap.put(Constants.TIMEASC, timeasc);
            order = timeasc.equals("yes");
        }
        if (null != etime) {
            paramsMap.put(Constants.ENDTIME, etime);
            endTime = new Timestamp(Long.valueOf(etime));
        }
        if (null != btime) {
            paramsMap.put(Constants.BEGINTIME, btime);
            startTime = new Timestamp(Long.valueOf(btime));
        }
        if (null != result) {
            paramsMap.put(Constants.LOG_RESULT, result);
        }
        if (null != device) {
            paramsMap.put(Constants.LOG_DEVICE, device);
        }
        if (null != deviceId) {
            paramsMap.put(Constants.LOG_DEVICEID, deviceId);
        }
        if (null != operateType) {
            paramsMap.put(Constants.LOG_OPERATETYPE, operateType);
        }
        if (null != provider) {
            paramsMap.put(Constants.LOG_PROVIDER, provider);
        }
        if (null != userId) {
            paramsMap.put(Constants.LOG_USERID, userId);
        }
        if (null != infoType) {
            paramsMap.put(Constants.LOG_INFOTYPE, infoType);
        }
        if (null != createdTime) {
            paramsMap.put(Constants.LOG_CREATEDTIME, createdTime);
            created = new Timestamp(Long.valueOf(createdTime));
        }
        OperateLogType operateLogType = new OperateLogType(result, appKeyId, userId, device, provider, operateType, deviceId, infoType, created);
        paramsMap.put(Constants.ACTION, action);
        paramsMap.put(Constants.VERSION, version);
        paramsMap.put(Constants.APPKEY_ID, appKeyId);
        paramsMap.put(Constants.SIGNATURE, signature);
        paramsMap.put(Constants.TIMESTAMP, timestamp);

        //首先为每个API请求指定一个requestId，为了适配下层的接口，也将该requestId指定为Rpc的TranctionId
        String requestId = UuidUtil.getRandomUuid();
        //resultMap : 每次操作的返回结果
        Map<String, String> resultMap = new HashMap<String, String>();

        try {
            logger.info("paramsMap:" + paramsMap);
            resultMap = operateLogUnifiedCheckAndAuth.checkAndAuth(paramsMap, new HashMap<String, String>());
            if(null==resultMap || null==resultMap.get(Constants.HTTP_CODE) ||
                    !resultMap.get(Constants.HTTP_CODE).equals(HttpConstants.STATUS_OK)) {
                response.setStatus(constructResponse.getResponseStatus(resultMap));
                return new SearchOperateLogResponse(requestId,
                        resultMap.get(Constants.ERRORCODE), resultMap.get(Constants.ERRORMESSAGE));
            }
            List<OperateLogItem> operateLogItemList = logOperate.searchOperateLog(operateLogType, startTime, endTime, count, order, requestId);
            logger.info("Query operate log successfully!");
            response.setStatus(Integer.parseInt(HttpConstants.STATUS_OK));
            return new SearchOperateLogResponse(requestId, operateLogItemList, operateLogItemList.size());
        } catch (Exception e) {
            logger.warn("执行操作抛出异常", e);
            response.setStatus(constructResponse.getResponseStatus(null));
            return new SearchOperateLogResponse(requestId, Constants.ERRORCODE, Constants.ERRORMESSAGE);
        }
    }
}
