package com.distributed.nodemonitor.constant;

import com.distributed.common.utils.ConfigurationUtil;
import com.distributed.nodemonitor.util.IpHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Idan on 2018/5/7.
 */
public class Constants {

    private final static Logger logger = LoggerFactory.getLogger(Constants.class);

    // zk
    public final static String CLUSTER_NODE = "/cluster";
    public final static String ZK_CLUTER_ADDR;
    public static String APP_NAME;


    static {
        logger.info("+++++++++++App configuration information++++++++++++");
        try {
            Properties p = new ConfigurationUtil().getPropertyFileConfiguration("app.properties");

            ZK_CLUTER_ADDR = p.getProperty("ZK_CLUTER_ADDR");
            logger.info("ZK_CLUTER_ADDR:" + ZK_CLUTER_ADDR);

            try {
                APP_NAME = IpHelper.getHostIp();
            } catch (Exception e) {
                e.printStackTrace();
                APP_NAME = "127.0.0.1";
            }
            logger.info("APP_NAME:" + APP_NAME);

        } catch (IOException e) {
            logger.error("Failed to init app configuration", e);
            throw new RuntimeException("Failed to init app configuration", e);
        }
        logger.info("----------App configuration successfully----------");
    }

}
