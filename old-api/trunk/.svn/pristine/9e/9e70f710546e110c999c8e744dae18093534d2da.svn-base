package appcloud.api.manager.fake;

import java.util.ArrayList;
import java.util.List;

import appcloud.api.beans.Hypervisor;
import appcloud.api.beans.Server;
import appcloud.api.manager.HypervisorManager;

public class FakeHypervisorManeger implements HypervisorManager {

	@Override
	public List<Hypervisor> getList(String tenantId, boolean detailed) {
		List<Hypervisor> hypervisors = new ArrayList<Hypervisor>();
		hypervisors.add(new Hypervisor(1, "precise", null));
		hypervisors.add(new Hypervisor(2, "simon", null));
		return hypervisors;
	}

	@Override
	public Hypervisor get(String tenantId, String hypervisorName) {
		List<Server> servers = new ArrayList<Server>();
		Server server1 = new Server();
		server1.id =  "111111-c1e9-4281-bcff-2ff175e33ba9" + hypervisorName;
		server1.name = "server1";
		
		Server server2 = new Server();
		server2.id =  "2222222-c1e9-4281-bcff-faewfaefresh" + hypervisorName;
		server2.name = "server2";
		
		servers.add(server1);
		servers.add(server2);
		Hypervisor hypervisor = new Hypervisor(3, "indigo", servers);
		return hypervisor;
	}

}
