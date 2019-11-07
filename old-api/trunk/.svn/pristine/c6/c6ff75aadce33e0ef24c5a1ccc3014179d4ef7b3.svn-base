package appcloud.api.resources;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import appcloud.api.manager.KeepAliveManager;

@Path("keepalive")
public class KeepAliveResource {
	private KeepAliveManager keepaliveManager;
	
	public KeepAliveManager getKeepAliveManager() {
		return keepaliveManager;
	}

	public void setKeepAliveManager(KeepAliveManager keepaliveManager) {
		this.keepaliveManager = keepaliveManager;
	}
	
	@GET
	@Produces("application/xml")
	public String KeepAlive(){
		try{
			return keepaliveManager.KeepAlive();
		}catch (Exception e)
		{
			return "fail";
		}
	}
	
}
