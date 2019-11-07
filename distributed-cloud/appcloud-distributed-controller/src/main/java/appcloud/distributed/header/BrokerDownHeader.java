package appcloud.distributed.header;

import appcloud.distributed.configmanager.RouteInfo;
import appcloud.netty.remoting.common.ConsumerHeader;
import lombok.Data;

/**
 * Created by lizhenhao on 2017/12/5.
 */
@Data
public class BrokerDownHeader extends ConsumerHeader {

    /**
     * 宕机平台的IP，port以及域名
     */
    private RouteInfo downBrokerInfo;

    /**
     * 附加的master信息，如果masterAlive为false，则表示宕机平台是master，发送新master的IP和Port
     */

    private boolean masterAlive;

    private RouteInfo masterInfo;


    public BrokerDownHeader() {
        super();
    }

    public BrokerDownHeader(RouteInfo masterInfo,boolean masterAlive) {
        super();
        this.masterInfo = masterInfo;
        this.masterAlive = masterAlive;
    }

    public BrokerDownHeader(RouteInfo downBrokerInfo,RouteInfo masterInfo,boolean masterAlive) {
        this.downBrokerInfo = downBrokerInfo;
        this.masterInfo = masterInfo;
        this.masterAlive = masterAlive;

    }
    public void checkFileds(){};
}
