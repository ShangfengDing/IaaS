package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.AlertMsg;
import appcloud.common.proxy.AlertMsgProxy;
import appcloud.common.util.query.QueryObject;
import appcloud.dbproxy.mysql.dao.AlertMsgDAO;
import appcloud.dbproxy.mysql.model.AlertMsgTable;

public class MySQLAlertMsgProxy implements AlertMsgProxy{
	private static AlertMsgDAO dao = new AlertMsgDAO();
	/**
	 * 取得所有告警信息
	 * @return
	 */
	public List<? extends AlertMsg> findAll() throws Exception {
		return findAll(0, 0);
	}

	/**
	 * 分页取得告警信息
	 * @param page 第几页，0代表不分页
	 * @param size 每页大小，0代表不分页
	 * @return
	 */
	public List<? extends AlertMsg> findAll(Integer page, Integer size) throws Exception {
		List<? extends AlertMsg> alertMsgs = dao.findAll(page, size);
		return alertMsgs;
	}

	/**
	 * 取得总记录条数
	 * @return
	 */
	public long countAll() throws Exception {
		return dao.countAll();
	}
	
	/**
	 * 保存一条告警信息
	 * @param alertMsg
	 * @return
	 */
	public void save(AlertMsg alertMsg) throws Exception {
		dao.save(new AlertMsgTable(alertMsg));
	}
	
	/**
	 * 删除一条告警信息
	 * @param alertMsgId 信息id
	 * @return
	 */
	public void deleteById(Integer alertMsgId) throws Exception {
		dao.deleteByPrimaryKey(alertMsgId);
	}

	@Override
	public List<? extends AlertMsg> searchAll(QueryObject<AlertMsg> queryObject)
			throws Exception {
		return dao.findByProperties(queryObject, 0, 0);
	}

	@Override
	public List<? extends AlertMsg> searchAll(
			QueryObject<AlertMsg> queryObject, Integer page, Integer size)
			throws Exception {
		return dao.findByProperties(queryObject, page, size);
	}

	@Override
	public void update(AlertMsg alertMsg) throws Exception {
		dao.update(new AlertMsgTable(alertMsg));
	}
}
