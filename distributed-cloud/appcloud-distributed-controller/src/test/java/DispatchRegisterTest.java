import appcloud.distributed.CloudControllerClientWapper;
import appcloud.distributed.configmanager.RouteInfo;
import appcloud.distributed.util.StringUtil;
import appcloud.netty.remoting.RemoteServer;
import appcloud.netty.remoting.remote.NettyRemotingClient;
import appcloud.netty.remoting.remote.NettyRemotingServer;
import com.distributed.common.utils.UuidUtil;

import java.util.Random;

/**
 * Created by lizhenhao on 2017/12/28.
 */
public class DispatchRegisterTest {

    public final static int cons = 9000;

    static class TestController implements Runnable {



        public void run() {

            Random random = new Random();
            int port = random.nextInt(cons);
            RouteInfo localInfo = new RouteInfo();
            localInfo.setIpAddress("127.0.0.1");
            localInfo.setPort(String.valueOf(port));
            localInfo.setDataCenter("cloud slave"+String.valueOf(port));
            localInfo.setAddr("127.0.0.1:" + String.valueOf(port));
            localInfo.setUuid(UuidUtil.getRandomUuid());

            RouteInfo masterInfo = new RouteInfo();
            masterInfo.setPort("8118");
            masterInfo.setIpAddress("127.0.0.1");
            masterInfo.setDataCenter("slave");
            masterInfo.setDomainName("");
            masterInfo.setAddr("127.0.0.1:8118");

            RemoteServer remoteServer;
            if (StringUtil.isEmpty(localInfo.getPort())) remoteServer = new NettyRemotingServer();
            else remoteServer = new NettyRemotingServer(Integer.valueOf(localInfo.getPort()));

//            RouteInfoManager routeInfoManager = new RouteInfoManager(localInfo,masterInfo);
            CloudControllerClientWapper cloudControllerClientWapper = new CloudControllerClientWapper(new NettyRemotingClient());
//            CloudController cloudController = new CloudController(routeInfoManager,null,remoteServer,cloudControllerClientWapper,false);

//            cloudController.start();

        }
    }

    public static void main(String[] args) {
        int threadNumber = 1;
        Thread[] threads = new Thread[threadNumber];
        for (int i = 0 ;i<threadNumber;i++) {
            threads[i]  = new Thread(new TestController());
            threads[i].start();
        }

    }

}
