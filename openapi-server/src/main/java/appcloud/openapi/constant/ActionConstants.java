package appcloud.openapi.constant;


/**
 * openapi-server 请求参数Action的配置项
 * @author hgm
 */
public class ActionConstants {

	//云主机Action列表
	public final static String CREATE_INSTANCE = "CreateInstance";
	public final static String START_INSTANCE = "StartInstance";
	public final static String STOP_INSTANCE = "StopInstance";
	public final static String FORCE_STOP_INSTANCE = "ForceStopInstance";
	public final static String REBOOT_INSTANCE = "RebootInstance";
	public final static String DESCRIBE_INSTANCE_STATUS = "DescribeInstanceStatus";
	public final static String DELETE_INSTANCE = "DeleteInstance";
	public final static String FORCE_DELETE_INSTANCE = "ForceDeleteInstance";
	public final static String RESET_INSTANCE = "ResetInstance";
	public final static String ISO_DETACH = "IsoDetach";
	public final static String ISO_BOOT = "IsoBoot";
	public final static String MODIFY_INSTANCE_ATTRIBUTE = "ModifyInstanceAttribute";
	public final static String MODIFY_INSTANCE_RESOURCE = "ModifyInstanceResource";
	public final static String MODIFY_INSTANCE_SECURITYGROUP = "ModifyInstanceSecurityGroup";
	public final static String MODIFY_INSTANCE_CHARGETYPE = "ModifyInstanceChargeType";
	
	
	//云硬盘Action列表
	public final static String CREATE_DISK = "CreateDisk";
	public final static String ATTACH_DISK = "AttachDisk";
	public final static String DETACH_DISK = "DetachDisk";
	public final static String DELETE_DISK = "DeleteDisk";
	public final static String DESCRIBE_DISKS = "DescribeDisks";
	public final static String RESET_DISK = "ResetDisk";
	
	//快照Action列表
	public final static String CREATE_SNAPSHOT = "CreateSnapshot";
	public final static String DELETE_SNAPSHOT = "DeleteSnapshot";
	public final static String DESCRIBE_SNAPSHOTS = "DescribeSnapshots";
	
	//镜像Action列表
	public final static String CREATE_IMAGE = "CreateImage";
    public final static String DELETE_IMAGE = "DeleteImage";
    public final static String UPDATE_IMAGE = "UpdateImage";
    public final static String GET_IMAGE_DETAIL = "GetImageDetail";
    public final static String GET_IMAGE_LIST_WITH_DETAIL = "GetImageListWithDetail";
    // public final static String IMAGE_INDEX = "ImageIndex";
    
    //安全组Action列表
  	public final static String CREATE_SECURITY_GROUP = "CreateSecurityGroup";
  	public final static String DELETE_SECURITY_GROUP = "DeleteSecurityGroup";
  	public final static String AUTHORIZE_SECURITY_GROUP = "AuthorizeSecurityGroup";
  	public final static String DESCRIBE_SECURITY_GROUP_ATTRIBUTE = "DescribeSecurityGroupAttribute";
  	public final static String DESCRIBE_SECURITY_GROUPS = "DescribeSecurityGroups";
  	public final static String REVOKE_SECURITY_GROUP = "RevokeSecurityGroup";
  	public final static String MODIFY_SECURITY_GROUP_ATTRIBUTE = "ModifySecurityGroupAttribute";
    
}
