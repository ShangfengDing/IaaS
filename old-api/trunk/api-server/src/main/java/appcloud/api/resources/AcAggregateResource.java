package appcloud.api.resources;

import java.util.List;

import javax.ws.rs.Consumes;
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

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import appcloud.api.beans.AcAggregate;
import appcloud.api.manager.AcAggregateManager;

@Path("{tenantId}/ac-aggregates")
public class AcAggregateResource {

	private AcAggregateManager acAggregateManager;
	
	public AcAggregateManager getAcAggregateManager() {
		return acAggregateManager;
	}

	public void setAcAggregateManager(AcAggregateManager acAggregateManager) {
		this.acAggregateManager = acAggregateManager;
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<AcAggregate> index(@PathParam("tenantId") String tenantId, 
			@DefaultValue("") @QueryParam("zone_id") String zoneIdStr) throws Exception {
		Integer zoneId = null;
		if(!zoneIdStr.equals(""))
			try{
				zoneId = Integer.valueOf(zoneIdStr);
			}catch (NumberFormatException e) {
			}
		return acAggregateManager.getList(tenantId, false, zoneId);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{aggregateId}")
	public AcAggregate show(@PathParam("tenantId") String tenantId, @PathParam("aggregateId") Integer aggregateId) throws Exception {
		return acAggregateManager.get(tenantId, aggregateId);
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{aggregateId}")
	public AcAggregate update(@PathParam("tenantId") String tenantId, @PathParam("aggregateId") Integer aggregateId, AcAggregate aggregate) throws Exception {
		return acAggregateManager.update(tenantId, aggregateId, aggregate);
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{aggregateId}/updateRSUuid")
	public AcAggregate updateRSUUid(@PathParam("tenantId") String tenantId, @PathParam("aggregateId") Integer aggregateId, String rsUuid) throws Exception {
		return acAggregateManager.updateRSUuid(tenantId, aggregateId, rsUuid);
	}

	@PUT
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{aggregateId}/updateOverSell")
	public AcAggregate updateOverSell(@PathParam("tenantId") String tenantId, @PathParam("aggregateId") Integer aggregateId, String overSell) throws Exception {
		return acAggregateManager.updateOverSell(tenantId, aggregateId, overSell);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public AcAggregate create(@PathParam("tenantId") String tenantId, AcAggregate aggregate) throws Exception {
		return acAggregateManager.create(tenantId, aggregate);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Path("/{aggregateId}/action")
	public Response action(@PathParam("tenantId") String tenantId, @PathParam("aggregateId") Integer aggregateId, String xml) throws Exception {
        Document document;
        document = DocumentHelper.parseText(xml);
        Element root = document.getRootElement();
		
        if (root.getName().equals("add_host")) {
        	String hostId = root.attribute("host").getStringValue();
        	AcAggregate agt = acAggregateManager.addHost(tenantId, aggregateId, hostId);
        	
        	return Response.ok(agt).type(MediaType.APPLICATION_XML).build();
        } else if (root.getName().equals("remove_host")) {
        	String hostId = root.attribute("host").getStringValue();
        	AcAggregate agt = acAggregateManager.removeHost(tenantId, aggregateId, hostId);
        	
        	return Response.ok(agt).type(MediaType.APPLICATION_XML).build();
        }
		
		return Response.status(404).build();
	}
}
