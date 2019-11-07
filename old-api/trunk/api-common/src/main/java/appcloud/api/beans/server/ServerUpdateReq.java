package appcloud.api.beans.server;

import java.util.HashMap;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import appcloud.api.beans.MetadataAdaptor;

@XmlRootElement(name="server")
public class ServerUpdateReq {
	@XmlAttribute
	public String serverId;
	@XmlAttribute
	public String name;
	@XmlJavaTypeAdapter(MetadataAdaptor.class)
	public HashMap<String, String> metadata;
	
	
	public ServerUpdateReq() {}
	
	public ServerUpdateReq(String serverId, String name, HashMap<String, String> metadata) {
		super();
		this.serverId = serverId;
		this.name = name;
		this.metadata = metadata;
	}
}
