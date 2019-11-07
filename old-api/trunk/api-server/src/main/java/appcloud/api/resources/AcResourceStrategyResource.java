package appcloud.api.resources;

import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import appcloud.api.beans.AcResourceStrategy;
import appcloud.api.enums.AcStrategyTypeEnum;
import appcloud.api.manager.AcResourceStrategyManager;
import appcloud.common.model.ResourceStrategy.StrategyTypeEnum;

@Path("{adminId}/resource_strategys")
public class AcResourceStrategyResource {
	private AcResourceStrategyManager acResourceStrategyManager;

	public AcResourceStrategyManager getAcResourceStrategyManager() throws Exception  {
		return acResourceStrategyManager;
	}

	public void setAcResourceStrategyManager(
			AcResourceStrategyManager acResourceStrategyManager) throws Exception  {
		this.acResourceStrategyManager = acResourceStrategyManager;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<AcResourceStrategy> index(@PathParam("adminId") String adminId) throws Exception {
		return acResourceStrategyManager.getList(adminId);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{resourceStrategyId}")
	public AcResourceStrategy show(
			@PathParam("adminId") String adminId, 
			@PathParam("resourceStrategyId") Integer resourceStrategyId) throws Exception {
		System.out.println("adminId = " + adminId + ",resourceStrategyId = " + resourceStrategyId);
		return acResourceStrategyManager.get(adminId, resourceStrategyId);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public AcResourceStrategy create(@PathParam("adminId") String adminId, AcResourceStrategy acResourceStrategy) throws Exception {
		return acResourceStrategyManager.create(adminId, acResourceStrategy);
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_XML)
	@Path("/{resourceStrategyId}")
	public AcResourceStrategy update(@PathParam("adminId") String adminId, @PathParam("resourceStrategyId") Integer resourceStrategyId, 
			AcResourceStrategy acResourceStrategy) throws Exception {
		return acResourceStrategyManager.update(adminId, resourceStrategyId, acResourceStrategy);
	}
	
	@DELETE
	@Path("/{resourceStrategyId}")
	public void delete(@PathParam("adminId") String adminId, @PathParam("resourceStrategyId") Integer resourceStrategyId) throws Exception {
		acResourceStrategyManager.delete(adminId, resourceStrategyId);
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_XML)
	public List<AcResourceStrategy> getByProperties(
			@PathParam("adminId") String adminId,
			@DefaultValue("") @QueryParam("id") String id,
			@DefaultValue("") @QueryParam("uuid") String uuid,
			@DefaultValue("") @QueryParam("type") String type,
			@DefaultValue("") @QueryParam("name") String name,
			@DefaultValue("") @QueryParam("description") String description,
			@DefaultValue("") @QueryParam("clazzs") String clazzs,
			@DefaultValue("") @QueryParam("params") String params,
			@DefaultValue("") @QueryParam("page") String page,
			@DefaultValue("") @QueryParam("size") String size,
			@DefaultValue("") @QueryParam("time") String time) throws Exception {
		
		Integer idInt=null, pageInt=null, sizeInt=null;
		StrategyTypeEnum strategyType = null;
		Timestamp stamp = null;
		if(!"".equals(id)) {
			idInt = Integer.valueOf(id);
		} 
		if(!"".equals(page)) {
			pageInt = Integer.valueOf(page);
		}
		if(!"".equals(size)) {
			sizeInt = Integer.valueOf(size);
		} 
		if(!"".equals(type)) {
			strategyType = StrategyTypeEnum.valueOf(type);
		}
		/*if(!"".equals(time)){
			DateFormat df = new SimpleDateFormat("dow mon dd hh:mm:ss zzz yyyy");
			Date dt = df.parse(time);
			stamp = new Timestamp(dt.getTime());
		}*/
		return acResourceStrategyManager.searchByProperties(adminId, idInt, uuid,
				strategyType, name, description, clazzs, params, stamp,
				pageInt, sizeInt);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/search/count")
	public String countEnterpriseByProperties(
			@PathParam("adminId") String adminId,
			@DefaultValue("") @QueryParam("id") String id,
			@DefaultValue("") @QueryParam("uuid") String uuid,
			@DefaultValue("") @QueryParam("type") String type,
			@DefaultValue("") @QueryParam("name") String name,
			@DefaultValue("") @QueryParam("description") String description,
			@DefaultValue("") @QueryParam("clazzs") String clazzs,
			@DefaultValue("") @QueryParam("params") String params,
			@DefaultValue("") @QueryParam("time") String time) throws Exception {
		
		Integer idInt=null;
		AcStrategyTypeEnum strategyType = null;
		Timestamp stamp = null;
		
		if(!id.equals("")) {
			idInt = Integer.valueOf(id);
		} 
		if(!type.equals("")) {
			strategyType = AcStrategyTypeEnum.valueOf(type);
		}
		//dow mon dd hh:mm:ss zzz yyyy
		/*if (!"".equals(time)) {
			DateFormat df = new SimpleDateFormat("dow mon dd hh:mm:ss zzz yyyy");
			Date dt = df.parse(time);
			stamp = new Timestamp(dt.getTime());
		}*/
		
		return acResourceStrategyManager.countByProperties(adminId, idInt, uuid,
				strategyType, name, description, clazzs, params, stamp);
				   
	}
}
