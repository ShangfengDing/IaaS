package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.Service;
import appcloud.common.model.Service.ServiceTypeEnum;
import appcloud.common.util.query.QueryObject;

/**
 * 获取Service信息
 * @author wylazy
 *
 */
public interface ServiceProxy {

	
	/**
	 * 保存一条记录
	 * @param service
	 */
	public void save(Service service);
	
	/**
	 * 更新记录
	 * @param service
	 * @return
	 */
	public Service update(Service service);
	
	/**
	 * 删除记录
	 * @param id
	 */
	public void delete(Integer id);
	
	/**
	 * 通过Id获取一个Service
	 * @param id
	 * @param withHost 是否连同主机信息一块儿返回
	 * @return
	 */
	public Service getById(Integer id, boolean withHost);
	
	/**
	 * 查询主机上运行的Service
	 * @param hostIp 主机ip
	 * @param monitorPort Service运行的Monitor端口
	 * @param withHost 是否连同主机信息一块儿返回
	 * @return
	 */
	public Service getUniqueService(String hostIp, Integer monitorPort, boolean withHost);
	
	/**
	 * 获取某个主机上的全部Service，默认无Host信息
	 * @param hostId
	 * @return
	 */
	public List<? extends Service> getHostServicesById(Integer hostId);
	
	/**
	 * 获取主机上最后一次更新的service
	 * @param hostId
	 * @return
	 */
	public Service getLatestHostService(Integer hostId);
	
	/**
	 * 获取某个主机上的全部Service，默认无Host信息
	 * @param hostUuid
	 * @return
	 */
	public List<? extends Service> getHostServicesByUuid(String hostUuid);

	/**
	 * 通过能力获取服务
	 * @param type 服务的类型
	 * @param withHost 
	 * @return
	 */
	public List<? extends Service> getServiceByType(ServiceTypeEnum type, boolean withHost);
	

	/**
	 * 查询符合条件的Service
	 * @param withHost 是否连同主机信息一块儿返回
	 * @return
	 */
	public List<? extends Service> findAll(boolean withHost);
	
	/**
	 * 查询符合条件的Service
	 * @param queryObject 查询对象
	 * @param withHost 是否连同主机信息一块儿返回
	 * @return
	 */
	public List<? extends Service> searchAll(QueryObject<Service> queryObject, boolean withHost);
}
