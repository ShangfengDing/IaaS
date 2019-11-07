package appcloud.api.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import appcloud.api.beans.Aggregate;
import appcloud.api.manager.AggregateManager;

@Path("{tenantId}/os-aggregates")
public class AggregateResource {
	private AggregateManager aggregateManager;
	
	public AggregateManager getAggregateManager() {
		return aggregateManager;
	}

	public void setAggregateManager(AggregateManager aggregateManager) {
		this.aggregateManager = aggregateManager;
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Aggregate> index(@PathParam("tenantId") String tenantId) throws Exception {
		return aggregateManager.getList(tenantId, false);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{aggregateId}")
	public Aggregate show(@PathParam("tenantId") String tenantId, @PathParam("aggregateId") Integer aggregateId) throws Exception {
		return aggregateManager.get(tenantId, aggregateId);
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{aggregateId}")
	public Aggregate update(@PathParam("tenantId") String tenantId, @PathParam("aggregateId") Integer aggregateId, Aggregate aggregate) throws Exception {
		return aggregateManager.update(tenantId, aggregateId, aggregate);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Aggregate create(@PathParam("tenantId") String tenantId, Aggregate aggregate) throws Exception {
		return aggregateManager.create(aggregate);
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
        	Aggregate agt = aggregateManager.addHost(tenantId, aggregateId, hostId);
        	
        	return Response.ok(agt).type(MediaType.APPLICATION_XML).build();
        } else if (root.getName().equals("remove_host")) {
        	String hostId = root.attribute("host").getStringValue();
        	Aggregate agt = aggregateManager.removeHost(tenantId, aggregateId, hostId);
        	
        	return Response.ok(agt).type(MediaType.APPLICATION_XML).build();
        }
		
		return Response.status(404).build();
	}
	
}