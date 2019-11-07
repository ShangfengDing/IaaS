package appcloud.openapi.constant;


/**
 * openapi-server 请求参数Action的配置项
 * @author hgm
 */
public class ActionConstants {

	//运维人员Action列表
	public final static String ADMIN_DESCRIBE_INSTANCES = "AdminDescribeInstances";
	public final static String ADMIN_DESCRIBE_DISKS = "AdminDescribeDisks";
	public final static String ADMIN_DESCRIBE_SERVICES = "AdminDescribeServices";
	public final static String ADMIN_DESCRIBE_HOSTS = "AdminDescribeHosts";
	public final static String ADMIN_ONLINE_MIGRATE = "AdminOnlineMigrate";
	public final static String ADMIN_AUTHORIZE_IMAGE = "AdminAuthorizeImage";
	public final static String ADMIN_MONITOR_INSTANCE_DATA = "AdminDescribeInstancesMonitorData";
	public final static String HOST_MAC = "hostMac";//物理机的mac地址

	//云主机Action列表
	public final static String CREATE_INSTANCE = "CreateInstance";
	public final static String RECOVERY_INSTANCE = "RecoveryInstance";
	public final static String START_INSTANCE = "StartInstance";
	public final static String STOP_INSTANCE = "StopInstance";
	public final static String FORCE_STOP_INSTANCE = "ForceStopInstance";
	public final static String REBOOT_INSTANCE = "RebootInstance";
	public final static String DESCRIBE_INSTANCE_STATUS = "DescribeInstanceStatus";
	public final static String DESCRIBE_INSTANCES = "DescribeInstances";
	public final static String DELETE_INSTANCE = "DeleteInstance";
	public final static String FORCE_DELETE_INSTANCE = "ForceDeleteInstance";
	public final static String RESET_INSTANCE = "ResetInstance";
	public final static String ISO_DETACH = "IsoDetach";
	public final static String ISO_BOOT = "IsoBoot";
	public final static String MODIFY_INSTANCE_ATTRIBUTE = "ModifyInstanceAttribute";
	public final static String MODIFY_INSTANCE_RESOURCE = "ModifyInstanceResource";
	public final static String MODIFY_INSTANCE_SECURITYGROUP = "ModifyInstanceSecurityGroup";
	public final static String MODIFY_INSTANCE_CHARGETYPE = "ModifyInstanceChargeType";
	public final static String DESCRIBE_INSTANCE_MONITOR_DATA = "DescribeInstanceMonitorData";
	public final static String MIGRATE_INSTANCE = "MigrateInstance";
	public final static String ONLINE_MIGRATE_INSTANCE = "OnlineMigrateInstance";
	public final static String SUSPEND_INSTANCE = "SuspendInstance";
	public final static String RESUME_INSTANCE = "ResumeInstance";
	public final static String RENEW_INSTANCE = "RenewInstance";
	
	//云硬盘Action列表
	public final static String CREATE_DISK = "CreateDisk";
	public final static String CREATE_DISK_IMAGEBACK = "CreateDiskImageBack";
	public final static String ATTACH_DISK = "AttachDisk";
	public final static String DETACH_DISK = "DetachDisk";
	public final static String DELETE_DISK = "DeleteDisk";
	public final static String DESCRIBE_DISKS = "DescribeDisks";
	public final static String DESCRIBE_DISK_IMAGEBACK = "DescribeDiskImageBck";
	public final static String MODIFY_DISK_IMAGEBACK = "ModifyDiskImageBck";
	public final static String RESET_DISK = "ResetDisk";
	public final static String MODIFY_DISK_ATTRIBUTE = "ModifyDiskAttribute";
	public final static String RENEW_DISK = "RenewDisk";
	
	//快照Action列表
	public final static String CREATE_SNAPSHOT = "CreateSnapshot";
	public final static String DELETE_SNAPSHOT = "DeleteSnapshot";
	public final static String DESCRIBE_SNAPSHOTS = "DescribeSnapshots";
	
	//镜像Action列表
	public final static String CREATE_IMAGE = "CreateImage";
    public final static String DELETE_IMAGE = "DeleteImage";
    public final static String AUTHORIZE_IMAGE = "AuthorizeImage";
    public final static String UPDATE_IMAGE = "ModifyImageAttribute";
    public final static String GET_IMAGE_DETAIL = "GetImageDetail";
    public final static String GET_IMAGE_LIST_WITH_DETAIL = "DescribeImages";
    // public final static String IMAGE_INDEX = "ImageIndex";
    
    //安全组Action列表
  	public final static String CREATE_SECURITY_GROUP = "CreateSecurityGroup";
  	public final static String DELETE_SECURITY_GROUP = "DeleteSecurityGroup";
  	public final static String AUTHORIZE_SECURITY_GROUP = "AuthorizeSecurityGroup";
  	public final static String DESCRIBE_SECURITY_GROUP_ATTRIBUTE = "DescribeSecurityGroupAttribute";
  	public final static String DESCRIBE_SECURITY_GROUPS = "DescribeSecurityGroups";
  	public final static String REVOKE_SECURITY_GROUP = "RevokeSecurityGroup";
  	public final static String MODIFY_SECURITY_GROUP_ATTRIBUTE = "ModifySecurityGroupAttribute";
  	
  	//BillingAction列表
  	public final static String DESCRIBE_INSTANCE_TYPES = "DescribeInstanceTypes";
  	public final static String DESCRIBE_DISK_TYPES = "DescribeDiskTypes";
  	public final static String DESCRIBE_INTERNET_TYPES = "DescribeInternetTypes";
  	public final static String DESCRIBE_CPU_TYPES = "DescribeCpuTypes";
  	public final static String DESCRIBE_MEMORY_TYPES = "DescribeMemoryTypes";
	public final static String PAY_VM = "PayVmBilling";
	public final static String RENEW_VM = "RenewVmBilling";
	public final static String PAY_HD = "PayHdBilling";
	public final static String RENEW_HD = "RenewHdBilling";

	//分布式云相关列表
	public final static String ACCOUNT = "Account";
	public final static String BACKUP_VM = "BackUpVm";
	public final static String ROUTE_INFO = "RouteInfo";


	//地域相关列表
  	public final static String DESCRIBE_REGIONS = "DescribeRegions";
  	public final static String DESCRIBE_ZONES = "DescribeZones";

  	//操作日志Action列表
  	public final static String SAVE_OPERATE_LOG = "SaveOperateLog";
	public final static String SEARCH_OPERATE_LOG= "SearchOperateLog";

	public final static String GAIN_APPKEY_PAIR = "GainAppkeyPair";

	//用户相关的表
	public final static String USER_CREATE = "UserCreate";
	public final static String USER_CREATE_FOR_DIS = "UserCreateForDis";
	public final static String GROUP_CREATE = "GroupCreate";
}
