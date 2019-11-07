/**
 * File: RSEC.java
 * Author: jianglei
 * Create Time: 2012-11-27
 */
package appcloud.common.errorcode;

import appcloud.common.annotation.ErrorAnnotation;

/**
 * RSEC represents ResouceScheduler EroorCode
 * 
 * @author jianglei
 *
 */
public enum RSEC{
	
	@ErrorAnnotation(msg="成功。")
	NO_ERROR,
	
	@ErrorAnnotation(msg="NodeAgent错误。")
	NA_ERROR,
	
	// 部署时返回
	@ErrorAnnotation(msg="app已经存在。")
	APP_EXIST,
	
	// 停止时返回
	@ErrorAnnotation(msg="app不存在。")
	APP_NOT_EXIST,
	
	// resource shortage
	@ErrorAnnotation(msg="资源不满足需求。")
	RSR_STG,
	
	@ErrorAnnotation(msg="部署实例数少于要求。")
	LESS_DEPLOY,
	
	@ErrorAnnotation(msg="路由信息添加错误。")
	RT_ERROR,
	
	@ErrorAnnotation(msg="RPC通信底层错误。")
	RPC_ERROR,
	
	@ErrorAnnotation(msg="RPC通信底层错误。")
	DB_ERROR,
	
	@ErrorAnnotation(msg="未知错误类型。")
	UNKNOWN_ERROR,
}
