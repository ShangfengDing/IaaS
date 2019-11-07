package appcloud.api.resources;

import java.io.StringReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import appcloud.api.beans.Load;
import appcloud.api.beans.Meta;
import appcloud.api.beans.Metadata;
import appcloud.api.beans.Server;
import appcloud.api.beans.server.ServerActionReboot;
import appcloud.api.beans.server.ServerActionRebuild;
import appcloud.api.beans.server.ServerCreateReq;
import appcloud.api.beans.server.ServerUpdateReq;
import appcloud.api.constant.ServerMetadata;
import appcloud.api.enums.ServerOperationEnum;
import appcloud.api.manager.ServerManager;

@Path("{tenantId}/servers")
public class ServerResource {
	private static JAXBContext contextReboot = null;
	private static JAXBContext contextRebuild = null;
	private ServerManager serverManager;
	
	public ServerManager getServrManager () {
		return serverManager;
	}
	
	public void setServerManager (ServerManager serverManager) {
		this.serverManager = serverManager;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Server> index(@PathParam("tenantId") String tenantId,
			@DefaultValue("") @QueryParam("name") String name, 
			@DefaultValue("false") @QueryParam("all_tenants") String allTenants) throws Exception {
		List<Server> servers = serverManager.getList(tenantId, false, Boolean.valueOf(allTenants));
		
		return servers;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/detail")
	public List<Server> detail(@PathParam("tenantId") String tenantId,
			@DefaultValue("") @QueryParam("name") String name, 
			@DefaultValue("false") @QueryParam("all_tenants") String allTenants) throws Exception {
		List<Server> servers = (List<Server>) serverManager.getList(tenantId, true, Boolean.valueOf(allTenants));
		
		return servers;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/search")
	public List<Server> searchByProperties(@PathParam("tenantId") String tenantId,
			@DefaultValue("") @QueryParam("user_id") String userIdsString, 
			@DefaultValue("") @QueryParam("server_name") String serverName, 
			@DefaultValue("") @QueryParam("server_id") String serverId, 
			@DefaultValue("") @QueryParam("status") String status, 
			@DefaultValue("") @QueryParam("zone_id") String zoneIdStr, 
			@DefaultValue("") @QueryParam("aggregate_id") String aggregateIdStr, 
			@DefaultValue("") @QueryParam("host_id") String hostId, 
			@DefaultValue("") @QueryParam("server_ip") String serverIp, 
			@DefaultValue("") @QueryParam("start_date") String startDateStr, 
			@DefaultValue("") @QueryParam("end_date") String endDateStr,
			@DefaultValue("") @QueryParam("page") String pageStr,
			@DefaultValue("") @QueryParam("size") String sizeStr) throws Exception {
		List<Integer> userIds;
		if(userIdsString.equals("")) {
			userIds = null;
		} else {
			userIds = new ArrayList<Integer>();
			String[] ids = userIdsString.split(",");
			for(String id : ids) {
				userIds.add(Integer.parseInt(id));
			}
		}
		if(serverId.equals(""))
			serverId = null;
		if(serverName.equals(""))
			serverName = null;
		if(status.equals(""))
			status = null;
		Integer zoneId = null;
		if(!zoneIdStr.equals("")){
			try {
				zoneId = Integer.valueOf(zoneIdStr);
			}catch (Exception e){
				;
			}
		}
		Integer aggregateId = null;
		if(!aggregateIdStr.equals("")) {
			try {
				aggregateId = Integer.valueOf(aggregateIdStr);
			}catch (Exception e) {
				;
			}
		}
		if(hostId.equals(""))
			hostId = null;
		if(serverIp.equals(""))
			serverIp = null;
		
		Date startDate = null;
		if(!startDateStr.equals("")){
			try{
				startDate = new Date(Long.valueOf(startDateStr));
			}catch (Exception e) {
				;
			}
		}
		Date endDate = null;
		if(!endDateStr.equals(""))
				endDate = new Date(Long.valueOf(endDateStr));
		
		Integer page = 0;
		if(!pageStr.equals("")){
			try {
				page = Integer.valueOf(pageStr);
			}catch (Exception e){
				;
			}
		}
		Integer size = 0;
		if(!sizeStr.equals("")) {
			try {
				size = Integer.valueOf(sizeStr);
			}catch (Exception e) {
				;
			}
		}
		
		return serverManager.searchByProperties(tenantId, userIds, serverId, serverName, status, 
				zoneId, aggregateId, hostId, serverIp, startDate, endDate, page, size);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/search/count")
	public String countByProperties(@PathParam("tenantId") String tenantId,
			@DefaultValue("") @QueryParam("user_id") String userIdsString, 
			@DefaultValue("") @QueryParam("server_id") String serverId, 
			@DefaultValue("") @QueryParam("server_name") String serverName, 
			@DefaultValue("") @QueryParam("status") String status, 
			@DefaultValue("") @QueryParam("zone_id") String zoneIdStr, 
			@DefaultValue("") @QueryParam("aggregate_id") String aggregateIdStr, 
			@DefaultValue("") @QueryParam("host_id") String hostId, 
			@DefaultValue("") @QueryParam("server_ip") String serverIp, 
			@DefaultValue("") @QueryParam("start_date") String startDateStr, 
			@DefaultValue("") @QueryParam("end_date") String endDateStr) throws Exception {
		
		List<Integer> userIds;
		if(userIdsString.equals("")) {
			userIds = null;
		} else {
			userIds = new ArrayList<Integer>();
			String[] ids = userIdsString.split(",");
			for(String id : ids) {
				userIds.add(Integer.parseInt(id));
			}
		}
		if(serverId.equals(""))
			serverId = null;
		if(serverName.equals(""))
			serverName = null;
		if(status.equals(""))
			status = null;
		Integer zoneId = null;
		if(!zoneIdStr.equals("")){
			try {
				zoneId = Integer.valueOf(zoneIdStr);
			}catch (Exception e){
				;
			}
		}
		Integer aggregateId = null;
		if(!aggregateIdStr.equals("")) {
			try {
				aggregateId = Integer.valueOf(aggregateIdStr);
			}catch (Exception e) {
				;
			}
		}
		if(hostId.equals(""))
			hostId = null;
		if(serverIp.equals(""))
			serverIp = null;
		
		Date startDate = null;
		if(!startDateStr.equals("")){
			try{
				startDate = new Date(Long.valueOf(startDateStr));
			}catch (Exception e) {
				;
			}
		}
		Date endDate = null;
		if(!endDateStr.equals(""))
				endDate = new Date(Long.valueOf(endDateStr));
		
		return (serverManager.countByProperties(tenantId, userIds, serverId, serverName, status, zoneId,
				aggregateId, hostId, serverIp, startDate, endDate)).toString();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Server create(@PathParam("tenantId") String tenantId, ServerCreateReq cReq) throws Exception {
		return serverManager.create(tenantId, cReq);
	}

	@POST
	@Path("/servername")
	@Consumes(MediaType.APPLICATION_XML)
	public Server updateServerName(@PathParam("tenantId") String tenantId, ServerUpdateReq uReq) throws Exception {
		return serverManager.updateServerName(tenantId, uReq);
	}
	
	@POST
	@Path("/{serverId}/action")
	@Consumes(MediaType.APPLICATION_XML)
	public Response action(@PathParam("tenantId") String tenantId, @PathParam("serverId") String serverId, String action) throws Exception {
		Document document;
		System.out.println(action);
        document = DocumentHelper.parseText(action);
        Element root = document.getRootElement();
		if (root.getName().equals(ServerOperationEnum.OS_START.toString())) {
			serverManager.osStart(tenantId, serverId);
			return Response.ok().build();
		} else if (root.getName().equals(ServerOperationEnum.OS_STOP.toString())) {
			serverManager.osStop(tenantId, serverId);
			return Response.ok().build();
		} else if (root.getName().equals(ServerOperationEnum.OS_RESUME.toString())) {
			serverManager.resume(tenantId, serverId);
			return Response.ok().build();
		} else if (root.getName().equals(ServerOperationEnum.OS_SUSPEND.toString())) {
			serverManager.suspend(tenantId, serverId);
			return Response.ok().build();
		} else if (root.getName().equals(ServerOperationEnum.FORCE_DELETE.toString())) {
			serverManager.forceDelete(tenantId, serverId);
			return Response.ok().build();
		} else if (root.getName().equals(ServerOperationEnum.AC_FORCE_STOP.toString())) {
			serverManager.forceStop(tenantId, serverId);
			return Response.ok().build();
		} else if (root.getName().equals(ServerOperationEnum.RESIZE.toString())) {
			String flavorRef = root.attribute("flavorRef").getStringValue();
			serverManager.resize(tenantId, serverId, flavorRef);
			return Response.ok().build();
		} else if (root.getName().equals(ServerOperationEnum.AC_ISO_BOOT.toString())) {
			String imgRef = root.attribute("imageRef").getStringValue();
			serverManager.bootFromISO(tenantId, serverId, imgRef);
			return Response.ok().build();
		} else if (root.getName().equals(ServerOperationEnum.AC_ISO_DETACH.toString())) {
			serverManager.detachISO(tenantId, serverId);
			return Response.ok().build();
		} else if (root.getName().equals(ServerOperationEnum.REBOOT.toString())) {
			try {
				if (contextReboot == null) {
					contextReboot = JAXBContext.newInstance(ServerActionReboot.class);
				}
				Unmarshaller jaxbUnmarshaller = contextReboot.createUnmarshaller();
				ServerActionReboot reboot = (ServerActionReboot) jaxbUnmarshaller.unmarshal(new StringReader(action));
				
				serverManager.reboot(tenantId, serverId, reboot);
				
				return Response.ok().build();
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		} else if (root.getName().equals(ServerOperationEnum.REBUILD.toString())) {
			try {
				if (contextRebuild == null) {
					contextRebuild = JAXBContext.newInstance(ServerActionRebuild.class);
				}
				Unmarshaller jaxbUnmarshaller = contextRebuild.createUnmarshaller();
				ServerActionRebuild rebuild = (ServerActionRebuild) jaxbUnmarshaller.unmarshal(new StringReader(action));
				
				return Response.ok(serverManager.rebuild(tenantId, serverId, rebuild)).build();
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}else if (root.getName().equals(ServerOperationEnum.AC_FORCE_REFRESH.toString())) {
			String status = serverManager.forceRefresh(tenantId, serverId);
			return Response.ok(status).build();
		} else if (root.getName().equals(ServerOperationEnum.AC_RESET.toString())) {
			String status = serverManager.reset(tenantId, serverId);
			return Response.ok(status).build();
		} else if (root.getName().equals(ServerOperationEnum.AC_MODIFYPASSWORD.toString())) {
			String userName = root.attribute("userName").getStringValue();
			String newPasswd = root.attribute("newPasswd").getStringValue();
			String osType = root.attribute("osType").getStringValue();
			if(userName.equals(""))
				userName = null;
			if(newPasswd.equals(""))
				newPasswd = null;
			if(osType.equals(""))
				osType = null;
			serverManager.modPasswd(tenantId, serverId, userName, newPasswd, osType);
			return Response.ok().build();
		} else if(root.getName().equals(ServerOperationEnum.AC_MIGRATE.toString())) {
			String hostUuid = root.attribute("hostUuid").getStringValue();
			if(hostUuid == null) 
				hostUuid = "";
			
			return Response.ok(serverManager.migrate(tenantId, serverId, hostUuid)).build();
			
		} else if(root.getName().equals(ServerOperationEnum.AC_ONLINE_MIGRATE.toString())) {
			String hostUuid = root.attribute("hostUuid").getStringValue();
			if(hostUuid == null) 
				hostUuid = "";
			
			return Response.ok(serverManager.onlineMigrate(tenantId, serverId, hostUuid)).build();
			
		}
		
		return Response.status(404).build();
	}
	
	@POST
	@Path("/{serverId}/action/{hostUuid}")
	@Consumes(MediaType.APPLICATION_XML)
	public Server migrate(@PathParam("tenantId") String tenantId, @PathParam("serverId") String serverId, 
			@PathParam("hostUuid") String hostUuid) throws Exception {
		Server server = serverManager.migrate(tenantId, serverId, hostUuid);
		return server;
	}
	
	@DELETE
	@Path("/{serverId}")
	public void delete(@PathParam("tenantId") String tenantId, @PathParam("serverId") String serverId) throws Exception {
		serverManager.terminate(tenantId, serverId);
	}
	
	@GET
	@Path("/{serverId}")
	@Produces(MediaType.APPLICATION_XML)
	public Server show(@PathParam("tenantId") String tenantId, @PathParam("serverId") String serverId) throws Exception {
		Server server = serverManager.getDetail(tenantId, serverId);
		return server;
	}
	
	@PUT
	@Path("{serverId}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Server update(
			@PathParam("tenantId") String tenantId, 
			@PathParam("serverId") String serverId, 
			Server server) throws Exception{
		return serverManager.update(tenantId, serverId, server);
	}
	
	@GET
	@Path("/{serverId}/ac-monitors")
	@Produces(MediaType.APPLICATION_XML)
	public List<Load> monitor(@PathParam("tenantId") String tenantId, 
			@PathParam("serverId") String serverId,
			@DefaultValue("day") @QueryParam("type") String type,
			@DefaultValue("09/02/2011") @QueryParam("stime") Date startTime,
			@DefaultValue("09/02/2011") @QueryParam("etime") Date endTime) throws Exception {
		Timestamp startTimestamp = new Timestamp(startTime.getTime());
		Timestamp endTimestamp = new Timestamp(endTime.getTime()); 
		List<Load> loads = serverManager.getMonitorData(tenantId, serverId, type, startTimestamp, endTimestamp);
		return loads;
	}
	
	@GET
	@Path("/{serverId}/metadata")
	@Produces(MediaType.APPLICATION_XML)
	public Metadata getMetadata(@PathParam("tenantId") String tenantId,
			@PathParam("serverId") String serverId) 
			throws Exception{
		return serverManager.getMetadata(tenantId, serverId);
	}
	@POST
	@Path("/{serverId}/metadata")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Metadata updateMetadata(@PathParam("tenantId") String tenantId, 
			@PathParam("serverId") String serverId, Metadata metadata) throws Exception{
		return serverManager.updateMetadata(tenantId,serverId, metadata);
	}
	
	@PUT
	@Path("/{serverId}/metadata")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Metadata setMetadata(@PathParam("tenantId") String tenantId, 
			@PathParam("serverId") String serverId,
			Metadata metadata) throws Exception{
		return serverManager.setMetadata(tenantId, serverId, metadata);
	}
	
	@GET
	@Path("/{serverId}/metadata/{key}")
	@Produces(MediaType.APPLICATION_XML)
	public Meta getMetadataItem(@PathParam("tenantId") String tenantId, 
			@PathParam("serverId") String serverId,
			@PathParam("key") String key) throws Exception{
		return serverManager.getMetadataItem(tenantId, serverId, key);
	}
	
	//修改带宽
	@POST
	@Path("/{serverId}/metadata/{key}/{release}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Meta updateMetadataItem(@PathParam("tenantId") String tenantId, 
			@PathParam("serverId") String serverId,
			@PathParam("key") String key, @PathParam("release") boolean release, Meta meta) throws Exception{
		return serverManager.updateMetadataItem(tenantId, serverId, key, release, meta);
	}
	
	
	@PUT
	@Path("/{serverId}/metadata/{key}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Meta setMetadataItem(@PathParam("tenantId") String tenantId, 
			@PathParam("serverId") String serverId,
			@PathParam("key") String key, Meta meta) throws Exception{
		return serverManager.setMetadataItem(tenantId, serverId, key, meta);
	}
}
