package appcloud.api.client;

import java.util.List;

import appcloud.api.beans.Rule;
import appcloud.api.beans.securitygroup.RuleCreateReq;

import com.sun.jersey.api.client.GenericType;

public class RuleClient extends AbstractClient<Rule>{

	public RuleClient () {
		super();
	}
	public RuleClient (String baseURI) {
		super(baseURI);
	}
	@Override
	protected Class<?> getType() {
		return Rule.class;
	}
	
	@Override
	protected GenericType<List<Rule>> getGenericType() {		
		GenericType<List<Rule>> type = new GenericType<List<Rule>>() {};
		return type;
	}
	
	private String getPath() {
		return  "os-security-group-rules";
	}
	
	protected String buildPath(String tenantId) {
		return String.format("%s/%s", tenantId, getPath());
	}
	
	protected String buildPathWithId(String tenantId, Integer ruleId) {
		return String.format("%s/%s", buildPath(tenantId), ruleId);
	}
	
	public Rule get(String tenantId, Integer ruleId) {
		return super.get(buildPathWithId(tenantId, ruleId));
	}
	/*
	public List<Rule> getRules(String tenantId) {
		return  this.getList(buildPath(tenantId), null);
	}
	*/
	
	public Rule createRule(String tenantId, RuleCreateReq cReq) {
		return super.postWithRet(buildPath(tenantId), cReq);
	}
	
	public boolean deleteRule(String tenantId, Integer ruleId) {
		return super.delete(buildPathWithId(tenantId, ruleId));
	}
}
