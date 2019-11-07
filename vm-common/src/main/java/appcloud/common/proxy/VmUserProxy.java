package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.VmUser;
import appcloud.common.util.query.QueryObject;

/**
 * @author XuanJiaxing
 *
 */
public interface VmUserProxy {
	/**
	 * 取得所有用户信息
	 * @return
	 */
	public List<? extends VmUser> findAll() throws Exception;
	
	/**
	 * 分页取得用户信息
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends VmUser> findAll(Integer page, Integer size) throws Exception;

	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;

	/**
	 * 根据查询条件，搜索用户信息
	 * @param queryObject 查询条件，用户自己构造
	 * @return
	 */
	public List<? extends VmUser> searchAll(QueryObject<VmUser> queryObject) throws Exception;

	/**
	 * 根据查询条件，分页搜索用户信息
	 * @param queryObject 查询条件，用户自己构造
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends VmUser> searchAll(QueryObject<VmUser> queryObject,
			Integer page, Integer size) throws Exception;

	/**
	 * 取得查询记录条数
	 * @return
	 */
	public long countSearch(QueryObject<VmUser> queryObject) throws Exception;
	
	/**
	 * 根据组查询用户
	 * @param groupId
	 * @return
	 */
	public List<? extends VmUser> getByGroupId(Integer groupId);
	
	/**
	 * 根据所在企业查询用户
	 * @param enterpriseId
	 * @return
	 */
	public List<? extends VmUser> getByEnterpriseId(Integer enterpriseId) throws Exception;
	
	/**
	 * 根据组查询用户
	 * @param groupId
	 * @return
	 */
	public List<? extends VmUser> getByGroupId(Integer groupId, Integer page, Integer size);
	
	/**
	 * 通过主键取得某个用户信息
	 * @param userId
	 * @return
	 */
	public VmUser getById(Integer id) throws Exception;

	/**
	 * 通过用户id取得某个用户信息
	 * @param userId
	 * @return
	 */
	public VmUser getByUserId(Integer userId) throws Exception;
	/**
	 * 保存一个用户信息
	 * @param user
	 * @return
	 */
	public void save(VmUser user) throws Exception;
	
	/**
	 * 更新一个用户信息
	 * @param user
	 * @return
	 */
	public void update(VmUser user) throws Exception;
	
	/**
	 * 删除一个用户信息
	 * @param userId 
	 * @return
	 */
	public void deleteById(Integer userId) throws Exception;

	/**
	 * 根据appkeyid查找用户
	 * @param appKeyId
	 * @return
	 */
	public VmUser getByAppKeyId(String appKeyId) throws Exception;
}
