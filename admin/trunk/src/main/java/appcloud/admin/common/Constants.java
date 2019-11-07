package appcloud.admin.common;

import java.io.IOException;
import java.util.Properties;

import tip.util.log.Log;
import appcloud.common.util.ConfigurationUtil;


/**
 * 系统中多模块使用的常量
 * @author lzc
 *
 */
public final class Constants {
	/**
	 * Session中存用户权限信息的键值
	 */
	public final static String ADMIN_ID_KEY = "adminId";
	public final static String ADMIN_DISPLAY_NAME_KEY = "displayName";
	public final static String ADMIN_USERNAME_KEY = "username";
	public final static String TYPE_KEY = "type";
	public final static String TYPE_PLATFORM_ADMIN = "PLATADMIN";
	public final static String TYPE_APP_ADMIN = "APPADMIN";
	public final static String ADMIN_RESOURCE = "admin_resource";
	public final static String ADMIN_IS_AJAX = "admin_is_ajax";
	public final static String ADMIN_NO_LIMIT_AJAX = "ajaxNoLimit";
	
	public final static String REG_VER_CODE_KEY = "regVercode";
	
	/**
	 * Cookie认证相关
	 */
	public final static String AUTH_COOKIE_ID_NAME = "authId";
	public final static String AUTH_COOKIE_NAME = "auth";
	public final static String AUTH_COOKIE_CHECKED = "authckd";
	
	public static final Integer DEFAULT_CHECKTIME;
//	public static final String CSS_JS_SERVER;
	
	/**
	 * 计费参数相关
	 */
	public static final String ITEM_ALL = "all";
	public static final String ITEM_VM = "vm";
	public static final String ITEM_VMPACKAGE = "vmpackage";
	public static final String ITEM_CPU = "cpu";
	public static final String ITEM_MEM = "mem";
	public static final String ITEM_HD = "hd";
	public static final String ITEM_BW = "bw";
	public static final String ITEM_CHARGE = "charge";
	public static final String ITEM_INSTANCETYPE = "instancetype";
	public static final String PTYPE_VMPACKAGE = "vmpackage".toUpperCase();
	public static final String PTYPE_CPU = "cpu".toUpperCase();
	public static final String PTYPE_MEM = "mem".toUpperCase();
	public static final String PTYPE_HD = "hd".toUpperCase();
	public static final String PTYPE_BW = "bw".toUpperCase();
	public static final String PTYPE_CHARGE = "charge".toUpperCase();
	public static final String PTYPE_INSTANCETYPE = "instancetype".toUpperCase();
	
	public static final String CLIENT_SECRET_KEY;
	public static final String CLIENT_ID;
	
	public static final String FRONT_URL;
	/**
	 * host的类型
	 */
	public static final String HOST_TYPE = "COMPUTE_NODE";
	
	static {        
        Log.info("+++++++++++App configuration information++++++++++++");
        try {
            Properties p = new ConfigurationUtil().getPropertyFileConfiguration("app.properties");
            
//			CSS_JS_SERVER = p.getProperty("CSS_JS_SERVER");
//			Log.info("CSS_JS_SERVER:" + CSS_JS_SERVER);
			
			DEFAULT_CHECKTIME = Integer.valueOf( p.getProperty("DEFAULT_CHECKTIME"));
			Log.info("DEFAULT_CHECKTIME:" + DEFAULT_CHECKTIME);
			
			CLIENT_SECRET_KEY = p.getProperty("CLIENT_SECRET_KEY");
			Log.info("CLIENT_SECRET_KEY:" + CLIENT_SECRET_KEY);
			
			CLIENT_ID = p.getProperty("CLIENT_ID");
			Log.info("CLIENT_ID:"+CLIENT_ID);
			
			FRONT_URL = p.getProperty("FRONT_URL");
			Log.info("FRONT_URL:" + FRONT_URL);
			
        } catch (IOException e) {
        	Log.warn("Failed to init app configuration", e);
            throw new RuntimeException("Failed to init app configuration", e);
        }
        Log.info("----------App configuration successfully----------");
    }
}
