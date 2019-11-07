package appcloud.vmschduler.utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.log4j.Logger;


public class Constants {
	private static final String fileName = "vmSchduler.properties";
	public static final String SCHEDULER_NAME = "schedulerName";
	private static final Logger logger = Logger.getLogger(Constants.class);
	/* @Deprecated	
	 * public static String[] filterName = null;
	public static Integer memoryWeight = 0;
	public static Integer cpuWeight = 0;*/
	public static PropertiesConfiguration config;
	static{
		try {
			config = new PropertiesConfiguration(Constants.class.getClassLoader().getResource(fileName));
			config.setReloadingStrategy(new FileChangedReloadingStrategy());
			/*
			 * 	@Deprecated	
			filterName = config.getStringArray(keyName);
			memoryWeight = config.getInt("memoryWeight");
			cpuWeight = config.getInt("cpuWeight");
			*/
		} catch (ConfigurationException e) {
			logger.error(e.getMessage());
		}
       
	}
	public static void main(String[] args) {
		logger.info(Constants.config.getInt("cpuWeight", 0));
		
		logger.info(Constants.config.getInt("shabi", 1000));
}
}
