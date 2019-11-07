package appcloud.openapi.client;

//import appcloud.distributed.service.RouteInfoServiceImpl;
import appcloud.openapi.response.BackUpVmResponse;
import appcloud.openapi.response.RouteInfoResponse;
import com.distributed.common.entity.CloudInfo;
import com.distributed.common.factory.ControllerFactory;
import com.distributed.common.service.controller.AccountClient;
import com.distributed.common.service.controller.BackUpVmClient;
import com.distributed.common.service.controller.RouteInfoService;
import com.distributed.common.response.BaseResponse;
import com.distributed.common.utils.UuidUtil;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ControllerClient {

    public static Logger logger = Logger.getLogger(ControllerClient.class);
    AccountClient accountClient = ControllerFactory.getAccountClient();
    BackUpVmClient backUpVmClient = ControllerFactory.getBackUpVmClient();
    RouteInfoService routeInfo = ControllerFactory.getRouteInfoService();

    public ControllerClient(){}

    /**
     * 创建用户
     */
    public BaseResponse userCreate(String regionId, String zoneId, String newUserEmail, String groupSecretKey, String appkeyId, String appkeySecret, String userEmail,String accountName){
        BaseResponse response = accountClient.userCreate(regionId,zoneId,newUserEmail,groupSecretKey,appkeyId,appkeySecret,userEmail,accountName);
        return response;
    }

    /**
     * 创建备份
     */
    public BackUpVmResponse backUpVm( String sourceDataCenter, String instanceId,String diskId, boolean needToClean, String appkeyId, String appkeySecret, String userEmail,String accountName, String destDataCenter){

        BackUpVmResponse backUpVmResponse = new BackUpVmResponse();
        String requestId = UuidUtil.getRandomUuid();
        BaseResponse result = backUpVmClient.backUpVm(requestId,sourceDataCenter,instanceId,diskId,needToClean,appkeyId,appkeySecret,userEmail,accountName,destDataCenter);
        if (null != result && result.getMessage() == null) {
            backUpVmResponse.setBackUpResult(true);
        }
        backUpVmResponse.setBackUpResult(false);

        return backUpVmResponse;
    }

    /**
     * 获得路由信息
     */
    public RouteInfoResponse routeInfo(){
        RouteInfoResponse routeInfoResponse = new RouteInfoResponse();
        Set<String> RegionIds = routeInfo.findRegionIds();
        List<Map<String, String>> ProductDomains = routeInfo.findProductDomains();
        routeInfoResponse.setRegionIds(RegionIds);
        routeInfoResponse.setProductDomains(ProductDomains);
        return routeInfoResponse;
    }

    public Map<String, String> findOwnRouteInfo() {
        Map<String , String> resultMap = routeInfo.findOwnRouteInfo();
        return resultMap;
    }

    public List<CloudInfo> findAllCloudInfo() {
        return routeInfo.findAllCloudInfo();
    }

    public CloudInfo findMonitorCloudInfo() {
        return routeInfo.findMonitorCloudInfo();
    }
}
