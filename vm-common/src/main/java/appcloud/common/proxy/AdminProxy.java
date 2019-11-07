package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.Admin;
import appcloud.common.util.query.QueryObject;

/**
 * @author lzc
 *
 */
public interface AdminProxy {

	/**
	 * 取得所有管理员信息
	 * @param withRole是否同时取得角色信息
	 * @param withPrivilege是否同时取得角色资源对应信息
	 * @param withResource是否同时取得资源标签信息
	 * @return
	 */
	public List<? extends Admin> findAll(boolean withRole, boolean withPrivilege, boolean withResource) throws Exception;
	
	/**
	 * 分页取得管理员信息
	 * @param withRole是否同时取得角色信息
	 * @param withPrivilege是否同时取得角色资源对应信息
	 * @param withResource是否同时取得资源标签信息
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends Admin> findAll(boolean withRole, boolean withPrivilege, boolean withResource, Integer page, Integer size) throws Exception;

	/**
	 * 根据查询条件，搜索管理员信息
	 * @param queryObject
	 * @param withRole
	 * @param withPrivilege
	 * @param withResource
	 * @return
	 * @throws Exception
	 */
	public List<? extends Admin> searchAll(QueryObject<Admin> queryObject,
			boolean withRole, boolean withPrivilege, boolean withResource) throws Exception;
	/**
	 * 根据查询条件，搜索管理员信息，并分页
	 * @param queryObject
	 * @param withRole
	 * @param withPrivilege
	 * @param withResource
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<? extends Admin> searchAll(QueryObject<Admin> queryObject,
			boolean withRole, boolean withPrivilege, boolean withResource, Integer page, Integer size) throws Exception;
	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;
	
	/**
	 * 根据查询条件计数
	 * @param quetyObject
	 * @return
	 */
	public long countSearch(QueryObject<Admin> queryObject) throws Exception;
	/**
	 * 取得某个管理员信息
	 * @param adminId 管理员id
	 * @param withRole是否同时取得角色信息
	 * @param withPrivilege是否同时取得角色资源对应信息
	 * @param withResource是否同时取得资源标签信息
	 * @return
	 */
	public Admin getById(Integer adminId, boolean withRole, boolean withPrivilege, boolean withResource) throws Exception;
	
	/**
	 * 根据用户名，取得某个管理员信息
	 * @param username 管理员用户名
	 * @param withRole是否同时取得角色信息
	 * @param withPrivilege是否同时取得角色资源对应信息
	 * @param withResource是否同时取得资源标签信息
	 * @return
	 */
	public Admin getByUsername(String username, boolean withRole, boolean withPrivilege, boolean withResource) throws Exception;
	
	/**
	 * 保存一个新管理员信息
	 * @param admin
	 * @return
	 */
	public void save(Admin admin) throws Exception;
	
	/**
	 * 更新一个管理员信息
	 * @param admin
	 * @return
	 */
	public void update(Admin admin) throws Exception;
	
	/**
	 * 删除一个管理员信息
	 * @param adminId 管理员id
	 * @return
	 */
	public void deleteById(Integer adminId) throws Exception;
}
