package appcloud.iaas.sdk.model;

import appcloud.core.sdk.common.RpcYhaiRequest;
import appcloud.iaas.sdk.common.Constants;
import appcloud.openapi.constant.ActionConstants;

public class ModifyInstanceAttributeRequest extends RpcYhaiRequest {
	public ModifyInstanceAttributeRequest() {
		super(Constants.PRODUCT, Constants.VERSION, ActionConstants.MODIFY_INSTANCE_ATTRIBUTE);
	}
	
	private String instanceId;
	private String instanceName;
	private String description;
	private String password;
	private String hostName;
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
		putQueryParameters("InstanceId",instanceId);
	}
	public String getInstanceName() {
		return instanceName;
	}
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
		putQueryParameters("InstanceName",instanceName);
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
		putQueryParameters("Description",description);
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
		putQueryParameters("Password",password);
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
		putQueryParameters("HostName",hostName);
	}
	
}
