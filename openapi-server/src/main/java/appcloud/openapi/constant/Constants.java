package appcloud.openapi.constant;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import appcloud.common.util.ConfigurationUtil;

/**
 * openapi-server 的配置项
 * @author hgm
 */
public class Constants {

	//接口公共参数名称
	public static final String FORMAT = "Format";
	public final static String VERSION = "Version";
	public final static String APPKEY_ID = "AppkeyId";
	public final static String SIGNATURE = "Signature";
	public final static String TIMESTAMP = "Timestamp";
	public final static String USER_EMAIL = "UserEmail";
	//通用参数
	public final static String DESCRIPTION = "Description";
	//实例接口相关参数名称
	public final static String ACTION = "Action";
	public final static String ZONE_ID = "ZoneId";
	public final static String IMAGE_ID = "ImageId"; 
	public final static String INSTANCE_TYPE = "InstanceType";
	public final static String SECURITY_GROUP_ID = "SecurityGroupId";
	public final static String INSTANCE_NAME = "InstanceName";
	public final static String INSTANCE_CHARGE_TYPE = "InstanceChargeType";
	public final static String INSTANCE_CHARGE_LENGTH = "InstanceChargeLength";
	public final static String INTERNET_CHARGE_TYPE = "InternetChargeType";
	public final static String INTERNET_MAX_BANDWIDTH_OUT = "InternetMaxBandwidthOut";
	public final static String HOST_NAME = "HostName";
	public final static String PASSWORD = "Password";
	public final static String SYSTEM_DISK_CATEGORY = "SystemDisk.Category";
	public final static String DATA_DISK1_CATEGORY = "DataDisk.1.Category";
	public final static String DATA_DISK1_SIZE = "DataDisk.1.Size";
	public final static String DATA_DISK1_DELETE_WITHINSTANCE = "DataDisk.1.DeleteWithInstance";
	public final static String ACCESS_TOKEN = "AccessToken";
	public final static String INSTANCE_ID = "InstanceId";
	public final static String FORCE_STOP = "ForceStop";
	public final static String FORCE_DELETE = "ForceDelete";
	public final static String CPU_NUM = "CPUNum";
	public final static String RAM_SIZE = "RAMSize";
	public final static String PUBLIC_BANDWIDTH = "maxBandwidth";
	//快照相关接口的参数名称
	public final static String SNAPSHOT_NAME = "SnapshotName";
	public final static String SNAPSHOT_ID = "SnapshotId";
	public final static String SNAPSHOT_IDS = "SnapshotIds";
	public final static String SNAPSHOT_STATUS = "Status";
	
	//接口的镜像相关参数
    public final static String TENANT_ID = "TenantId";
    public final static String SERVER_ID = "ServerId";
    public final static String VOLUME_ID = "volumeId";
    public final static String DISPLAY_NAME = "DisplayName";
    public final static String DISPLAY_DESCRIPTION = "DisplayDescription";
    public final static String GROUP_ID_LIST = "GroupIdList";
    public final static String DISTRIBUTION = "Distribution";
    public final static String IMAGE_UUID = "ImageUuid";
    public final static String GROUP_ID = "GroupId";
    public final static String IMAGE_TYPE = "ImageType";
    public final static String IMAGE_STATUS = "ImageStatus";
    
	//硬盘相关接口的参数名称
	public final static String DISK_ID = "DiskId";
	public final static String DISK_NAME = "DiskName";
	public final static String DISK_SIZE = "Size";
	public final static String DISK_CATEGORY = "DiskCategory";
	public final static String DISK_TYPE = "DiskType";
	public final static String REGION_ID = "RegionId";
	public final static String DISK_IDS = "DiskIds";
	public final static String DISK_STATUS = "Status";
	public final static String DISK_ATTACH_STATUS = "DiskAttachStatus";
	
	//安全组相关接口的参数名称
	public final static String SECURITY_GROUP_NAME = "SecurityGroupName";
	public final static String IP_PROTOCOL = "IpProtocol";
	public final static String PORT_RANGE = "PortRange";
	public final static String SOURCE_CIDR_IP = "SourceCidrIp";
	public final static String POLICY = "Policy";
	
	//接口返回错误类型
	public final static String NOT_FOUND_PARAMETER = "NotFoundParameter";
	public final static String MISSING_PARAMETER ="MissingParameter";
	public final static String INVALID_PARAMETER = "InvalidParameter";
	public final static String OPERATIONDENIED = "OperationDenied";
	public final static String INTERNAL_ERROR = "InternalError";
	public final static String NOT_AUTHORIZED = "ValueUnauthorized";
	public final static String INSUFFICIENT_BALANCE = "InsufficientBalance";
	public final static String ERRORCODE = "ErrorCode";
	public final static String ERRORMESSAGE = "ErrorMessage";
	public final static String DEFAULT_ERROR_MESSAGE = "The request processing has failed due to some unknown error.";
	//特殊参数值
	public final static Integer MAX_NAME = 20;
	public final static Integer MIN_NAME = 1;
	public final static Integer MAX_DESCRIPTION = 50;
	public final static Integer MIN_DESCRIPTION = 1;
	public final static Integer MAX_BANDWIDTHOUT = 200;
	public final static Integer MAX_CPUNUM = 8;
	public final static Integer MAX_RAMSIZE = 8;
	public final static String OS_WINDOWS = "WINDOWS";
	public final static Integer MAX_WINDOWS_HOSTNAME = 15;
	public final static String OS_LINUX = "LINUX";
	public final static Integer MAX_LINUX_HOSTNAME = 30;
	public final static Integer MIN_HOSTNAME = 2;
	public final static Integer MAX_PASSWORD = 30;
	public final static Integer MIN_PASSWORD = 8;
	public final static Integer MAX_DATADISK = 200;
	public final static String DISK_CLOUD = "cloud";  //云平台硬盘类型
	public final static Integer DEFAULT_PAGE_SIZE = 10; //分页查询是设置的每页行数的默认值为10
	public final static Integer MAX_PAGE_SIZE = 50; //分页查询是设置的每页行数的最大为50
	public static final double yearDays = 360.0;
	public static final double monthDays = 30.0;
	public static final double yearDays10 = 36.0;
	public static final double monthDays10 = 3.0;
	//云主机计费方式
	public final static String PAY_BY_HOUR = "PayByHour";
	public final static String PAY_BY_DAY = "PayByDay";
	public final static String PAY_BY_MONTH = "PayByMonth";
	public final static String PAY_BY_YEAR = "PayByYear";
	//云主机计费最大长度
	public final static Integer MAX_PAY_BY_HOUR = 24;
	public final static Integer MAX_PAY_BY_DAY = 30;
	public final static Integer MAX_PAY_BY_MONTH = 12;
	public final static Integer MAX_PAY_BY_YEAR = 3;
	public final static Integer ZERO_PRICE_FOR_YEAR = 10;
	//带宽计费方式
	public final static String PAY_BY_BANDWIDTH = "PayByBandwidth";
	public final static String PAY_BY_TRAFFIC = "PayByTraffic";

	public final static String OPENAPI_VERSION;
	//第三方的资源计费方式
	public final static String PTYPE_THIRD_PARTY="THIRD_PARTY";
	
	//其他参数
	public final static String APPKEY_SECRET = "AppKeySecret";
	public final static String ACCOUNT_EMAIL = "email";
	public final static String ACCOUNT_USER_ID = "uid";
	public final static String INSTANCE_TYPE_MYSQL = "INSTANCETYPE";
	public final static Integer MAX_TIME_OUT;
	public final static String HTTP_CODE = "HttpCode";
	public final static String PAGE_NUMBER = "PageNumber";
	public final static String PAGE_SIZE = "PageSize";
	//云主机实例自身相关字段
	public final static String METADATA_DISCRIPTION = "displayDescription";
	public final static String PUB_NETWORK_TYPE = "public";
	public final static String PRI_NETWORK_TYPE = "private";

	static {
		final Logger logger = Logger.getLogger("App configuration");
		logger.info("+++++++++++Openapi configuration information++++++++++++");
		try {
			Properties p = new ConfigurationUtil().getPropertyFileConfiguration("openapi.properties");

			OPENAPI_VERSION = p.getProperty("OPENAPI_VERSION");
			logger.info("OPENAPI_VERSION: " + OPENAPI_VERSION);

			MAX_TIME_OUT = Integer.parseInt(p.getProperty("MAX_TIME_OUT","600000"));
			logger.info("MAX_TIME_OUT: " + MAX_TIME_OUT);

        } catch (IOException e) {
        	logger.warn("Failed to init app configuration" + e.getMessage());
        	throw new RuntimeException("Failed to init app configuration", e);
        }
		logger.info("----------App configuration successfully----------");
    }
}
