package appcloud.api.beans;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import appcloud.api.enums.AcServiceStatusEnum;
import appcloud.api.enums.AcServiceTypeEnum;

@XmlRootElement(name="service")
public class AcService {
	@XmlAttribute
	public Integer id;
	@XmlAttribute
	public Integer hostId;
	@XmlAttribute
	public String hostUuid;
	@XmlAttribute
	public Integer zoneId;
	
	public String hostIp;
	public Integer monitorPort;
	public AcServiceTypeEnum type;	
	public Date updateTime;
	public Date lastStartTime;
	public Date lastStopTime;
	public AcServiceStatusEnum serviceStatus;
	// no metadata
	
	public AcService(){
		super();
	}
	
	public AcService(Integer id, Integer hostId, String hostUuid,
			Integer zoneId, String hostIp, Integer monitorPort,
			AcServiceTypeEnum type, Date updateTime, Date lastStartTime,
			Date lastStopTime, AcServiceStatusEnum serviceStatus) {
		super();
		this.id = id;
		this.hostId = hostId;
		this.hostUuid = hostUuid;
		this.zoneId = zoneId;
		this.hostIp = hostIp;
		this.monitorPort = monitorPort;
		this.type = type;
		this.updateTime = updateTime;
		this.lastStartTime = lastStartTime;
		this.lastStopTime = lastStopTime;
		this.serviceStatus = serviceStatus;
	}
	
}
