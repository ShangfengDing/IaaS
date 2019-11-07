package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.ResourceStrategy;
import appcloud.common.model.ResourceStrategy.StrategyTypeEnum;
import appcloud.common.util.query.QueryObject;

public interface ResourceStrategyProxy {

	/**
	 * 取得所有资源分配策略
	 * @return
	 */
	public List<? extends ResourceStrategy> findAll() throws Exception;
	
	/**
	 * search all by queryObject
	 * @param queryObject
	 * @return
	 * @throws Exception
	 */
	public List<? extends ResourceStrategy> searchALL(QueryObject<ResourceStrategy> queryObject) throws Exception;
	/**
	 * 根据id取得某个资源策略
	 * @param resourceStrategyId
	 * @return
	 */
	public ResourceStrategy getById(Integer resourceStrategyId) throws Exception;
	
	/**
	 * 根据type取得某个资源策略
	 * @param type
	 * @return
	 */
	public List<? extends ResourceStrategy> getByType(StrategyTypeEnum type) throws Exception;
	
	/**
	 * 根据uuid取得某个资源策略
	 * @param resourceStrategyUuid
	 * @return
	 */
	public List<? extends ResourceStrategy> getByUuid(String resourceStrategyUuid) throws Exception;
	
	/**
	 * 保存一个资源策略，前端暂时用不到
	 * @param resourceStrategy
	 * @return
	 */
	public void save(ResourceStrategy resourceStrategy) throws Exception;
	
	/**
	 * 更新一个资源策略，前端暂时用不到
	 * @param resourceStrategy
	 * @return
	 */
	public void update(ResourceStrategy resourceStrategy) throws Exception;
	
	/**
	 * 根据id删除一个资源策略，前端暂时用不到
	 * @param resrcStrategyId
	 * @return
	 */
	public void deleteById(Integer resourceStrategyId) throws Exception;
	
	/**
	 * 根据uuid删除一个资源策略，前端暂时用不到
	 * @param resourceStrategyUuid
	 * @return
	 */
	public void deleteByUuid(String resourceStrategyUuid) throws Exception;
}
