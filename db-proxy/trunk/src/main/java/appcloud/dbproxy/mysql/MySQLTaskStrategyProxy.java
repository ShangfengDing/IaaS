package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.TaskStrategy;
import appcloud.common.proxy.TaskStrategyProxy;
import appcloud.dbproxy.mysql.dao.TaskStrategyDAO;
import appcloud.dbproxy.mysql.model.TaskStrategyTable;

public class MySQLTaskStrategyProxy implements TaskStrategyProxy{
	private static TaskStrategyDAO dao = new TaskStrategyDAO();

	@Override
	public List<? extends TaskStrategy> findAll() throws Exception{
		return dao.findAll();
	}

	@Override
	public TaskStrategy getById(Integer taskStrategyId) throws Exception{
		return dao.findByPrimaryKey(taskStrategyId);
	}

	@Override
	public void save(TaskStrategy taskStrategy) throws Exception{
		dao.save(new TaskStrategyTable(taskStrategy));
	}

	@Override
	public void update(TaskStrategy taskStrategy) throws Exception{
		dao.update(new TaskStrategyTable(taskStrategy));
	}

	@Override
	public void deleteById(Integer taskStrategyId) throws Exception{
		dao.deleteByPrimaryKey(taskStrategyId);
	} 
}
