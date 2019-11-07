// Update time: 2013-05-08 13:24:49
package appcloud.resourcescheduler.stub.dbproxys;

import java.util.ArrayList;
import appcloud.common.util.query.QueryObject;
import java.util.List;
import java.lang.Exception;
import appcloud.common.model.Developer;
import java.lang.Integer;
import appcloud.common.proxy.DeveloperProxy;
;

public class DeveloperProxyStub implements DeveloperProxy{
	
	@Override
	public void save(Developer arg0) throws Exception{
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
	public Developer getById(Integer arg0, boolean arg1) throws Exception{
		return new Developer();
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

}
