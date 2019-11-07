package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.AdminPrivilege;
import appcloud.common.util.query.QueryObject;

public interface AdminPrivilegeProxy {
	/**
	 * 取得角色资源对应信息
	 * @return
	 */
	public List<? extends AdminPrivilege> findAll() throws Exception;
	
	/**
	 * 分页取得角色资源对应信息
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends AdminPrivilege> findAll(Integer page, Integer size) throws Exception;

	/**
	 * 根据查询条件查找
	 * @param queryObject
	 * @return
	 * @throws Exception
	 */
	public List<? extends AdminPrivilege> searchAll(QueryObject<AdminPrivilege> queryObject) throws Exception;

	/**
	 * 根据查询条件查找,并分页
	 * @param queryObject
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<? extends AdminPrivilege> searchAll(QueryObject<AdminPrivilege> queryObject, Integer page, Integer size) throws Exception;
	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;
	
	public long countSearch(QueryObject<AdminPrivilege> queryObject) throws Exception;
	
	/**
	 * 取得某个角色资源对应信息
	 * @param adminPrivilegeId 
	 * @return
	 */
	public AdminPrivilege getById(Integer adminPrivilegeId) throws Exception;
	
	/**
	 * 根据角色id，取得其对应的关系表
	 * @param roleId 角色Id
	 * @return
	 */
	public List<? extends AdminPrivilege> getByRoleId(Integer roleId) throws Exception;
	
	/**
	 * 保存
	 * @param adminPrivilege
	 * @return
	 */
	public void save(AdminPrivilege adminPrivilege) throws Exception;
	
	/**
	 * 更新
	 * @param adminPrivilege
	 * @return
	 */
	public void update(AdminPrivilege adminPrivilege) throws Exception;
	
	/**
	 * 删除
	 * @param adminPrivilegeId 对应id
	 * @return
	 */
	public void deleteById(Integer adminPrivilegeId) throws Exception;
}
