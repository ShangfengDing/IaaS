package appcloud.distributed.header;

import appcloud.distributed.configmanager.RouteInfo;
import appcloud.netty.remoting.common.ConsumerHeader;

import java.util.List;

/**
 * Created by lizhenhao on 2017/11/22.
 */
public class DispatchRegisterHeader extends ConsumerHeader {

    private List<RouteInfo> routeInfoConfigList;

    private RouteInfo masterInfo;

    public DispatchRegisterHeader(){
        super();
    }

    public DispatchRegisterHeader(List<RouteInfo> routeInfoConfigList, RouteInfo master) {
        this.routeInfoConfigList = routeInfoConfigList;
        this.masterInfo = master;
    }


    public void checkFileds() {

    }

    public List<RouteInfo> getRouteInfoConfigList() {
        return routeInfoConfigList;
    }

    public void setRouteInfoConfigList(List<RouteInfo> routeInfoConfigList) {
        this.routeInfoConfigList = routeInfoConfigList;
    }

    public RouteInfo getMasterInfo() {
        return masterInfo;
    }

    public void setMasterInfo(RouteInfo masterInfo) {
        this.masterInfo = masterInfo;
    }
}
