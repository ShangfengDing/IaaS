package appcloud.api.client;

import java.util.List;

import appcloud.api.beans.SecurityGroup;
import appcloud.api.beans.securitygroup.SecurityGroupCreateReq;

import com.sun.jersey.api.client.GenericType;

public class SecurityGroupClient  extends AbstractClient<SecurityGroup>{
	
	public SecurityGroupClient(){
		super();
	}
	
	public SecurityGroupClient(String baseURI) {
		super(baseURI);
	}

	@Override
	protected Class<?> getType() {
		return SecurityGroup.class;
	}
	
	@Override
	protected GenericType<List<SecurityGroup>> getGenericType() {		
		GenericType<List<SecurityGroup>> type = new GenericType<List<SecurityGroup>>() {};
		return type;
	}
	
	private String getPath() {
		return  "os-security-group";
	}
	
	protected String buildPath(String tenantId) {
		return String.format("%s/%s", tenantId, getPath());
	}
	
	protected String buildPathWithId(String tenantId, Integer groupId) {
		return String.format("%s/%s", buildPath(tenantId), groupId);
	}
	
	public SecurityGroup get(String tenantId, Integer groupId) {
		return super.get(buildPathWithId(tenantId, groupId));
	}
	
	public List<SecurityGroup> getSecurityGroups(String tenantId) {
		return super.getList(buildPath(tenantId), null);
	}
	
	public SecurityGroup createSecurityGroup(String tenantId, SecurityGroupCreateReq cReq) {
		return super.postWithRet(buildPath(tenantId), cReq);
	}
	
	public boolean deleteSecurityGroup(String tenantId, Integer groupId) {
		return super.delete(buildPathWithId(tenantId, groupId));
	}
	
	public SecurityGroup updateSecurityGroup(String tenantId, Integer groupId, String name, String description, String userId) {
		SecurityGroup sg = new SecurityGroup();
		sg.name = name;
		sg.description = description;
		sg.tenantId = userId;
//		SecurityGroupCreateReq cReq = new SecurityGroupCreateReq(name, description);
//		cReq.name = name;
//		cReq.description = description;
		
		return super.update(buildPathWithId(tenantId, groupId), sg);
	}
}
