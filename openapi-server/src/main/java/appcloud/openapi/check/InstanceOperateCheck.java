package appcloud.openapi.check;

import java.util.Map;

/**
 *	此类用于用户实际操作云平台资源前进行的权限检查
 *	@author hgm
 */
public interface InstanceOperateCheck {
	/**
	 *	创建云主机前，检查用户操作权限
	 *	@param paramsMap 接口中所有参数的map
	 *	@return 返回检查结果 Map<String, String>
	 *	@author hgm
	 */
	public Map<String, String> checkCreateInstance(Map<String, String> paramsMap) throws Exception;
	/**
	 *	启动云主机前，检查用户操作权限
	 *	@param paramsMap 接口中所有参数的map
	 *	@return 返回检查结果 Map<String, String>
	 *	@author hgm
	 */
	public Map<String, String> checkStartInstance(Map<String, String> paramsMap) throws Exception;
	/**
	 *	停止云主机前，检查用户操作权限
	 *	@param paramsMap 接口中所有参数的map
	 *	@return 返回检查结果 Map<String, String>
	 *	@author hgm
	 */
	public Map<String, String> checkStopInstance(Map<String, String> paramsMap) throws Exception;
	/**
	 *	重启云主机前，检查用户操作权限
	 *	@param paramsMap 接口中所有参数的map
	 *	@return 返回检查结果 Map<String, String>
	 *	@author hgm
	 */
	public Map<String, String> checkRebootInstance(Map<String, String> paramsMap) throws Exception ;
	/**
	 *	删除云主机前，检查用户操作权限
	 *	@param paramsMap 接口中所有参数的map
	 *	@return 返回检查结果 Map<String, String>
	 *	@author hgm
	 */
	public Map<String, String> checkDeleteInstance(Map<String, String> paramsMap) throws Exception ;
	/**
	 *	重置云主机系统前，检查用户操作权限
	 *	@param paramsMap 接口中所有参数的map
	 *	@return 返回检查结果 Map<String, String>
	 *	@author luofan
	 */
	public Map<String, String> checkResetInstance(Map<String, String> paramsMap) throws Exception ;
	/**
	 *	iso弹出前，检查用户操作权限
	 *	@param paramsMap 接口中所有参数的map
	 *	@return 返回检查结果 Map<String, String>
	 *	@author luofan
	 */
	public Map<String, String> checkIsoDetach(Map<String, String> paramsMap) throws Exception ;
	/**
	 *	iso安装前，检查用户操作权限
	 *	@param paramsMap 接口中所有参数的map
	 *	@return 返回检查结果 Map<String, String>
	 *	@author luofan
	 */
	public Map<String, String> checkIsoBoot(Map<String, String> paramsMap) throws Exception ;
	/**
	 *  修改云主机属性前，检查用户操作权限
	 *	@param paramsMap 接口中所有参数的map
	 *	@return 返回检查结果 Map<String, String>
	 *	@author luofan
	 */
	public Map<String, String> checkModifyInstanceAttribute(Map<String, String> paramsMap) throws Exception;
	/**
	 *  修改云主机资源前，检查用户操作权限
	 *	@param paramsMap 接口中所有参数的map
	 *	@return 返回检查结果 Map<String, String>
	 *	@author luofan
	 */
	public Map<String, String> checkModifyInstanceResource(Map<String, String> paramsMap) throws Exception;
	/**
	 *  修改云主机防火墙规则前，检查用户操作权限
	 *	@param paramsMap 接口中所有参数的map
	 *	@return 返回检查结果 Map<String, String>
	 *	@author luofan
	 */
	public Map<String, String> checkModifyInstanceSecurityGroup(Map<String, String> paramsMap) throws Exception;
	/**
	 *  修改云主机付费方式前，检查用户操作权限
	 *	@param paramsMap 接口中所有参数的map
	 *	@return 返回检查结果 Map<String, String>
	 *	@author luofan
	 */
	public Map<String, String> checkModifyInstanceChargeType(Map<String, String> paramsMap) throws Exception;
}
