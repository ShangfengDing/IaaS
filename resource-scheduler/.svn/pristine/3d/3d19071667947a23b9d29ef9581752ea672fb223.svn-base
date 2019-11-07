/**
 * File: Constants.java
 * Author: weed
 * Create Time: 2013-6-20
 */
package appcloud.resourcescheduler.common;

import java.util.Properties;

import org.apache.log4j.Logger;

import appcloud.common.util.ConfigurationUtil;
/**
 * @author weed
 *
 */
public class Constants {
	public final static String MODULE_NAME = "Resource_Scheduler";
	public final static String TOKENS_DIRECTION; 
	
	static{
		final Logger logger = Logger.getLogger("App configuration");
		try{
			Properties p = new ConfigurationUtil().getPropertyFileConfiguration("vnc.properties");
			
			TOKENS_DIRECTION = p.getProperty("TOKENS_DIRECTION");
			logger.info("TOKENS_DIRECTION:" + TOKENS_DIRECTION);
		} catch(Exception e) {
			logger.warn("Failed to init app configuration" + e.getMessage());
        	throw new RuntimeException("Failed to init app configuration", e);
		}
		logger.info("----------App configuration successfully----------");
	}
}
			
