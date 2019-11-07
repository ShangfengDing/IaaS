package appcloud.openapi.datatype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class InstanceAttributes {
	private String InstanceId;
	private String InstanceName;
	private String Description;
	private String ImageId;
	private String PrivateIpAddress;
	private String PrivateMAC;
	private String PublicIpAddress;
	private String PublicMAC;
	private String InstanceChargeType;
	private String ExpiredTime;
	private String UserId;
	private String CreationTime;
	private String ZoneId;
	private String Status;
    private Integer Vcpus;
    private Integer MemoryMb;
    private Integer LocalGb;	
    private String InternetMaxBandwidth;
    private String Ostype;
    private String SecurityGroupId;
    private String SecurityGroupName;
    private String vncPassword;

	private String vncPort;
	private String hostIp;
	
	public InstanceAttributes() {
	}
	
	public String getInstanceId() {
		return InstanceId;
	}
	public void setInstanceId(String instanceId) {
		InstanceId = instanceId;
	}
	public String getInstanceName() {
		return InstanceName;
	}
	public void setInstanceName(String instanceName) {
		InstanceName = instanceName;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getImageId() {
		return ImageId;
	}
	public void setImageId(String imageId) {
		ImageId = imageId;
	}
	public String getPrivateIpAddress() {
		return PrivateIpAddress;
	}
	public void setPrivateIpAddress(String privateIpAddress) {
		PrivateIpAddress = privateIpAddress;
	}
	public String getPublicIpAddress() {
		return PublicIpAddress;
	}
	public void setPublicIpAddress(String publicIpAddress) {
		PublicIpAddress = publicIpAddress;
	}
	public String getInstanceChargeType() {
		return InstanceChargeType;
	}
	public void setInstanceChargeType(String instanceChargeType) {
		InstanceChargeType = instanceChargeType;
	}
	public String getExpiredTime() {
		return ExpiredTime;
	}
	public void setExpiredTime(String expiredTime) {
		ExpiredTime = expiredTime;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getCreationTime() {
		return CreationTime;
	}
	public void setCreationTime(String creationTime) {
		CreationTime = creationTime;
	}
	public String getZoneId() {
		return ZoneId;
	}
	public void setZoneId(String zoneId) {
		ZoneId = zoneId;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public Integer getVcpus() {
		return Vcpus;
	}

	public void setVcpus(Integer vcpus) {
		Vcpus = vcpus;
	}

	public Integer getMemoryMb() {
		return MemoryMb;
	}

	public void setMemoryMb(Integer memoryMb) {
		MemoryMb = memoryMb;
	}

	public Integer getLocalGb() {
		return LocalGb;
	}

	public void setLocalGb(Integer localGb) {
		LocalGb = localGb;
	}

	public String getInternetMaxBandwidth() {
		return InternetMaxBandwidth;
	}

	public void setInternetMaxBandwidth(String internetMaxBandwidth) {
		InternetMaxBandwidth = internetMaxBandwidth;
	}

	public String getOstype() {
		return Ostype;
	}

	public void setOstype(String ostype) {
		Ostype = ostype;
	}

	public String getSecurityGroupId() {
		return SecurityGroupId;
	}

	public void setSecurityGroupId(String securityGroupId) {
		SecurityGroupId = securityGroupId;
	}

	public String getSecurityGroupName() {
		return SecurityGroupName;
	}

	public void setSecurityGroupName(String securityGroupName) {
		SecurityGroupName = securityGroupName;
	}

	public String getVncPassword() {
		return vncPassword;
	}

	public void setVncPassword(String vncPassword) {
		this.vncPassword = vncPassword;
	}

	public String getPrivateMAC() {
		return PrivateMAC;
	}

	public void setPrivateMAC(String privateMAC) {
		PrivateMAC = privateMAC;
	}

	public String getPublicMAC() {
		return PublicMAC;
	}

	public void setPublicMAC(String publicMAC) {
		PublicMAC = publicMAC;
	}

	public String getVncPort() {
		return vncPort;
	}

	public void setVncPort(String vncPort) {
		this.vncPort = vncPort;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}
}
