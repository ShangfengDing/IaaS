package appcloud.api.client;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import appcloud.api.beans.AcGroup;
import appcloud.api.beans.AcResourceStrategy;
import appcloud.api.enums.AcStrategyTypeEnum;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class AcGroupClient extends AbstractClient<AcGroup>{
	
	public AcGroupClient(){
		super();
	}
	
	public AcGroupClient(String baseURI) {
		super(baseURI);
	}

	@Override
	protected Class<?> getType() {
		return AcGroup.class;
	}
	
	@Override
	protected GenericType<List<AcGroup>> getGenericType() {		
		GenericType<List<AcGroup>> type = new GenericType<List<AcGroup>>() {};
		return type;
	}
	
	private String getPath() {
		return  "ac-groups";
	}
	
	protected String buildPath() {
		return String.format("admin/%s", getPath());
	}
	
	protected String buildPathWithId(String userId) {
		return String.format("%s/%s", buildPath(), userId);
	}

	public AcGroup get(Integer groupId) {
		return super.get(buildPathWithId(groupId.toString()));
	}
	
	public List<AcGroup> getAcGroups() {
		return super.getList(buildPath(), null);
	}
	
	public List<AcGroup> getByClusterId(Integer clusterId) {
		MultivaluedMap<String, String> params = new  MultivaluedMapImpl();
		if(clusterId != null) 
			params.add("clusterId", String.valueOf(clusterId));
		return super.getList(String.format("%s/search", buildPath()), params);
	}

	// TODO 增加secretkey作为验证
	public AcGroup update(Integer groupId, AcGroup group) {
		return super.update(buildPathWithId(groupId.toString()), group);
	}

	// TODO 自动生成secretkey并返回
	public void create(AcGroup group) {
		super.postWithRet(buildPath(), group);
	}
	
	public void delete(Integer groupId) {
		super.delete(buildPathWithId(groupId.toString()));
	}
}
