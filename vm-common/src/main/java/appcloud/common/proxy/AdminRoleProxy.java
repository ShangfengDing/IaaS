package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.AdminResource;
import appcloud.common.model.AdminRole;
import appcloud.common.util.query.QueryObject;

public interface AdminRoleProxy {
	/**
	 * 取得所有管理员角色信息
	 * @return
	 */
	public List<? extends AdminRole> findAll() throws Exception;
	
	/**
	 * 分页取得管理员角色信息
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends AdminRole> findAll(Integer page, Integer size) throws Exception;
	
	/**
	 * 根据查询条件查找
	 * @param queryObject
	 * @return
	 * @throws Exception
	 */
	public List<? extends AdminRole> searchAll(QueryObject<AdminRole> queryObject) throws Exception;

	/**
	 * 根据查询条件查找,并分页
	 * @param queryObject
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<? extends AdminRole> searchAll(QueryObject<AdminRole> queryObject, Integer page, Integer size) throws Exception;

	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;
	
	/**
	 * 根据查询条件计数
	 * @param queryObject
	 * @return
	 */
	public long countSearch(QueryObject<AdminRole> queryObject) throws Exception;
	/**
	 * 取得某个管理员角色信息
	 * @param adminRoleId 管理员角色id
	 * @return
	 */
	public AdminRole getById(Integer adminRoleId) throws Exception;
	
	
	/**
	 * 保存一个新管理员信息
	 * @param admin
	 * @return
	 */
	public void save(AdminRole adminRole) throws Exception;
	
	/**
	 * 更新一个管理员信息
	 * @param admin
	 * @return
	 */
	public void update(AdminRole adminRole) throws Exception;
	
	/**
	 * 删除一个管理员信息
	 * @param adminId 管理员id
	 * @return
	 */
	public void deleteById(Integer adminRoleId) throws Exception;
}
