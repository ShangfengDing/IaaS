// Update time: 2013-05-08 13:24:49
package appcloud.resourcescheduler.stub.dbproxys;

import java.util.ArrayList;
import java.util.List;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.String;
import appcloud.common.model.Nginx;
import appcloud.common.proxy.NginxProxy;
;

public class NginxProxyStub implements NginxProxy{
	
	@Override
	public void save(Nginx arg0) throws Exception{
	}
	@Override
	public void update(Nginx arg0) throws Exception{
	}
	@Override
	public List getDomainSuffixs(String arg0) throws Exception{
		return new ArrayList();
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
	public Nginx getById(boolean arg0, Integer arg1) throws Exception{
		return new Nginx();
	}
	@Override
	public List getByDomainSuffix(String arg0) throws Exception{
		return new ArrayList();
	}

}
