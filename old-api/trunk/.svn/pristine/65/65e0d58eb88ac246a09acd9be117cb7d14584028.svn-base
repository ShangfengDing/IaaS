package appcloud.api.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import appcloud.api.beans.AcService;
import appcloud.api.beans.acservice.AcServiceAction;
import appcloud.api.enums.AcServiceActionEnum;
import appcloud.api.manager.AcServiceManager;

@Path("{tenantId}/ac-service")
public class AcServiceResource {
	private AcServiceManager acServiceManager;

	public AcServiceManager getAcServiceManager() {
		return acServiceManager;
	}

	public void setAcServiceManager(AcServiceManager acServiceManager) {
		this.acServiceManager = acServiceManager;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<AcService> index(@PathParam("tenantId") String tenantId) throws Exception {
		return acServiceManager.getAll(tenantId);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{hostId}")
	public List<AcService> getHostServices(@PathParam("tenantId") String tenantId, 
			 @PathParam("hostId") String hostId) throws Exception {
		return acServiceManager.getHostServices(tenantId, hostId);
	}
	
	@POST
	@Path("/{hostId}/action")
	@Consumes(MediaType.APPLICATION_XML)
	public Response action(@PathParam("tenantId") String tenantId,
			@PathParam("hostId") String hostId, AcServiceAction serviceAction) throws Exception {
		if(serviceAction.action.equals(AcServiceActionEnum.AC_SERVICE_START)){
			acServiceManager.startService(tenantId, hostId, serviceAction.types);
			return Response.ok().build();
		}
		else if(serviceAction.action.equals(AcServiceActionEnum.AC_SERVICE_STOP)){
			acServiceManager.stopService(tenantId, hostId, serviceAction.types);
			return Response.ok().build();
		}
		return Response.status(404).build();
	}
	
}
