package appcloud.common.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import appcloud.common.util.ConfigurationUtil;

public class Config {
	private final static Logger logger = Logger.getLogger(Config.class);
	
	public final static String IMAGE_ROOTDIR;
	static {        
		logger.info("+++++++++++App configuration information++++++++++++");
        try {
            Properties p = new ConfigurationUtil().getPropertyFileConfiguration("image.properties");
            
            IMAGE_ROOTDIR = p.getProperty("IMAGE_ROOTDIR","images");
            logger.info("IMAGE_ROOTDIR:" + IMAGE_ROOTDIR);
        } catch (IOException e) {
        	logger.warn("Failed to init app configuration", e);
            throw new RuntimeException("Failed to init app configuration", e);
        }
        logger.info("----------App configuration successfully----------");
    }
}
