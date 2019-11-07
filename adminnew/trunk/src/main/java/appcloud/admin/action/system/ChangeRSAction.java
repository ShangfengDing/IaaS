package appcloud.admin.action.system;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.client.AcAggregateClient;

import com.appcloud.vm.fe.util.ClientFactory;

public class ChangeRSAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7533825173664948684L;
	private static Logger logger = Logger.getLogger(ChangeRSAction.class);
	private AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();

	private String clusterId;
	private String rsUuid;
	
	@Override
	public String execute() throws Exception {
		logger.info("clusterId = " + clusterId);
		aggregateClient.updateRSUuid(Integer.valueOf(clusterId), rsUuid);
		return SUCCESS;
	}

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	public String getRsUuid() {
		return rsUuid;
	}

	public void setRsUuid(String rsUuid) {
		this.rsUuid = rsUuid;
	}
	
}
