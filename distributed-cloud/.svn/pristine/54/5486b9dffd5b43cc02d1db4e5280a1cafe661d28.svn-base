package appcloud.distributed.service;

import appcloud.distributed.common.Constants;
import appcloud.distributed.configmanager.RouteInfo;
import appcloud.distributed.configmanager.RouteInfoManager;
import com.distributed.common.entity.CloudInfo;
import com.distributed.common.service.controller.RouteInfoService;
import com.distributed.common.utils.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by zouji on 2018/1/10.
 */
public class RouteInfoServiceImpl implements RouteInfoService {

    private final Logger logger = LoggerFactory.getLogger(RouteInfoServiceImpl.class);
    private RouteInfoManager routeInfoManager = RouteInfoManager.getRouteInfoManager();


    public Set<String> findRegionIds() {
        Set<String> regionIds = new HashSet<String>();
        regionIds.add(Constants.REGION_ID);
        return regionIds;
    }

    public List<Map<String, String>> findProductDomains() {
        List<Map<String, String>> productDomainList = new ArrayList<Map<String, String>>();
        List<RouteInfo> routeInfoList = routeInfoManager.getAllRouteInfo();
        for (RouteInfo routeInfo : routeInfoList) {
            Map<String, String> productDomain = new HashMap<String, String>();
            productDomain.put("productName", routeInfo.getAddr());
            productDomain.put("zoneId", routeInfo.getDataCenter());
            productDomain.put("domainName", routeInfo.getDomainName());
            productDomainList.add(productDomain);
        }
        return productDomainList;
    }

    public Map<String, String> findOwnRouteInfo() {
        RouteInfo routeInfo = routeInfoManager.getOwnRouteInfo();
        if (ModelUtil.isEmpty(routeInfo)) {
            logger.info("暂时无法获得本地的routeInfo");
            return null;
        }
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("addr", routeInfo.getAddr());
        resultMap.put("zoneId", routeInfo.getDataCenter());
        return resultMap;
    }

    @Override
    public List<CloudInfo> findAllCloudInfo() {
        List<CloudInfo> cloudInfoList = new ArrayList<>();
        List<RouteInfo> routeInfoList = routeInfoManager.getAllRouteInfo();
        RouteInfo masterRouteInfo = routeInfoManager.getMasterRouteInfo();
        logger.info("the master route: "+masterRouteInfo);
        for (RouteInfo routeInfo : routeInfoList) {
            String role = masterRouteInfo.getDataCenter().equals(routeInfo.getDataCenter()) ? "master" : "follower";
            CloudInfo cloudInfo = new CloudInfo(routeInfo.getIpAddress(), routeInfo.getPort(), routeInfo.getDomainName(), routeInfo.getAddr(), routeInfo.getDataCenter(), routeInfo.getUuid(), role);
            cloudInfoList.add(cloudInfo);
        }
        return cloudInfoList;
    }

    @Override
    public CloudInfo findMonitorCloudInfo() {
        RouteInfo routeInfo = routeInfoManager.getMonitorRoute();
        RouteInfo masterRouteInfo = routeInfoManager.getMasterRouteInfo();
        if (routeInfo != null) {
            String role = masterRouteInfo.getDataCenter().equals(routeInfo.getDataCenter()) ? "master" : "follower";
            return new CloudInfo(routeInfo.getIpAddress(), routeInfo.getPort(), routeInfo.getDomainName(), routeInfo.getAddr(), routeInfo.getDataCenter(), routeInfo.getUuid(), role);
        }
        else {
            return null;
        }
    }


}
