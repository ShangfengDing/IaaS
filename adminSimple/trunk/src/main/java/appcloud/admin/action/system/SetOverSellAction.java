package appcloud.admin.action.system;

import com.appcloud.vm.fe.util.ClientFactory;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.client.AcAggregateClient;

public class SetOverSellAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1255374637399867550L;
	private AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();
	private String clusterId;
	private String cpu;
	private String memory;
	private String disk;
	
	@Override
	public String execute() throws Exception {
		Integer cpu_oversell = Integer.parseInt(cpu);
		Integer memory_oversell = Integer.parseInt(memory);
		Integer disk_oversell = Integer.parseInt(disk);
		aggregateClient.updateOverSell(Integer.valueOf(clusterId), cpu_oversell,
				memory_oversell, disk_oversell);
		return SUCCESS;
	}
	
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public String getMemory() {
		return memory;
	}
	public void setMemory(String memory) {
		this.memory = memory;
	}
	public String getDisk() {
		return disk;
	}
	public void setDisk(String disk) {
		this.disk = disk;
	}

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}
	
}
