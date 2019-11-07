// Update time: 2013-05-08 13:24:49
package appcloud.resourcescheduler.stub.dbproxys;

import java.util.ArrayList;
import appcloud.common.model.VmSecurityGroup;
import appcloud.common.util.query.QueryObject;
import java.util.List;
import java.lang.Exception;
import java.lang.Integer;
import appcloud.common.proxy.VmSecurityGroupProxy;
import java.lang.String;
;

public class VmSecurityGroupProxyStub implements VmSecurityGroupProxy{
	
	@Override
	public void save(VmSecurityGroup arg0) throws Exception{
	}
	@Override
	public void update(VmSecurityGroup arg0) throws Exception{
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
	public VmSecurityGroup getById(Integer arg0, boolean arg1) throws Exception{
		return new VmSecurityGroup();
	}
	@Override
	public List searchAll(QueryObject arg0, boolean arg1, Integer arg2, Integer arg3) throws Exception{
		return new ArrayList();
	}
	@Override
	public List searchAll(QueryObject arg0, boolean arg1) throws Exception{
		return new ArrayList();
	}
	@Override
	public long countSearch(QueryObject arg0) throws Exception{
		return 0;
	}
	@Override
	public VmSecurityGroup getByUuid(String arg0, boolean arg1) throws Exception{
		return new VmSecurityGroup();
	}

}
