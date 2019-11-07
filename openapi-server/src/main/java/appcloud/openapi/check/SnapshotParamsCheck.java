package appcloud.openapi.check;

import java.util.Map;

import javax.ws.rs.core.Response;

public interface SnapshotParamsCheck {

	/**
	 * 开放API检查创建镜像接口自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkCreateParams(Map<String, String> paramsMap) throws Exception;
	
	
	/**
	 * 开放API检查删除镜像接口自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkDeleteParams(Map<String, String> paramsMap) throws Exception;
	
	/**
	 * 开放API检查查询镜像接口自身参数
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkDescribeParams(Map<String, String> paramsMap) throws Exception;
}
