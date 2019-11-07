package appcloud.distributed.common;

import com.distributed.common.utils.ConfigReader;
import com.distributed.common.utils.ProxyUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by lizhenhao on 2017/11/22.
 */
public class Constants {

    private final static Logger logger = LoggerFactory.getLogger(Constants.class);

    public final static String NFS = "/nfs";

    /**
     * 通信以及锁的配置信息
     */
    public final static long ONEWAY_SEND_TIME_WAIT = 30000;
    public final static long SYNC_TIME_WAIT = 30000;
    public final static long SYNC_TIME_WAIT_LONG = 60000;


    /**
     * 线程池数量配置
     */
    public final static int REQUEST_WORKER_THREAD_NUMBER = 3;
    public final static int REGISTER_WORKER_THREAD_NUMBER = 1;
    public final static int HEART_BEAT_TIMES_LIMIT = 3;

    /**
     * 堆外内存分配量
     */
    public final static int CAPACITY = 5 * 1024 * 1024;

    /**
     * 版本队列的配置
     */
    public final static int VERSION_QUEUE_SIZE = 30;
    public final static String VERSION_PATH="/srv/nfs4/version";
    public final static String VERSION_FILE_NAME="version.txt";


    /**
     * DNS相关配置信息
     */
//    public final static String DNS_ZONES = "/etc/named.rfc1912.zones";
    public final static String DNS_ZONES = "/etc/named.conf";
    public final static String DNS_ZONES_PATH = "/var/named/";
    public final static Integer DNS_TTL = 1000;
    public final static String DNS_SERVER_IP;
    public final static String DNS_SERVER_ROOT_NAME;
    public final static String DNS_SERVER_PASSWORD;
    public final static String DNS_NFS_PATH = "/srv/nfs4/dns/";
    public final static String DNS_PATH;

    /**
     * zookeeper 配置
     */
    public final static String CLUSTER_NODE = "/cluster";
    public final static String ZK_CLUTER_ADDR;

    public final static int DNS_SSH_PORT = 22;

    public final static String REGION_ID;

    static {
        logger.info("+++++++++++App configuration information++++++++++++");
        try {
            Properties p = new ConfigurationUtil().getPropertyFileConfiguration("app.properties");

            REGION_ID = p.getProperty("REGION_ID");
            logger.info("REGION_ID:" + REGION_ID);

            DNS_PATH = p.getProperty("DNS_PATH");
            logger.info("DNS_PATH:" + DNS_PATH);

            DNS_SERVER_IP = p.getProperty("DNS_SERVER_IP");
            logger.info("DNS_SERVER_IP:" + DNS_SERVER_IP);

            DNS_SERVER_ROOT_NAME = p.getProperty("DNS_SERVER_ROOT_NAME");
            logger.info("DNS_SERVER_ROOT_NAME:" + DNS_SERVER_ROOT_NAME);

            DNS_SERVER_PASSWORD = p.getProperty("DNS_SERVER_PASSWORD");
            logger.info("DNS_SERVER_PASSWORD:" + DNS_SERVER_PASSWORD);

            ZK_CLUTER_ADDR = p.getProperty("ZK_CLUTER_ADDR");
            logger.info("ZK_CLUTER_ADDR:" + ZK_CLUTER_ADDR);

        } catch (IOException e) {
            logger.error("Failed to init app configuration", e);
            throw new RuntimeException("Failed to init app configuration", e);
        }
        logger.info("----------App configuration successfully----------");
    }
}
