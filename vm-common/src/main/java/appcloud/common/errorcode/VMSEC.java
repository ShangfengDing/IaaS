package appcloud.common.errorcode;

import appcloud.common.annotation.ErrorAnnotation;

public enum VMSEC {
	@ErrorAnnotation(msg="成功。")
	SUCCESS,
	
	@ErrorAnnotation(msg="操作失败。")
	TASK_ERROR,

	@ErrorAnnotation(msg="虚拟机的主机ID为空。")
	NULL_HOST,
	
	@ErrorAnnotation(msg="允许删除防火墙组。")
	DELSG_PERMISSION,
	
	@ErrorAnnotation(msg="不允许删除防火墙组。")
	DELSG_PERMISSION_DENIED,
}
