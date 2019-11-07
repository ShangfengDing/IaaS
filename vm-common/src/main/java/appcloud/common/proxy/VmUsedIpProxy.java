/**
 * File: VmUsedIpProxy.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.NetSegment;
import appcloud.common.model.VmUsedIp;

/**
 * @author weed
 *
 */
public interface VmUsedIpProxy {
	/**
	 * 取得所有vm使用的Ip和mac地址
	 * @return
	 */
	public List<VmUsedIp> findAll(Integer IpSegmentid) throws Exception;
	
	/**
	 * 保存一个vm使用的Ip和mac地址
	 * @param vmUsedIp
	 * @return
	 */
	public void save(VmUsedIp vmUsedIp) throws Exception;
	
	/**
	* @Title: deleteIp
	* @Description: 删除某个ip段内的id
	* @param ipSegmentId
	* @param ip
	* @throws Exception
	*/ 
	public void deleteIp(Integer IpSegmentid, String ip) throws Exception;
	
	/**
	 * 根据UsedIp来计算这个段中有多少个ip已经被使用
	 * @param ipSegmentId
	 * @return
	 * @throws Exception
	 */
	public Long countUsedIp(Integer ipSegmentId) throws Exception;
}
