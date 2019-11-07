package appcloud.api.client;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import appcloud.api.beans.Enterprise;
import appcloud.api.beans.EnterpriseInvitation;

public class EnterpriseInvitationClient extends AbstractClient<EnterpriseInvitation>{
	public EnterpriseInvitationClient(){
		super();
	}
	
	public EnterpriseInvitationClient(String baseURI) {
		super(baseURI);
	}
	
	@Override
	protected Class<?> getType() {
		return EnterpriseInvitation.class;
	}

	@Override
	protected GenericType<List<EnterpriseInvitation>> getGenericType() {
		GenericType<List<EnterpriseInvitation>> type = new GenericType<List<EnterpriseInvitation>>() {};
		return type;
	}
	
	private String getPath() {
		return  "enterprise_invitation";
	}
	
	protected String buildPath() {
		return String.format("admin/%s", getPath());
	}
	
	protected String buildPathWithId(String enterpriseInvitationId) {
		return String.format("%s/%s", buildPath(), enterpriseInvitationId);
	}
	
	public void create(EnterpriseInvitation enterpriseInvitation) {
		super.postWithRet(buildPath(), enterpriseInvitation);
	}
	
	public void delete(Integer enterpriseInvitationId) {
		super.delete(buildPathWithId(enterpriseInvitationId.toString()));
	}
	
	public void update(Integer enterpriseInvitationId, EnterpriseInvitation enterpriseInvitation) {
		super.update(buildPathWithId(enterpriseInvitationId.toString()), enterpriseInvitation);
	}
	
	public EnterpriseInvitation getById(Integer enterpriseInvitationId) {
		return super.get(buildPathWithId(enterpriseInvitationId.toString()));
	}
	
	public List<EnterpriseInvitation> getInvitationOfEnterprise(String enterpriseId) {
		MultivaluedMap<String, String> params = new  MultivaluedMapImpl();
		if(enterpriseId != null)
			params.add("enterprise_id", enterpriseId);
		return super.getList(buildPath(), params);
	}
	
	public List<EnterpriseInvitation> getInvitationOfUser(String userId) {
		MultivaluedMap<String, String> params = new  MultivaluedMapImpl();
		if(userId != null)
			params.add("user_id", String.valueOf(userId));
		return super.getList(buildPath(), params);
	}
	
	public List<EnterpriseInvitation> getInvitationByStatus(String status) {
		MultivaluedMap<String, String> params = new  MultivaluedMapImpl();
		if(status != null)
			params.add("status", status);
		return super.getList(buildPath(), params);
	}
	
	public List<EnterpriseInvitation> getInvitationByProperties(String id, String enterpriseId, String userId, String status, String page, String size) {
		MultivaluedMap<String, String> params = new  MultivaluedMapImpl();
		if(id != null) 
			params.add("id", id);
		if(enterpriseId != null) 
			params.add("enterprise_id", enterpriseId);
		if(userId != null) 
			params.add("user_id", userId);
		if(status != null)
			params.add("status", status);
		if(page != null)
			params.add("page", page);
		if(size != null)
			params.add("size", size);
		
		return super.getList(String.format("%s/search", buildPath()), params);
	}
	
	public Long countInvitationByProperties(String id, String enterpriseId, String userId, String status) {
		MultivaluedMap<String, String> params = new  MultivaluedMapImpl();
		if(id != null) 
			params.add("id", id);
		if(enterpriseId != null) 
			params.add("enterprise_id", enterpriseId);
		if(userId != null) 
			params.add("user_id", userId);
		if(status != null)
			params.add("status", status);
		
		return super.count(String.format("%s/search/count", buildPath()), params);
	}
}
