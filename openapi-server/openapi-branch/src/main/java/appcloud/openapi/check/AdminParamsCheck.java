package appcloud.openapi.check;

import java.util.Map;

/**
 * Created by lizhenhao on 2016/11/16.
 */
public interface AdminParamsCheck {

    public Map<String, String> checkAdminVolumeDescribeParams(Map<String, String> paramsMap) throws Exception;

    public Map<String, String> checkServicesParams(Map<String, String> paramsMap) throws Exception;

    public Map<String, String> checkAdminInstanceDescribeParams(Map<String, String> paramsMap) throws Exception;

    public Map<String, String> checkAdminHostsParams(Map<String, String> paramsMap) throws Exception;

    public Map<String, String> checkAdminOnlineMigrateParams(Map<String, String> paramsMap) throws Exception;

    public Map<String, String> checkAdminDescribeMonitorDataParams(Map<String, String> paramsMap) throws Exception;

    public Map<String, String> checkAdminAuthorizeImageParams(Map<String, String> paramsMap) throws Exception;

}
