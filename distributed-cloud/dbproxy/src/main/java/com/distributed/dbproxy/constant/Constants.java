package com.distributed.dbproxy.constant;

import com.distributed.dbproxy.utils.ConfigurationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Idan on 2017/12/12.
 */
public class Constants {
    private static final Logger logger = LoggerFactory.getLogger(Constants.class);

    public static ConcurrentHashMap<String, String> proxyHashMap = new ConcurrentHashMap<>();
    public final static int REQUEST_WORKER_THREAD_NUMBER = 3;
    public static int SERVER_PORT = 9001;
    static {
        try {
            Properties p = new ConfigurationUtil().getPropertyFileConfiguration("app.properties");
            if (p!=null) {
                SERVER_PORT = Integer.valueOf(p.getProperty("SERVER_PORT", "9001"));
                System.out.println("the server port: "+SERVER_PORT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
