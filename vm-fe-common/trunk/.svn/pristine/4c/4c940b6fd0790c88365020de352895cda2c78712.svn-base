package com.appcloud.vm.fe.common;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Bill_Constants {
	/**
	 * 原属于vm-billingrate工程的com.appcloud.vm.billingrate.common.Constants类
	 * 合并至此 
	 */
    /**
     * 计费模块，参数信息
     */
	public final static int BILLING_DOUBLE_BIT = 2;
	public final static int BILLING_CONSTANT = 100;
	public final static String BILLING_ITEM = "item";
	public final static String BILLING_ITEM_CPU = "cpu";
	public final static String BILLING_ITEM_MEM = "mem";
	public final static String BILLING_ITEM_HD = "hd";
	public final static String BILLING_ITEM_BW = "bw";
	public final static String BILLING_ITEM_VMPACKAGE = "vmpackage";
	public final static String BILLING_ITEM_CHARGE = "charge";
	public final static String BILLING_ITEM_INSTANCETYPE = "instancetype";
	public final static String BILLING_ITEM_VM = "vm";
	public final static String BILLING_ITEM_ALL = "all";
	public final static String BILLING_ITEM_FLAVOR = "flavor";
	public final static String BILLING_ID = "id";
	public final static String BILLING_NAME = "name";
	public final static String BILLING_DESCRIPTION = "description";
	public final static String BILLING_CLUSTERID = "clusterid";
	public final static String BILLING_CPU = "cpu";
	public final static String BILLING_MEMORY = "memory";
	public final static String BILLING_HARDDISK = "harddisk";
	public final static String BILLING_BANDWIDTH = "bandwidth";
	public final static String BILLING_HOURPRICE = "hourprice";
	public final static String BILLING_DAYPRICE = "dayprice";
	public final static String BILLING_MONTHPRICE = "monthprice";
	public final static String BILLING_YEARPRICE = "yearprice";
	
    public final static String BILLING_UID = "uid";
    public final static String BILLING_RECID = "recid";
    public final static String BILLING_PNAME = "pname";
    public final static String BILLING_PAYMENT = "payment";
    public final static String BILLING_PAYMENT_TYPE = "payment_type";
    public final static String BILLING_TIMES = "times";
    public final static String BILLING_COUNT = "count";
    public final static String BILLING_PTYPE = "ptype";
    public final static String BILLING_BILLING_TYPE = "billing_type";
    public final static String BILLING_PTIME = "ptime";
    public final static String BILLING_PID = "pid";
    public final static String BILLING_AMOUNT = "amount";
    public final static String BILLING_START_TIME = "start_time";
    public final static String BILLING_END_TIME = "end_time";
    public final static String BILLING_PAY_TYPE = "pay_type";
    public final static String BILLING_RULE_IDS = "rids";
    public static final String BILLING_PAGE_SIZE = "pagesize";
	public static final String BILLING_PAGE = "page";
	public static final String BILLING_BILLING_BEGINTIME = "begintime";
	public static final String BILLING_BILLING_ENDTIME = "endtime";
    
    public final static int MAX_FREEACCOUNT_ATTEMPTS;
    
    static {
        final Logger logger = Logger.getLogger("App configuration");
        logger.info("+++++++++++App configuration information++++++++++++");
        try {
            Properties p = new ConfigurationUtil().getPropertyFileConfiguration("app.properties");
            
            MAX_FREEACCOUNT_ATTEMPTS = Integer.parseInt(p.getProperty("MAX_FREEACCOUNT_ATTEMPTS"));
            logger.info("MAX_FREEACCOUNT_ATTEMPTS:" + MAX_FREEACCOUNT_ATTEMPTS);
            
        } catch (IOException e) {
            logger.fatal("Failed to init app configuration", e);
            throw new RuntimeException("Failed to init app configuration", e);
        }
        logger.info("----------App configuration successfully----------");
    }
}
