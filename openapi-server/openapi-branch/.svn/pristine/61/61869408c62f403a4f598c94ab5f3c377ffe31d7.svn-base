package appcloud.openapi.billing;

import java.util.Map;

public interface BillingManager {
	/**
	 * 创建云主机/硬盘时扣费
	 * @param paramsMap 接口参数Map
	 * @param requestId 操作接口请求id，可当做Rpc的transactionId
	 * @return Response
	 * @throws Exception
	 */
	public Map<String, String> pay (Map<String, String> paramsMap,Map<String, String> defaultParamsMap)throws Exception;
	/**
	 * 创建云主机/硬盘时添加到期时间
	 * @param paramsMap 接口参数Map
	 * @param requestId 操作接口请求id，可当做Rpc的transactionId
	 * @return Response
	 * @throws Exception
	 */
	public Map<String, String> AddEndtime (Map<String, String> paramsMap,Map<String, String> defaultParamsMap,Map<String, String> serverMap)throws Exception;
	/**
	 * 云主机/硬盘续费
	 * @param paramsMap 接口参数Map
	 * @param requestId 操作接口请求id，可当做Rpc的transactionId
	 * @return Response
	 * @throws Exception
	 */
	public Map<String, String> renew (Map<String, String> paramsMap,Map<String, String> defaultParamsMap, String requestId)throws Exception;
	/**
	 * 修改云主机付费类型时扣费并修改到期时间
	 * @param paramsMap 接口参数Map
	 * @param requestId 操作接口请求id，可当做Rpc的transactionId
	 * @return Response
	 * @throws Exception
	 */
	public Map<String, String> modifyInstanceChargeType(Map<String, String> paramsMap,Map<String, String> defaultParamsMap, String requestId) throws Exception;
	/**
	 * 修改云主机资源时扣费
	 * @param paramsMap 接口参数Map
	 * @param requestId 操作接口请求id，可当做Rpc的transactionId
	 * @return Response
	 * @throws Exception
	 */
	public Map<String, String> payForModifyInstanceResource (Map<String, String> paramsMap,String requestId)throws Exception;
	/**
	 * 查询实例资源规格列表
	 * @param paramsMap 接口参数Map
	 * @param requestId 操作接口请求id，可当做Rpc的transactionId
	 * @return Response
	 * @throws Exception
	 */
	public Map<String, Object> DescribeInstanceTypes (Map<String, String> paramsMap,String requestId)throws Exception;
	/**
	 * 查询硬盘资源规格列表
	 * @param paramsMap 接口参数Map
	 * @param requestId 操作接口请求id，可当做Rpc的transactionId
	 * @return Response
	 * @throws Exception
	 */
	public Map<String, Object> DescribeDiskTypes (Map<String, String> paramsMap,String requestId)throws Exception;
	/**
	 * 查询大网带宽资源规格列表
	 * @param paramsMap 接口参数Map
	 * @param requestId 操作接口请求id，可当做Rpc的transactionId
	 * @return Response
	 * @throws Exception
	 */
	public Map<String, Object> DescribeInternetTypes (Map<String, String> paramsMap,String requestId)throws Exception;
	/**
	 * 查询内核资源规格列表
	 * @param paramsMap 接口参数Map
	 * @param requestId 操作接口请求id，可当做Rpc的transactionId
	 * @return Response
	 * @throws Exception
	 */
	public Map<String, Object> DescribeCpuTypes (Map<String, String> paramsMap,String requestId)throws Exception;
	/**
	 * 查询内存资源规格列表
	 * @param paramsMap 接口参数Map
	 * @param requestId 操作接口请求id，可当做Rpc的transactionId
	 * @return Response
	 * @throws Exception
	 */
	public Map<String, Object> DescribeMemoryTypes (Map<String, String> paramsMap,String requestId)throws Exception;
}
