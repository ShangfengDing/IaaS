package appcloud.iaas.sdk.security;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class RevokeSecurityGroupRequest extends RpcYhaiRequest{
	public RevokeSecurityGroupRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.REVOKE_SECURITY_GROUP);
	}
	
	private String securityGroupId;
	private String regionId;
	private String ipProtocol;
	private String portRange;
	private String sourceCidrIp;
	private String policy;
	private String priority;
	private String sourceGroupId;
	private String sourceGroupOwnerAccount;
	
	public String getSecurityGroupId() {
		return securityGroupId;
	}
	public void setSecurityGroupId(String securityGroupId) {
		this.securityGroupId = securityGroupId;
		putQueryParameters("SecurityGroupId",securityGroupId);
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
		putQueryParameters("RegionId",regionId);
	}
	public String getIpProtocol() {
		return ipProtocol;
	}
	public void setIpProtocol(String ipProtocol) {
		this.ipProtocol = ipProtocol;
		putQueryParameters("IpProtocol",ipProtocol);
	}
	public String getPortRange() {
		return portRange;
	}
	public void setPortRange(String portRange) {
		this.portRange = portRange;
		putQueryParameters("PortRange",portRange);
	}
	public String getSourceCidrIp() {
		return sourceCidrIp;
	}
	public void setSourceCidrIp(String sourceCidrIp) {
		this.sourceCidrIp = sourceCidrIp;
		putQueryParameters("SourceCidrIp",sourceCidrIp);
	}
	public String getPolicy() {
		return policy;
	}
	public void setPolicy(String policy) {
		this.policy = policy;
		putQueryParameters("Policy",policy);
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
		putQueryParameters("Priority",priority);
	}
	public String getSourceGroupId() {
		return sourceGroupId;
	}
	public void setSourceGroupId(String sourceGroupId) {
		this.sourceGroupId = sourceGroupId;
		putQueryParameters("SourceGroupId",sourceGroupId);
	}
	public String getSourceGroupOwnerAccount() {
		return sourceGroupOwnerAccount;
	}
	public void setSourceGroupOwnerAccount(String sourceGroupOwnerAccount) {
		this.sourceGroupOwnerAccount = sourceGroupOwnerAccount;
		putQueryParameters("SourceGroupOwnerAccount",sourceGroupOwnerAccount);
	}
	
}
