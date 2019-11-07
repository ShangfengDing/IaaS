package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.Host;
import appcloud.common.util.query.QueryObject;

/**
 * @author lzc
 *
 */
public interface HostProxy {

	/**
	 * 取得所有节点信息
	 * @param withCluster 同时取得集群信息
	 * @param withLoad 同时取得主机负载信息（包括Load和NetworkLoad）
	 * @param withNum 同时取得计算节点上承载的j2ee实例数和vm实例数
	 * @return
	 */
	public List<? extends Host> findAll(boolean withCluster, boolean withLoad, boolean withNum) throws Exception;

	/**
	 * 分页取得节点信息
	 * @param withCluster 同时取得集群信息
	 * @param withLoad 同时取得主机负载信息（包括Load和NetworkLoad）
	 * @param withNum 同时取得计算节点上承载的j2ee实例数和vm实例数
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends Host> findAll(boolean withCluster, boolean withLoad, boolean withNum, 
			Integer page, Integer size) throws Exception;

	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;

	/**
	 * 搜索全部
	 * @param properties 查询条件，用户自己构造
	 * @param withCluster 同时取得集群信息
	 * @param withLoad 同时取得主机负载信息（包括Load和NetworkLoad）
	 * @param withNum 同时取得计算节点上承载的j2ee实例数和vm实例数
	 * @return
	 */
	public List<? extends Host> searchAll(QueryObject<Host> queryObject,
			boolean withCluster, boolean withLoad, boolean withNum) throws Exception;

	/**
	 * 分页搜索
	 * @param properties 查询条件，用户自己构造
	 * @param withCluster 同时取得集群信息
	 * @param withLoad 同时取得主机负载信息（包括Load和NetworkLoad）
	 * @param withNum 同时取得计算节点上承载的j2ee实例数和vm实例数
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends Host> searchAll(QueryObject<Host> queryObject,
			boolean withCluster, boolean withLoad, boolean withNum, 
			Integer page, Integer size) throws Exception;

	/**
	 * 取得查询记录条数
	 * @return
	 */
	public long countSearch(QueryObject<Host> queryObject) throws Exception;
	
	/**
	 * 通过uuid取得某个host信息
	 * @param uuid hostUuid
	 * @param withCluster 同时取得集群信息
	 * @param withLoad 同时取得主机负载信息（包括Load和NetworkLoad）
	 * @param withNum 同时取得计算节点上承载的j2ee实例数和vm实例数
	 * @return
	 */
	public Host getByUuid(String uuid, boolean withCluster, boolean withLoad, boolean withNum) throws Exception;
	
	/**
	 * 保存一个主机节点
	 * @param host
	 * @return
	 */
	public void save(Host host) throws Exception;
	
	/**
	 * 更新一个主机节点信息
	 * @param host
	 * @return
	 */
	public Host update(Host host) throws Exception;
	
	/**
	 * 删除一个主机节点
	 * @param uuid 主机的uuid
	 * @return
	 */
	public void deleteByUuid(String uuid) throws Exception;

	/**
	 * 通过type查找节点
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List<? extends Host> findByType(String type) throws Exception;
}
