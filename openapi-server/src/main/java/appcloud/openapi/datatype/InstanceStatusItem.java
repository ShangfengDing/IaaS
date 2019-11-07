package appcloud.openapi.datatype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class InstanceStatusItem {

	private String InstanceId;
	private String Status;
	private String InstanceName;
	private String InstanceDescription;
	private String PublicIpAddress;
	private String PrivateIpAddress;
	private String CreatedTime;

	public InstanceStatusItem(){};
	public InstanceStatusItem(String instanceId, String status, String instanceName, String instanceDescription, 
			String publicIpAddress, String privateIpAddress, String createdTime) {
		InstanceId = instanceId;
		Status = status;
		InstanceName = instanceName;
		InstanceDescription = instanceDescription;
		PublicIpAddress = publicIpAddress;
		PrivateIpAddress = privateIpAddress;
		CreatedTime = createdTime;
	}

	public String getInstanceId() {
		return InstanceId;
	}
	public void setInstanceId(String instanceId) {
		InstanceId = instanceId;
	}

	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}

	public String getInstanceName() {
		return InstanceName;
	}
	public void setInstanceName(String instanceName) {
		InstanceName = instanceName;
	}

	public String getInstanceDescription() {
		return InstanceDescription;
	}
	public void setInstanceDescription(String instanceDescription) {
		InstanceDescription = instanceDescription;
	}

	public String getPublicIpAddress() {
		return PublicIpAddress;
	}
	public void setPublicIpAddress(String publicIpAddress) {
		PublicIpAddress = publicIpAddress;
	}

	public String getPrivateIpAddress() {
		return PrivateIpAddress;
	}
	public void setPrivateIpAddress(String privateIpAddress) {
		PrivateIpAddress = privateIpAddress;
	}

	public String getCreatedTime() {
		return CreatedTime;
	}
	public void setCreatedTime(String createdTime) {
		CreatedTime = createdTime;
	}

}
