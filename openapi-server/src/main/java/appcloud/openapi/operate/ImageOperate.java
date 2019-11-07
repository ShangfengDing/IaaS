package appcloud.openapi.operate;

import java.util.List;
import java.util.Map;

import appcloud.api.beans.Image;
import appcloud.common.model.VmImage;
import appcloud.common.util.query.QueryObject;
import appcloud.rpc.tools.RpcException;

public interface ImageOperate {

    Map<String, String> createImage(String tenantId, Image newImage, String transactionId) throws Exception;

    Map<String, String> deleteImage(String tenantId, VmImage imageToDel, String transactionId) throws Exception;

    Map<String, String> updateImage(Image imageToUpdate, String transactionId) throws RpcException, Exception;

    Map<String, Object> getImageDetail(VmImage imageToShow, String transactionId);
    
    Map<String, Object> getImageList(QueryObject<VmImage> query, Map<String, String> paramsMap, boolean detailed, String transactionId) throws Exception;

   

}
