package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.VmSnapshot;
import appcloud.common.util.query.QueryObject;

/**
 * @author XuanJiaxing
 *
 */
public interface VmSnapshotProxy {
	/**
	 * 取得所有快照信息
	 * @param withVolume 是否同时取得卷信息
	 * @return
	 */
	public List<? extends VmSnapshot> findAll(boolean withVolume) throws Exception;
	
	/**
	 * 分页取得快照信息
	 * @param withVolume 是否同时取得卷信息
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends VmSnapshot> findAll(boolean withVolume, 
			Integer page, Integer size) throws Exception;

	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;

	/**
	 * 根据查询条件，搜索快照信息
	 * @param queryObject 查询条件，用户自己构造
	 * @param withVolume 是否同时取得卷信息
	 * @return
	 */
	public List<? extends VmSnapshot> searchAll(QueryObject<VmSnapshot> queryObject,
			boolean withVolume) throws Exception;

	/**
	 * 根据查询条件，分页搜索快照信息
	 * @param queryObject 查询条件，用户自己构造
	 * @param withVolume 是否同时取得卷信息
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends VmSnapshot> searchAll(QueryObject<VmSnapshot> queryObject,
			boolean withVolume,
			Integer page, Integer size) throws Exception;

	/**
	 * 取得查询记录条数
	 * @return
	 */
	public long countSearch(QueryObject<VmSnapshot> queryObject) throws Exception;
	
	/**
	 * 取得某个快照信息
	 * @param snapshotId
	 * @param withVolume 是否同时取得卷信息
	 * @return
	 */
	public VmSnapshot getById(Integer snapshotId,
			boolean withVolume) throws Exception;
	
	/**
	 * 根据快照名称，取得某个快照信息
	 * @param name
	 * @param withVolume 是否同时取得卷信息
	 * @return
	 */
	public VmSnapshot getByName(String name,
			boolean withVolume) throws Exception;
	
	/**
	 * 保存一个快照信息
	 * @param snapshot
	 * @return
	 */
	public void save(VmSnapshot snapshot) throws Exception;
	
	/**
	 * 更新一个快照信息
	 * @param snapshot
	 * @return
	 */
	public void update(VmSnapshot snapshot) throws Exception;
	
	/**
	 * 删除一个快照信息
	 * @param snapshotId 
	 * @return
	 */
	public void deleteById(Integer snapshotId) throws Exception;

	public VmSnapshot getByUuid(String uuid, boolean withVolume) throws Exception;
}
