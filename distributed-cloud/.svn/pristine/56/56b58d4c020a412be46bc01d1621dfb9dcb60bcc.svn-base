import appcloud.distributed.CloudController;
import appcloud.distributed.configmanager.RouteInfo;
import appcloud.distributed.configmanager.RouteInfoManager;
import appcloud.distributed.helper.HelperLoader;
import appcloud.distributed.nativeConfig.DnsContentConfig;
import appcloud.distributed.util.StringUtil;
import appcloud.distributed.util.XmlUtil;
import appcloud.netty.remoting.RemoteServer;
import appcloud.netty.remoting.remote.NettyRemotingServer;

import java.util.Map;

/**
 * Created by Idan on 2018/4/11.
 */
public class CloudStartUp_2 {

    private static String CLOUD_PATH = "/cloud_properties_2.xml";

    public static void main(String[] args) {

        HelperLoader.init();
        Map<String,RouteInfo> map = XmlUtil.getAllRouteInfo(CLOUD_PATH);
        RouteInfo masterInfo = map.get(XmlUtil.MASTER);
        RouteInfo localInfo = map.get(XmlUtil.LOCAL);
        RemoteServer remoteServer;
        if (StringUtil.isEmpty(localInfo.getPort())) {
            remoteServer = new NettyRemotingServer();
        } else {
            remoteServer = new NettyRemotingServer(Integer.valueOf(localInfo.getPort()));
        }
        DnsContentConfig dnsContentConfig = new DnsContentConfig();
        RouteInfoManager routeInfoManager = RouteInfoManager.getRouteInfoManager();
        routeInfoManager.IntRouteInfoManager(localInfo, masterInfo);
        routeInfoManager.putRouteInfo(localInfo);
        CloudController cloudController = new CloudController(routeInfoManager,dnsContentConfig,remoteServer,masterInfo.getAddr().equals(localInfo.getAddr()),null);

        cloudController.start();

    }

}
