package appcloud.api.beans.server;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import appcloud.api.beans.MetadataAdaptor;
import appcloud.api.beans.SecurityGroup;

@XmlRootElement(name="server")
public class ServerCreateReq {
	@XmlAttribute
	public String imageRef;
	@XmlAttribute
	public String flavorRef;
	@XmlAttribute
	public String name;
	@XmlAttribute
	public String availabilityZone;
	@XmlAttribute
	public String availabilityAggregate;
	@XmlJavaTypeAdapter(MetadataAdaptor.class)
	public HashMap<String, String> metadata;
	
	public SecurityGroup securityGroup;
	
	public ServerCreateReq() {}
	
	public ServerCreateReq(String imageRef, String flavorRef, String name,
			String availabilityZone, String availabilityAggregate, HashMap<String, String> metadata,
			SecurityGroup securityGroup) {
		super();
		this.imageRef = imageRef;
		this.flavorRef = flavorRef;
		this.name = name;
		this.availabilityZone = availabilityZone;
		this.availabilityAggregate = availabilityAggregate;
		this.metadata = metadata;
		this.securityGroup = securityGroup;
	}
	
	public ServerCreateReq(String imageRef, String flavorRef, String name,
			String availabilityZone, HashMap<String, String> metadata,
			SecurityGroup securityGroup) {
		super();
		this.imageRef = imageRef;
		this.flavorRef = flavorRef;
		this.name = name;
		this.availabilityZone = availabilityZone;
		this.metadata = metadata;
		this.securityGroup = securityGroup;
	}
}
