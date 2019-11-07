package appcloud.api.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import appcloud.api.enums.AcHostStatusEnum;
import appcloud.api.enums.AcHostTypeEnum;

@XmlRootElement(name="host")
public class AcHost {
	@XmlAttribute
	public String id;
	@XmlAttribute
	public String ip;
	@XmlAttribute
	public AcHostTypeEnum hostType;
	@XmlAttribute 
	public AcHostStatusEnum hostStatus;
	@XmlAttribute
	public String location;
	
	public Integer privateVlanId;
	public Integer publicVlanId;
	public Integer aggregateId;
	public String aggregateName;
	public Integer availabilityZoneId;
	public String availabilityZoneName;
	public Integer numberOfVmInstances;
	
	public List<Resource> resources;

	public AcHost() {}
	
	public AcHost(String id, String ip, AcHostTypeEnum hostType,
			AcHostStatusEnum hostStatus) {
		super();
		this.id = id;
		this.ip = ip;
		this.hostType = hostType;
		this.hostStatus = hostStatus;
	}

	public AcHost(String id, String ip, AcHostTypeEnum hostType,
			AcHostStatusEnum hostStatus, String location,
			Integer privateVlanId, Integer publicVlanId, Integer aggregateId,
			String aggregateName, Integer availabilityZoneId,
			String availabilityZoneName, Integer numberOfVmInstances,
			List<Resource> resources) {
		super();
		this.id = id;
		this.ip = ip;
		this.hostType = hostType;
		this.hostStatus = hostStatus;
		this.location = location;
		this.privateVlanId = privateVlanId;
		this.publicVlanId = publicVlanId;
		this.aggregateId = aggregateId;
		this.aggregateName = aggregateName;
		this.availabilityZoneId = availabilityZoneId;
		this.availabilityZoneName = availabilityZoneName;
		this.numberOfVmInstances = numberOfVmInstances;
		this.resources = resources;
	}
	
}
