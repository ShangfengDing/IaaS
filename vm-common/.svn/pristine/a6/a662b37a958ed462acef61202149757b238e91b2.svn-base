package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.AlertMsg;
import appcloud.common.util.query.QueryObject;

/**
 * @author lzc
 *
 * edit by weed
 * add searchAll
 */
public interface AlertMsgProxy {

	/**
	 * 取得所有告警信息
	 * @return
	 */
	public List<? extends AlertMsg> findAll() throws Exception;

	/**
	 * 分页取得告警信息
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends AlertMsg> findAll(Integer page, Integer size) throws Exception;

	/**
	 * 搜索全部
	 * @param queryObject 查询条件，用户自己构造
	 * @return
	 */
	public List<? extends AlertMsg> searchAll(QueryObject<AlertMsg> queryObject) throws Exception;

	/**
	 * 分页搜索
	 * @param queryObject 查询条件，用户自己构造
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends AlertMsg> searchAll(QueryObject<AlertMsg> queryObject,
			Integer page, Integer size) throws Exception;
	
	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception;
	
	/**
	 * 保存一条告警信息
	 * @param alertMsg
	 * @return
	 */
	public void save(AlertMsg alertMsg) throws Exception;
	
	/**
	 * 更新一条告警信息
	 * @param alertMsg
	 * @return
	 */
	public void update(AlertMsg alertMsg) throws Exception;
	
	/**
	 * 删除一条告警信息
	 * @param alertMsgId 信息id
	 * @return
	 */
	public void deleteById(Integer alertMsgId) throws Exception;
}
