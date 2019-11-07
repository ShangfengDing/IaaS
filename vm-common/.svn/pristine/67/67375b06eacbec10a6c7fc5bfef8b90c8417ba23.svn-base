package appcloud.common.proxy;

import appcloud.common.model.VmImage;
import appcloud.common.model.VmImageBack;
import appcloud.common.util.query.QueryObject;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by lizhenhao on 2017/11/30.
 */
public interface VmImageBackProxy {
    public List<? extends VmImageBack> findAll() throws Exception;

    /**
     * 分页取得公用镜像信息
     * @param page 第几页，0代表不分页
     * @param size 每页大小，0代表不分页
     * @return
     */
    public List<? extends VmImageBack> findAll(Integer page, Integer size) throws Exception;

    /**
     * 取得总记录条数
     * @return
     */
    public long countAll() throws Exception;

    /**
     * 根据查询条件，搜索公用镜像信息
     * @param properties 查询条件，用户自己构造
     * @return
     */
    public List<? extends VmImageBack> searchAll(QueryObject<VmImageBack> queryObject) throws Exception;

    /**
     * 根据查询条件，分页搜索母镜像信息
     * @param properties 查询条件，用户自己构造
     * @param page 第几页，0代表不分页
     * @param size 每页大小，0代表不分页
     * @return
     */
    public List<? extends VmImageBack> searchAll(QueryObject<VmImageBack> queryObject,
                                             Integer page, Integer size) throws Exception;

    /**
     * 取得查询记录条数
     * @return
     */
    public long countSearch(QueryObject<VmImageBack> queryObject) throws Exception;

    /**
     * 根据uuid取得某个母镜像信息
     * @param uuid
     * @return
     */
    public VmImageBack getByUuid(String uuid) throws Exception;


    public VmImageBack getByUuid(String uuid,boolean withCluster, boolean withZone, boolean withInstance,boolean withHost) throws Exception;

    /**
     * 取得某个母镜像信息
     * @param imageId
     * @return
     */
    public VmImageBack getById(Integer imageId) throws Exception;


    /**
     * 根据主机uuid和top标志查询硬盘的第top-1个母镜像
     * @param instanceUuid
     * @param isTop
     * @return
     * @throws Exception
     */
    public VmImageBack getByInstanceUuidAndTop(String instanceUuid, boolean isTop) throws Exception;

    /**
     * 根据activeVolumeId和top标志
     */
    public VmImageBack getByVolumeUuidAndTop(String volumeUuid, boolean isTop) throws Exception;

    /**
     * 根据主机uuid查询硬盘的所有母镜像
     * @param instanceUuid
     * @return
     * @throws Exception
     */
    public List<? extends VmImageBack> getByInstanceUuid(String instanceUuid) throws Exception;

        /**
         * 保存一个公用镜像信息
         * @param image
         * @return
         */
    public void save(VmImageBack image) throws Exception;

    /**
     * 更新一个母镜像信息
     * @param image
     * @return
     */
    public void update(VmImageBack image) throws Exception;

    /**
     * 删除一个母镜像信息
     * @param imageId
     * @return
     */
    public void deleteById(Integer imageId) throws Exception;


}

