package appcloud.api.manager.fake;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import appcloud.api.beans.Host;
import appcloud.api.beans.Load;
import appcloud.api.beans.Resource;
import appcloud.api.manager.HostManager;

public class FakeHostManager implements HostManager{
	public  List<Host> getList(String tenantId, boolean detailed) {
		List<Host> Hosts = new ArrayList<Host>();	
		Hosts.add(new Host("host1", "compute", "zone1", null));
		Hosts.add(new Host("host2", "network","zone1", null));
		return Hosts;
	}

	public Host get(String tenantId, String hostName){
		Host host = new Host();
		List<Resource> resources = new ArrayList<Resource>();
		resources.add(new Resource("main", 1024, hostName, 3, 500));
		resources.add(new Resource("butterfly", 2048, hostName, 5 , 800));
		resources.add(new Resource("saint", 4096, hostName, 8, 10000));
		host.resource = resources;
		host.zone = "beijing";
		host.service = "compute";
		return host;
	}

	@Override
	public List<Load> getMonitorData(String tenantId, String hostName,
			String type, Timestamp startTime, Timestamp endTime) {
		return new FakeServerManager().getMonitorData(tenantId, hostName, type, startTime, endTime);
	}
	
}
