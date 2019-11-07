package appcloud.openapi.checkutils;

import appcloud.api.enums.ServerOperationEnum;
import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.exception.OperationFailedException;
import appcloud.common.model.VmInstance;
import appcloud.common.proxy.VmInstanceProxy;
import appcloud.common.util.ConnectionFactory;

import org.apache.log4j.Logger;

import static appcloud.api.enums.ServerOperationEnum.AC_ONLINE_MIGRATE;

/**
 * Created by rain on 2016/12/2.
 */
public class ManagerChecker {
    private static Logger logger = Logger.getLogger(ManagerChecker.class);
    private static VmInstanceProxy instanceProxy = (VmInstanceProxy) ConnectionFactory.getTipProxy(
            VmInstanceProxy.class,
            appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);

    public static Boolean checkOperation(String serverId, ServerOperationEnum operation) throws Exception {
        VmInstance vmInstance = checkReady(serverId);
        VmInstance.VmStatusEnum vmStatus = vmInstance.getVmStatus();
        boolean allowed = false;
        if (vmStatus != null) {
            switch (operation) {
                case AC_ONLINE_MIGRATE:
                    if (vmStatus.equals(VmInstance.VmStatusEnum.ACTIVE))
                        allowed = true;
                    break;
            }
        }
        if (!allowed) {
            logger.info("operation is not allowed now, instance status is " + vmStatus);
        }
        return allowed;
    }

    public static VmInstance checkReady (String serverId) throws Exception {
        if (serverId == null) {
            logger.info("in checkReady: serverId is null");
            throw new OperationFailedException("serverId is NULL");
        }

        VmInstance instance = instanceProxy.getByUuid(serverId, false, false, false, false, false, false, false, false);
        if (instance == null) {
            logger.info("server does not exist");
            throw new ItemNotFoundException("server does not exist");
        }

        if (instance.getTaskStatus() == null) {
            logger.info("in instance : task_status is NULL");
            throw new OperationFailedException("check tast status");
        }
        if (!instance.getTaskStatus().equals(VmInstance.TaskStatusEnum.READY)) {
            logger.info("server is not ready");
            throw new OperationFailedException("please wait");
        }

        return instance;
    }
}
