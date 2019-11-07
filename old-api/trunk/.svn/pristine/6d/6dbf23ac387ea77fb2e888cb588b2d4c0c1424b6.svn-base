package appcloud.api.beans;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="network")
public class Network {
	@XmlElement(name="ip")
	public List<IP> ips = new ArrayList<IP>();
	
	@XmlAttribute
	public String id;
	
	public Network() {}

	public Network(List<IP> ips, String id) {
		this.ips = ips;
		this.id = id;
	}
}
