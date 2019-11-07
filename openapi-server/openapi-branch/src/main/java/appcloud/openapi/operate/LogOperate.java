package appcloud.openapi.operate;

import appcloud.common.model.OperateLogType;
import appcloud.openapi.datatype.OperateLogItem;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by zouji on 2017/11/1.
 */
public interface LogOperate {

    /**
     * 保存操作日志
     * @param  operateLogType 接口参数
     * @param requestId 操作接口请求id，可当做Rpc的transactionId
     * @return Response
     * @throws Exception
     */
    Map<String, String> saveOperateLog(OperateLogType operateLogType, String requestId) throws Exception;

    /**
     *
     * @param operateLogType
     * @param startTime
     * @param endTime
     * @param size
     * @param timeasc
     * @param requestId
     * @return
     * @throws Exception
     */
    List<OperateLogItem> searchOperateLog(OperateLogType operateLogType, Timestamp startTime, Timestamp endTime, Integer size, Boolean timeasc, String requestId) throws Exception;
}
