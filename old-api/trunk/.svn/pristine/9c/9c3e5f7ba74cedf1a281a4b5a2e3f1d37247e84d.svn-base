package appcloud.api.client.constants;

import java.io.IOException;
import java.util.Properties;

import tip.util.log.Log;
import appcloud.common.util.ConfigurationUtil;

public class Constants {

	public final static String BASE_URI;
    
    private final static String DEFAULT_BASE_URI = "http://127.0.0.1:8080/api";
    
    static {
    	 Log.info("+++++++++++App configuration information++++++++++++");
         try {
             Properties p = new ConfigurationUtil().getPropertyFileConfiguration("app.properties");
             
             BASE_URI = p.getProperty("BASE_URI", DEFAULT_BASE_URI);
             Log.info("BASE_URI:" + BASE_URI);
         } catch (IOException e) {
         	Log.warn("Failed to init app configuration", e);
             throw new RuntimeException("Failed to init app configuration", e);
         }
         Log.info("----------App configuration successfully----------");
    }
}
