package appcloud.openapi.operate;

import appcloud.openapi.response.*;

import java.util.Map;

/**
 * Created by lizhenhao on 2016/11/16.
 */
public interface AdminOperate {

    /**
     *管理员查询实例列表
     * @param paramsMap
     * @param requestId
     * @return DescribeInstancesResponse
     * @throws Exception
     */

    public DescribeInstancesResponse AdminDescribeInstances(Map<String, String> paramsMap, String requestId) throws Exception;

    public DisksDetailReponse AdminDescribeDisks (Map<String, String> paramsMap, String requestId) throws Exception;

    public ServicesResponse AdminDescribeServices(Map<String,String> paramsMap, String requestId) throws Exception;

    public HostsResponse AdminDescribeHosts(Map<String,String> paramsMap, String requestId) throws Exception;

    public Map<String, String> AdminOnlineMigrate(Map<String,String> paramsMap, String requestId) throws Exception;

    public Map<String, Object> AdminDescribeMonitorData(Map<String,String> paramsMap, String requestId) throws Exception;

    public Map<String, String> AdminAuthorizeImage(Map<String,String> paramsMap) throws Exception;
}
