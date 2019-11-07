package appcloud.api.manager.real;

import appcloud.api.beans.AcInstanceLog;
import appcloud.api.manager.InstanceLogManager;
import appcloud.api.manager.util.BeansGenerator;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.InstanceLogType;
import appcloud.common.service.LolLogService;
import appcloud.common.util.ConnectionFactory;
import appcloud.rpc.tools.RpcException;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouji on 2017/11/22.
 */
public class RealInstanceLogManager implements InstanceLogManager{

    private static Logger logger = Logger.getLogger(RealInstanceLogManager.class);
    private BeansGenerator generator;
    private LolLogService lol;

    private static RealInstanceLogManager manager = new RealInstanceLogManager();

    private RealInstanceLogManager() {
        super();
        generator = BeansGenerator.getInstance();
        lol = (LolLogService) ConnectionFactory.getAMQPService(LolLogService.class, RoutingKeys.LOL_SERVER);
    }

    public static RealInstanceLogManager getInstance() {
        return manager;
    }

    @Override
    public List<AcInstanceLog> searchLog(InstanceLogType instanceLogType, Timestamp startTime, Timestamp endTime, Integer size, Boolean timeasc) throws RpcException {
        logger.info(String.format("Administrator request to search log, %s, startTime:%s, endTime:%s, size:%d", instanceLogType,
                startTime, endTime, size));
        System.out.println("1userId: " + instanceLogType.getUserId());
        List<InstanceLogType> instanceLogTypeList = lol.searchInstanceLog(instanceLogType, startTime, endTime, size, timeasc);
        List<AcInstanceLog> acInstanceLogList = new ArrayList<AcInstanceLog>();
        if (instanceLogTypeList != null && instanceLogTypeList.size() != 0) {
            for (InstanceLogType instanceLog : instanceLogTypeList) {
                acInstanceLogList.add(generator.instanceLogTypeToAcInstanceLog(instanceLog));
            }
        }
        return acInstanceLogList;
    }
}
