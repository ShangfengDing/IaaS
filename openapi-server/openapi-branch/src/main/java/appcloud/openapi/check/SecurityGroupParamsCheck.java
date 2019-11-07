package appcloud.openapi.check;

import java.util.Map;

public interface SecurityGroupParamsCheck {

	/**
	 * 检查创建安全组接口自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkCreateParams(Map<String, String> paramsMap) throws Exception;
	
	/**
	 * 检查删除安全组接口自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkDeleteParams(Map<String, String> paramsMap) throws Exception;
	
	/**
	 * 检查授权安全组接口自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkAuthorize(Map<String, String> paramsMap) throws Exception;
	
	/**
	 * 描述安全组接口自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkDescribeSGsParams(Map<String, String> paramsMap) throws Exception;
	
	/**
	 * 检查撤销安全组接口自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkRevoke(Map<String, String> paramsMap) throws Exception;
	
	/**
	 * 检查安全组规则自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkDescribeAttitude(Map<String, String> paramsMap) throws Exception;
	
	/**
	 * 检查修改安全组规则属性自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkModifyAttitude(Map<String, String> paramsMap) throws Exception;
	
}
