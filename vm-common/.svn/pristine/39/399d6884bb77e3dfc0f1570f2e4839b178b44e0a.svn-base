/**
 * File: VmIpSegMentProxy.java
 * Author: weed
 * Create Time: 2013-4-15
 */
package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.VmIpSegMent;

/**
 * @author weed
 *
 */
public interface VmIpSegMentProxy {
	/**
	 * 取得所有ip分段
	 * @return
	 */
	public List<VmIpSegMent> findAll() throws Exception;
	
	/**
	 * 分页取得ip分段
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<VmIpSegMent> findAll(Integer dhcpId, Integer segment) throws Exception;

	/**
	 * 取得某个ip分段信息
	 * @param vmIpSegMentId ip分段id
	 * @return
	 */
	public VmIpSegMent getById(Integer vmIpSegMentId) throws Exception;
	
	/**
	 * 保存一个ip分段
	 * @param vmIpSegMent
	 * @return
	 */
	public void save(VmIpSegMent vmIpSegMent) throws Exception;
		
	/**
	 * 删除一个ip分段
	 * @param vmIpSegMentId
	 * @return
	 */
	public void deleteById(Integer vmIpSegMentId) throws Exception;
	
}
