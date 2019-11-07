package appcloud.openapi.operate;

import java.util.Map;

import appcloud.api.beans.Flavor;
import appcloud.api.beans.server.ServerCreateReq;

public interface InstanceOperate {

	/**
	 *	创建flavor，在数据库中插入一条instance-type数据
	 *	@params userId 用户Id
	 *			instanceType 实例类型，包括计算资源信息
	 *			dataDisk 除镜像自身大小外的数据盘大小
	 *			imageId 镜像uuid
	 *	@return 
	 *	@author hgm	
	 */
	public Flavor createFlavor(Integer userId, String instanceType, Integer dataDisk, String imageId) throws Exception;
	/**
	 *	创建云主机实例
	 *  @param serverCreateReq 创建请求
	 *  @param paramsMap 接口参数Map
	 *  @param requestId 操作接口请求id，可当做Rpc的transactionId
	 *  @return instance uuid
	 *  @author hgm
	 */
	public Map<String, String> createInstance(ServerCreateReq serverCreateReq, Map<String, String> paramsMap, String requestId) throws Exception;
	/**
	 *	启动云主机实例
	 *  @param serverCreateReq 创建请求
	 *  @param paramsMap 接口参数Map 
	 *  @param requestId 操作接口请求id，可当做Rpc的transactionId
	 *  @return instance uuid
	 *  @author hgm
	 * @param requestId 
	 */
	public Map<String, String> startInstance(Map<String, String> paramsMap, String requestId) throws Exception;
	/**
	 *	停止云主机实例
	 *  @param paramsMap 接口参数Map
	 *  @param requestId 操作接口请求id，可当做Rpc的transactionId
	 *  @return instance uuid
	 *  @author hgm
	 */
	public Map<String, String> stopInstance(Map<String, String> paramsMap, String requestId) throws Exception;
	
	/**
	 * 重启云主机实例
	 * @param paramsMap 接口参数Map
	 * @param requestId 操作接口请求id，可当做Rpc的transactionId
	 * @return Response
	 * @throws Exception
	 */
	public Map<String, String> rebootInstance(Map<String, String> paramsMap, String requestId) throws Exception;
	/**
	 * 查询实例状态
	 * @param paramsMap 接口参数Map
	 * @param requestId 操作接口请求id，可当做Rpc的transactionId
	 * @return Response
	 * @throws Exception
	 */
	public Map<String, Object> describeInstanceStatus(Map<String, String> paramsMap, String requestId) throws Exception;
	/**
	 * 删除云主机实例
	 * @param paramsMap 接口参数Map
	 * @param requestId 操作接口请求id，可当做Rpc的transactionId
	 * @return Response
	 * @throws Exception
	 */
	public Map<String, String> deleteInstance(Map<String, String> paramsMap, String requestId) throws Exception;
	/**
	 * 重置云主机实例系统
	 * @param paramsMap 接口参数Map
	 * @param requestId 操作接口请求id，可当做Rpc的transactionId
	 * @return Response
	 * @throws Exception
	 */
	public Map<String, String> resetInstance(Map<String, String> paramsMap, String requestId) throws Exception;
	/**
	 * iso弹出
	 * @param paramsMap 接口参数Map
	 * @param requestId 操作接口请求id，可当做Rpc的transactionId
	 * @return Response
	 * @throws Exception
	 */
	public Map<String, String> isoDetach(Map<String, String> paramsMap, String requestId) throws Exception;
	/**
	 * iso安装
	 * @param paramsMap 接口参数Map
	 * @param requestId 操作接口请求id，可当做Rpc的transactionId
	 * @return Response
	 * @throws Exception
	 */
	public Map<String, String> isoBoot(Map<String, String> paramsMap, String requestId) throws Exception;
	/**
	 * 修改云主机属性
	 * @param paramsMap 接口参数Map
	 * @param requestId 操作接口请求id，可当做Rpc的transactionId
	 * @return Response
	 * @throws Exception
	 */
	public Map<String, String> modifyInstanceAttribute(Map<String, String> paramsMap,Map<String, String> defaultParamsMap, String requestId) throws Exception;
	/**
	 * 修改云主机资源
	 * @param paramsMap 接口参数Map
	 * @param requestId 操作接口请求id，可当做Rpc的transactionId
	 * @return Response
	 * @throws Exception
	 */
	public Map<String, String> modifyInstanceResource(Map<String, String> paramsMap, String requestId) throws Exception;
	/**
	 * 修改云主机防火墙规则
	 * @param paramsMap 接口参数Map
	 * @param requestId 操作接口请求id，可当做Rpc的transactionId
	 * @return Response
	 * @throws Exception
	 */
	public Map<String, String> modifyInstanceSecurityGroup(Map<String, String> paramsMap, String requestId) throws Exception;	
}
