import appcloud.distributed.CloudControllerClientWapper;
import appcloud.distributed.configmanager.RouteInfo;
import appcloud.netty.remoting.remote.NettyRemotingClient;
import com.distributed.common.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lizhenhao on 2017/12/26.
 */
public class RegisterBrokerTest {

    public final static int cons = 9000;

    protected final static Logger LOGGER = LoggerFactory.getLogger(RegisterBrokerTest.class);

    public static CountDownLatch count = new CountDownLatch(3);

    static class TestRegister implements Runnable {

        public int registerTimes = 5;

        public void run() {
            while (registerTimes > 0) {
                Random random = new Random();
//                int port = random.nextInt(cons);
                int port = 8118;
                String masterAddr = "127.0.0.1:8118";
                RouteInfo ownInfo = new RouteInfo();
                ownInfo.setIpAddress("127.0.0.1");
                ownInfo.setPort(String.valueOf(port));
                ownInfo.setDomainName("iaas.free4inno.com");
                ownInfo.setDataCenter("cloud slave"+String.valueOf(port));
                ownInfo.setAddr("127.0.0.1:" + String.valueOf(port));
                ownInfo.setUuid(UuidUtil.getRandomUuid());
                CloudControllerClientWapper cloudControllerClientWapper = new CloudControllerClientWapper(new NettyRemotingClient());
                cloudControllerClientWapper.start();
                cloudControllerClientWapper.sendRegisterRequest(ownInfo, masterAddr);

                registerTimes--;
            }
            LOGGER.info("线程"+Thread.currentThread().getName() + "停止发送");
            count.countDown();
        }
    }

    public static void main(String[] args) {
        int threadNumber = 3;
        Thread[] threads = new Thread[threadNumber];
        for (int i = 0 ;i<threadNumber;i++) {
            threads[i]  = new Thread(new TestRegister());
            threads[i].start();
        }

        try {
            count.await();
        } catch (InterruptedException e) {
            System.out.println("线程出错");
        }
        LOGGER.info("主线程停止");
    }

}
