package appcloud.api.beans;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name="server")
public class Server extends AbstractLinkItem{
	@XmlAttribute
	public String id;
	@XmlAttribute
	public String name;
	@XmlAttribute
	public String tenantId;
	@XmlAttribute
	public String status;
	@XmlAttribute
	public String accessIPv4;
	@XmlAttribute
	public String accessIPv6;
	@XmlAttribute
	public Integer progress;
	@XmlAttribute
	public String hostId;
	@XmlAttribute
	public Date created;
	@XmlAttribute(namespace="http://docs.openstack.org/compute/ext/extended_availability_zone/api/v2")
	public Integer availabilityZone;
	@XmlAttribute
	public Integer aggregate;
	@XmlAttribute
	public Date updated;
	public Image image;
	public Flavor flavor;
	public String adminPass;
	
	@XmlElementWrapper(name="addresses")
	@XmlElements(
		@XmlElement(name="network", type=Network.class)
	)
	public List<Network> addresses;
	
	@XmlJavaTypeAdapter(MetadataAdaptor.class)
	public HashMap<String, String> metadata = null;
	
	@XmlElement
	public SecurityGroup securityGroup;
		
	public Server() {}
	
	public Server(String tenantId, String id, String name) {
		this.id = id;
		this.name = name;
		this.tenantId = tenantId;
	}

	public Server(String id, String name, String tenantId, String status,
			String accessIPv4, String accessIPv6, Integer progress,
			String hostId, Date created, Date updated, Image image,
			Flavor flavor, String adminPass, List<Network> addresses,
			HashMap<String, String> metadata, SecurityGroup securityGroup) {
		super();
		this.id = id;
		this.name = name;
		this.tenantId = tenantId;
		this.status = status;
		this.accessIPv4 = accessIPv4;
		this.accessIPv6 = accessIPv6;
		this.progress = progress;
		this.hostId = hostId;
		this.created = created;
		this.updated = updated;
		this.image = image;
		this.flavor = flavor;
		this.adminPass = adminPass;
		this.addresses = addresses;
		this.metadata = metadata;
		this.securityGroup = securityGroup;
	}

	@Override
	protected URI _getBookmark() {
		UriBuilder builder = getUriBuilder();
		builder.path(this.tenantId);
		builder.path("servers");
		//builder.path(ServerResource.class, "detail");
		return builder.path(this.id).build();
		//return null;
	}
}
