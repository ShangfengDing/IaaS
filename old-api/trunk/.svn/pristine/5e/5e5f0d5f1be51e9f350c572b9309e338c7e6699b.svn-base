package appcloud.api.manager.fake;

import java.util.ArrayList;
import java.util.List;

import appcloud.api.beans.AcVlan;
import appcloud.api.enums.AcVlanTypeEnum;
import appcloud.api.manager.AcVlanManager;

public class FakeAcVlanManager implements AcVlanManager {

	@Override
	public List<AcVlan> getList(String tenantId) throws Exception {
		List <AcVlan> vlans = new ArrayList<AcVlan>();
		vlans.add(new AcVlan(1, "vlan_got1", AcVlanTypeEnum.PUBLIC, "it is got in list"));
		vlans.add(new AcVlan(2, "vlan_got2", AcVlanTypeEnum.PRIVATE, "it is got in list"));
		return vlans;
	}

	@Override
	public AcVlan get(String tenantId, Integer vlanId) throws Exception {
		return new AcVlan(vlanId, "vlan_got", AcVlanTypeEnum.PUBLIC, "it is got");
	}

}
