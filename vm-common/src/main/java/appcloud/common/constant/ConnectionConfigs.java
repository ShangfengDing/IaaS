package appcloud.common.constant;

import java.io.IOException;
import java.util.Properties;

import tip.util.log.Log;

import appcloud.common.util.ConfigurationUtil;

/**
 * 这个类加载系统的配置项
 * 
 * @author jianglei
 *
 */
public class ConnectionConfigs {
	public final static String DB_PROXY_ADDRESS;
    public final static String AMPQ_SERVER_ADDRESS;
    public final static int TIME_OUT;
    public final static Integer PRIVATE_MAX_BANDWIDTH;
    public final static int RUN_PERIOD;


    private final static String DEFAULT_DB_PROXY_ADDRESS = "tcp://127.0.0.1:9001";
    private final static String DEFAULT_AMPQ_SERVER_ADDRESS = "127.0.0.1";
    private final static String DEFAULT_TIME_OUT = "100000";
    private final static String DEFAULT_RUN_PERIOD = "300000";

    private final static String DEFAULT_PRIVATE_MAX_BANDWIDTH = "30";

    static {        
        Log.info("+++++++++++App configuration information++++++++++++");
        try {
            Properties p = new ConfigurationUtil().getPropertyFileConfiguration("app.properties");
            
            DB_PROXY_ADDRESS = p.getProperty("DB_PROXY_ADDRESS", DEFAULT_DB_PROXY_ADDRESS);
            Log.info("DB_PROXY_ADDRESS:" + DB_PROXY_ADDRESS);
            
            AMPQ_SERVER_ADDRESS = p.getProperty("AMPQ_SERVER_ADDRESS", DEFAULT_AMPQ_SERVER_ADDRESS);
            Log.info("AMPQ_SERVER_ADDRESS:" + AMPQ_SERVER_ADDRESS);
            
            TIME_OUT = Integer.parseInt((p.getProperty("TIME_OUT", DEFAULT_TIME_OUT)));
            Log.info("TIME_OUT:" + TIME_OUT);

            RUN_PERIOD = Integer.parseInt((p.getProperty("RUN_PERIOD", DEFAULT_RUN_PERIOD)));
            Log.info("RUN_PERIOD:" + RUN_PERIOD);
            
            PRIVATE_MAX_BANDWIDTH = Integer.parseInt(p.getProperty("PRIVATE_MAX_BANDWIDTH", DEFAULT_PRIVATE_MAX_BANDWIDTH));
            Log.info("PRIVATE_MAX_BINDWIDTH:" + PRIVATE_MAX_BANDWIDTH);
        } catch (IOException e) {
        	Log.warn("Failed to init app configuration", e);
            throw new RuntimeException("Failed to init app configuration", e);
        }
        Log.info("----------App configuration successfully----------");
    }
}
