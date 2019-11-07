package appcloud.api.client;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import appcloud.api.beans.AcUser;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;


public class AcUserClient extends AbstractClient<AcUser>{
	
	public AcUserClient(){
		super();
	}
	
	public AcUserClient(String baseURI) {
		super(baseURI);
	}

	@Override
	protected Class<?> getType() {
		return AcUser.class;
	}
	
	@Override
	protected GenericType<List<AcUser>> getGenericType() {		
		GenericType<List<AcUser>> type = new GenericType<List<AcUser>>() {};
		return type;
	}
	
	private String getPath() {
		return  "ac-users";
	}
	
	protected String buildPath() {
		return String.format("admin/%s", getPath());
	}
	
	protected String buildPathWithId(String userId) {
		return String.format("%s/%s", buildPath(), userId);
	}

	public AcUser get(String userId) {
		return super.get(buildPathWithId(userId));
	}

	public AcUser getAccount(String userId) {
		return super.get(buildPathWithId("enterprise"+"/"+userId));
	}

	public List<AcUser> getAcUsers() {
		return super.getList(buildPath(), null);
	}
	
	public AcUser update(String userId, AcUser user) {
		return super.update(buildPathWithId(userId), user);
	}
	
	public AcUser create(AcUser user) {
		return super.postWithRet(buildPath(), user);
	}
	
	public boolean delete(String userId) {
		return super.delete(buildPathWithId(userId.toString()));
	}
	
	public List<AcUser> search(Integer groupId, String email, Boolean isActive, Integer enterpriseId, Integer page, Integer size) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		if(groupId != null)
			params.add("group_id", groupId.toString());
		if(email != null) 
			params.add("user_email", email);
		if(isActive != null)
			params.add("is_active", isActive.toString());
		if(enterpriseId != null)
			params.add("enterprise_id", enterpriseId.toString());
		if(page!= null)
			params.add("page", String.valueOf(page));
		if(size != null)
			params.add("size", String.valueOf(size));

		return super.getList(String.format("%s/search", buildPath()), params);
	}
	
	public List<AcUser> searchByEnterprise(Integer groupId, String email, Boolean isActive, String enterpriseIds, Integer page, Integer size) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		if(groupId != null)
			params.add("group_id", groupId.toString());
		if(email != null) 
			params.add("user_email", email);
		if(isActive != null)
			params.add("is_active", isActive.toString());
		if(enterpriseIds != null)
			params.add("enterprise_id", enterpriseIds.toString());
		if(page!= null)
			params.add("page", String.valueOf(page));
		if(size != null)
			params.add("size", String.valueOf(size));

		return super.getList(String.format("%s/search", buildPath()), params);
	}
	
	public Long count(Integer groupId, String email, Boolean isActive, Integer enterpriseId) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		if(groupId != null)
			params.add("group_id", groupId.toString());
		if(email != null)
			params.add("email", email);
		if(isActive != null)
			params.add("is_active", isActive.toString());
		if(enterpriseId != null)
			params.add("enterprise_id", enterpriseId.toString());

		return super.count(String.format("%s/search/count", buildPath()), params);
	}
	
	public Long countByEnterprises(Integer groupId, String email, Boolean isActive, String enterpriseIdsStr) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		if(groupId != null)
			params.add("group_id", groupId.toString());
		if(email != null)
			params.add("email", email);
		if(isActive != null)
			params.add("is_active", isActive.toString());
		if(enterpriseIdsStr != null)
			params.add("enterprise_id", enterpriseIdsStr);

		return super.count(String.format("%s/search/count", buildPath()), params);
	}

	public static void main (String args[]) {
		AcUser temp = new AcUser("1034", "1037019239",true,2,null);
		AcUser acUser = new AcUserClient().create(temp);
	}
}

