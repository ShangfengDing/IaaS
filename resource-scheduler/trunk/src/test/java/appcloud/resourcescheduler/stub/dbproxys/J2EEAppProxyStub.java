// Update time: 2013-05-08 13:24:49
package appcloud.resourcescheduler.stub.dbproxys;

import java.util.ArrayList;
import appcloud.common.proxy.J2EEAppProxy;
import appcloud.common.util.query.QueryObject;
import java.util.List;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.String;
import appcloud.common.model.J2EEApp;
;

public class J2EEAppProxyStub implements J2EEAppProxy{
	
	@Override
	public Integer save(J2EEApp arg0) throws Exception{
		return 0;
	}
	@Override
	public void update(J2EEApp arg0) throws Exception{
	}
	@Override
	public List findAll(boolean arg0, boolean arg1, boolean arg2, Integer arg3, Integer arg4) throws Exception{
		return new ArrayList();
	}
	@Override
	public List findAll(boolean arg0, boolean arg1, boolean arg2) throws Exception{
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
	public J2EEApp getById(Integer arg0, boolean arg1, boolean arg2, boolean arg3) throws Exception{
		return new J2EEApp();
	}
	@Override
	public List searchAll(QueryObject arg0, boolean arg1, boolean arg2, boolean arg3) throws Exception{
		return new ArrayList();
	}
	@Override
	public List searchAll(QueryObject arg0, boolean arg1, boolean arg2, boolean arg3, Integer arg4, Integer arg5) throws Exception{
		return new ArrayList();
	}
	@Override
	public long countSearch(QueryObject arg0) throws Exception{
		return 0;
	}
	@Override
	public J2EEApp getByUuid(String arg0, boolean arg1, boolean arg2, boolean arg3) throws Exception{
		return new J2EEApp();
	}
	@Override
	public void deleteByUuid(String arg0) throws Exception{
	}
	@Override
	public List getByJ2EEInfoId(Integer arg0, boolean arg1, boolean arg2, boolean arg3) throws Exception{
		return new ArrayList();
	}
	@Override
	public List getByJ2EEInfoId(Integer arg0, boolean arg1, boolean arg2, boolean arg3, Integer arg4, Integer arg5) throws Exception{
		return new ArrayList();
	}
	@Override
	public long countByJ2EEInfoId(Integer arg0) throws Exception{
		return 0;
	}

}
