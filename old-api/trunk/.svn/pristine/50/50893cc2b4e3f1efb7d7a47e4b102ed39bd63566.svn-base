package appcloud.api.resources;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import appcloud.api.beans.Host;
import appcloud.api.beans.Load;
import appcloud.api.manager.HostManager;

@Path("{tenantId}/os-hosts")
public class HostResource {
	private HostManager hostManager;
	
	public HostManager getHostManager() {
		return hostManager;
	}

	public void setHostManager(HostManager hostManager) {
		this.hostManager = hostManager;
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Host> index(@PathParam("tenantId") String tenantId) throws Exception {
		List<Host> hosts = hostManager.getList(tenantId, false);
		return hosts;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{host_name}")
	public Host show(@PathParam("tenantId") String tenantId, @PathParam("host_name") String hostName) throws Exception {
		Host host = hostManager.get(tenantId, hostName);
		return host;
	}
	
	@GET
	@Path("/{hostName}/ac-monitors")
	@Produces(MediaType.APPLICATION_XML)
	public List<Load> monitor(@PathParam("tenantId") String tenantId, 
			@PathParam("hostName") String hostName,
			@DefaultValue("day") @QueryParam("type") String type,
			@DefaultValue("09/02/2011") @QueryParam("stime") Date startTime,
			@DefaultValue("09/02/2011") @QueryParam("etime") Date endTime) throws Exception {
		Timestamp startTimestamp = new Timestamp(startTime.getTime());
		Timestamp endTimestamp = new Timestamp(endTime.getTime()); 
		List<Load> loads = hostManager.getMonitorData(tenantId, hostName, type, startTimestamp, endTimestamp);
		return loads;
	}
	
}
