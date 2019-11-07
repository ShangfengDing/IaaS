package appcloud.vs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.log4j.Logger;

public class Conf {
	public static Integer MAX_SHARE_IMAGE_NUM = 0;
	public static String MAX_SHARE_IMAGE_NUM_PRO = "MAX_SHARE_IMAGE_NUM";
	public static final Integer DEFAULT_MAX_SHARE_IMAGE_NUM = 10;
	public static String IMAGE_SPLIT_STRING = null;
	public static String IMAGE_SPLIT_STRING_PRO = "IMAGE_SPLIT_STRING";
	public static final String DEFAULT_IMAGE_SPLIT_STRING = "#@#";
	public static String IMAGE_UUID_SUFFIX = null;
	public static String IMAGE_UUID_SUFFIX_PRO = "IMAGE_UUID_SUFFIX";
	public static final String DEFAULT_IMAGE_UUID_SUFFIX = "1";
	public static Boolean DEBUG = false; 
	
	//volume挂载目录，一般形式为ip+NFS=192.168.1.24:/srv/nfs4 or ip+NFS+"/"
	public static final String NFS= ":/srv/nfs4";
	//调度相关
	private static final String FILENAME = "vs.properties";
	public static final String SCHEDULER_NAME = "schedulerName";
	public static final String FREE_DISK = "freeDisk";
	public static final Integer DEFAULT_FREE_DISK = 100;//默认差额100G
	public static PropertiesConfiguration config;
	private static Logger logger = Logger.getLogger(Conf.class);
	
	public static final String DISK = "DISK";
	//为了避免小数，1:1时为100
	public static final Integer OVER_SELL_BASE = 100;
	
	static {
//		Properties p = new Properties();
//		InputStream is = Conf.class.getClassLoader().getResourceAsStream("vs.properties");
        try {
        	config = new PropertiesConfiguration(Conf.class.getClassLoader().getResource(FILENAME));
			config.setReloadingStrategy(new FileChangedReloadingStrategy());
			MAX_SHARE_IMAGE_NUM = config.getInteger(Conf.MAX_SHARE_IMAGE_NUM_PRO, Conf.DEFAULT_MAX_SHARE_IMAGE_NUM);
			IMAGE_SPLIT_STRING = config.getString(Conf.IMAGE_SPLIT_STRING_PRO, Conf.DEFAULT_IMAGE_SPLIT_STRING);
			IMAGE_UUID_SUFFIX = config.getString(IMAGE_UUID_SUFFIX_PRO, DEFAULT_IMAGE_UUID_SUFFIX);
			String property = config.getString("DEBUG");
			if(property!=null){
				DEBUG = Boolean.parseBoolean(property);
			}
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	public static void main(String[] args) {
		String[] agrs = config.getStringArray(Conf.SCHEDULER_NAME);
		System.out.println(agrs[0]);
	}
}