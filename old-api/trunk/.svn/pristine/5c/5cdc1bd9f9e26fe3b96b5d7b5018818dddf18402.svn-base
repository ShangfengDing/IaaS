package appcloud.api.client;

import appcloud.api.beans.AcInstanceLog;
import appcloud.common.model.InstanceLogType;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MultivaluedMap;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zouji on 2017/11/22.
 */
public class InstanceLogClient extends AbstractClient<AcInstanceLog>{

    public InstanceLogClient(String baseURI) {
        super(baseURI);
    }

    @Override
    protected Class<?> getType() {
        return AcInstanceLog.class;
    }

    @Override
    protected GenericType<List<AcInstanceLog>> getGenericType() {
        GenericType<List<AcInstanceLog>> type = new GenericType<List<AcInstanceLog>>(){};
        return type;
    }

    private String getPath() {
        return "instance-log";
    }

    protected String buildPath() {
        return String.format("admin/%s", getPath());
    }

    public List<AcInstanceLog> searchLog(
            AcInstanceLog acInstanceLog, Timestamp startTime, Timestamp endTime,
            Integer size, Boolean timeasc) {
        if (startTime != null && endTime != null) {
            if (startTime.getTime() > endTime.getTime())
                return null;
        }
        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        if (acInstanceLog != null) {
            if (acInstanceLog.userId != null)
                params.add("user_id", acInstanceLog.userId.toString());
            if (acInstanceLog.type != null)
                params.add("type", acInstanceLog.type.toString());
            if (acInstanceLog.payType != null)
                params.add("pay_type", acInstanceLog.payType.toString());
            if (acInstanceLog.groupId != null)
                params.add("group_id", acInstanceLog.groupId.toString());
            if (acInstanceLog.uuid != null)
                params.add("uuid", acInstanceLog.uuid);
        }
        if (startTime != null) {
            params.add("start_time", String.valueOf(startTime.getTime()));
        }
        if (endTime != null) {
            params.add("end_time", String.valueOf(endTime.getTime()));
        }
        if (size != null) {
            params.add("count", size.toString());
        }
        if (timeasc) {
            params.add("order", "yes");
        }
        return super.getList(buildPath(), params);
    }
}
