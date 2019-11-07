package appcloud.openapi.operate.impl;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.InstanceLogType;
import appcloud.common.service.LolLogService;
import appcloud.common.util.ConnectionFactory;
import appcloud.openapi.operate.InstanceLogOperate;
import appcloud.rpc.tools.RpcException;
import com.appcloud.vm.fe.model.VmHdEndtime;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouji on 2017/11/13.
 */
@Component
public class InstanceLogOperateImpl implements InstanceLogOperate {

    private static Logger logger = Logger.getLogger(InstanceLogOperateImpl.class);

    private LolLogService lolLogService;

    public InstanceLogOperateImpl() {
        super();
        lolLogService = (LolLogService) ConnectionFactory
                .getAMQPService(LolLogService.class,
                        RoutingKeys.LOL_SERVER);
    }

    public void add(VmHdEndtime vmHdEndtime, Timestamp createdTime, Integer groupId) {
        //RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), vmHdEndtime.getUserId().toString());
        try {
            lolLogService.addInstanceLog(VmHdEndTimeToInstanceLogType(vmHdEndtime, groupId, createdTime));
        } catch (RpcException e) {
            e.printStackTrace();
            logger.info("save instanceLog!");
        }
    }

    public List<InstanceLogType> search(VmHdEndtime vmHdEndtime, Timestamp createdTime, Integer groupId, Timestamp startTime, Timestamp endTime, Integer size, Boolean timeasc) {
        List<InstanceLogType> instanceLogTypeList = new ArrayList<>();
        //RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), "null");
        try {
            instanceLogTypeList = lolLogService.searchInstanceLog(VmHdEndTimeToInstanceLogType(vmHdEndtime, groupId, createdTime), startTime, endTime, size, timeasc);
        } catch (RpcException e) {
            e.printStackTrace();
            logger.info("search instanceLog!");
        }
        return instanceLogTypeList;
    }

    public void update(VmHdEndtime vmHdEndtime, Timestamp createdTime, Integer groupId) {
        try {
            lolLogService.updateInstanceLog(VmHdEndTimeToInstanceLogType(vmHdEndtime, groupId, createdTime));
        } catch (RpcException e) {
            e.printStackTrace();
            logger.info("update instanceLog");
        }
    }

    private InstanceLogType VmHdEndTimeToInstanceLogType(VmHdEndtime vmHdEndtime, Integer groupId, Timestamp createdTime) {
        InstanceLogType instanceLogType = new InstanceLogType();
        if (vmHdEndtime.getUserId() != null) {
            instanceLogType.setUserId(vmHdEndtime.getUserId());
        }
        if (vmHdEndtime.getEndTime() != null) {
            instanceLogType.setEndTime(new Timestamp(vmHdEndtime.getEndTime().getTime()));
        }
        if (!StringUtils.isEmpty(vmHdEndtime.getPayType())) {
            instanceLogType.setPayType(Enum.valueOf(InstanceLogType.PayType.class, vmHdEndtime.getPayType()));
        }
        if (!StringUtils.isEmpty(vmHdEndtime.getType())) {
            instanceLogType.setType(Enum.valueOf(InstanceLogType.Type.class, vmHdEndtime.getType()));
        }
        if (!StringUtils.isEmpty(vmHdEndtime.getUuid())) {
            instanceLogType.setUuid(vmHdEndtime.getUuid());
        }
        instanceLogType.setCreatedTime(createdTime);
        instanceLogType.setGroupId(groupId);
        return instanceLogType;
    }
}
