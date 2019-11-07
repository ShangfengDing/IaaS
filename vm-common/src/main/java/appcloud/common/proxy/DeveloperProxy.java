package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.Developer;
import appcloud.common.util.query.QueryObject;

/**
 * @author lzc
 *
 */
public interface DeveloperProxy {
	/**
	 * 取得所有用户信息列表
	 * @param withNum 是否同时取得用户的j2ee应用数、实例数、虚拟机数
	 * @return
	 */
	public List<? extends Developer> findAll(boolean withNum) throws Exception;

	/**
	 * 分页取得用户信息列表
	 * @param withNum 是否同时取得用户的j2ee应用数、实例数、虚拟机数
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends Developer> findAll(boolean withNum, Integer page, Integer size) throws Exception;

	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;
	
	/**
	 * 搜索用户信息
	 * @param queryObject 查询条件，用户自己构造
	 * @param withNum 是否同时取得用户的j2ee应用数、实例数、虚拟机数
	 * @return 存在则返回，否则返回emptyList
	 */
	public List<? extends Developer> searchAll(QueryObject<Developer> queryObject, boolean withNum) throws Exception;
	
	/**
	 * 搜索用户信息
	 * @param queryObject 查询条件，用户自己构造
	 * @param withNum 是否同时取得用户的j2ee应用数、实例数、虚拟机数
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return 存在则返回，否则返回emptyList
	 */
	public List<? extends Developer> searchAll(QueryObject<Developer> queryObject, boolean withNum, 
			Integer page, Integer size) throws Exception;

	/**
	 * 取得查询记录条数
	 * @return
	 */
	public long countSearch(QueryObject<Developer> queryObject) throws Exception;
	
	/**
	 * 根据id取得某个用户信息
	 * @param id
	 * @param withNum
	 * @return 存在则返回，否则返回null
	 */
	public Developer getById(Integer id, boolean withNum) throws Exception;
	
	/**
	 * 保存一个用户信息
	 * @param dev
	 * @return
	 */
	public void save(Developer dev) throws Exception;
}
