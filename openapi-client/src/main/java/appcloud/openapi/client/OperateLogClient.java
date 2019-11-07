package appcloud.openapi.client;

import appcloud.core.sdk.exceptions.ClientException;
import appcloud.core.sdk.exceptions.ServerException;
import appcloud.core.sdk.http.FormatType;
import appcloud.core.sdk.profile.DefaultProfile;
import appcloud.core.sdk.profile.YhaiClientProfile;
import appcloud.core.sdk.utils.DefaultYhaiClient;
import appcloud.core.sdk.utils.YhaiClient;
import appcloud.iaas.sdk.model.SaveOperateLogRequest;
import appcloud.iaas.sdk.model.SearchOperateLogRequest;
import appcloud.openapi.response.BaseResponse;
import appcloud.openapi.response.SaveOperateLogResponse;
import appcloud.openapi.response.SearchOperateLogResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


/**
 * Created by zouji on 2017/11/1.
 */
public class OperateLogClient {

    public static Logger logger = Logger.getLogger(OperateLogClient.class);

    public BaseResponse SaveOperateLog(String result, String userId,
           String device, String provider, String operateType, String deviceId, String infoType,
           String createdTime, String regionId, String zoneId, String appkeyId, String appkeySecret) {
        SaveOperateLogRequest saveOeprateLogRequest = new SaveOperateLogRequest();
        if (!StringUtils.isEmpty(zoneId)){
            saveOeprateLogRequest.setZoneId(zoneId);
        }
        saveOeprateLogRequest.setResult(result);
        saveOeprateLogRequest.setAppkeyId(appkeyId);
        saveOeprateLogRequest.setUserId(userId);
        saveOeprateLogRequest.setDevice(device);
        saveOeprateLogRequest.setProvider(provider);
        saveOeprateLogRequest.setOperateType(operateType);
        saveOeprateLogRequest.setDeviceId(deviceId);
        saveOeprateLogRequest.setInfoType(infoType);
        saveOeprateLogRequest.setCreatedTime(createdTime);
        saveOeprateLogRequest.setAcceptFormat(FormatType.XML);
        YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
        YhaiClient client = new DefaultYhaiClient(profile);
        SaveOperateLogResponse response = null;
        try {
            response = client.getYhaiResponse(saveOeprateLogRequest, SaveOperateLogResponse.class);
        } catch (ServerException e) {
            String rs = e.getErrorCode() + " : " + e.getErrorMessage();
            logger.warn(rs, e);
            response = new SaveOperateLogResponse(null, e.getErrorCode(), e.getErrorMessage());
        } catch (ClientException e) {
            String rs = e.getErrorCode() + " : " + e.getErrorMessage();
            logger.warn(rs, e);
            response = new SaveOperateLogResponse(null, e.getErrorCode(), e.getErrorMessage());
        }
        return response;
    }

    public SearchOperateLogResponse searchOperateLogResponse(String result, String userId, String device, String provider,
             String operateType, String deviceId, String infoType, String createdTime, String etime, String btime,
             String timeasc, String size, String regionId, String zoneId, String appkeyId, String appkeySecret) {
        SearchOperateLogRequest searchOperateLogRequest = new SearchOperateLogRequest();
        if (!StringUtils.isEmpty(zoneId)){
            searchOperateLogRequest.setZoneId(zoneId);
        }
        searchOperateLogRequest.setResult(result);
        searchOperateLogRequest.setAppkeyId(appkeyId);
        searchOperateLogRequest.setUserId(userId);
        searchOperateLogRequest.setDevice(device);
        searchOperateLogRequest.setProvider(provider);
        searchOperateLogRequest.setOperateType(operateType);
        searchOperateLogRequest.setDeviceId(deviceId);
        searchOperateLogRequest.setInfoType(infoType);
        searchOperateLogRequest.setCreatedTime(createdTime);
        searchOperateLogRequest.setEtime(etime);
        searchOperateLogRequest.setBtime(btime);
        searchOperateLogRequest.setTimeasc(timeasc);
        searchOperateLogRequest.setSize(size);
        searchOperateLogRequest.setAcceptFormat(FormatType.XML);
        YhaiClientProfile profile = DefaultProfile.getProfile(regionId, appkeyId, appkeySecret);
        YhaiClient client = new DefaultYhaiClient(profile);
        SearchOperateLogResponse response = null;
        try {
            response = client.getYhaiResponse(searchOperateLogRequest, SearchOperateLogResponse.class);
        } catch (ServerException e) {
            String rs = e.getErrorCode() + " : " + e.getErrorMessage();
            logger.warn(rs, e);
            response = new SearchOperateLogResponse(null, e.getErrorCode(), e.getErrorMessage());
        } catch (ClientException e) {
            String rs = e.getErrorCode() + " : " + e.getErrorMessage();
            logger.warn(rs, e);
            response = new SearchOperateLogResponse(null, e.getErrorCode(), e.getErrorMessage());
        }
        return response;
    }
}
