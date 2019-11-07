package appcloud.openapi.manager;

import java.util.Map;

import appcloud.api.beans.Image;
import appcloud.common.model.VmImage;
import appcloud.common.util.query.QueryObject;

public interface ImageManager {

    Image create(Map<String, String> paramsMap) throws Exception;

    VmImage authorizeImage(Map<String, String> paramsMap) throws Exception;

    VmImage deleteImage(Map<String, String> paramsMap) throws Exception;

    Image updateImage(Map<String, String> paramsMap) throws Exception;

    VmImage getImageDetail(Map<String, String> paramsMap) throws Exception;
    
    QueryObject<VmImage> getImageList(Map<String, String> paramsMap);

    

}
