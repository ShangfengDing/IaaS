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

import appcloud.api.beans.AcHost;
import appcloud.api.beans.Load;
import appcloud.api.manager.AcHostManager;
import appcloud.common.model.Host.HostStatusEnum;
import appcloud.common.model.Host.HostTypeEnum;
import appcloud.common.model.Service.ServiceTypeEnum;

@Path("{tenantId}/ac-hosts")
public class AcHostResource {
	private AcHostManager acHostManager;
	
	public AcHostManager getAcHostManager() {
		return acHostManager;
	}

	public void setAcHostManager(AcHostManager hostManager) {
		this.acHostManager = hostManager;
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<AcHost> index(@PathParam("tenantId") String tenantId, 
			@DefaultValue("") @QueryParam("aggregate_id") String aggregateIdStr,
			@DefaultValue("") @QueryParam("zone_id") String zoneIdStr) throws Exception {
		Integer aggregateId = null;
		if(! aggregateIdStr.equals(""))
			try{
				aggregateId = Integer.valueOf(aggregateIdStr);
			}catch(NumberFormatException e) {
				;
			}
		Integer zoneId = null;
		if(! zoneIdStr.equals(""))
			try{
				zoneId = Integer.valueOf(zoneIdStr);
			}catch(NumberFormatException e) {
				;
			}
		List<AcHost> hosts = acHostManager.getList(tenantId, false, aggregateId, zoneId);
		return hosts;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{hostId}")
	public AcHost show(@PathParam("tenantId") String tenantId, @PathParam("hostId") String hostId) throws Exception {
		AcHost host = acHostManager.get(tenantId, hostId);
		return host;
	}
	
	@GET
	@Path("/{hostId}/ac-monitors")
	@Produces(MediaType.APPLICATION_XML)
	public List<Load> monitor(@PathParam("tenantId") String tenantId, 
			@PathParam("hostId") String hostId,
			@DefaultValue("day") @QueryParam("type") String type,
			@DefaultValue("09/02/2011") @QueryParam("stime") Date startTime,
			@DefaultValue("09/02/2011") @QueryParam("etime") Date endTime) throws Exception {
		Timestamp startTimestamp = new Timestamp(startTime.getTime());
		Timestamp endTimestamp = new Timestamp(endTime.getTime()); 
		List<Load> loads = acHostManager.getMonitorData(tenantId, hostId, type, startTimestamp, endTimestamp);
		return loads;
	}
	
	@GET
	@Path("/{hostId}/ac-monitors/current")
	@Produces(MediaType.APPLICATION_XML)
	public Load currentLoad(@PathParam("tenantId") String tenantId, 
			@PathParam("hostId") String hostId) throws Exception {
		
		Load load = acHostManager.getCurrentLoad(tenantId, hostId);
		return load;
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_XML)
	public List<AcHost> getHostsByProperties(
			@PathParam("tenant_id") String tenantId, 
			@DefaultValue("") @QueryParam("user_id") String userId, 
			@DefaultValue("") @QueryParam("ip") String ip,
			@DefaultValue("") @QueryParam("type") String type,
			@DefaultValue("") @QueryParam("status") String status,
			@DefaultValue("") @QueryParam("zid") String zid,
			@DefaultValue("") @QueryParam("aid") String aid,
			@DefaultValue("") @QueryParam("service") String service,
			@DefaultValue("") @QueryParam("cpu_operator") String cpuOperator,
			@DefaultValue("") @QueryParam("cpu_count") String cpuCount,
			@DefaultValue("") @QueryParam("memory_operator") String memoryOperator,
			@DefaultValue("") @QueryParam("memory_count") String memoryCount,
			@DefaultValue("") @QueryParam("disk_operator") String diskOperator,
			@DefaultValue("") @QueryParam("disk_count") String diskCount,
			@DefaultValue("") @QueryParam("page") String page, 
			@DefaultValue("") @QueryParam("size") String size) throws Exception{
		
		Integer zoneId, aggregateId, cpuCountInt=null, memoryCountInt=null, diskCountInt=null; 
		if(zid.equals("")) {
			zoneId = null;
		} else {
			zoneId = Integer.valueOf(zid);
		}
		if(aid.equals("")) {
			aggregateId = null;
		} else {
			aggregateId = Integer.valueOf(aid);
		}
		if(cpuCount.equals("")) {
			cpuCountInt = null;
		}else {
			cpuCountInt = Integer.valueOf(cpuCount);
		}
		if(memoryCount.equals("")) {
			memoryCountInt = null;
		}else {
			memoryCountInt = Integer.valueOf(memoryCount);
		}
		if(diskCount.equals("")) {
			diskCountInt = null;
		}else {
			diskCountInt = Integer.valueOf(diskCount);
		}
		
		ServiceTypeEnum serviceType = null;
		if(service != null && !service.equals("")) {
			serviceType = ServiceTypeEnum.valueOf(service);
		}
		HostTypeEnum hostType = null;
		if(type != null && !type.equals("")) {
			hostType = HostTypeEnum.valueOf(type);
		}
		HostStatusEnum hostStatus = null;
		if(status != null && !status.equals("")) {
			hostStatus = HostStatusEnum.valueOf(status);
		}
		
		Integer pageInt=null, sizeInt=null;;
		if(page != null && !page.equals("")) {
			pageInt = Integer.valueOf(page);
		}
		if(size != null && !size.equals("")) {
			sizeInt = Integer.valueOf(size);
		}
		
		return acHostManager.getHostsByProperties(
				   tenantId, ip, hostType, hostStatus, aggregateId, zoneId, serviceType, 
				   Integer.parseInt(cpuOperator), cpuCountInt,
				   Integer.parseInt(memoryOperator), memoryCountInt,
				   Integer.parseInt(diskOperator), diskCountInt,
				   pageInt, sizeInt);
	}
	
	
 	
	@GET
	@Path("/search/count")
	@Produces(MediaType.APPLICATION_XML)
	public String countByProperties(
			@PathParam("tenant_id") String tenantId, 
			@DefaultValue("") @QueryParam("user_id") String userId, 
			@DefaultValue("") @QueryParam("ip") String ip,
			@DefaultValue("") @QueryParam("type") String type,
			@DefaultValue("") @QueryParam("status") String status,
			@DefaultValue("") @QueryParam("zid") String zid,
			@DefaultValue("") @QueryParam("aid") String aid,
			@DefaultValue("") @QueryParam("service") String service,
			@DefaultValue("") @QueryParam("cpu_operator") String cpuOperator,
			@DefaultValue("") @QueryParam("cpu_count") String cpuCount,
			@DefaultValue("") @QueryParam("memory_operator") String memoryOperator,
			@DefaultValue("") @QueryParam("memory_count") String memoryCount,
			@DefaultValue("") @QueryParam("disk_operator") String diskOperator,
			@DefaultValue("") @QueryParam("disk_count") String diskCount
			)
			throws Exception{
		
		Integer zoneId, aggregateId, cpuCountInt=null, memoryCountInt=null, diskCountInt=null; 
		if(zid.equals("")) {
			zoneId = null;
		} else {
			zoneId = Integer.valueOf(zid);
		}
		if(aid.equals("")) {
			aggregateId = null;
		} else {
			aggregateId = Integer.valueOf(aid);
		}
		if(cpuCount.equals("")) {
			cpuCountInt = null;
		}else {
			cpuCountInt = Integer.valueOf(cpuCount);
		}
		if(memoryCount.equals("")) {
			memoryCountInt = null;
		}else {
			memoryCountInt = Integer.valueOf(memoryCount);
		}
		if(diskCount.equals("")) {
			diskCountInt = null;
		}else {
			diskCountInt = Integer.valueOf(diskCount);
		}
		
		ServiceTypeEnum serviceType = null;
		if(service != null && !service.equals("")) {
			serviceType = ServiceTypeEnum.valueOf(service);
		}
		HostTypeEnum hostType = null;
		if(type != null && !type.equals("")) {
			hostType = HostTypeEnum.valueOf(type);
		}
		HostStatusEnum hostStatus = null;
		if(status != null && !status.equals("")) {
			hostStatus = HostStatusEnum.valueOf(status);
		}
		
		return String.valueOf(acHostManager.countByProperties(
				   tenantId, ip, hostType, hostStatus, aggregateId, zoneId, serviceType, 
				   Integer.parseInt(cpuOperator), cpuCountInt,
				   Integer.parseInt(memoryOperator), memoryCountInt,
				   Integer.parseInt(diskOperator), diskCountInt));
		
	}
	
}
