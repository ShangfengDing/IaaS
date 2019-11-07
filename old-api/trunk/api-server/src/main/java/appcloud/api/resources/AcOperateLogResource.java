package appcloud.api.resources;

import appcloud.api.beans.AcOperateLog;
import appcloud.api.manager.AcOperateLogManager;
import appcloud.common.model.OperateLogType;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zouji on 2017/11/24.
 */

@Path("{tenantId}/operate-log")
public class AcOperateLogResource {

    private AcOperateLogManager acOperateLogManager;

    public AcOperateLogManager getAcOperateLogManager() {
        return acOperateLogManager;
    }

    public void setAcOperateLogManager(AcOperateLogManager acOperateLogManager) {
        this.acOperateLogManager = acOperateLogManager;
    }
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<AcOperateLog> index(
            @PathParam("tenantId") String tenantId,
            @DefaultValue("") @QueryParam("start_time") String startTimeStr,
            @DefaultValue("") @QueryParam("end_time") String endTimeStr,
            @DefaultValue("") @QueryParam("result") String result,
            @DefaultValue("") @QueryParam("appkey_id") String appkeyId,
            @DefaultValue("") @QueryParam("user_id") String userId,
            @DefaultValue("") @QueryParam("device") String device,
            @DefaultValue("") @QueryParam("provider") String provider,
            @DefaultValue("") @QueryParam("operate_type") String operateType,
            @DefaultValue("") @QueryParam("device_id") String deviceId,
            @DefaultValue("") @QueryParam("info_type") String infoType,
            @DefaultValue("") @QueryParam("count") String count,
            @DefaultValue("") @QueryParam("order") String order)
            throws Exception {
        OperateLogType operateLogType = new OperateLogType();
        Boolean timeasc = false;
        Integer size = 20;
        operateLogType.setResult(result);
        operateLogType.setAppkeyId(appkeyId);
        operateLogType.setDevice(device);
        operateLogType.setDeviceId(deviceId);
        operateLogType.setInfoType(infoType);
        operateLogType.setOperateType(operateType);
        operateLogType.setProvider(provider);
        operateLogType.setUserId(userId);
        if (order.equals("yes")) {
            timeasc = true;
        }
        if (!count.equals("")) {
            size = Integer.valueOf(count);
        }
        Timestamp startTime = startTimeStr.equals("")? null:new Timestamp(Long.valueOf(startTimeStr));
        Timestamp endTime = endTimeStr.equals("")? null:new Timestamp(Long.valueOf(endTimeStr));
        return acOperateLogManager.searchLog(operateLogType, startTime, endTime, size, timeasc);
    }
}
