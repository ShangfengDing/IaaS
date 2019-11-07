// Update time: 2013-05-08 13:24:49
package appcloud.resourcescheduler.stub.dbproxys;

import java.util.ArrayList;
import appcloud.common.model.VmInstance;
import appcloud.common.util.query.QueryObject;
import java.util.List;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.String;
import appcloud.common.proxy.VmInstanceProxy;
;

public class VmInstanceProxyStub implements VmInstanceProxy{
	
	@Override
	public void save(VmInstance arg0) throws Exception{
	}
	@Override
	public void update(VmInstance arg0) throws Exception{
	}
	@Override
	public List findAll(boolean arg0, boolean arg1, boolean arg2, boolean arg3, boolean arg4, boolean arg5, boolean arg6, boolean arg7, Integer arg8, Integer arg9) throws Exception{
		return new ArrayList();
	}
	@Override
	public List findAll(boolean arg0, boolean arg1, boolean arg2, boolean arg3, boolean arg4, boolean arg5, boolean arg6, boolean arg7) throws Exception{
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
	public List searchAll(QueryObject arg0, boolean arg1, boolean arg2, boolean arg3, boolean arg4, boolean arg5, boolean arg6, boolean arg7, boolean arg8, Integer arg9, Integer arg10) throws Exception{
		return new ArrayList();
	}
	@Override
	public List searchAll(QueryObject arg0, boolean arg1, boolean arg2, boolean arg3, boolean arg4, boolean arg5, boolean arg6, boolean arg7, boolean arg8) throws Exception{
		return new ArrayList();
	}
	@Override
	public long countSearch(QueryObject arg0) throws Exception{
		return 0;
	}
	@Override
	public VmInstance getByUuid(String arg0, boolean arg1, boolean arg2, boolean arg3, boolean arg4, boolean arg5, boolean arg6, boolean arg7, boolean arg8) throws Exception{
		return new VmInstance();
	}
	@Override
	public void deleteByUuid(String arg0) throws Exception{
	}
	@Override
	public List findAllUuid() throws Exception{
		return new ArrayList();
	}

}
