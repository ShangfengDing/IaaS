package appcloud.common.proxy;

import java.util.List;

import appcloud.common.model.AlertSetting;

/**
 * @author lzc
 *
 */
public interface AlertSettingProxy {

	/**
	 * 取得所有告警设置
	 * @return
	 */
	public List<? extends AlertSetting> findAll() throws Exception;
	
	/**
	 * 保存告警设置
	 * @param alertSetting
	 * @return
	 */
	public void save(AlertSetting alertSetting) throws Exception;
	
	/**
	 * 获取一个告警设置
	 * @param alertSettingId 告警设置id
	 * @return
	 */
	public AlertSetting getById(Integer alertSettingId) throws Exception;
	
	/**
	 * 更新告警设置
	 * @param alertSetting
	 * @return
	 */
	public void update(AlertSetting alertSetting) throws Exception;
	
	/**
	 * 删除某个告警设置
	 * @param id
	 * @return
	 */
	public void deleteById(Integer id) throws Exception;
}