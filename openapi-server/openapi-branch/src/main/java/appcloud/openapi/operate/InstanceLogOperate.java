package appcloud.openapi.operate;

import appcloud.common.model.InstanceLogType;
import com.appcloud.vm.fe.model.VmHdEndtime;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zouji on 2017/11/13.
 */
public interface InstanceLogOperate {

    void add(VmHdEndtime vmHdEndtime, Timestamp createdTime, Integer groupId);

    List<InstanceLogType> search(VmHdEndtime vmHdEndtime, Timestamp createdTime, Integer groupId, Timestamp startTime, Timestamp endTime, Integer size, Boolean timeasc);

    void update(VmHdEndtime vmHdEndtime, Timestamp createdTime, Integer groupId);
}
