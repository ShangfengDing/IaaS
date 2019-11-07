package appcloud.api.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="host")
public class Host {
	@XmlAttribute(name="host_name")
	public String hostName;
	@XmlAttribute
	public String service;
	@XmlAttribute
	public String zone;

	public List<Resource> resource;

	public Host() {}
	public Host(String hostName, String service, String zone,List<Resource> resource) {
		super();
		this.hostName = hostName;
		this.service = service;
		this.zone = zone;
		this.resource = resource;
	}
}
