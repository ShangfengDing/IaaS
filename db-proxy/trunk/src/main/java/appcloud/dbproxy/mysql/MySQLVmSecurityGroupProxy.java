package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.VmSecurityGroup;
import appcloud.common.model.VmSecurityGroupRule;
import appcloud.common.proxy.VmSecurityGroupProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.VmSecurityGroupDAO;
import appcloud.dbproxy.mysql.dao.VmSecurityGroupRuleDAO;
import appcloud.dbproxy.mysql.model.VmSecurityGroupTable;

public class MySQLVmSecurityGroupProxy implements VmSecurityGroupProxy{

	private static VmSecurityGroupDAO dao = new VmSecurityGroupDAO();
	private static VmSecurityGroupRuleDAO ruleDao = new VmSecurityGroupRuleDAO();
	
	@Override
	public List<? extends VmSecurityGroup> findAll(boolean withRules)
			throws Exception {
		return findAll(withRules, 0, 0);
	}

	@Override
	public List<? extends VmSecurityGroup> findAll(boolean withRules,
			Integer page, Integer size) throws Exception {
		List <? extends VmSecurityGroup> groups = dao.findAll(page, size);
		for(VmSecurityGroup group : groups)
			fillUpVmSecurityGroup(group, withRules);
		return groups;
	}

	@Override
	public long countAll() throws Exception {
		return dao.countAll();
	}

	@Override
	public List<? extends VmSecurityGroup> searchAll(
			QueryObject<VmSecurityGroup> queryObject, boolean withRules)
			throws Exception {
		return searchAll(queryObject, withRules, 0, 0);
	}

	@Override
	public List<? extends VmSecurityGroup> searchAll(
			QueryObject<VmSecurityGroup> queryObject, boolean withRules,
			Integer page, Integer size) throws Exception {
		List<? extends VmSecurityGroup> groups = dao.findByProperties(queryObject, page, size);
		for(VmSecurityGroup group: groups)
			fillUpVmSecurityGroup(group, withRules);
		return groups;
	}

	@Override
	public long countSearch(QueryObject<VmSecurityGroup> queryObject)
			throws Exception {
		return dao.countByProperties(queryObject);
	}

	@Override
	public VmSecurityGroup getByUuid(String uuid, boolean withRules) throws Exception{
		List<?extends VmSecurityGroup> groups = dao.findByProperty("uuid", uuid);
		VmSecurityGroup group = null;
		if(groups.size() != 0){
			group = groups.get(0);
			fillUpVmSecurityGroup(group, withRules);
		}
		return group;
	}
	
	@Override
	public VmSecurityGroup getById(Integer groupId, boolean withRules)
			throws Exception {
		VmSecurityGroup group = dao.findById(groupId);
		fillUpVmSecurityGroup(group, withRules);
		return group;
	}
	
	@Override
	public void save(VmSecurityGroup group) throws Exception {
		dao.save(new VmSecurityGroupTable(group));
	}

	@Override
	public void update(VmSecurityGroup group) throws Exception {
		dao.update(new VmSecurityGroupTable(group));
	}

	@Override
	public void deleteById(Integer groupId) throws Exception {
		dao.deleteByPrimaryKey(groupId);
	}
	
	private void fillUpVmSecurityGroup(VmSecurityGroup group, boolean withRules) {
		if(group == null)
			return;
		if(withRules){
			List<? extends VmSecurityGroupRule> rules = ruleDao.findByProperty("groupId", group.getId());
			group.setVmSecurityGroupRules((List<VmSecurityGroupRule>)rules);
		}
	}

}
