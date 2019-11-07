package appcloud.api.manager.real;

import appcloud.api.beans.AcOperateLog;
import appcloud.api.manager.AcOperateLogManager;
import appcloud.api.manager.util.BeansGenerator;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.OperateLogType;
import appcloud.common.service.LolLogService;
import appcloud.common.util.ConnectionFactory;
import appcloud.rpc.tools.RpcException;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouji on 2017/11/24.
 */
public class RealAcOperateLogManager implements AcOperateLogManager{

    private static Logger logger = Logger.getLogger(RealAcOperateLogManager.class);
    private BeansGenerator generator;
    private LolLogService lol;

    private static RealAcOperateLogManager manager = new RealAcOperateLogManager();

    private RealAcOperateLogManager() {
        super();
        generator = BeansGenerator.getInstance();
        lol = (LolLogService) ConnectionFactory.getAMQPService(LolLogService.class, RoutingKeys.LOL_SERVER);
    }

    public static RealAcOperateLogManager getInstance() {
        return manager;
    }

    @Override
    public List<AcOperateLog> searchLog(OperateLogType operateLogType, Timestamp startTime, Timestamp endTime, Integer size, Boolean timeasc) throws RpcException {
        logger.info(String.format("Administrator request to search log, %s, startTime:%s, endTime:%s, size:%d", operateLogType,
                startTime, endTime, size));
        List<OperateLogType> operateLogTypeList = lol.searchOperateLog(operateLogType, startTime, endTime, size, timeasc);
        List<AcOperateLog> acOperateLogList = new ArrayList<AcOperateLog>();
        if (operateLogTypeList != null && operateLogTypeList.size() != 0) {
            for (OperateLogType operateLog : operateLogTypeList) {
                acOperateLogList.add(generator.operateLogTypeToAcOperateLog(operateLog));
            }
        }
        return acOperateLogList;
    }
}
