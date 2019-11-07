package appcloud.openapi.check.impl;

import appcloud.api.enums.ServerOperationEnum;
import appcloud.common.model.VmUser;
import appcloud.common.proxy.HostProxy;
import appcloud.common.proxy.VmInstanceProxy;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.openapi.check.AdminOperateCheck;
import appcloud.openapi.checkutils.InstanceChecker;
import appcloud.openapi.checkutils.ManagerChecker;
import appcloud.openapi.constant.ActionConstants;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.util.CommonGenerator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lizhenhao on 2016/11/16.
 */
@Component
public class AdminOperateCheckImpl implements AdminOperateCheck{
    private static Logger logger = Logger.getLogger(AdminOperateCheckImpl.class);
    private static AdminOperateCheckImpl adminOperateCheck = new AdminOperateCheckImpl();
    public static AdminOperateCheckImpl getInstance() {return adminOperateCheck;}
    private static CommonGenerator commonGenerator  = new CommonGenerator();
    private static VmUserProxy vmUserProxy;
    private static VmInstanceProxy vmInstanceProxy;
    private static HostProxy hostProxy;
    private AdminOperateCheckImpl() {
        super();
        vmUserProxy = (VmUserProxy) ConnectionFactory.getTipProxy(
                VmUserProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
        vmInstanceProxy = (VmInstanceProxy)ConnectionFactory.getTipProxy(
                VmInstanceProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
        hostProxy = (HostProxy)ConnectionFactory.getTipProxy(
                HostProxy.class,
                appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
    }

    @Override
    public Map<String, String> checkAdminInstanceDescribeOperate(Map<String, String> paramsMap) throws Exception {
        logger.info("admin describe instances info permission is OK .");
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }

    @Override
    public Map<String, String> checkAdminVolumeDescribeOperate(Map<String, String> paramsMap) throws Exception {
        logger.info("admin describe volume info permission is OK .");
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }

    @Override
    public Map<String, String> checkAdminServiceOperate(Map<String, String> paramsMap) throws Exception {
        logger.info("admin service info permission is OK .");
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }

    @Override
    public Map<String, String> checkAdminHostOperate(Map<String, String> paramsMap) throws Exception {
        logger.info("admin host info permission is OK .");
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }

    @Override
    public Map<String, String> checkAdminOnlineMigrateOperate(Map<String, String> paramsMap) throws Exception {
        //检查当前云主机的和物理机是不是在同一集群
        logger.info("Check operation permission before online migrate instance");
        if(vmInstanceProxy.getByUuid(paramsMap.get(Constants.INSTANCE_ID), false, true, false, false, false, false, false, false).getAvailabilityClusterId().intValue()
                != hostProxy.getByUuid(paramsMap.get(Constants.HOST_ID), true, false, false).getClusterId().intValue()){
            logger.warn("Instance and host are not be in the same cluster !");
            return commonGenerator.internalError(null, null);
        }
        VmUser user = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
        if(null==user) {
            logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
            return commonGenerator.internalError(null, null);
        }
        ServerOperationEnum operation = ServerOperationEnum.AC_ONLINE_MIGRATE;
        if(null==operation) {
            String message = "Find operation action failed, the internal error.";
            logger.warn(message);
            return commonGenerator.internalError(message, Constants.INTERNAL_ERROR);
        }
        if(!ManagerChecker.checkOperation(paramsMap.get(Constants.INSTANCE_ID), operation)){
            String message = "The current status of the instance does not support this operation.";
            logger.warn(message);
            return commonGenerator.operationDeny(message, Constants.INSTANCE_ID);
        }
        logger.info("Operation permission is OK .");
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }

    @Override
    public Map<String, String> checkAdminDescribeMonitorOperate(Map<String, String> paramsMap) throws Exception {
        logger.info("admin host info permission is OK .");
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }

    @Override
    public Map<String, String> checkAdminAuthorizeImageOperate(Map<String, String> paramsMap) throws Exception {
        logger.info("authorize image info permission is OK .");
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
        return resultMap;
    }
}
