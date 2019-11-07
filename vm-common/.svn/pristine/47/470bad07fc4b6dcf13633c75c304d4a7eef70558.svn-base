package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.VMApp;
import appcloud.common.util.query.QueryObject;


/**
 * @author lzc
 *
 */
public interface VMAppProxy {

	/**
	 * 根据devId取得某个用户的所有vm对象
	 * @param devId 用户id
	 * @param withDev
	 * @param withInstance
	 * @return 若没有，返回空列表
	 * @throws Exception
	 */
	public List<? extends VMApp> getByDevId(Integer devId, boolean withDev, boolean withInstance) throws Exception;
	
	/**
	 * 查询某个开发者拥有的虚拟机数量
	 * @param devId 开发者id
	 * @return 
	 * @throws Exception
	 */
	public long countByDevId(Integer devId) throws Exception;
	
	/**
	 * 根据uuid取得vm对象
	 * @param uuid vm的uuid
	 * @param withDev 是否同时取得开发者信息
	 * @param withInstance 是否同时取得vm的一个实例信息
	 * @return 相应的vm对象信息
	 * @throws Exception
	 */
	public VMApp getByUuid(String uuid, boolean withDev, boolean withInstance) throws Exception;
	
	/**
	 * 根据uuid删除一个vmApp
	 * @param uuid
	 * @return
	 */
	public void deleteByUuid(String uuid) throws Exception;

	/**
	 * 根据查询条件，搜索VM信息
	 * @param properties 查询条件，用户自己构造
	 * @param withDev 是否同时取得开发者信息
	 * @param withInstance 是否同时取得vm的一个实例信息
	 * @return 符合查询条件的vm列表
	 */
	public List<? extends VMApp> searchAll(QueryObject<VMApp> queryObject, boolean withDev, boolean withInstance) throws Exception;

	/**
	 * 根据查询条件，分页搜索VM信息
	 * @param properties 查询条件，用户自己构造
	 * @param withDev 是否同时取得开发者信息
	 * @param withInstance 是否同时取得vm的一个实例信息
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return 符合查询条件的vm列表
	 */
	public List<? extends VMApp> searchAll(QueryObject<VMApp> queryObject, boolean withDev, boolean withInstance,
			Integer page, Integer size) throws Exception;
}
