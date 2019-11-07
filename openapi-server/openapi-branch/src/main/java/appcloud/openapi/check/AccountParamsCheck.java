package appcloud.openapi.check;

import java.util.Map;

/**
 * Created by Idan on 2017/10/22.
 */
public interface AccountParamsCheck {

    public Map<String, String > checkCreateUser(Map<String,String> paramsMap) throws Exception;

    public Map<String, String > checkCreateGroup(Map<String,String> paramsMap) throws Exception;
}
