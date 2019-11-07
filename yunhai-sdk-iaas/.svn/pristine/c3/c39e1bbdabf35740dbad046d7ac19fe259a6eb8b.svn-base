package appcloud.iaas.sdk.common;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.free4lab.utils.account.ConfigurationUtil;

public class Constants {
	public static final String PRODUCT;
	public static final String VERSION;	
	
    static {
        final Logger logger = Logger.getLogger("iaas configuration");
        logger.info("+++++++++++iaas configuration information++++++++++++");
        try {
            Properties p = new ConfigurationUtil().getPropertyFileConfiguration("iaas_sdk.properties");
            PRODUCT = p.getProperty("PRODUCT");
            logger.info("PRODUCT:" + PRODUCT);

            VERSION = p.getProperty("VERSION");
            logger.info("VERSION:" + VERSION);
        } catch (IOException e) {
            logger.fatal("Failed to init app configuration", e);
            throw new RuntimeException("Failed to init app configuration", e);
        }
        logger.info("----------App configuration successfully----------");
    }
}
