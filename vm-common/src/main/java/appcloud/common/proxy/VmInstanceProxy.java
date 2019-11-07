package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.VmInstance;
import appcloud.common.util.query.QueryObject;

public interface VmInstanceProxy {
    
    /**
     * 
    * @Title: 取得所有实例信息
    * @param withHost   是否同时获得实例所属主机的信息
    * @param withCluster   是否同时获得实例所属集群的信息
    * @param withVmPublicImage   是否同时获得实例所属公共镜像的信息
    * @param withVmInstanceType    是否同时获得实例所属套餐的信息
    * @param withVmNetWork  是否同时获得实例所属网络相关的信息
    * @param withVmSecurityGroup  是否同时获得实例所属防火墙的信息
    * @return
     */
    public List<? extends VmInstance> findAll(boolean withHost, boolean withCluster, boolean withVmPublicImage,
          boolean withVmInstanceType, boolean withVmInstanceMetadata, boolean withVolume, boolean withVmNetWork, boolean withVmSecurityGroupRules) throws Exception;
    
    /**
     * 
    * @Title: 分页取得所有实例信息
    * @param withHost   是否同时获得实例所属主机的信息
    * @param withCluster   是否同时获得实例所属集群的信息
    * @param withVmPublicImage   是否同时获得实例所属公共镜像的信息
    * @param withVmInstanceType    是否同时获得实例所属套餐的信息
    * @param withVmNetWork  是否同时获得实例所属网络相关的信息
    * @param withVmSecurityGroup  是否同时获得实例所属防火墙的信息
    * @return
    * @param page 第几页，0代表不分页
    * @param size 每页大小，0代表不分页
    * @return
     */
    public List<? extends VmInstance> findAll(boolean withHost, boolean withCluster, boolean withVmPublicImage,
          boolean withVmInstanceType, boolean withVmInstanceMetadata, boolean withVolume, boolean withVmNetWork, boolean withVmSecurityGroupRules,
          Integer page, Integer size) throws Exception;
    
    /**
     * 
    * @Title: 取得总实例数
    * @return
    * @throws Exception
     */
    public long countAll() throws Exception;
    
    /**
     * 
    * @Title: 根据查询条件，搜索全部实例信息
    * @param queryObject
    * @param withHost
      * @param withCluster   是否同时获得实例所属集群的信息
    * @param withVmPublicImage   是否同时获得实例所属公共镜像的信息
    * @param withVmInstanceType    是否同时获得实例所属套餐的信息
    * @param withVmNetWork  是否同时获得实例所属网络相关的信息
    * @param withVmSecurityGroup  是否同时获得实例所属防火墙的信息
    * @return
    * @throws Exception
     */
    public List<? extends VmInstance> searchAll(QueryObject<VmInstance> queryObject,
            boolean withHost, boolean withCluster, boolean withVmPublicImage, 
            boolean withVmInstanceType, boolean withVmInstanceMetadata, boolean withVolume, boolean withVmNetWork, 
            boolean withVmSecurityGroup) throws Exception;
    
    
    /**
     * 
    * @Title: 根据查询条件，分页搜索实例信息
    * @param queryObject
    * @param withHost
    * @param withCluster   是否同时获得实例所属集群的信息
    * @param withVmPublicImage   是否同时获得实例所属公共镜像的信息
    * @param withVmInstanceType    是否同时获得实例所属套餐的信息
    * @param withVmNetWork  是否同时获得实例所属网络相关的信息
    * @param withVmSecurityGroup  是否同时获得实例所属防火墙的信息
    * @param page
    * @param size
    * @return
    * @throws Exception
     */
    public List<? extends VmInstance> searchAll(QueryObject<VmInstance> queryObject,
            boolean withHost, boolean withCluster, boolean withVmPublicImage, 
            boolean withVmInstanceType, boolean withVmInstanceMetadata, boolean withVolume, boolean withVmNetWork, 
            boolean withVmSecurityGroup, Integer page, Integer size) throws Exception;
    
    /**
     * 
    * @Title: 取得查询记录条数
    * @param queryObject
    * @return
    * @throws Exception
     */
    public long countSearch(QueryObject<VmInstance> queryObject) throws Exception;
    
    
    
    /**
     * 
    * @Title: 通过uuid获取某个实例信息
    * @param uuid
    * @param withHost   是否同时获得实例所属主机的信息
    * @param withCluster   是否同时获得实例所属集群的信息
    * @param withVmPublicImage   是否同时获得实例所属公共镜像的信息
    * @param withVmInstanceType    是否同时获得实例所属套餐的信息
    * @param withVmInstanceMetadata		是否同时获得元数据列表信息
    * @param withVolume		是否同时获得卷信息
    * @param withVmNetWork  是否同时获得实例所属网络相关的信息
    * @param withVmSecurityGroup  是否同时获得实例所属防火墙的信息
    * @return
    * @throws Exception
     */
    public VmInstance getByUuid(String uuid, boolean withHost, boolean withCluster, boolean withVmPublicImage,
          boolean withVmInstanceType, boolean withVmInstanceMetadata, boolean withVolume, boolean withVmNetWork, boolean withVmSecurityGroup) throws Exception;
    
    /**
     * 
    * @Title: 保存一个实例信息
    * @param vmInstance
    * @throws Exception
     */
    public void save(VmInstance vmInstance) throws Exception;
    
    /**
     * 
    * @Title: 更新一个实例信息
    * @param vmInstance
    * @throws Exception
     */
    public void update(VmInstance vmInstance) throws Exception;
    
    /**
     * 
    * @Title: 根据uuid删除某个实例信息
    * @param uuid
    * @throws Exception
     */
    public void deleteByUuid(String uuid) throws Exception;
    
    /**
     * 
    * @Title: 根据id删除某个实例信息
    * @param uuid
    * @throws Exception
     */
    public void deleteById(Integer VmInstanceId) throws Exception;

    /**
     * 
     * @Title: findAllUuid
     * @Description: 取得所有虚拟机的uuid
     */
    public List<String> findAllUuid() throws Exception;

    
   
    
}
