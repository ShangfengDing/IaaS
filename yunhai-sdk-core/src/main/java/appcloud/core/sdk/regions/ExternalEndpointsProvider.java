package appcloud.core.sdk.regions;



import com.distributed.common.factory.ControllerFactory;
import com.distributed.common.service.controller.RouteInfoService;

import java.util.*;

/**
 * Created by zouji on 2018/1/11.
 * 访问分布式云的endpoints
 */
public class ExternalEndpointsProvider implements IEndpointsProvider{

    private RouteInfoService routeInfoService = ControllerFactory.getRouteInfoService();
    public static void main(String[] args) {
        ExternalEndpointsProvider externalEndpointsProvider = new ExternalEndpointsProvider();
        System.out.println(externalEndpointsProvider.getEndpoints());
    }
    public List<Endpoint> getEndpoints() {
        List<Endpoint> endpointList = new ArrayList<>();
        Set<String> regionIds = routeInfoService.findRegionIds();
        List<ProductDomain> productDomainList = new ArrayList<>();
        for (Map<String, String> tmp : routeInfoService.findProductDomains()) {
            String productName = "";
            String zoneId = "";
            String domainName = "";
            for (String key : tmp.keySet()) {
                if (key.equals("productName")) {
                    //TODO 2018/3/26 此处需要商榷
                    productName = "iaas";
                } else if (key.equals("zoneId")) {
                    zoneId = tmp.get(key);
                } else if (key.equals("domainName")) {
                    domainName = tmp.get(key);
                }
            }
            ProductDomain productDomain = new ProductDomain(productName, zoneId, domainName);
            productDomainList.add(productDomain);
        }
        Endpoint endpoint = new Endpoint("iaas", regionIds, productDomainList);
        endpointList.add(endpoint);
        return endpointList;
    }
}
