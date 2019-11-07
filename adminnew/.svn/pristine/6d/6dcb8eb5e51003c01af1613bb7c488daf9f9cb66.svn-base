package appcloud.admin.action.system;

import java.util.ArrayList;
import java.util.List;

import appcloud.common.model.VmZone;
import appcloud.common.proxy.VmZoneProxy;
import appcloud.common.util.ConnectionFactory;
import com.aliyuncs.ecs.model.v20140526.DescribeZonesResponse;
import com.opensymphony.xwork2.Action;
import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AvailabilityZone;
import appcloud.api.client.AcAggregateClient;

import com.appcloud.vm.fe.util.ClientFactory;

public class ZoneAction extends BaseAction {
	private static final long serialVersionUID = -8978894090535415113L;
	private static final Logger logger = Logger.getLogger(ZoneAction.class);
	
	private AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();
	private List<AvailabilityZone> zones= new ArrayList<AvailabilityZone>();
	private VmZone vmZone;
	private VmZoneProxy vmZoneProxy= ConnectionFactory.getVmZoneProxy();
	
	public String execute() {
		zones = aggregateClient.getZones();
		return SUCCESS;
	}

	public String getZone(){
		try {
			List<VmZone> list = (List<VmZone>)vmZoneProxy.findAll();
			if(list!=null) {
				vmZone = list.get(0);
				return Action.SUCCESS;
			}

		}catch(Exception e){
			logger.error("find vmZone error");
			e.printStackTrace();
		}
		return Action.ERROR;
	}

	public List<AvailabilityZone> getZones() {
		return zones;
	}

	public void setZones(List<AvailabilityZone> zones) {
		this.zones = zones;
	}

	public VmZone getVmZone() {
		return vmZone;
	}

	public void setVmZone(VmZone vmZone) {
		this.vmZone = vmZone;
	}
}
