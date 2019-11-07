package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.AdminLog;

/**
 * @author lzc
 *
 */
public interface AdminLogProxy {
	/**
	 * 取得所有管理员操作日志记录
	 * @return
	 */
	public List<? extends AdminLog> findAll() throws Exception;
	
	/**
	 * 分页取得管理员操作日志记录
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends AdminLog> findAll(Integer page, Integer size) throws Exception;

	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;
	
	/**
	 * 保存一条日志记录
	 * @param adminLog
	 * @return
	 */
	public void save(AdminLog adminLog) throws Exception;
		
	/**
	 * 删除一条日志记录
	 * @param adminLogId
	 * @return
	 */
	public void deleteById(Integer adminLogId) throws Exception;
}