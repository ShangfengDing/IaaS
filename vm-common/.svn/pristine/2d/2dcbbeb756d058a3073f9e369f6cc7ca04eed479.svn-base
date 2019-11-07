package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.Cluster;
import appcloud.common.model.ClusterStatistic;
import appcloud.common.model.ResourceStrategy;
import appcloud.common.util.query.QueryObject;

/**
 * @author lzc
 *
 */
public interface ClusterProxy {

	/**
	 * 取得集群信息列表
	 * @param withResrcTaskStrategy 同时取得集群的资源策略、任务策略
	 * @param withHosts 同时取得集群的主机列表
	 * @param withNum 同时取得集群的主机数目
	 * @return
	 */
	public List<? extends Cluster> findAll(boolean withResrcTaskStrategy, boolean withHosts, boolean withNum) throws Exception;

	/**
	 * 分页取得集群信息列表
	 * @param withResrcTaskStrategy 同时取得集群的资源策略、任务策略
	 * @param withHosts 同时取得集群的主机列表
	 * @param withNum 同时取得集群的主机数目
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends Cluster> findAll(boolean withResrcTaskStrategy, boolean withHosts, boolean withNum, 
			Integer page, Integer size) throws Exception;

	/**
	 * search clusters with queryObject
	 * @param queryObject
	 * @return
	 * @throws Exception
	 */
	public List<? extends Cluster> searchAll(QueryObject<Cluster> queryObject) throws Exception;

	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;
	
	/**
	 * 取得某个集群信息
	 * @param id 集群id
	 * @param withResrcTaskStrategy 同时取得集群的资源策略、任务策略
	 * @param withHosts 同时取得集群的主机列表
	 * @param withNum 同时取得集群的主机数目
	 * @return 存在返回具体信息，不存在返回null
	 */
	public Cluster getById(Integer id, boolean withResrcTaskStrategy, boolean withHosts, boolean withNum) throws Exception;
	
	/**
	 * 取得某个集群信息
	 * @param Name 集群Name
	 * @return 存在返回具体信息，不存在返回null
	 */
	public Cluster getByName(String name) throws Exception;

	
	/**
	 * 保存一个新集群信息
	 * @param cluster
	 * @return
	 */
	public void save(Cluster cluster) throws Exception;
	
	/**
	 * 取得集群的统计信息，包括负载情况、主机数、用户数、实例数等
	 * @param clusterId
	 * @return
	 */
	public ClusterStatistic getClusterStatisticById(Integer clusterId) throws Exception;
	
	/**
	 * 更新集群信息
	 * @param cluster
	 * @return
	 */
	public void update(Cluster cluster) throws Exception;
	
	/**
	 * 删除集群
	 * @param clusterId
	 * @return
	 */
	public void deleteById(Integer clusterId) throws Exception;
}
