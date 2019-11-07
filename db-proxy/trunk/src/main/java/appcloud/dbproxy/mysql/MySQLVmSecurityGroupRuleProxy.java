package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.VmSecurityGroup;
import appcloud.common.model.VmSecurityGroupRule;
import appcloud.common.proxy.VmSecurityGroupRuleProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.VmSecurityGroupDAO;
import appcloud.dbproxy.mysql.dao.VmSecurityGroupRuleDAO;
import appcloud.dbproxy.mysql.model.VmSecurityGroupRuleTable;

public class MySQLVmSecurityGroupRuleProxy implements VmSecurityGroupRuleProxy{

	private static VmSecurityGroupRuleDAO dao = new VmSecurityGroupRuleDAO();
	private static VmSecurityGroupDAO groupDao = new VmSecurityGroupDAO();
	
	@Override
	public List<? extends VmSecurityGroupRule> findAll(boolean withGroup)
			throws Exception {
		return findAll(withGroup, 0, 0);
	}

	@Override
	public List<? extends VmSecurityGroupRule> findAll(
			boolean withGroup, Integer page, Integer size) throws Exception {
		List<? extends VmSecurityGroupRule> rules =  dao.findAll();
		for(VmSecurityGroupRule rule : rules)
			fillUpVmSecurityGroupRule(rule, withGroup);
		return rules;
	}

	@Override
	public long countAll() throws Exception {
		return dao.countAll();
	}

	@Override
	public List<? extends VmSecurityGroupRule> searchAll(
			QueryObject<VmSecurityGroupRule> queryObject,
			boolean withGroup) throws Exception {
		return searchAll(queryObject, withGroup, 0, 0);
	}

	@Override
	public List<? extends VmSecurityGroupRule> searchAll(
			QueryObject<VmSecurityGroupRule> queryObject,
			boolean withGroup, Integer page, Integer size) throws Exception {
		List<? extends VmSecurityGroupRule> rules = dao.findByProperties(queryObject, page, size);
		for(VmSecurityGroupRule rule : rules)
			fillUpVmSecurityGroupRule(rule, withGroup);
		return rules;
	}

	@Override
	public long countSearch(QueryObject<VmSecurityGroupRule> queryObject)
			throws Exception {
		return dao.countByProperties(queryObject);
	}
	
	@Override
	public VmSecurityGroupRule getByUuid(String uuid, boolean withGroup) throws Exception{
		List<? extends VmSecurityGroupRule> rules = dao.findByProperty("uuid", uuid);
		VmSecurityGroupRule rule = null;
		if(rules.size() != 0){
			rule = rules.get(0);
			fillUpVmSecurityGroupRule(rule, withGroup);
		}
		return rule;
	}

	@Override
	public VmSecurityGroupRule getById(Integer ruleId, boolean withGroup)
			throws Exception {
		VmSecurityGroupRule rule = dao.findById(ruleId);
		fillUpVmSecurityGroupRule(rule, withGroup);
		return rule;
	}

	@Override
	public List <?extends VmSecurityGroupRule> getByGroupId(Integer groupId,
			boolean withGroup) throws Exception {
		List<? extends VmSecurityGroupRule> rules = dao.findByProperty("groupId", groupId);
		for(VmSecurityGroupRule rule : rules)
			fillUpVmSecurityGroupRule(rule, withGroup);
		return rules;
	}

	@Override
	public void save(VmSecurityGroupRule rule) throws Exception {
		dao.save(new VmSecurityGroupRuleTable(rule));
	}

	@Override
	public void update(VmSecurityGroupRule rule) throws Exception {
		dao.update(new VmSecurityGroupRuleTable(rule));
	}

	@Override
	public void deleteById(Integer ruleId) throws Exception {
		dao.deleteByPrimaryKey(ruleId);
	}

	private void fillUpVmSecurityGroupRule(VmSecurityGroupRule rule, boolean withGroup) {
		if(rule != null && withGroup) {
			VmSecurityGroup group = groupDao.findById(rule.getGroupId());
			rule.setVmSecurityGroup(group);
		}
	}
}
