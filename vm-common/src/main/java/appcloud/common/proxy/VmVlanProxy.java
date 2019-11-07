package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.VmVlan;

/**
 * @author XuanJiaxing
 *
 */
public interface VmVlanProxy {

	/**
	 * 取得虚拟局域网信息
	 * @return
	 */
	public List<?extends VmVlan> findAll() throws Exception;
	
	/**
	 * 分页取得虚拟局域网信息
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<?extends VmVlan> findAll (Integer page, Integer size) throws Exception;
	
	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;
	
	/**
	 * 取得某个虚拟局域网信息
	 * @param vlanId
	 * @return
	 */
	public VmVlan getById(Integer vlanId) throws Exception;
	
	/**
	 * 保存一个虚拟局域网信息
	 * @param vlan
	 * @return
	 */
	public void save(VmVlan vlan) throws Exception;
	
	/**
	 * 更新一个虚拟局域网信息
	 * @param vlan
	 * @return
	 */
	public void update(VmVlan vlan) throws Exception;
	
	/**
	 * 删除一个虚拟局域网信息
	 * @param vlanId 
	 * @return
	 */
	public void deleteById(Integer vlanId) throws Exception;
}
