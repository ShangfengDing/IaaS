package appcloud.api.client;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import appcloud.api.beans.Enterprise;
import appcloud.api.beans.EnterpriseInvitation;

import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class EnterpriseClient extends AbstractClient<Enterprise>{
	public EnterpriseClient(){
		super();
	}
	
	public EnterpriseClient(String baseURI) {
		super(baseURI);
	}

	@Override
	protected Class<?> getType() {
		return Enterprise.class;
	}
	
	@Override
	protected GenericType<List<Enterprise>> getGenericType() {		
		GenericType<List<Enterprise>> type = new GenericType<List<Enterprise>>() {};
		return type;
	}
	
	private String getPath() {
		return  "enterprises";
	}
	
	protected String buildPath() {
		return String.format("admin/%s", getPath());
	}
	
	protected String buildPathWithId(String enterpriseId) {
		return String.format("%s/%s", buildPath(), enterpriseId);
	}

	public Enterprise get(Integer enterpriseId) {
		return super.get(buildPathWithId(enterpriseId.toString()));
	}
	
	public List<Enterprise> getEnterprises() {
		return super.getList(buildPath(), null);
	}
	
	public Enterprise update(Integer enterpriseId, Enterprise enterprise) {
		return super.update(buildPathWithId(enterpriseId.toString()), enterprise);
	}
	
	public void create(Enterprise enterprise) {
		super.postWithRet(buildPath(), enterprise);
	}
	
	public void delete(Integer enterpriseId) {
		super.delete(buildPathWithId(enterpriseId.toString()));
	}
	
	public List<Enterprise> getByProperties(Integer id, String ownerId, Boolean isActive,
			String name, String description, String phone, String email, String address,
			String postcode, String homepage, Integer parentCompany, Integer page, Integer size) {
		MultivaluedMap<String, String> params = new  MultivaluedMapImpl();
		if(id != null) 
			params.add("id", id.toString());
		if(ownerId != null) 
			params.add("owner_id", ownerId.toString());
		if(isActive != null) 
			params.add("is_active", isActive.toString());
		if(name != null)
			params.add("name", name);
		if(description != null)
			params.add("description", description);
		if(phone != null)
			params.add("phone", phone);
		if(email != null) 
			params.add("email", email);
		if(address != null) 
			params.add("address", address);
		if(postcode != null)
			params.add("postcode", postcode);
		if(homepage != null)
			params.add("homepage", homepage);
		if(parentCompany != null)
			params.add("parentCompany", parentCompany.toString());
		if(page != null)
			params.add("page", page.toString());
		if(size != null)
			params.add("size", size.toString());
		return super.getList(String.format("%s/search", buildPath()), params);
		
	}
	
	public long countByProperties(Integer id, String ownerId, Boolean isActive,
			String name, String description, String phone, String email, String address,
			String postcode, String homepage, Integer parentCompany) {
		MultivaluedMap<String, String> params = new  MultivaluedMapImpl();
		if(id != null) 
			params.add("id", id.toString());
		if(ownerId != null) 
			params.add("owner_id", ownerId.toString());
		if(isActive != null) 
			params.add("is_active", isActive.toString());
		if(name != null)
			params.add("name", name);
		if(description != null)
			params.add("description", description);
		if(phone != null)
			params.add("phone", phone);
		if(email != null) 
			params.add("email", email);
		if(address != null) 
			params.add("address", address);
		if(postcode != null)
			params.add("postcode", postcode);
		if(homepage != null)
			params.add("homepage", homepage);
		if(parentCompany != null)
			params.add("parentCompany", parentCompany.toString());
		
		return super.count(String.format("%s/search", buildPath()), params);
	}
}
