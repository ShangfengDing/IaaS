package appcloud.imageserver.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import appcloud.common.util.ConfigurationUtil;

public class Config {
	private final static Logger logger = Logger.getLogger(Config.class);
	
	public final static String IMAGE_ROOTDIR;
	public final static String IMG_PATH;
	static {        
		logger.info("+++++++++++App configuration information++++++++++++");
        try {
            Properties p = new ConfigurationUtil().getPropertyFileConfiguration("image.properties");
            
            IMAGE_ROOTDIR = p.getProperty("IMAGE_ROOTDIR","images");
            IMG_PATH  = p.getProperty("IMG_PATH","/srv/nfs4/");
            logger.info("IMAGE_ROOTDIR:" + IMAGE_ROOTDIR);
            logger.info("IMG_PATH:" + IMG_PATH);
        } catch (IOException e) {
        	logger.warn("Failed to init app configuration", e);
            throw new RuntimeException("Failed to init app configuration", e);
        }
        logger.info("----------App configuration successfully----------");
    }
}
