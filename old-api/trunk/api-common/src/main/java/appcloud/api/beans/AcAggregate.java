package appcloud.api.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="aggregate")
public class AcAggregate {
	@XmlAttribute
	public Integer id;
	@XmlAttribute
	public String name;
	@XmlAttribute
	public String resrcStrategyUuid;
	@XmlAttribute
	public Integer cpu_oversell;
	@XmlAttribute
	public Integer memory_oversell;
	@XmlAttribute
	public Integer disk_oversell;
	@XmlAttribute
	public Date createdAt;
	@XmlAttribute
	public Date updatedAt;
	public Date deleteAt;
	public Boolean deleted;
	public String availabilityZoneName;
	public Integer availabilityZoneId;
	
	public Integer publicVlanId;
	public Integer privateVlanId;

	@XmlElementWrapper(name="acHosts")
	@XmlElements(
		@XmlElement(name="acHost", type=AcHost.class)
	)
	public List<AcHost> acHosts = new ArrayList<AcHost>();
	
	public AcAggregate(){};
	
	public AcAggregate(String name, Integer id, String resrcStrategyUuid, Integer cpu_oversell, Integer memory_oversell, 
			Integer disk_oversell, String availabilityZoneName,
			Integer availabilityZoneId, Boolean deleted, Date createdAt,
			Date updatedAt, Date deleteAt) {
		super();
		this.name = name;
		this.id = id;
		this.resrcStrategyUuid = resrcStrategyUuid;
		this.cpu_oversell = cpu_oversell;
		this.memory_oversell = memory_oversell;
		this.disk_oversell = disk_oversell;
		this.availabilityZoneName = availabilityZoneName;
		this.availabilityZoneId = availabilityZoneId;
		this.deleted = deleted;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deleteAt = deleteAt;
	}

	public AcAggregate(Integer id, String name, String resrcStrategyUuid, Integer cpu_oversell, Integer memory_oversell, 
			Integer disk_oversell, Date createdAt, Date updatedAt,
			Date deleteAt, Boolean deleted, String availabilityZoneName,
			Integer availabilityZoneId, Integer publicVlanId,
			Integer privateVlanId, List<AcHost> acHosts) {
		super();
		this.id = id;
		this.name = name;
		this.resrcStrategyUuid = resrcStrategyUuid;
		this.cpu_oversell = cpu_oversell;
		this.memory_oversell = memory_oversell;
		this.disk_oversell = disk_oversell;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deleteAt = deleteAt;
		this.deleted = deleted;
		this.availabilityZoneName = availabilityZoneName;
		this.availabilityZoneId = availabilityZoneId;
		this.publicVlanId = publicVlanId;
		this.privateVlanId = privateVlanId;
		this.acHosts = acHosts;
	}

}
