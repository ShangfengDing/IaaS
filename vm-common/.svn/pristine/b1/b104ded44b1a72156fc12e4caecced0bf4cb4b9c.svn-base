/**
 * File: AppRouterError.java
 * Author: weed
 * Create Time: 2012-11-26
 */
package appcloud.common.errorcode;

import appcloud.common.annotation.ErrorAnnotation;

/**
 * @author weed
 *
 */
public enum AREC{
	
	@ErrorAnnotation(msg="成功。")
	NO_ERROR,
	
	@ErrorAnnotation(msg="类型错误。")
	TYPE_ERROR,
	
	@ErrorAnnotation(msg="源地址不存在。")
	SRC_NOT_EXIST,
	
	@ErrorAnnotation(msg="批量接口参数中存在多个源地址。")
	MULTI_SRC,
	
	@ErrorAnnotation(msg="目标地址不存在。")
	DST_NOT_EXIST,
	
	@ErrorAnnotation(msg="批量接口参数中目标地址重复。")
	DUPED_DST,
	
	@ErrorAnnotation(msg="只能指向一个app的域名。")
	NONAPP_DST,
	
	@ErrorAnnotation(msg="不支持跨域的域名指向。")
	SUFFIX_CROSSED,
	
	@ErrorAnnotation(msg="路由项已存在。")
	RE_EXISTED,
	
	@ErrorAnnotation(msg="路由项不存在。")
	RE_NOT_EXIST,
	
	@ErrorAnnotation(msg="要添加的域名已存在。")
	DOMAIN_EXISTED,
	
	@ErrorAnnotation(msg="要删除的域名不存在。")
	DOMAIN_NOT_EXIST,
	
	@ErrorAnnotation(msg="要更新的Nginx不存在。")
	NGINX_NOT_EXIST,
	
	;
}
