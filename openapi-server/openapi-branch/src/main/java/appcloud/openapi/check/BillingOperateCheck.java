package appcloud.openapi.check;

import java.util.Map;

public interface BillingOperateCheck {
	/**
	 *	查询实例资源规格列表前，检查用户操作权限
	 *	@param paramsMap 接口中所有参数的map
	 *	@return 返回检查结果 Map<String, String>
	 *	@author luofan
	 */
	public Map<String, String> checkDescribeInstanceTypes(Map<String, String> paramsMap) throws Exception ;
	/**
	 *	查询硬盘资源规格列表前，检查用户操作权限
	 *	@param paramsMap 接口中所有参数的map
	 *	@return 返回检查结果 Map<String, String>
	 *	@author luofan
	 */
	public Map<String, String> checkDescribeDiskTypes(Map<String, String> paramsMap) throws Exception ;
	/**
	 *	查询带宽资源规格列表前，检查用户操作权限
	 *	@param paramsMap 接口中所有参数的map
	 *	@return 返回检查结果 Map<String, String>
	 *	@author luofan
	 */
	public Map<String, String> checkDescribeInternetTypes(Map<String, String> paramsMap) throws Exception ;
	/**
	 *	查询内核资源规格列表前，检查用户操作权限
	 *	@param paramsMap 接口中所有参数的map
	 *	@return 返回检查结果 Map<String, String>
	 *	@author luofan
	 */
	public Map<String, String> checkDescribeCpuTypes(Map<String, String> paramsMap) throws Exception ;
	/**
	 *	查询内存资源规格列表前，检查用户操作权限
	 *	@param paramsMap 接口中所有参数的map
	 *	@return 返回检查结果 Map<String, String>
	 *	@author luofan
	 */
	public Map<String, String> checkDescribeMemoryTypes(Map<String, String> paramsMap) throws Exception ;
}
