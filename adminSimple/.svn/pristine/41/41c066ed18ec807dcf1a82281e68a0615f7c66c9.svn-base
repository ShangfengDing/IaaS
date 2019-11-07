package appcloud.admin.action.system;

import java.util.ArrayList;
import java.util.List;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcVlan;
import appcloud.api.client.AcVlanClient;
import appcloud.api.enums.AcVlanTypeEnum;

import com.appcloud.vm.fe.util.ClientFactory;

public class PreNewClusterAction extends BaseAction {
	private static final long serialVersionUID = -8978894090535415113L;
	//private static final Logger logger = Logger.getLogger(PreNewClusterAction.class);
	
	//private AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();
	private AcVlanClient vlanClient = ClientFactory.getVlanClient();
	//private List<AvailabilityZone> zones= new ArrayList<AvailabilityZone>();
	private List<AcVlan> privateVlans = new ArrayList<AcVlan>();
	private List<AcVlan> publicVlans = new ArrayList<AcVlan>();
	private List<AcVlan> vlans = new ArrayList<AcVlan>();
	private int zoneId;
	
	//建cluster前，查找所有zone和vlan
	public String execute() {
		//zones = aggregateClient.getZones();
		vlans = vlanClient.getAcVlans();
		for (AcVlan vlan : vlans) {
			if (vlan.type.equals(AcVlanTypeEnum.PRIVATE)) {
				privateVlans.add(vlan);
			} else {
				publicVlans.add(vlan);
			}
		}
		return SUCCESS;
	}
//
//	public List<AvailabilityZone> getZones() {
//		return zones;
//	}
//
//	public void setZones(List<AvailabilityZone> zones) {
//		this.zones = zones;
//	}

	public List<AcVlan> getPrivateVlans() {
		return privateVlans;
	}

	public void setPrivateVlans(List<AcVlan> privateVlans) {
		this.privateVlans = privateVlans;
	}

	public List<AcVlan> getPublicVlans() {
		return publicVlans;
	}

	public void setPublicVlans(List<AcVlan> publicVlans) {
		this.publicVlans = publicVlans;
	}

	public List<AcVlan> getVlans() {
		return vlans;
	}

	public void setVlans(List<AcVlan> vlans) {
		this.vlans = vlans;
	}

	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

}
