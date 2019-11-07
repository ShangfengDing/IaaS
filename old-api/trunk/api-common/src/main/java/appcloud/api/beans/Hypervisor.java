package appcloud.api.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="hypervisor")
public class Hypervisor {
	
	@XmlElement
	public Integer id;
	@XmlElement
	public String hypervisorHostname;
	
	@XmlElementWrapper(name="servers")
	@XmlElements(
		@XmlElement(name="server", type=Server.class)
	)
	public List <Server> servers;
	public Hypervisor() {
		super();
	}
	public Hypervisor(Integer id, String hypervisorHostname,
			List<Server> servers) {
		super();
		this.id = id;
		this.hypervisorHostname = hypervisorHostname;
		this.servers = servers;
	}

}
