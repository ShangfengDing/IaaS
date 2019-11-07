package appcloud.admin.action.network;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcAggregate;
import appcloud.api.client.AcAggregateClient;

import com.appcloud.vm.fe.util.ClientFactory;

public class PreNewNetAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(PreNewNetAction.class);
	private AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();
	
	private List<AcAggregate> aggregates = new ArrayList<AcAggregate>();
	private int zoneId;
	
	public String execute() {
		aggregates = aggregateClient.getAggregatesOfZone(zoneId);
		logger.info("读取所有集群列表成功");
		return SUCCESS;
	}

	public List<AcAggregate> getAggregates() {
		return aggregates;
	}

	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

}
