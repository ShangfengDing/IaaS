package appcloud.openapi.check;

import java.util.Map;

import org.codehaus.jettison.json.JSONException;

/**
 * check the 
 * @author Gong Lingpu
 * @2016年1月19日
 */
public interface ImageOperateCheck {
    public Map<String, String> checkCreateImage(Map<String, String> paramsMap) throws Exception;
   
    public Map<String, String> checkDeleteImage(Map<String, String> paramsMap) throws Exception;

    public Map<String, String> checkUpdateImage(Map<String, String> paramsMap) throws Exception;

    public Map<String, String> checkGetImageDetail(Map<String, String> paramsMap) throws Exception;
    
    public Map<String, String> checkGetImageList(Map<String, String> paramsMap) throws Exception;

    
}
