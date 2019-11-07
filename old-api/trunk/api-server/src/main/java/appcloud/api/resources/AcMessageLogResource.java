package appcloud.api.resources;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import appcloud.api.beans.AcMailConf;
import appcloud.api.beans.AcMessageLog;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;
import appcloud.api.manager.AcMessageLogManager;

@Path("{tenantId}/ac-message-log")
public class AcMessageLogResource {

	private AcMessageLogManager acMessageLogManager;
	
	public AcMessageLogManager getAcMessageLogManager() {
		return acMessageLogManager;
	}

	public void setAcMessageLogManager(AcMessageLogManager acMessageLogManager) {
		this.acMessageLogManager = acMessageLogManager;
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<AcMessageLog> index(@PathParam("tenantId") String tenantId, 
			@DefaultValue("") @QueryParam("module") String module, 
			@DefaultValue("") @QueryParam("transaction_id") String transactionId, 
			@DefaultValue("") @QueryParam("user_id") String userId, 
			@DefaultValue("") @QueryParam("vm_hd_uuid") String vmHdUuid, 
			@DefaultValue("") @QueryParam("operation") String operateDrpt, 
			@DefaultValue("") @QueryParam("log_content") String logContent, 
			@DefaultValue("") @QueryParam("source_class") String sourceClass, 
			@DefaultValue("") @QueryParam("ip_address") String ipAddress, 
			@DefaultValue("") @QueryParam("log_level") String logLevel, 
			@DefaultValue("") @QueryParam("start_date") String startDateStr, 
			@DefaultValue("") @QueryParam("end_date") String endDateStr, 
			@DefaultValue("") @QueryParam("count") String countStr,
			@DefaultValue("") @QueryParam("order") String order) 
			throws Exception {
		AcModuleEnum acModule = null;
		if(!module.equals(""))
			acModule = AcModuleEnum.valueOf(module);
		if(transactionId.equals(""))
			transactionId = null;
		if(userId.equals(""))
			userId = null;
		if(vmHdUuid.equals(""))
			vmHdUuid = null;
		if(operateDrpt.equals(""))
			operateDrpt = null;
		if(logContent.equals(""))
			logContent = null;
		if(sourceClass.equals(""))
			sourceClass = null;
		if(ipAddress.equals(""))
			ipAddress = null;
		AcLogLevelEnum acLogLevel = null;
		if(!logLevel.equals(""))
			acLogLevel = AcLogLevelEnum.valueOf(logLevel);
			
		AcMessageLog log = new AcMessageLog( acModule,  transactionId, userId,
				 vmHdUuid, operateDrpt, logContent, sourceClass,
				ipAddress,  acLogLevel, null);
		
		Date startDate = null;
		if(!startDateStr.equals(""))
				startDate = new Date(Long.valueOf(startDateStr));
		Date endDate = null;
		if(!endDateStr.equals(""))
				endDate = new Date(Long.valueOf(endDateStr));
		
		Integer count = null;
		if(!countStr.equals(""))
			count = new Integer(countStr);
		int orderInt = 0;;
		if(order != null && !order.equals("")) {
			orderInt = Integer.valueOf(order);
		}
		return acMessageLogManager.searchLog(tenantId, log, startDate, endDate, count, orderInt);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public AcMessageLog create(@PathParam("tenantId") String tenantId, AcMessageLog log)
			throws Exception{
		return acMessageLogManager.addLog(tenantId, log);
	}
	
	@GET
	@Path("mail-conf")
	public AcMailConf getMailConf(@PathParam("tenantId") String tenantId) throws Exception{
		return acMessageLogManager.getMailConf(tenantId);
	}
	
	@POST
	@Path("mail-conf")
	public AcMailConf setMailConf(@PathParam("tenantId") String tenantId, AcMailConf acMailConf) 
			throws Exception {
		return acMessageLogManager.mailSet(tenantId, acMailConf);
	}
}
