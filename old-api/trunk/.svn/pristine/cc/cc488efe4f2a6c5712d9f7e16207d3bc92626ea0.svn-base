package appcloud.api.beans;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="flavor")
public class Flavor extends AbstractLinkItem{
	@XmlAttribute
	public Integer id;
	@XmlAttribute
	public String name;
	@XmlAttribute
	public String tenantId;
	@XmlAttribute
	public Integer ram;
	@XmlAttribute
	public Integer disk;
	@XmlAttribute
	public Integer vcpus;
	@XmlAttribute(namespace="http://docs.openstack.org/compute/ext/flavor_access/api/v1.1")
	public Boolean isPulbic;
		
	public Flavor() {}

	public Flavor(String tenantId, Integer id, String name) {
		this.id = id;
		this.name = name;
		this.tenantId = tenantId;
	}

	public Flavor(Integer id, String name, String tenantId, Integer ram,
			Integer disk, Integer vcpus) {
		this.id = id;
		this.name = name;
		this.tenantId = tenantId;
		this.ram = ram;
		this.disk = disk;
		this.vcpus = vcpus;
	}

	@Override
	protected URI _getBookmark() {
		UriBuilder builder = getUriBuilder();
		builder.path(this.tenantId);
		builder.path("flavors");
		//builder.path(FlavorResource.class, "get");
		return builder.build(this.id);
	}
}
