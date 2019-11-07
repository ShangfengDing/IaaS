package appcloud.common.proxy;
import java.util.List;

import appcloud.common.model.VmSecurityGroup;
import appcloud.common.util.query.QueryObject;

/**
 * @author XuanJiaxing
 * 
 */
public interface VmSecurityGroupProxy {
	/**
	 * 取得所有防火墙信息
	 * @param withRules 是否同时取得规则
	 * @return
	 */
	public List<? extends VmSecurityGroup> findAll(boolean withRules) throws Exception;
	
	/**
	 * 分页取得防火墙信息
	 * @param withRules 是否同时取得规则
	 * @param page 第几页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends VmSecurityGroup> findAll(boolean withRules
			,Integer page, Integer size) throws Exception;

	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;
	

	/**
	 * 根据查询条件，搜索防火墙信息
	 * @param queryObject 查询条件，用户自己构造
	 * @param withRules 是否同时取得规则
	 * @return
	 */
	public List<? extends VmSecurityGroup> searchAll(QueryObject<VmSecurityGroup> queryObject,
			boolean withRules) throws Exception;

	/**
	 * 根据查询条件，分页搜索防火墙信息
	 * @param queryObject 查询条件，用户自己构造
	 * @param withRules 是否同时取得规则息
	 * @param page 第几页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends VmSecurityGroup> searchAll(QueryObject<VmSecurityGroup> queryObject, boolean withRules
			,Integer page, Integer size) throws Exception;

	/**
	 * 取得查询记录条数
	 * @return
	 */
	public long countSearch(QueryObject<VmSecurityGroup> queryObject) throws Exception;
	
	/**
	 * 取得某个防火墙信息
	 * @param uuid
	 * @param withRules 是否同时取得规则
	 * @return
	 */
	public VmSecurityGroup getByUuid(String uuid, boolean withRules) throws Exception;
	
	
	/**
	 * 取得某个防火墙信息
	 * @param groupId
	 * @param withRules 是否同时取得规则
	 * @return
	 */
	public VmSecurityGroup getById(Integer groupId, boolean withRules) throws Exception;
	
	/**
	 * 保存一个防火墙信息
	 * @param group
	 * @return
	 */
	public void save(VmSecurityGroup group) throws Exception;
	
	/**
	 * 更新一个 防火墙信息
	 * @param group
	 * @return
	 */
	public void update(VmSecurityGroup group) throws Exception;
	
	/**
	 * 删除一个 防火墙信息
	 * @param groupId 
	 * @return
	 */
	public void deleteById(Integer groupId) throws Exception;
}
