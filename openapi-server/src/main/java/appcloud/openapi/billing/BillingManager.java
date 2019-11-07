package appcloud.openapi.billing;

import java.util.Map;

import appcloud.api.beans.server.ServerCreateReq;
import appcloud.common.model.VmUser;
import appcloud.openapi.constant.Constants;

public interface BillingManager {
	/**
	 * 创建云主机时扣费
	 * @param paramsMap 接口参数Map
	 * @param requestId 操作接口请求id，可当做Rpc的transactionId
	 * @return Response
	 * @throws Exception
	 */
	public Map<String, String> pay (Map<String, String> paramsMap,Map<String, String> defaultParamsMap)throws Exception;
	/**
	 * 创建云主机时添加到期时间
	 * @param paramsMap 接口参数Map
	 * @param requestId 操作接口请求id，可当做Rpc的transactionId
	 * @return Response
	 * @throws Exception
	 */
	public Map<String, String> AddEndtime (Map<String, String> paramsMap,Map<String, String> defaultParamsMap,Map<String, String> serverMap)throws Exception;
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
}
