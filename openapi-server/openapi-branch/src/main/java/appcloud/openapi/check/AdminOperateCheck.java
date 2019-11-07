package appcloud.openapi.check;

import java.util.Map;

/**
 * Created by lizhenhao on 2016/11/16.
 */
public interface AdminOperateCheck {

    public Map<String, String> checkAdminInstanceDescribeOperate(Map<String, String> paramsMap) throws Exception;

    public Map<String, String> checkAdminVolumeDescribeOperate(Map<String, String> paramsMap) throws Exception;

    public Map<String, String> checkAdminServiceOperate(Map<String, String> paramsMap) throws Exception;

    public Map<String, String> checkAdminHostOperate(Map<String, String> paramsMap) throws Exception;

    public Map<String, String> checkAdminOnlineMigrateOperate(Map<String, String> paramsMap) throws Exception;

    public Map<String, String> checkAdminDescribeMonitorOperate(Map<String, String> paramsMap) throws Exception;

    public Map<String, String> checkAdminAuthorizeImageOperate(Map<String, String> paramsMap) throws Exception;

}
