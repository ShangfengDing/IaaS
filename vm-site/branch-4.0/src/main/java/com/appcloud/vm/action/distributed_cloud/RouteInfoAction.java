package com.appcloud.vm.action.distributed_cloud;

import appcloud.openapi.client.ControllerClient;
import appcloud.openapi.response.RouteInfoResponse;
import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.util.OpenClientFactory;
import com.distributed.common.service.controller.RouteInfoService;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RouteInfoAction extends BaseAction {

    private Logger logger = Logger.getLogger(RouteInfoAction.class);
    private ControllerClient controllerClient = OpenClientFactory.getControllerClient();
    private Set<String> regionIds;
    private List<Map<String, String>> productDomain;

    public String execute() {
        RouteInfoResponse routeInfoResponse = controllerClient.routeInfo();
        regionIds = routeInfoResponse.getRegionIds();
        if(regionIds.isEmpty()){
            logger.info("get regionId failed! ");
        } else {
            logger.info("success get regionId! ");
        }

        productDomain = routeInfoResponse.getProductDomains();
        if(productDomain.isEmpty()){
            logger.info("get product domain failed! ");
        } else {
            logger.info("success get product domain! ");
        }


        return SUCCESS;
    }

    public Set<String> getRegionIds() {
        return regionIds;
    }

    public void setRegionIds(Set<String> regionIds) {
        this.regionIds = regionIds;
    }

    public List<Map<String, String>> getProductDomain() {
        return productDomain;
    }

    public void setProductDomain(List<Map<String, String>> productDomain) {
        this.productDomain = productDomain;
    }
}
