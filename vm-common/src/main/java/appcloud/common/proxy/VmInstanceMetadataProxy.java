package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.VmInstanceMetadata;

/**
* @ClassName: VmInstanceMetadataProxy
* @author haowei.yu
* @date 2013-4-7 上午10:35:16
 */
public interface VmInstanceMetadataProxy {

    /**
     * 
    * @Title: 根据主键取得VmInstanceMetadata信息
    * @param vmInstanceMetadataId
    * @param withVmInstance 是否同时取得VmInstance信息
    * @return
     */
    public  VmInstanceMetadata getByVmInstanceMetadataId(Integer vmInstanceMetadataId,
          boolean withVmInstance) throws Exception;
    
    /**
     * 
    * @Title: 根据VmInstance的id取得VmInstanceMetadata信息
    * @param vmInstanceId
    * @param withVmInstance  是否同时取得VmInstance信息
    * @return
     */
    public List<? extends VmInstanceMetadata> getByVmInstanceId(Integer vmInstanceId,
          boolean withVmInstance) throws Exception;
    
    /**
     * 
    * @Title: 存储一个vmInstanceMetadata
    * @param vmInstance
     */
    public void save (VmInstanceMetadata vmInstanceMetadata) throws Exception;
    
    
    /**
     * 
    * @Title: 更新一个vmInstanceMetadata信息
    * @param vmInstanceMetadata
     */
    public void update(VmInstanceMetadata vmInstanceMetadata) throws Exception;
    
    /**
     * 
    * @Title: 根据id删除一个vmInstanceMetadata
    * @param vmInstanceMetadataId
     */
    public void deleteById(Integer vmInstanceMetadataId) throws Exception;
    
    /**
     * 
    * @Title: 根据VmInstance的id删除对应的vmInstanceMetadata
    * @param vmInstanceId
     */
    public void deleteByVmInstanceId(Integer vmInstanceId) throws Exception;
    
    
    
}
