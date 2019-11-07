package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.AdminResource;
import appcloud.common.util.query.QueryObject;

public interface AdminResourceProxy {
	/**
	 * 取得所有资源信息
	 * @return
	 */
	public List<? extends AdminResource> findAll() throws Exception;
	
	/**
	 * 分页取得资源信息
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends AdminResource> findAll(Integer page, Integer size) throws Exception;

	/**
	 * 根据查询条件查找
	 * @param queryObject
	 * @return
	 * @throws Exception
	 */
	public List<? extends AdminResource> searchAll(QueryObject<AdminResource> queryObject) throws Exception;

	/**
	 * 根据查询条件查找,并分页
	 * @param queryObject
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<? extends AdminResource> searchAll(QueryObject<AdminResource> queryObject, Integer page, Integer size) throws Exception;
	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;
	
	/**
	 * 取得某个资源信息
	 * @param adminResourceId
	 * @return
	 */
	public AdminResource getById(Integer adminResourceId) throws Exception;
	
	
	/**
	 * 保存
	 * @param adminResource
	 * @return
	 */
	public void save(AdminResource adminResource) throws Exception;
	
	/**
	 * 更新
	 * @param adminResource
	 * @return
	 */
	public void update(AdminResource adminResource) throws Exception;
	
	/**
	 * 删除
	 * @param adminResourceId 资源标签id
	 * @return
	 */
	public void deleteById(Integer adminResourceId) throws Exception;
}
