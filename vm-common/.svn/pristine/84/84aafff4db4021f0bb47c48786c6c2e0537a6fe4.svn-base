package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.VmGroup;
import appcloud.common.util.query.QueryObject;

/**
 * @author XuanJiaxing
 *
 */
public interface VmGroupProxy {
	/**
	 * 取得所有组信息
	 * @return
	 */
	public List<? extends VmGroup> findAll() throws Exception;
	
	/**
	 * 分页取得组信息
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends VmGroup> findAll(Integer page, Integer size) throws Exception;

	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;

	/**
	 * 根据查询条件，搜索组信息
	 * @param queryObject 查询条件，用户自己构造
	 * @return
	 */
	public List<? extends VmGroup> searchAll(QueryObject<VmGroup> queryObject) throws Exception;

	/** 
	 * 根据查询条件，分页搜索组信息
	 * @param queryObject 查询条件，用户自己构造
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends VmGroup> searchAll(QueryObject<VmGroup> queryObject,
			Integer page, Integer size) throws Exception;

	/**
	 * 取得查询记录条数
	 * @return
	 */
	public long countSearch(QueryObject<VmGroup> queryObject) throws Exception;
	
	
	/**
	 * 取得某个组信息
	 * @param groupId
	 * @return
	 */
	public VmGroup getById(Integer groupId) throws Exception;
	
	/**
	 * 保存一个组信息
	 * @param group
	 * @return
	 */
	public void save(VmGroup group) throws Exception;
	
	/**
	 * 更新一个组信息
	 * @param group
	 * @return
	 */
	public void update(VmGroup group) throws Exception;
	
	/**
	 * 删除一个组信息
	 * @param groupId 
	 * @return
	 */
	public void deleteById(Integer groupId) throws Exception;
}
