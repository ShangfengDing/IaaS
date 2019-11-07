package appcloud.openapi.check;

import java.util.Map;

public interface VolumeParamsCheck {

	/**
	 * 开放API检查创建硬盘接口自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkCreateParams(Map<String, String> paramsMap) throws Exception;
	
	/**
	 * 开放API检查挂载硬盘接口自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkAttachParams(Map<String, String> paramsMap) throws Exception;
	
	/**
	 * 开放API检查卸载硬盘接口自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkDetachParams(Map<String, String> paramsMap) throws Exception;
	
	/**
	 * 开放API检查删除硬盘接口自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkDeleteParams(Map<String, String> paramsMap) throws Exception;
	
	/**
	 * 开放API检查查询硬盘接口自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkDescribeParams(Map<String, String> paramsMap) throws Exception;
	
	/**
	 * 开放API检查回滚硬盘接口自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkResetParams(Map<String, String> paramsMap) throws Exception;
}
