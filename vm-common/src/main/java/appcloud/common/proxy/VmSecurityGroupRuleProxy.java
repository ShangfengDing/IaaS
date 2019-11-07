package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.VmSecurityGroupRule;
import appcloud.common.util.query.QueryObject;

public interface VmSecurityGroupRuleProxy {
	/**
	 * 取得所有规则信息
	 * @param withGroup 是否同时取得防火墙
	 * @return
	 */
	public List<? extends VmSecurityGroupRule> findAll(boolean withGroup) throws Exception;
	
	/**
	 * 分页取得防火墙信息
	 * @param withGroup 是否同时取得防火墙
	 * @param page 第几页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends VmSecurityGroupRule> findAll(boolean withGroup
			,Integer page, Integer size) throws Exception;

	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;
	

	/**
	 * 根据查询条件，搜索规则信息
	 * @param queryObject 查询条件，用户自己构造
	 * @param withGroup 是否同时取得防火墙
	 * @return
	 */
	public List<? extends VmSecurityGroupRule> searchAll(QueryObject<VmSecurityGroupRule> queryObject,
			boolean withGroup) throws Exception;

	/**
	 * 根据查询条件，分页搜索规则信息
	 * @param queryObject 查询条件，用户自己构造
	 * @param withGroup 是否同时取得防火墙息
	 * @param page 第几页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends VmSecurityGroupRule> searchAll(QueryObject<VmSecurityGroupRule> queryObject, boolean withGroup
			,Integer page, Integer size) throws Exception;

	/**
	 * 取得查询记录条数
	 * @return
	 */
	public long countSearch(QueryObject<VmSecurityGroupRule> queryObject) throws Exception;

	/**
	 * 取得某个规则信息
	 * @param uuid
	 * @param withGroup 是否同时取得防火墙
	 * @return
	 */
	public VmSecurityGroupRule getByUuid(String uuid, boolean withGroup) throws Exception;
	
	
	/**
	 * 取得某个规则信息
	 * @param ruleId
	 * @param withGroup 是否同时取得防火墙
	 * @return
	 */
	public VmSecurityGroupRule getById(Integer ruleId, boolean withGroup) throws Exception;
	
	/**
	 * 取得某个规则信息
	 * @param groupId
	 * @param withGroup 是否同时取得防火墙
	 * @return
	 */
	public List<? extends VmSecurityGroupRule> getByGroupId(Integer groupId, boolean withGroup) throws Exception;
	
	
	
	/**
	 * 保存一个规则信息
	 * @param rule
	 * @return
	 */
	public void save(VmSecurityGroupRule rule) throws Exception;
	
	/**
	 * 更新一个 规则信息
	 * @param rule
	 * @return
	 */
	public void update(VmSecurityGroupRule rule) throws Exception;
	
	/**
	 * 删除一个 规则信息
	 * @param ruleId 
	 * @return
	 */
	public void deleteById(Integer ruleId) throws Exception;
}
