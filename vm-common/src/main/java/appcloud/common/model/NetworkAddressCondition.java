package appcloud.common.model;

import java.util.List;


/**
 * @ClassName: NetworkAddressConditon
 * @Description: TODO
 * @author wenchaoz361
 * @date 2013-4-22 上午11:17:06
 */
public class NetworkAddressCondition {
    private List<Subnet> subnets;
    private List<RouteItem> routeItems;
    
    public NetworkAddressCondition() {
		super();
	}
    
    /**
    * <p>Title: </p>
    * <p>Description: </p>
    * @param subnets
    * @param routeItems
    */ 
    public NetworkAddressCondition(List<Subnet> subnets, List<RouteItem> routeItems) {
        super();
        this.subnets = subnets;
        this.routeItems = routeItems;
    }
    
	public List<Subnet> getSubnets() {
        return subnets;
    }
    public void setSubnets(List<Subnet> subnets) {
        this.subnets = subnets;
    }
    public List<RouteItem> getRouteItems() {
        return routeItems;
    }
    public void setRouteItems(List<RouteItem> routeItems) {
        this.routeItems = routeItems;
    }

	@Override
	public String toString() {
		return "NetworkAddressCondition [subnets=" + subnets + ", routeItems="
				+ routeItems + "]";
	}
}
