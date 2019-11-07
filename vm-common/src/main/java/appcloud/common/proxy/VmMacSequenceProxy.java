/**
 * File: VmMacSequenceProxy.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.VmMacSequence;

/**
 * @author weed
 *
 */
public interface VmMacSequenceProxy {
	/**
	 * 取得所有mac地址
	 * @return
	 */
	public List<? extends VmMacSequence> findAll() throws Exception;
	
	/**
	 * 分页取得mac地址
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends VmMacSequence> findAll(Integer page, Integer size) throws Exception;

	/**
	 * 取得某个mac地址
	 * @param vmMacSequenceId mac地址id
	 * @return
	 */
	public VmMacSequence getById(Integer vmMacSequenceId) throws Exception;
	
	/**
	 * 根据用户名，取得某个mac地址
	 * @param clusterId
	 * @return
	 * @throws Exception
	 */
	public VmMacSequence getByClusterId(Integer clusterId) throws Exception;
	
	/**
	 * 保存一个mac地址
	 * @param vmMacSequence
	 * @return
	 */
	public void save(VmMacSequence vmMacSequence) throws Exception;
		
	/**
	 * 删除一个mac地址
	 * @param vmMacSequenceId
	 * @return
	 */
	public void deleteById(Integer vmMacSequenceId) throws Exception;
	
	/**
	 * 删除一个mac地址
	 * @param clusterId
	 * @return
	 */
	public void deleteByClusterId(Integer clusterId) throws Exception;
}
