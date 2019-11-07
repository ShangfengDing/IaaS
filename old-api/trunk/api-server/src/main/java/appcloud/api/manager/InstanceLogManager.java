package appcloud.api.manager;

import appcloud.api.beans.AcInstanceLog;
import appcloud.common.model.InstanceLogType;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zouji on 2017/11/22.
 */
public interface InstanceLogManager {

    public List<AcInstanceLog> searchLog(InstanceLogType instanceLogType, Timestamp startTime, Timestamp endTime, Integer size, Boolean timeasc) throws Exception;
}
