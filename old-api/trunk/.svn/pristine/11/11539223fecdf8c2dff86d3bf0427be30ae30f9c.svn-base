package appcloud.api.beans;

import java.util.Date;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import appcloud.api.enums.AttachStatusEnum;

@XmlRootElement(name="backup")
public class Backup {
	@XmlAttribute
	public String id;
	@XmlAttribute
	public String displayName;
	@XmlAttribute
	public String tenantId;
	@XmlAttribute
	public String displayDescription;
	@XmlAttribute
	public String volumeId;
	@XmlAttribute
	public String status;
	@XmlAttribute
	public Integer size;
	@XmlAttribute
	public Date createdAt;
	@XmlAttribute
	public Boolean force;
	
	@XmlJavaTypeAdapter(MetadataAdaptor.class)
	public HashMap<String, String> metadata = null;
	
	@XmlAttribute
	public AttachStatusEnum attachStatus;
	public String instanceId;
	public String instanceName;
		
	public Backup() {}

	public Backup(String id, String displayName, String tenantId,
			String displayDescription, String volumeId, String status,
			Integer size, Date createdAt, Boolean force,
			HashMap<String, String> metadata) {
		super();
		this.id = id;
		this.displayName = displayName;
		this.tenantId = tenantId;
		this.displayDescription = displayDescription;
		this.volumeId = volumeId;
		this.status = status;
		this.size = size;
		this.createdAt = createdAt;
		this.force = force;
		this.metadata = metadata;
	}
}
