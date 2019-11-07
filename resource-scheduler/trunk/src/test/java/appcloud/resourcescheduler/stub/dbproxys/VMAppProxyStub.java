// Update time: 2013-05-08 13:24:49
package appcloud.resourcescheduler.stub.dbproxys;

import java.util.ArrayList;
import appcloud.common.model.VMApp;
import appcloud.common.proxy.VMAppProxy;
import appcloud.common.util.query.QueryObject;
import java.util.List;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.String;
;

public class VMAppProxyStub implements VMAppProxy{
	
	@Override
	public List searchAll(QueryObject arg0, boolean arg1, boolean arg2) throws Exception{
		return new ArrayList();
	}
	@Override
	public List searchAll(QueryObject arg0, boolean arg1, boolean arg2, Integer arg3, Integer arg4) throws Exception{
		return new ArrayList();
	}
	@Override
	public VMApp getByUuid(String arg0, boolean arg1, boolean arg2) throws Exception{
		return new VMApp();
	}
	@Override
	public void deleteByUuid(String arg0) throws Exception{
	}
	@Override
	public List getByDevId(Integer arg0, boolean arg1, boolean arg2) throws Exception{
		return new ArrayList();
	}
	@Override
	public long countByDevId(Integer arg0) throws Exception{
		return 0;
	}

}
