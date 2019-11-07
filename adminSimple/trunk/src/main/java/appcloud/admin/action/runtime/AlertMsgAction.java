package appcloud.admin.action.runtime;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.common.model.AlertMsg;
import appcloud.common.util.ConnectionFactory;

public class AlertMsgAction extends BaseAction {
	/**
	 * 查找所有告警日志信息
	 */
	private static final long serialVersionUID = 2851570832024373103L;
	private static final Logger logger = Logger.getLogger(AlertMsgAction.class);
	private List<AlertMsg> alertMsgs = new ArrayList<AlertMsg>();
	
	@SuppressWarnings("unchecked")
	public String execute(){	
		try {
			alertMsgs = (List<AlertMsg>) ConnectionFactory.getAlertMsgProxy().findAll();
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("数据库读取失败");
		}
		return SUCCESS;
	}

	public List<AlertMsg> getAlertMsgs() {
		return alertMsgs;
	}

	public void setAlertMsgs(List<AlertMsg> alertMsgs) {
		this.alertMsgs = alertMsgs;
	}
}
