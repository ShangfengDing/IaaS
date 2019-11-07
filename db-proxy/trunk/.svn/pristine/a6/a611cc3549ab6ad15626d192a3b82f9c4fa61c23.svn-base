package appcloud.dbproxy.mysql;

import java.util.List;

import appcloud.common.model.AlertSetting;
import appcloud.common.proxy.AlertSettingProxy;
import appcloud.dbproxy.mysql.dao.AlertSettingDAO;
import appcloud.dbproxy.mysql.model.AlertSettingTable;

public class MySQLAlertSettingProxy implements AlertSettingProxy{
	private static AlertSettingDAO dao = new AlertSettingDAO();
	/**
	 * 取得所有告警设置
	 * @return
	 */
	public List<? extends AlertSetting> findAll() throws Exception {
		List<? extends AlertSetting> alertSettings = dao.findAll();
		return alertSettings;
	}
	
	/**
	 * 保存告警设置
	 * @param alertSetting
	 * @return
	 */
	public void save(AlertSetting alertSetting) throws Exception {
		dao.save(new AlertSettingTable(alertSetting));
	}
	
	/**
	 * 获取一个告警设置
	 * @param alertSettingId 告警设置id
	 * @return
	 */
	public AlertSetting getById(Integer alertSettingId) throws Exception {
		return dao.findById(alertSettingId);
	}
	
	/**
	 * 更新告警设置
	 * @param alertSetting
	 * @return
	 */
	public void update(AlertSetting alertSetting) throws Exception {
		dao.update(new AlertSettingTable(alertSetting));
	}
	
	/**
	 * 删除某个告警设置
	 * @param id
	 * @return
	 */
	public void deleteById(Integer id) throws Exception {
		dao.deleteByPrimaryKey(id);
	}
}
