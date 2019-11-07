package appcloud.api.client;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import appcloud.api.beans.AcResourceStrategy;
import appcloud.api.enums.AcStrategyTypeEnum;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class AcResourceStrategyClient  extends AbstractClient<AcResourceStrategy>{
	public AcResourceStrategyClient(){
		super();
	}
	
	public AcResourceStrategyClient(String baseURI) {
		super(baseURI);
	}

	@Override
	protected Class<?> getType() {
		return AcResourceStrategy.class;
	}
	
	@Override
	protected GenericType<List<AcResourceStrategy>> getGenericType() {		
		GenericType<List<AcResourceStrategy>> type = new GenericType<List<AcResourceStrategy>>() {};
		return type;
	}
	
	private String getPath() {
		return  "resource_strategys";
	}
	
	protected String buildPath() {
		return String.format("admin/%s", getPath());
	}
	
	protected String buildPathWithId(String resourceStrategyId) {
		return String.format("%s/%s", buildPath(), resourceStrategyId);
	}
	
	public AcResourceStrategy get(Integer resourceStrategyId) {
		return super.get(buildPathWithId(resourceStrategyId.toString()));
	}
	
	public List<AcResourceStrategy> getResourceStrategys() {
		return super.getList(buildPath(), null);
	}
	
	public AcResourceStrategy update(Integer resourceStrategyId, AcResourceStrategy resourceStrategy) {
		return super.update(buildPathWithId(resourceStrategyId.toString()), resourceStrategy);
	}
	
	public void create(AcResourceStrategy resourceStrategy) {
		super.postWithRet(buildPath(), resourceStrategy);
	}
	
	public void delete(Integer resourceStrategyId) {
		super.delete(buildPathWithId(resourceStrategyId.toString()));
	}
	
	public List<AcResourceStrategy> getAll() {
		MultivaluedMap<String, String> params = new  MultivaluedMapImpl();
		return super.getList(String.format("%s/search", buildPath()), params);
	}
	
	public List<AcResourceStrategy> getByUuid(String uuid) {
		MultivaluedMap<String, String> params = new  MultivaluedMapImpl();
		if(uuid != null) 
			params.add("uuid", uuid);
		return super.getList(String.format("%s/search", buildPath()), params);
	}
	
	public List<AcResourceStrategy> getByProperties(Integer id, String uuid, AcStrategyTypeEnum type,
			String name, String description, String clazzs, String params2, Date time, Integer page, Integer size) {
		MultivaluedMap<String, String> params = new  MultivaluedMapImpl();
		if(id != null) 
			params.add("id", id.toString());
		if(uuid != null) 
			params.add("uuid", uuid);
		if(type != null) 
			params.add("type", type.name());
		if(name != null)
			params.add("name", name);
		if(description != null)
			params.add("description", description);
		if(clazzs != null)
			params.add("clazzs", clazzs);
		if(params2 != null) 
			params.add("params", params2);
		if(page != null)
			params.add("page", page.toString());
		if(size != null)
			params.add("size", size.toString());
		if(time != null)
			params.add("time", time.toString());
		
		return super.getList(String.format("%s/search", buildPath()), params);
		
	}
	
	public long countByProperties(Integer id, String uuid, AcStrategyTypeEnum type,
			String name, String description, String clazzs, String params2, Date time) {
		MultivaluedMap<String, String> params = new  MultivaluedMapImpl();
		if(id != null) 
			params.add("id", id.toString());
		if(uuid != null) 
			params.add("uuid", uuid);
		if(type != null) 
			params.add("type", type.name());
		if(name != null)
			params.add("name", name);
		if(description != null)
			params.add("description", description);
		if(clazzs != null)
			params.add("clazzs", clazzs);
		if(params2 != null) 
			params.add("params", params2);
		if(time != null)
			params.add("time", time.toString());
		
		return super.count(String.format("%s/search/count", buildPath()), params);
	}
}
