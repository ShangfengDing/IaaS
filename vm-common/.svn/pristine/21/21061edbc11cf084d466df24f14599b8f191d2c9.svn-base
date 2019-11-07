package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.Admin;
import appcloud.common.model.AdminRole;
import appcloud.common.model.AdminURL;
import appcloud.common.util.query.QueryObject;


public interface AdminURLProxy {
	/**
	 * 取得所有此资源所对应的可访问url
	 * @return
	 */
	public List<? extends AdminURL> findAll() throws Exception;
	
	/**
	 * 分页取得所以资源对应的url
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends AdminURL> findAll(Integer page, Integer size) throws Exception;
	
	/**
	 * 根据查询条件查找
	 * @param queryObject
	 * @return
	 * @throws Exception
	 */
	public List<? extends AdminURL> searchAll(QueryObject<AdminURL> queryObject) throws Exception;

	/**
	 * 根据查询条件查找,并分页
	 * @param queryObject
	 * @param page
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<? extends AdminURL> searchAll(QueryObject<AdminURL> queryObject, Integer page, Integer size) throws Exception;
	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;
	
	/**
	 * 取得某个资源对应url信息
	 * @param adminURLId
	 * @return
	 */
	public AdminURL getById(Integer adminURLId) throws Exception;
	
	/**
	 * 根据资源Id取得的对应url信息
	 * @param adminResourceId
	 * @return
	 */
	public List<? extends AdminURL> getByResourceId(Integer adminResourceId) throws Exception;
	
	
	
	/**
	 * 保存
	 * @param adminURL
	 * @return
	 */
	public void save(AdminURL adminURL) throws Exception;
	
	/**
	 * 更新
	 * @param adminURL
	 * @return
	 */
	public void update(AdminURL adminURL) throws Exception;
	
	/**
	 * 删除
	 * @param adminURLId 
	 * @return
	 */
	public void deleteById(Integer adminURLId) throws Exception;
}
