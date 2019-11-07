package com.appcloud.vm.fe.common;

import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

import com.appcloud.vm.fe.util.ConfigurationUtil;

public class Constants {
	public static final String API_SERVER;
	public static final String ADMIN_TENANT_ID;
	public static final String CLIENT_ID;
	public static final String DEFAULT_CLIENT_ID = "iaas";
	public static final String CLIENT_SECRET_KEY;
	public static final String DEFAULT_CLIENT_SECRET_KEY = "iaasSecretKey";
	//image相关的常量
	public static final String IMAGE_STATUS_AVAILABLE;
	public static final String QUERY_ALL = "all";
	public static final String QUERY_NO_DATA = "query_no_data";
	
	//默认配置
	public static final String DEFAULT_API_SERVER = "http://192.168.1.61:8081/api/";
	//"http://api.free4lab.com/api";
	public static final String DEFAULT_ADMIN_TENANT_ID = "10";
	
	//帮助
	public static final String FREEHELP_URL;
	public static final String DEFAULT_FREEHELP_URL = "http://freehelp.free4lab.com/";
	
	//计费相关
	public static final String VM_URL_ACCOUNT;
	public static final String DEFAULT_VM_URL_ACCOUNT = "http://account.free4lab.com/";
	//"http://account.free4lab.com/"
	public static final String USERS_SERVER;
	public static final String DEFAULT_USERS_SERVER = "http://account.free4lab.com/users/";
	//"http://account.free4lab.com/api/users/"
	public static final String USERS_SERVER_GETUIDBYEMAIL;
	public static final String ACCOUNT_SERVER;
	public static final String DEFAULT_ACCOUNT_SERVER = "http://account.free4lab.com/api/billing/";
	//"http://account.free4lab.com/api/billing/"
	public static final String BILLING_SERVER;

	public static final String DEFAULT_BILLING_SERVER = "http://192.168.1.61:8084/billingrate/";

	public static final String BILLING_SERVER_ADDRATE;
	public static final String BILLING_SERVER_GETRATE;
	public static final String BILLING_SERVER_UPDATERATE;
	public static final String BILLING_SERVER_DELRATE;
	public static final String BILLING_SERVER_GETPRICE;
	public static final String BILLING_SERVER_PAY;
	public static final String BILLING_SERVER_RECHARGE;
	public static final String BILLING_SERVER_RESET;
	public static final String BILLING_SERVER_BALANCE;
	public static final String BILLING_SERVER_GETHISTORY;
	public static final String BILLING_SERVER_GETBALANCES;
	public static final Integer MAX_ATTEMPTS;
	public static final Integer DEFAULT_MAX_ATTEMPTS = 10;
	public static final String BILLING_ID = "id";
	public static final String BILLING_NAME = "name";
	public static final String BILLING_DESCRIPTION = "description";
	public static final String BILLING_CLUSTERID = "clusterid";
	public static final String BILLING_PTYPE = "ptype";
	public static final String BILLING_CPU = "cpu";
	public static final String BILLING_MEM = "memory";
	public static final String BILLING_HD = "harddisk";
	public static final String BILLING_BW = "bandwidth";
	public static final String BILLING_ALL = "all";
	public static final String BILLING_CHARGE = "CHARGE";
	public static final String BILLING_CPU_ABBR = "CPU";
	public static final String BILLING_MEM_ABBR = "MEM";
	public static final String BILLING_HD_ABBR = "HD";
	public static final String BILLING_BW_ABBR = "BW";
	public static final String BILLING_VMPACKAGE = "VMPACKAGE";
	public static final String BILLING_FLAVOR = "flavor";
	public static final String BILLING_YEARPRICE = "yearprice";
	public static final String BILLING_MONTHPRICE = "monthprice";
	public static final String BILLING_DAYPRICE = "dayprice";
	public static final String BILLING_HOURPRICE = "hourprice";
	public static final String BILLING_ITEM = "item";
	public static final String BILLING_UID = "uid";
	public static final String BILLING_COUNT = "count";
	public static final String BILLING_BILLINGRATES = "billingrates";
	public static final String BILLING_PAYMENT = "payment";
	public static final String BILLING_PRICE = "price";
	public static final String BILLING_RESULT = "result";
	public static final String BILLING_MESSAGE = "message";
	public static final double yearDays = 360.0;
	public static final double monthDays = 30.0;
	public static final double yearDays10 = 36.0;
	public static final double monthDays10 = 3.0;
	
	public static final String DEFAULT_IMAGE_STATUS_AVAILABLE = "AVAILABLE";
	
	//云服务提供商
	public static final String YUN_HAI = "yunhai";
	public static final String ALI_YUN = "aliyun";
	public static final String AMAZON = "amazon";
	/**
	 * MANAGER_EMAIL字段用于管理平台获取云海所有region信息时进行签名认证用的，此处的值应该和数据库front中数据表appkey中的一致
	 */
	public static final String MANAGER_EMAIL = "manager@free4lab.com";
	
	static {
		final Logger logger = Logger.getLogger("App configuration");
		logger.info("+++++++++++App configuration information++++++++++++");
		try {
			Properties p = new ConfigurationUtil().getPropertyFileConfiguration("app.properties");
           
			CLIENT_SECRET_KEY = p.getProperty("CLIENT_SECRET_KEY", DEFAULT_CLIENT_SECRET_KEY);
			logger.info("CLIENT_SECRET_KEY: " + CLIENT_SECRET_KEY);
			
			CLIENT_ID = p.getProperty("CLIENT_ID", DEFAULT_CLIENT_ID);
			logger.info("CLIENT_ID:" + CLIENT_ID);
			
            API_SERVER = p.getProperty("API_SERVER", DEFAULT_API_SERVER);
            logger.info("API_SERVER:" + API_SERVER);

            ADMIN_TENANT_ID = p.getProperty("ADMIN_TENANT_ID", DEFAULT_ADMIN_TENANT_ID);
            logger.info("ADMIN_TENANT_ID:" + ADMIN_TENANT_ID);
            
            IMAGE_STATUS_AVAILABLE = p.getProperty("IMAGE_STATUS_AVAILABLE", DEFAULT_IMAGE_STATUS_AVAILABLE);
            logger.info("IMAGE_STATUS_AVAILABLE:" + IMAGE_STATUS_AVAILABLE);
            
            USERS_SERVER = p.getProperty("USERS_SERVER", DEFAULT_USERS_SERVER);
            logger.info("USERS_SERVER:" + USERS_SERVER);
            
            ACCOUNT_SERVER = p.getProperty("ACCOUNT_SERVER", DEFAULT_ACCOUNT_SERVER);
            logger.info("ACCOUNT_SERVER:" + ACCOUNT_SERVER);
            
            BILLING_SERVER = p.getProperty("BILLING_SERVER", DEFAULT_BILLING_SERVER);
            logger.info("BILLING_SERVER:" + BILLING_SERVER);
            
            MAX_ATTEMPTS = Integer.valueOf(
            		p.getProperty("MAX_ATTEMPTS", DEFAULT_MAX_ATTEMPTS.toString()));
            logger.info("MAX_ATTEMPTS:" + MAX_ATTEMPTS);
            
            USERS_SERVER_GETUIDBYEMAIL = USERS_SERVER + "id";
            logger.info("USERS_SERVER_GETUIDBYEMAIL:" + USERS_SERVER_GETUIDBYEMAIL);
            
            BILLING_SERVER_ADDRATE = BILLING_SERVER + "addrate";
            logger.info("BILLING_SERVER_ADDRATE:" + BILLING_SERVER_ADDRATE);
            
            BILLING_SERVER_GETRATE = BILLING_SERVER + "getrate";
            logger.info("BILLING_SERVER_GETRATE:" + BILLING_SERVER_GETRATE);
            
            BILLING_SERVER_UPDATERATE = BILLING_SERVER + "updaterate";
            logger.info("BILLING_SERVER_UPDATERATE:" + BILLING_SERVER_UPDATERATE);
            
            BILLING_SERVER_DELRATE = BILLING_SERVER + "delrate";
            logger.info("BILLING_SERVER_DELRATE:" + BILLING_SERVER_DELRATE);
            
            BILLING_SERVER_GETPRICE = BILLING_SERVER + "getprice";
            logger.info("BILLING_SERVER_GETPRICE:" + BILLING_SERVER_GETPRICE);

            BILLING_SERVER_PAY = ACCOUNT_SERVER + "pay";
            logger.info("BILLING_SERVER_PAY:" + BILLING_SERVER_PAY);
            
            BILLING_SERVER_RECHARGE = ACCOUNT_SERVER + "recharge";
            logger.info("BILLING_SERVER_RECHARGE:" + BILLING_SERVER_RECHARGE);
            
            BILLING_SERVER_RESET = ACCOUNT_SERVER+"reset";
            logger.info("BILLING_SERVER_RESET:"+BILLING_SERVER_RESET);

            BILLING_SERVER_BALANCE = ACCOUNT_SERVER + "balance";
            logger.info("BILLING_SERVER_BALANCE:" + BILLING_SERVER_BALANCE);
            
            BILLING_SERVER_GETHISTORY = ACCOUNT_SERVER + "records_page";
            logger.info("BILLING_SERVER_GETHISTORY:" + BILLING_SERVER_GETHISTORY);
            
            BILLING_SERVER_GETBALANCES = ACCOUNT_SERVER + "getbalances";
            logger.info("BILLING_SERVER_GETBALANCES:" + BILLING_SERVER_GETBALANCES);
            
            FREEHELP_URL = p.getProperty("FREEHELP_URL", DEFAULT_FREEHELP_URL);
			logger.info("FREEHELP_URL:" + FREEHELP_URL);
			
			VM_URL_ACCOUNT = p.getProperty("URL_ACCOUNT", DEFAULT_VM_URL_ACCOUNT);
			logger.info("VM_URL_ACCOUNT:" + VM_URL_ACCOUNT);
        } catch (IOException e) {
        	logger.warn("Failed to init app configuration" + e.getMessage());
        	throw new RuntimeException("Failed to init app configuration", e);
        }
		logger.info("----------App configuration successfully----------");
    }
}
