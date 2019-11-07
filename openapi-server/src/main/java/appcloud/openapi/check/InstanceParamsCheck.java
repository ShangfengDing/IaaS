package appcloud.openapi.check;

import java.util.Map;

public interface InstanceParamsCheck {

	/**
	 *	开放API检查创建云主机接口自身参数
	 *	@param Map<String, String> 所有参数
	 *	@return Response
	 *	@author hgm 
	 */
	public Map<String, String> checkCreateParams(Map<String, String> paramsMap) throws Exception;
	/**
	 *	开放API检查启动云主机实例接口自身参数
	 *	@param Map<String, String> 所有参数
	 *	@return Response
	 *	@author hgm 
	 */
	public Map<String, String> checkStartParams(Map<String, String> paramsMap) throws Exception;
	/**
	 *	开放API检查停止云主机实例接口自身参数
	 *	@param Map<String, String> 所有参数
	 *	@return Response
	 *	@author hgm 
	 */
	public Map<String, String> checkStopParams(Map<String, String> paramsMap) throws Exception;
	
	/**
	 * 开放API检查重启云主机实例接口自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkRebootParams(Map<String, String> paramsMap) throws Exception;
	/**
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkDescribeInstanceStatusParams(Map<String, String> paramsMap) throws Exception;
	/**
	 * 开放API检查删除云主机实例接口自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkDeleteInstanceParams(Map<String, String> paramsMap) throws Exception;
	/**
	 * 开放API检查弹出iso接口自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkIsoDetachParams(Map<String, String> paramsMap) throws Exception;
	/**
	 * 开放API检查iso安装接口自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkIsoBootParams(Map<String, String> paramsMap) throws Exception;
	/**
	 * 开放API检查重置云主机系统实例接口自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkResetInstanceParams(Map<String, String> paramsMap) throws Exception;
	/**
	 * 开放API检查修改云主机属性接口自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkModifyInstanceAttributeParams(Map<String, String> paramsMap) throws Exception;
	/**
	 * 开放API检查修改云主机资源接口自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkModifyInstanceResourceParams(Map<String, String> paramsMap) throws Exception;
	/**
	 * 开放API检查修改云主机防火墙规则接口自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkModifyInstanceSecurityGroupParams(Map<String, String> paramsMap) throws Exception;
	/**
	 * 开放API检查修改云主机付费方式接口自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkModifyInstanceChargeTypeParams(Map<String, String> paramsMap) throws Exception;
}
