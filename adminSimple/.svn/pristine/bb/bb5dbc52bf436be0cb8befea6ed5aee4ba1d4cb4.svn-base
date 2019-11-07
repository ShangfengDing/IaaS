package appcloud.admin.action.runtime;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcMailConf;
import appcloud.api.client.AcMessageLogClient;

import com.appcloud.vm.fe.util.ClientFactory;

public class MailConfAction extends BaseAction {
	/**
	 * 查找告警设置
	 */
	private static final long serialVersionUID = 2851570832024373103L;
	private static final Logger logger = Logger.getLogger(MailConfAction.class);
	private AcMessageLogClient acMessageLogClient = ClientFactory.getAcMessageLogClient();
	
	private AcMailConf acMailConf;
	
	public String execute(){
		acMailConf = acMessageLogClient.getMailConf();
		logger.info(acMailConf);
		return SUCCESS;
	}

	public AcMailConf getAcMailConf() {
		return acMailConf;
	}

	public void setAcMailConf(AcMailConf acMailConf) {
		this.acMailConf = acMailConf;
	}

}
