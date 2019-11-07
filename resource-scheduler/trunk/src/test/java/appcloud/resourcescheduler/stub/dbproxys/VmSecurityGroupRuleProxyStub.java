// Update time: 2013-05-08 13:24:49
package appcloud.resourcescheduler.stub.dbproxys;

import java.util.ArrayList;
import appcloud.common.util.query.QueryObject;
import java.util.List;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.String;
import appcloud.common.model.VmSecurityGroupRule;
import appcloud.common.proxy.VmSecurityGroupRuleProxy;
;

public class VmSecurityGroupRuleProxyStub implements VmSecurityGroupRuleProxy{
	
	@Override
	public void save(VmSecurityGroupRule arg0) throws Exception{
	}
	@Override
	public void update(VmSecurityGroupRule arg0) throws Exception{
	}
	@Override
	public List findAll(boolean arg0, Integer arg1, Integer arg2) throws Exception{
		return new ArrayList();
	}
	@Override
	public List findAll(boolean arg0) throws Exception{
		return new ArrayList();
	}
	@Override
	public long countAll() throws Exception{
		return 0;
	}
	@Override
	public void deleteById(Integer arg0) throws Exception{
	}
	@Override
	public VmSecurityGroupRule getById(Integer arg0, boolean arg1) throws Exception{
		return new VmSecurityGroupRule();
	}
	@Override
	public List searchAll(QueryObject arg0, boolean arg1) throws Exception{
		return new ArrayList();
	}
	@Override
	public List searchAll(QueryObject arg0, boolean arg1, Integer arg2, Integer arg3) throws Exception{
		return new ArrayList();
	}
	@Override
	public long countSearch(QueryObject arg0) throws Exception{
		return 0;
	}
	@Override
	public VmSecurityGroupRule getByUuid(String arg0, boolean arg1) throws Exception{
		return new VmSecurityGroupRule();
	}
	@Override
	public List getByGroupId(Integer arg0, boolean arg1) throws Exception{
		return new ArrayList();
	}

}
