package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.J2EEApp;
import appcloud.common.util.query.QueryObject;

public interface J2EEAppProxy {
	/**
	 * 取得所有J2EE应用信息
	 * @param withDev 是否同时取得开发者信息
	 * @param withInstance 是否同时取得实例信息
	 * @param withNum 是否同时取得实例数
	 * @return
	 */
	public List<? extends J2EEApp> findAll(boolean withDev, boolean withInstance, boolean withNum) throws Exception;

	/**
	 * 分页取得J2EE应用信息，根据应用状态进行筛选
	 * @param withDev 是否同时取得开发者信息
	 * @param withInstance 是否同时取得实例信息
	 * @param withNum 是否同时取得实例数
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends J2EEApp> findAll(boolean withDev, boolean withInstance, boolean withNum, 
			Integer page, Integer size) throws Exception;

	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;

	/**
	 * 根据大应用j2eeInfoId，取得J2EE应用信息，开发者门户用到
	 * @param j2eeInfoId 大应用j2eeinfo的id
	 * @param withDev 是否同时取得开发者信息
	 * @param withInstance 是否同时取得实例信息
	 * @param withNum 是否同时取得实例数
	 * @return
	 */
	public List<? extends J2EEApp> getByJ2EEInfoId(Integer j2eeInfoId, 
			boolean withDev, boolean withInstance, boolean withNum) throws Exception;

	/**
	 * 根据大应用j2eeInfoId，分页取得J2EE应用信息，开发者门户用到
	 * @param j2eeInfoId 大应用j2eeinfo的id
	 * @param withDev 是否同时取得开发者信息
	 * @param withInstance 是否同时取得实例信息
	 * @param withNum 是否同时取得实例数
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends J2EEApp> getByJ2EEInfoId(Integer j2eeInfoId, 
			boolean withDev, boolean withInstance, boolean withNum, 
			Integer page, Integer size) throws Exception;

	/**
	 * 取得大应用j2eeInfoId的具体小应用数目
	 * @return
	 */
	public long countByJ2EEInfoId(Integer j2eeInfoId) throws Exception;

	/**
	 * 根据查询条件，搜索应用信息
	 * @param properties 查询条件，用户自己构造
	 * @param withDev 是否同时取得开发者信息
	 * @param withInstance 是否同时取得实例信息
	 * @param withNum 是否同时取得实例数
	 * @return
	 */
	public List<? extends J2EEApp> searchAll(QueryObject<J2EEApp> queryObject,
			boolean withDev, boolean withInstance, boolean withNum) throws Exception;

	/**
	 * 根据查询条件，分页搜索应用信息
	 * @param properties 查询条件，用户自己构造
	 * @param withDev 是否同时取得开发者信息
	 * @param withInstance 是否同时取得实例信息
	 * @param withNum 是否同时取得实例数
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends J2EEApp> searchAll(QueryObject<J2EEApp> queryObject,
			boolean withDev, boolean withInstance, boolean withNum, 
			Integer page, Integer size) throws Exception;

	/**
	 * 取得查询记录条数
	 * @return
	 */
	public long countSearch(QueryObject<J2EEApp> queryObject) throws Exception;
	
	/**
	 * 根据uuid取得某个应用信息
	 * @param uuid
	 * @param withDev
	 * @param withInstance
	 * @param withNum
	 * @return
	 */
	public J2EEApp getByUuid(String uuid, boolean withDev, boolean withInstance, boolean withNum) throws Exception;
	
	/**
	 * 根据id取得某个应用版本信息
	 * @param id
	 * @param withDev
	 * @param withInstance
	 * @param withNum
	 * @return
	 * @throws Exception
	 */
	public J2EEApp getById(Integer id, boolean withDev, boolean withInstance, boolean withNum) throws Exception;
	
	/**
	 * 存储一个j2ee应用版本
	 * @param app
	 * @return 新建应用版本的id
	 */
	public Integer save(J2EEApp app) throws Exception;
	
	/**
	 * 更新一个j2ee应用版本信息
	 * @param app
	 * @return
	 */
	public void update(J2EEApp app) throws Exception;
	
	/**
	 * 根据uuid删除一个j2ee应用版本
	 * @param uuid
	 * @return
	 */
	public void deleteByUuid(String uuid) throws Exception;
	
	/**
	 * 根据id删除一个j2ee应用版本
	 * @param id
	 * @return
	 */
	public void deleteById(Integer id) throws Exception;
}
