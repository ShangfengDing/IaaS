package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.ResrcStrategy;

/**
 * @author lzc
 *
 */
public interface ResrcStrategyProxy {

	/**
	 * 取得所有资源分配策略
	 * @return
	 */
	public List<? extends ResrcStrategy> findAll() throws Exception;
	
	/**
	 * 根据id取得某个资源策略
	 * @param resrcStrategyId
	 * @return
	 */
	public ResrcStrategy getById(Integer resrcStrategyId) throws Exception;
	
	/**
	 * 保存一个资源策略，前端暂时用不到
	 * @param resrcStrategy
	 * @return
	 */
	public void save(ResrcStrategy resrcStrategy) throws Exception;
	
	/**
	 * 更新一个资源策略，前端暂时用不到
	 * @param resrcStrategy
	 * @return
	 */
	public void update(ResrcStrategy resrcStrategy) throws Exception;
	
	/**
	 * 根据id删除一个资源策略，前端暂时用不到
	 * @param resrcStrategyId
	 * @return
	 */
	public void deleteById(Integer resrcStrategyId) throws Exception;
}
