package com.appcloud.vm.common;

import appcloud.openapi.client.OperateLogClient;
import appcloud.openapi.datatype.OperateLogItem;
import appcloud.openapi.response.BaseResponse;
import appcloud.openapi.response.SearchOperateLogResponse;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by zouji on 2017/11/3.
 */
public class Log {

    private static final Logger logger = Logger.getLogger(Log.class);
    private static OperateLogClient operateLogClient = OpenClientFactory.getOperateLogClient();
    public static void INFO(Map<String, String> mapInfo, Appkey appkey, String regionId) {
        mapInfo.put("infoType", "info");
        saveOperateLog(mapInfo, appkey, regionId);
    }

    public static void ERROR(Map<String, String> mapInfo, Appkey appkey, String regionId) {
        mapInfo.put("infoType", "error");
        saveOperateLog(mapInfo, appkey, regionId);
    }

    public static List<OperateLogItem> QueryLog(Map<String, String> mapInfo, Appkey appkey, String regionId) {
        String result = mapInfo.containsKey("result") ? mapInfo.get("result") : null;
        String userId = mapInfo.containsKey("userId") ? mapInfo.get("userId") : null;
        String device = mapInfo.containsKey("device") ? mapInfo.get("device") : null;
        String provider = mapInfo.containsKey("provider") ? mapInfo.get("provider") : null;
        String operateType = mapInfo.containsKey("operateType") ? mapInfo.get("operateType") : null;
        String deviceId = mapInfo.containsKey("deviceId") ? mapInfo.get("deviceId") : null;
        String infoType = mapInfo.containsKey("infoType") ? mapInfo.get("infoType") : null;
        String createdTime = mapInfo.containsKey("createdTime") ? mapInfo.get("createdTime") : null;
        String btime = mapInfo.containsKey("btime") ? mapInfo.get("btime") : null;
        String etime = mapInfo.containsKey("etime") ? mapInfo.get("etime") : null;
        String timeasc = mapInfo.containsKey("timeasc") ? mapInfo.get("timeasc") : null;
        String size = mapInfo.containsKey("size") ? mapInfo.get("size") : null;
        logger.info("regionId=" + regionId);
        SearchOperateLogResponse searchOperateLogResponse = operateLogClient.searchOperateLogResponse(
                result, userId, device, provider, operateType, deviceId, infoType, createdTime,
                etime, btime, timeasc, size, regionId,Constants.ZONE_ID, appkey.getAppkeyId(), appkey.getAppkeySecret());
        logger.info("查询操作日志：" +
                " result = " + result +
                " appkeyId = " + appkey.getAppkeyId() +
                " userId = " + userId +
                " device = " + device +
                " provider = " + provider +
                " operateType = " + operateType +
                " deviceId = " + deviceId +
                " infoType = " + infoType +
                " createdTime = " + createdTime +
                " btime = " + btime +
                " etime = " + etime +
                " timeasc = " + timeasc +
                " size = " + size);
        return searchOperateLogResponse.getOperateLogs();
    }

    public static void saveOperateLog(Map<String, String > mapInfo, Appkey appkey, String regionId) {
        String result = mapInfo.containsKey("result") ? mapInfo.get("result") : null;
        String appkeyId = mapInfo.containsKey("appkeyId") ? mapInfo.get("appkeyId") : null;
        String userId = mapInfo.containsKey("userId") ? mapInfo.get("userId") : null;
        String device = mapInfo.containsKey("device") ? mapInfo.get("device") : null;
        String provider = mapInfo.containsKey("provider") ? mapInfo.get("provider") : null;
        String operateType = mapInfo.containsKey("operateType") ? mapInfo.get("operateType") : null;
        String deviceId = mapInfo.containsKey("deviceId") ? mapInfo.get("deviceId") : null;
        String infoType = mapInfo.containsKey("infoType") ? mapInfo.get("infoType") : null;
        String createdTime = mapInfo.containsKey("createdTime") ? mapInfo.get("createdTime") : String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());
        BaseResponse BaseResponse = operateLogClient.SaveOperateLog(result, userId,
                device, provider, operateType, deviceId, infoType, createdTime, regionId,null, appkey.getAppkeyId(), appkey.getAppkeySecret());
        logger.info("保存操作日志：" +
                " result = " + result +
                " appkeyId = " + appkeyId +
                " userId = " + userId +
                " device = " + device +
                " provider = " + provider +
                " operateType = " + operateType +
                " deviceId = " + deviceId +
                " infoType = " + infoType +
                " createdTime = " + createdTime);
    }
}
