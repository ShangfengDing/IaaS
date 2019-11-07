// Update time: 2013-05-08 13:24:49
package appcloud.resourcescheduler.stub.dbproxys;

import java.util.ArrayList;
import appcloud.common.proxy.RoutingEntryProxy;
import appcloud.common.util.query.QueryObject;
import java.util.List;
import java.util.HashSet;
import java.lang.Exception;
import appcloud.common.model.RoutingEntry;
import java.lang.Integer;
import java.lang.String;
import java.util.Set;
;

public class RoutingEntryProxyStub implements RoutingEntryProxy{
	
	@Override
	public void save(RoutingEntry arg0) throws Exception{
	}
	@Override
	public List search(QueryObject arg0, int arg1, int arg2) throws Exception{
		return new ArrayList();
	}
	@Override
	public RoutingEntry enable(RoutingEntry arg0) throws Exception{
		return new RoutingEntry();
	}
	@Override
	public List findAll() throws Exception{
		return new ArrayList();
	}
	@Override
	public void deleteById(Integer arg0) throws Exception{
	}
	@Override
	public List getBySrc(String arg0, String arg1) throws Exception{
		return new ArrayList();
	}
	@Override
	public boolean disableBySrc(String arg0, String arg1) throws Exception{
		return true;
	}
	@Override
	public void deleteBySrc(String arg0, String arg1) throws Exception{
	}
	@Override
	public Set deleteByDest(String arg0, String arg1) throws Exception{
		return new HashSet();
	}
	@Override
	public List getBySrcSuffix(List arg0) throws Exception{
		return new ArrayList();
	}

}
