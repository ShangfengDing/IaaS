package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.AdminLog;
import appcloud.common.proxy.AdminLogProxy;
import appcloud.dbproxy.mysql.dao.AdminLogDAO;
import appcloud.dbproxy.mysql.model.AdminLogTable;

public class MySQLAdminLogProxy implements AdminLogProxy{
	private static AdminLogDAO dao = new AdminLogDAO();
	
	/**
	 * 取得所有管理员操作日志记录
	 * @return
	 */
	//done
	@Override
	public List<? extends AdminLog> findAll() throws Exception {
		return findAll(0, 0);
	}
	
	/**
	 * 分页取得管理员操作日志记录
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	//done
	@Override
	public List<? extends AdminLog> findAll(Integer page, Integer size) throws Exception {
		List<? extends AdminLog> adminLogs = dao.findAll(page, size);
		return adminLogs;
	}

	/**
	 * 取得总记录条数
	 * @return
	 */
	//done
	@Override
	public long countAll() throws Exception {
		return dao.countAll();
	}
	
	/**
	 * 保存一条日志记录
	 * @param adminLog
	 * @return
	 */
	//done
	@Override
	public void save(AdminLog adminLog) throws Exception {
		dao.save(new AdminLogTable(adminLog));
	}
		
	/**
	 * 删除一条日志记录
	 * @param adminLogId
	 * @return
	 */
	//done
	@Override
	public void deleteById(Integer adminLogId) throws Exception {
		dao.deleteByPrimaryKey(adminLogId);
	}
}
