/**
 * File: NAEC.java
 * Author: jianglei
 * Create Time: 2012-11-27
 */
package appcloud.common.errorcode;

import appcloud.common.annotation.ErrorAnnotation;

/**
 * NAEC represents NodeAgent EroorCode
 * 
 * @author jianglei
 *
 */
public enum NAEC{
	
	@ErrorAnnotation(msg="成功。")
	NO_ERROR,
	
	@ErrorAnnotation(msg="无承载此类型app能力。")
	OVER_CAP,
	
	@ErrorAnnotation(msg="GC出现问题。")
	GC_ERROR,
	
	@ErrorAnnotation(msg="数据库出现问题。")
	DB_ERROR,
	
	@ErrorAnnotation(msg="实例信息未删除成功。")
	INFO_NOT_DEL,
}
