package appcloud.api.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="availabilityZone")
public class AvailabilityZone {
	@XmlAttribute
	public String name;
	@XmlAttribute
	public Integer id;
	@XmlAttribute
	public String regionId;
	@XmlAttribute
	public String zoneId;
	@XmlAttribute
	public Date createdAt;
	@XmlAttribute
	public Date updatedAt;

	@XmlElementWrapper(name="aggregates")
	@XmlElements(
		@XmlElement(name="acAggregate", type=AcAggregate.class)
	)
	public List<AcAggregate> aggregates = new ArrayList<AcAggregate>();
		
	public AvailabilityZone() {}

	public AvailabilityZone(String name, Integer id, String regionId, 
			String zoneId, Date createdAt, Date updatedAt) {
		super();
		this.name = name;
		this.id = id;
		this.regionId = regionId;
		this.zoneId = zoneId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
