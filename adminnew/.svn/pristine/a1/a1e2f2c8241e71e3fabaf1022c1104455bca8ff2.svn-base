package appcloud.admin.action.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcHost;
import appcloud.api.client.AcHostClient;

import com.appcloud.vm.fe.util.ClientFactory;

public class HostByCidAction extends BaseAction {
	private static final long serialVersionUID = -8978894090535415113L;
	private static final Logger logger = Logger.getLogger(HostByCidAction.class);
	private AcHostClient hostClient = ClientFactory.getHostClient();
	//查找所有host列表
	private List<AcHost> hosts = new ArrayList<AcHost>();
	private String clusterId;
	
	public String execute() {
		logger.info(clusterId);
		if(clusterId.equalsIgnoreCase("")){
			hosts = hostClient.getAcHosts();
		}else{
			int cid = Integer.parseInt(clusterId);
			hosts = hostClient.getAcHostOfAggregate(cid);
		}
		
		return SUCCESS;
	}
	
	public List<AcHost> getHosts() {
		return hosts;
	}
	public void setHosts(List<AcHost> hosts) {
		this.hosts = hosts;
	}

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

}
