// Update time: 2013-05-08 13:24:49
package appcloud.resourcescheduler.stub.dbproxys;

import java.util.ArrayList;
import appcloud.common.proxy.ResrcStrategyProxy;
import java.util.List;
import java.lang.Exception;
import java.lang.Integer;
import appcloud.common.model.ResrcStrategy;
;

public class ResrcStrategyProxyStub implements ResrcStrategyProxy{
	
	@Override
	public void save(ResrcStrategy arg0) throws Exception{
	}
	@Override
	public void update(ResrcStrategy arg0) throws Exception{
	}
	@Override
	public List findAll() throws Exception{
		return new ArrayList();
	}
	@Override
	public void deleteById(Integer arg0) throws Exception{
	}
	@Override
	public ResrcStrategy getById(Integer arg0) throws Exception{
		return new ResrcStrategy();
	}

}
