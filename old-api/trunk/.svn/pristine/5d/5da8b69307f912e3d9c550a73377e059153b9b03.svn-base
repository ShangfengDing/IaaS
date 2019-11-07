package appcloud.api.resources;

import appcloud.api.beans.AcInstanceLog;
import appcloud.api.manager.InstanceLogManager;
import appcloud.common.model.InstanceLogType;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zouji on 2017/11/22.
 */

@Path("{tenantId}/instance-log")
public class InstanceLogResource {

    private InstanceLogManager instanceLogManager;

    public InstanceLogManager getInstanceLogManager() {
        return instanceLogManager;
    }

    public void setInstanceLogManager(InstanceLogManager instanceLogManager) {
        this.instanceLogManager = instanceLogManager;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<AcInstanceLog> index(
            @PathParam("tenantId") String tenantId,
            @DefaultValue("") @QueryParam("start_time") String startTimeStr,
            @DefaultValue("") @QueryParam("end_time") String endTimeStr,
            @DefaultValue("") @QueryParam("user_id") String userId,
            @DefaultValue("") @QueryParam("uuid") String uuid,
            @DefaultValue("") @QueryParam("type") String type,
            @DefaultValue("") @QueryParam("group_id") String groupId,
            @DefaultValue("") @QueryParam("pay_type") String payType,
            @DefaultValue("") @QueryParam("order") String order,
            @DefaultValue("") @QueryParam("count") String count)
            throws Exception {
        System.out.println("uuid ");
        InstanceLogType instanceLogType = new InstanceLogType();
        Boolean timeasc = false;
        Integer size = 20;
        System.out.println("userId: " + userId);
        if (!userId.equals("")) {
            instanceLogType.setUserId(Integer.valueOf(userId));
        }
        if (!uuid.equals("")) {
            instanceLogType.setUuid(uuid);
        }
        if (!type.equals("")) {
            instanceLogType.setType(Enum.valueOf(InstanceLogType.Type.class, type));
        }
        if (!groupId.equals("")) {
            instanceLogType.setGroupId(Integer.valueOf(groupId));
        }
        if (!payType.equals("")) {
            instanceLogType.setPayType(Enum.valueOf(InstanceLogType.PayType.class, payType));
        }
        if (order.equals("yes")) {
            timeasc = true;
        }
        if (!count.equals("")) {
            size = Integer.valueOf(count);
        }
        Timestamp startTime = startTimeStr.equals("")? null:new Timestamp(Long.valueOf(startTimeStr));
        Timestamp endTime = endTimeStr.equals("")? null:new Timestamp(Long.valueOf(endTimeStr));
        System.out.println("1userId: " + userId);
        return instanceLogManager.searchLog(instanceLogType, startTime, endTime, size, timeasc);
    }
}
