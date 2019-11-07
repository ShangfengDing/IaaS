package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.Instance;
import appcloud.common.model.Instance.InstanceTypeEnum;
import appcloud.common.util.query.QueryObject;


public interface InstanceProxy {
	/**
	 * 取得所有实例信息
	 * @param withApp 是否同时获得实例所属应用的信息
	 * @param withHost 是否同时获得实例所在主机的信息
	 * @param withLoad 是否同时获得实例负载信息
	 * @return
	 */
	public List<? extends Instance> findAll(boolean withApp, boolean withHost, boolean withLoad) throws Exception;

	/**
	 * 分页取得全部实例信息
	 * @param withApp 是否同时获得实例所属应用的信息
	 * @param withHost 是否同时获得实例所在主机的信息
	 * @param withLoad 是否同时获得实例负载信息
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends Instance> findAll(boolean withApp, boolean withHost, boolean withLoad, 
			Integer page, Integer size) throws Exception;

	/**
	 * 取得总实例数
	 * @return
	 */
	public long countAll() throws Exception;
	
	/**
	 * 根据类型取得所有实例信息，j2ee实例和vm实例分开取得
	 * @param type 实例类型："j2ee"或者"vm"
	 * @param withApp 是否同时获得实例所属应用的信息
	 * @param withHost 是否同时获得实例所在主机的信息
	 * @param withLoad 是否同时获得实例负载信息
	 * @return
	 */
	public List<? extends Instance> getByType(InstanceTypeEnum type, 
			boolean withApp, boolean withHost, boolean withLoad) throws Exception;

	/**
	 * 分页，根据类型取得所有实例信息，j2ee实例和vm实例分开取得
	 * @param type 实例类型："j2ee"或者"vm"
	 * @param withApp 是否同时获得实例所属应用的信息
	 * @param withHost 是否同时获得实例所在主机的信息
	 * @param withLoad 是否同时获得实例负载信息
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends Instance> getByType(InstanceTypeEnum type, 
			boolean withApp, boolean withHost, boolean withLoad, 
			Integer page, Integer size) throws Exception;

	/**
	 * 根据类型取得总实例数，j2ee实例和vm实例分开取得
	 * @return
	 */
	public long countByType(InstanceTypeEnum type) throws Exception;
	
	/**
	 * 根据查询条件，搜索全部实例信息
	 * @param properties 查询条件，用户自己构造
	 * @param withApp 是否同时获得实例所属应用的信息
	 * @param withHost 是否同时获得实例所在主机的信息
	 * @param withLoad 是否同时获得实例负载信息
	 * @return
	 */
	public List<? extends Instance> searchAll(QueryObject<Instance> queryObject,
			boolean withApp, boolean withHost, boolean withLoad) throws Exception;

	/**
	 * 根据查询条件，分页搜索实例信息
	 * @param properties 查询条件，用户自己构造
	 * @param withApp 是否同时获得实例所属应用的信息
	 * @param withHost 是否同时获得实例所在主机的信息
	 * @param withLoad 是否同时获得实例负载信息
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends Instance> searchAll(QueryObject<Instance> queryObject,
			boolean withApp, boolean withHost, boolean withLoad, 
			Integer page, Integer size) throws Exception;

	/**
	 * 取得查询记录条数
	 * @return
	 */
	public long countSearch(QueryObject<Instance> queryObject) throws Exception;
	
	/**
	 * 通过uuid获取某个实例信息
	 * @param uuid 实例uuid
	 * @param withApp 是否同时获得实例所属应用的信息
	 * @param withHost 是否同时获得实例所在主机的信息
	 * @param withLoad 是否同时获得实例负载信息
	 * @return
	 */
	public Instance getByUuid(String uuid, boolean withApp, boolean withHost, boolean withLoad) throws Exception;
	
	/**
	 * 保存一个实例信息
	 * @param instance
	 * @return
	 */
	public void save(Instance instance) throws Exception;
	
	/**
	 * 更新一个实例信息
	 * @param instance 最新的实例信息
	 * @return
	 */
	public void update(Instance instance) throws Exception;
	
	/**
	 * 删除某个实例信息
	 * @param uuid 实例uuid
	 * @return
	 */
	public void deleteByUuid(String uuid) throws Exception;

}
