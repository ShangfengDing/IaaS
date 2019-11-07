package appcloud.openapi.operate.impl;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.OperateLogType;
import appcloud.common.model.RpcExtra;
import appcloud.common.service.LolLogService;
import appcloud.common.util.ConnectionFactory;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.datatype.OperateLogItem;
import appcloud.openapi.operate.LogOperate;
import appcloud.rpc.tools.RpcException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zouji on 2017/11/1.
 */
@Component
public class LogOperateImpl implements LogOperate {

    private static Logger logger = Logger.getLogger(LogOperateImpl.class);

    private LolLogService lolLogService;
    public LogOperateImpl() {
        super();
        lolLogService = (LolLogService) ConnectionFactory
                .getAMQPService(LolLogService.class,
                        RoutingKeys.LOL_SERVER);
    }

    public Map<String, String> saveOperateLog(OperateLogType operateLogType, String requestId) {
        Map<String, String> resultMap = new HashMap<>();
        logger.info("saveOpereateLog:" + operateLogType);
        if (null == requestId || requestId.length() < 1) {
            logger.info("RequestId is null or is error!");
            resultMap.put(Constants.ERRORCODE, Constants.INTERNAL_ERROR);
            resultMap.put(Constants.ERRORMESSAGE, "The request processing has failed due to some unknown error.");
            resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_INTERNAL_SERVER_ERROR + "");
            return resultMap;
        }
        try {
            lolLogService.addOperateLog(operateLogType);
        } catch (RpcException e) {
            e.printStackTrace();
            logger.info("save log failed!");
        }
        return resultMap;
    }

    public List<OperateLogItem> searchOperateLog(OperateLogType operateLogType, Timestamp startTime, Timestamp endTime, Integer size, Boolean timeasc, String requestId) {
        List<OperateLogItem> operateLogItemList = new ArrayList<>();
        logger.info("searchOperateLog:" + operateLogType);
        try {
            List<OperateLogType> operateLogTypeList = lolLogService.searchOperateLog(operateLogType, startTime, endTime, size, timeasc);
            for (OperateLogType operateLogTypeTmp : operateLogTypeList) {
                operateLogItemList.add(new OperateLogItem(operateLogTypeTmp.getResult(),
                        operateLogTypeTmp.getAppkeyId(),
                        operateLogTypeTmp.getUserId(),
                        operateLogTypeTmp.getDevice(),
                        operateLogTypeTmp.getProvider(),
                        operateLogTypeTmp.getOperateType(),
                        operateLogTypeTmp.getDeviceId(),
                        operateLogTypeTmp.getInfoType(),
                        operateLogTypeTmp.getCreatedTime() == null? null:String.valueOf(operateLogTypeTmp.getCreatedTime().getTime())));
            }
        } catch (RpcException e) {
            logger.info("search failed!");
            e.printStackTrace();
        }
        return operateLogItemList;
    }
}
