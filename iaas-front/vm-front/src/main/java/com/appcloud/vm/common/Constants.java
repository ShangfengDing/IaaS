package com.appcloud.vm.common;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import appcloud.common.util.ConfigurationUtil;

public class Constants {
	public static final String CLIENT_SECRET_KEY;
	public static final String CLIENT_ID;
	
	public static final String VNC_HOST;
	
	public static final String VNC_PORT;
		
	public static final String FRONT_URL;
	
	public static final String WEBVNC_URL;
	
	public static final Integer DEFAULT_GROUP;
	
	public static final Integer DEFAULT_CHECKTIME;
	
	public static final Integer DEFAULT_HD_CHECKTIME;
	
	public static final Integer DEFAULT_CHECKTIMESLOT;
	
	public static final Integer LOG_COUNT;
	
	public static final String DOMAIN;

	public static final String URL_NEWFRONT;

	public static final String ACCOUNT_URL;

	public static final String ZONE_ID;
	
	static {
		final Logger logger = Logger.getLogger("App configuration");
		logger.info("+++++++++++App configuration information++++++++++++");
		try {
			Properties p = new ConfigurationUtil().getPropertyFileConfiguration("app.properties");

			URL_NEWFRONT = p.getProperty("URL_NEWFRONT");
			logger.info("URL_NEWFRONT:" + URL_NEWFRONT);

			VNC_HOST = p.getProperty("VNC_HOST");
			logger.info("VNC_HOST:" + VNC_HOST);
			
			VNC_PORT = p.getProperty("VNC_PORT");
			logger.info("VNC_PORT:" + VNC_PORT);
			
			CLIENT_SECRET_KEY = p.getProperty("CLIENT_SECRET_KEY");
			logger.info("CLIENT_SECRET_KEY:" + CLIENT_SECRET_KEY);
			
            CLIENT_ID = p.getProperty("CLIENT_ID");
			logger.info("CLIENT_ID:" + CLIENT_ID);
			
			FRONT_URL = p.getProperty("FRONT_URL");
			logger.info("FRONT_URL:" + FRONT_URL);
			
			WEBVNC_URL = p.getProperty("WEBVNC_URL");
			logger.info("WEBVNC_URL:" + WEBVNC_URL);
			
			DEFAULT_GROUP = Integer.valueOf( p.getProperty("DEFAULT_GROUP"));
			logger.info("DEFAULT_GROUP:" + DEFAULT_GROUP);
			
			DEFAULT_CHECKTIME = Integer.valueOf( p.getProperty("DEFAULT_CHECKTIME"));
			logger.info("DEFAULT_CHECKTIME:" + DEFAULT_CHECKTIME);
			
			DEFAULT_HD_CHECKTIME = Integer.valueOf( p.getProperty("DEFAULT_HD_CHECKTIME"));
			logger.info("DEFAULT_HD_CHECKTIME:" + DEFAULT_HD_CHECKTIME);
			
			DEFAULT_CHECKTIMESLOT = Integer.valueOf( p.getProperty("DEFAULT_CHECKTIMESLOT"));
			logger.info("DEFAULT_CHECKTIMESLOT:" + DEFAULT_CHECKTIMESLOT);
			
			LOG_COUNT = Integer.valueOf(p.getProperty("LOG_COUNT"));
			logger.info("LOG_COUNT:" + LOG_COUNT);
			
			DOMAIN = p.getProperty("DOMAIN");
			logger.info("DOMAIN:" + DOMAIN);

			ACCOUNT_URL = p.getProperty("URL_ACCOUNT");
			logger.info("ACCOUNT_URL"+ACCOUNT_URL);

			ZONE_ID=p.getProperty("ZONE_ID");
			logger.info("ZONE_ID"+ZONE_ID);
        } catch (IOException e) {
        	logger.warn("Failed to init app configuration" + e.getMessage());
        	throw new RuntimeException("Failed to init app configuration", e);
        }
		logger.info("----------App configuration successfully----------");
    }
}
