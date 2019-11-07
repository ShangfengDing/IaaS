package appcloud.openapi.check;

import java.util.Map;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;

public interface ImageParamsCheck {

    /**
     * @author Gong Lingpu (gonglingpu@foxmail.com)
     * Description:  check the parameters before creating a new image
     * @param paramsMap
     * @return
     * @throws JSONException
     * @throws Exception
     */
    public Map<String, String> checkCreateParams(Map<String, String> paramsMap) throws JSONException, Exception;

    /**
     * @author Gong Lingpu (gonglingpu@foxmail.com)
     * Description :check the parameters before deleting a image
     * @param paramsMap
     * @return
     * @throws JSONException
     */
    public Map<String, String> checkDeleteParams(Map<String, String> paramsMap) throws JSONException;

    /**
     * @author Gong Lingpu (gonglingpu@foxmail.com)
     * Description : check the parameters before update a image
     * @param paramsMap
     * @return
     * @throws JSONException
     * @throws Exception 
     */
    public Map<String, String> checkUpdateParams(Map<String, String> paramsMap) throws JSONException, Exception;

    /**
     * @author Gong Lingpu (gonglingpu@foxmail.com)
     * Description:   check the paramters before get a specific image detail
     * @param paramsMap
     * @return
     * @throws JSONException
     */
    public Map<String, String> checkShowImageDetailParams(Map<String, String> paramsMap) throws JSONException;
    
    /**
     * @author Gong Lingpu (gonglingpu@foxmail.com)
     * Description:check parameters before get images list belongs to a tenant
     * @param paramsMap
     * @return
     * @throws JSONException
     */
    public Map<String, String> checkGetListParams(Map<String, String> paramsMap) throws JSONException;

   

}
