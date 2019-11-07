package appcloud.api.beans;

import java.util.Date;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import appcloud.api.enums.AcVolumeTypeEnum;

@XmlRootElement(name="volume")
public class Volume {

	@XmlAttribute
	public  String id;
	@XmlAttribute
	public String displayName;
	@XmlAttribute
	public String displayDescription;
	
	//check it
	@XmlAttribute
	public String status;
	
	@XmlAttribute
	public Integer size;
	@XmlAttribute
	public String volumeType;
	@XmlAttribute
	public String availabilityZone;
	
	@XmlJavaTypeAdapter(MetadataAdaptor.class)
	public HashMap<String, String> metadata = null;

	@XmlAttribute
	public String snapshotId;
	
	@XmlElement
	public VolumeAttachment attachments;
	
	@XmlAttribute
	public Date createdAt;
	
	@XmlElement
	public AcVolumeTypeEnum acVolumeType;
	
	public Volume() {
		super();
	}
	public Volume(String id, String displayName, String displayDescription,
			String status, Integer size, String volumeType,
			String availabilityZone, HashMap<String, String> metadata,
			String snapshotId, VolumeAttachment attachments, Date createdAt, AcVolumeTypeEnum acVolumeType) {
		super();
		this.id = id;
		this.displayName = displayName;
		this.displayDescription = displayDescription;
		this.status = status;
		this.size = size;
		this.volumeType = volumeType;
		this.availabilityZone = availabilityZone;
		this.metadata = metadata;
		this.snapshotId = snapshotId;
		this.attachments = attachments;
		this.createdAt = createdAt;
		this.acVolumeType = acVolumeType;
	}

	public Volume(String id, String displayName, VolumeAttachment attachments, HashMap<String, String> metadata) {
		this.id = id;
		this.displayName = displayName;
		this.attachments = attachments;
		this.metadata = metadata;
	}
	
}
