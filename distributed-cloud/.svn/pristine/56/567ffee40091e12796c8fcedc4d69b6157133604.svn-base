package appcloud.distributed.header;

import appcloud.distributed.configmanager.RouteInfo;
import appcloud.netty.remoting.common.ConsumerHeader;

/**
 * Created by zouji on 2017/12/12.
 */
public class ChkConnectionHeader extends ConsumerHeader {

    private RouteInfo masterInfo;

    public ChkConnectionHeader() {
        super();
    }

    public ChkConnectionHeader(RouteInfo masterInfo) {
        super();
        this.masterInfo = masterInfo;
    }


    public void checkFileds(){};

    public RouteInfo getMasterInfo() {
        return masterInfo;
    }

    public void setMasterInfo(RouteInfo masterInfo) {
        this.masterInfo = masterInfo;
    }
}
