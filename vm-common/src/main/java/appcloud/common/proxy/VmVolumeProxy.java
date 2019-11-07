package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.VmVolume;
import appcloud.common.util.query.QueryObject;

/**
 * @author XuanJiaxing
 * 
 */
public interface VmVolumeProxy {
	/**
	 * 取得所有卷信息
	 * @param withHost 是否同时取得主机信息
	 * @param withCluster 是否同时取得集群信息
	 * @return
	 */
	public List<? extends VmVolume> findAll(boolean withHost, boolean withCluster, boolean withZone, boolean withImage) throws Exception;
	
	/**
	 * 分页取得卷信息
	 * @param withHost 是否同时取得主机信息
	 * @param withCluster 是否同时取得集群信息
	 * @param page 第几页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends VmVolume> findAll(boolean withHost, boolean withCluster,  boolean withZone, boolean withImage
			,Integer page, Integer size) throws Exception;

	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;
	

	/**
	 * 根据查询条件，搜索卷信息
	 * @param queryObject 查询条件，用户自己构造
	 * @param withHost 是否同时取得主机信息
	 * @param withCluster 是否同时取得集群信息
	 * @return
	 */
	public List<? extends VmVolume> searchAll(QueryObject<VmVolume> queryObject,
			boolean withHost, boolean withCluster, boolean withZone, boolean withImage) throws Exception;

	/**
	 * 根据查询条件，分页搜索卷信息
	 * @param queryObject 查询条件，用户自己构造
	 * @param withHost 是否同时取得主机信息
	 * @param withCluster 是否同时取得集群信息
	 * @param page 第几页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends VmVolume> searchAll(QueryObject<VmVolume> queryObject,
			boolean withHost, boolean withCluster, boolean withZone, boolean withImage
			,Integer page, Integer size) throws Exception;

	/**
	 * 取得查询记录条数
	 * @return
	 */
	public long countSearch(QueryObject<VmVolume> queryObject) throws Exception;
	
	/**
	 * 取得某个卷信息
	 * @param volumeId
	 * @param withHost 是否同时取得主机信息
	 * @param withCluster 是否同时取得集群信息
	 * @return
	 */
	public VmVolume getById(Integer volumeId,
			boolean withHost, boolean withCluster, boolean withZone, boolean withImage) throws Exception;
	
	/**
	 * 取得某个卷信息
	 * @param uuid
	 * @param withHost 是否同时取得主机信息
	 * @param withCluster 是否同时取得集群信息
	 * @param withZone 是否同时取得集群信息
	 * @return
	 */
	public VmVolume getByUUID(String uuid,
			boolean withHost, boolean withCluster, boolean withZone, boolean withImage) throws Exception;
	
	/**
	 * 取得某个卷信息
	 * @param name
	 * @param withHost 是否同时取得主机信息
	 * @param withCluster 是否同时取得集群信息
	 * @return
	 */
	public VmVolume getByImageName(String name,
			boolean withHost, boolean withCluster, boolean withZone, boolean withImage) throws Exception;
	
	
	/**
	 * 保存一个卷信息
	 * @param volume
	 * @return
	 */
	public void save(VmVolume volume) throws Exception;
	
	/**
	 * 更新一个 卷信息
	 * @param volume
	 * @return
	 */
	public void update(VmVolume volume) throws Exception;
	
	/**
	 * 删除一个 卷信息
	 * @param volumeId 
	 * @return
	 */
	public void deleteById(Integer volumeId) throws Exception;

	/**
	 * 通过母volume获取得备份卷信息
	 * @param name
	 * @param withHost 是否同时取得主机信息
	 * @param withCluster 是否同时取得集群信息
	 * @return
	 */
	public List<? extends VmVolume> getBySrcBackupUUID(String uuid,
			boolean withHost, boolean withCluster, boolean withZone, boolean withImage);
	
	/**
	 * Description 删除image时使用
	 * @param imageUuid
	 * @return
	 * @author GongLingpu
	 */
	public String getHostUuidByImageUuid(String imageUuid);
}
