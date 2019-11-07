package appcloud.api.beans;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "volume_attachment")
public class VolumeAttachment {

	@XmlAttribute
	public String device;
	@XmlAttribute 
	public String serverId;
	@XmlAttribute
	public String id;
	@XmlAttribute
	public String volumeId;
	
	public VolumeAttachment() {
		super();
	}
	public VolumeAttachment(String device, String serverId, String id,
			String volumeId) {
		super();
		this.device = device;
		this.serverId = serverId;
		this.id = id;
		this.volumeId = volumeId;
	}
	
}
