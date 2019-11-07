package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.VmHostUsed;


public interface VmHostUsedProxy {

	/**
	 * 取得所有主机资源使用情况
	 * @return
	 */
	public List<? extends VmHostUsed> findAll() throws Exception;
	
	/**
	 * 根据host uuid取得某个主机的资源使用情况
	 * @param hostUuid
	 * @return
	 */
	public VmHostUsed getByHostUuid(String hostUuid) throws Exception;
	
	/**
	 * 保存一个主机资源使用情况
	 * @param vmHostUsed
	 * @return
	 */
	public void save(VmHostUsed vmHostUsed) throws Exception;
	
	/**
	 * 更新一个主机资源使用情况
	 * @param vmHostUsed
	 * @return
	 */
	public void update(VmHostUsed vmHostUsed) throws Exception;
	
	/**
	 * 根据host uuid删除一个主机资源使用情况
	 * @param vmHostUsed
	 * @return
	 */
	public void deleteByHostUuid(String hostUuid) throws Exception;
	
	/**
	 * 根据id删除一个主机资源使用情况
	 * @param vmHostUsed
	 * @return
	 */
	public void deleteById(Integer id) throws Exception;
}
