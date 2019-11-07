package appcloud.api.client;

import appcloud.api.beans.AcOperateLog;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MultivaluedMap;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zouji on 2017/11/24.
 */
public class AcOperateLogClient extends AbstractClient<AcOperateLog>{

    public AcOperateLogClient(String baseURI) {
        super(baseURI);
    }

    @Override
    protected Class<?> getType() {
        return AcOperateLog.class;
    }

    @Override
    protected GenericType<List<AcOperateLog>> getGenericType() {
        GenericType<List<AcOperateLog>> type = new GenericType<List<AcOperateLog>>(){};
        return type;
    }

    private String getPath() {
        return "operate-log";
    }

    protected String buildPath() {
        return String.format("admin/%s", getPath());
    }

    public List<AcOperateLog> searchLog(
            AcOperateLog acOperateLog, Timestamp startTime, Timestamp endTime,
            Integer size, Boolean timeasc) {
        if (startTime != null && endTime != null) {
            if (startTime.getTime() > endTime.getTime())
                return null;
        }
        MultivaluedMap<String, String> params = new MultivaluedMapImpl();
        if (acOperateLog != null) {
            if (acOperateLog.result != null) {
                params.add("result", acOperateLog.result);
            }
            if (acOperateLog.appkeyId != null) {
                params.add("appkey_id", acOperateLog.appkeyId);
            }
            if (acOperateLog.userId != null) {
                params.add("user_id", acOperateLog.userId);
            }
            if (acOperateLog.device != null) {
                params.add("device", acOperateLog.device);
            }
            if (acOperateLog.provider != null) {
                params.add("provider", acOperateLog.provider);
            }
            if (acOperateLog.operateType != null) {
                params.add("operate_type", acOperateLog.operateType);
            }
            if (acOperateLog.deviceId != null) {
                params.add("device_id", acOperateLog.deviceId);
            }
            if (acOperateLog.infoType != null) {
                params.add("info_type", acOperateLog.infoType);
            }
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
