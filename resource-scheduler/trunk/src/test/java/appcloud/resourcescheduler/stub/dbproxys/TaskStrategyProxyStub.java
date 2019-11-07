// Update time: 2013-05-08 13:24:49
package appcloud.resourcescheduler.stub.dbproxys;

import java.util.ArrayList;
import java.util.List;
import java.lang.Exception;
import java.lang.Integer;
import appcloud.common.model.TaskStrategy;
import appcloud.common.proxy.TaskStrategyProxy;
;

public class TaskStrategyProxyStub implements TaskStrategyProxy{
	
	@Override
	public void save(TaskStrategy arg0) throws Exception{
	}
	@Override
	public void update(TaskStrategy arg0) throws Exception{
	}
	@Override
	public List findAll() throws Exception{
		return new ArrayList();
	}
	@Override
	public void deleteById(Integer arg0) throws Exception{
	}
	@Override
	public TaskStrategy getById(Integer arg0) throws Exception{
		return new TaskStrategy();
	}

}
