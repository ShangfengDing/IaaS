package appcloud.distributed.service;

import appcloud.distributed.CloudControllerClientWapper;
import appcloud.distributed.configmanager.RouteInfo;
import appcloud.distributed.configmanager.RouteInfoManager;
import appcloud.distributed.header.VmBackCheckHeader;
import appcloud.distributed.util.StringUtil;
import appcloud.netty.remoting.common.ResponseCode;
import appcloud.netty.remoting.protocol.RemoteCommand;
import com.distributed.common.response.BaseResponse;
import com.distributed.common.response.util.HttpConstants;
import com.distributed.common.utils.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lizhenhao on 2018/1/5.
 *
 * 备份虚拟机
 */
public class BackUpVmClientImpl implements com.distributed.common.service.controller.BackUpVmClient {

    RouteInfoManager routeInfoManager = RouteInfoManager.getRouteInfoManager();
    CloudControllerClientWapper clientWapper = CloudControllerClientWapper.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(BackUpVmClientImpl.class);


    /**
     * 备份操作
     * 1. 询问源数据中心是否已经有备份的目的地址了，
     * 2. 若没有则选择合适的数据中心（如果数据库中没有，则需要一定的策略去选择）
     * 3. 如果能将会把instance的信息备份到appcloud_distribution中 instance_back_info中，同时返回将备份的物理机ip和host等
     * 4. 向镜像源数据中心发送 发送镜像的请求
     * @param requestId
     * @param diskId
     * @param needToClean
     * @return
     */
    @Override
    public BaseResponse backUpVm(String requestId, String sourceDataCenter, String instanceId, String diskId, boolean needToClean, String appkeyId, String appkeySecret, String userEmail, String accountName, String destDataCenter) {
        if (StringUtil.isEmpty(sourceDataCenter)) {
            logger.info("the dataDataCenter is null");
            return new BaseResponse(requestId, HttpConstants.STATUS_FORBIDDEN, "dataDataCenter为空");
        }
        RouteInfo sourceRouteInfo = routeInfoManager.getRouteInfo(sourceDataCenter);
        if (ModelUtil.isEmpty(sourceRouteInfo)) {
            logger.info("the routeInfo does not exist, the dataCenter: "+sourceDataCenter);
            return new BaseResponse(requestId, HttpConstants.STATUS_FORBIDDEN, "源数据中心尚未注册到集群");
        }
        logger.info("the routeInfo for source, "+sourceRouteInfo.toString());
        // 确认有多个可以备份的数据中心
        if (routeInfoManager.getAllRouteInfo().size() <= 1) {
            logger.info("the system has too little dataCenter");
            return new BaseResponse(requestId, HttpConstants.STATUS_FORBIDDEN, "集群中平台数量太小，暂不支持备份");
        }
        // step 1
        RemoteCommand checkResponse = clientWapper.sendReadyToTransFileRequest(requestId, sourceRouteInfo.getAddr(), instanceId, diskId, needToClean, appkeyId, appkeySecret, userEmail, accountName, destDataCenter);
        if (!checkResponse.getRemark().equals(ResponseCode.SUCCESSED)) {
            return new BaseResponse(requestId, HttpConstants.STATUS_INTERNAL_SERVER_ERROR, "准备备份通信出错");
        }
        logger.info("备份数据结果"+checkResponse.toString());
        checkResponse.decodeConsumerHeader(VmBackCheckHeader.class);
        VmBackCheckHeader vmBackCheckHeader = (VmBackCheckHeader) checkResponse.getConsumHeader();
        if (ModelUtil.isEmpty(vmBackCheckHeader)) {
            return new BaseResponse(requestId, HttpConstants.STATUS_INTERNAL_SERVER_ERROR, "备份过程出错");
        } else {
            return new BaseResponse(requestId, HttpConstants.STATUS_OK, null);
        }
    }

}
