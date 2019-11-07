/**
 * File: VmVirtualInterfaceProxy.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.VmVirtualInterface;

/**
 * @author weed
 *
 */
public interface VmVirtualInterfaceProxy {
	/**
	 * 取得所有vm网卡信息
	 * @return
	 */
	public List<? extends VmVirtualInterface> findAll() throws Exception;
	
	/**
	 * 分页取得vm网卡信息
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends VmVirtualInterface> findAll(Integer page, Integer size) throws Exception;

	/**
	 * 取得某个vm网卡信息
	 * @param vmVirtualInterfaceId vm网卡信息id
	 * @return
	 */
	public VmVirtualInterface getById(Integer vmVirtualInterfaceId) throws Exception;
	
	/**
	 * 根据用户名，取得某个vm网卡信息
	 * @param instanceUuid
	 * @return
	 * @throws Exception
	 */
	public List<? extends VmVirtualInterface> getByInstanceUuid(String instanceUuid) throws Exception;
	
	/**
	 * 保存一个vm网卡信息
	 * @param vmVirtualInterface
	 * @return
	 */
	public void save(VmVirtualInterface vmVirtualInterface) throws Exception;
		
	/**
	 * 更新vm网卡信息
	 * @param vmVirtualInterface
	 * @return
	 */
	public void update(VmVirtualInterface vmVirtualInterface) throws Exception;
	
	/**
	 * 删除一个vm网卡信息
	 * @param vmVirtualInterfaceId
	 * @return
	 */
	public void deleteById(Integer vmVirtualInterfaceId) throws Exception;
	
	/**
	 * 删除一个vm网卡信息
	 * @param instanceUuid
	 * @return
	 */
	public void deleteByInstanceUuid(String instanceUuid) throws Exception;
	
	/**
	 * 根据ip查询
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public List<? extends VmVirtualInterface> getByIp(String ip) throws Exception;
}
