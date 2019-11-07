package appcloud.api.beans.server;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="os-migrateLive")
public class ServerActionResize {
	@XmlElement(name="host")
	public String hostId;
	public boolean blockMigration;
	public boolean diskOverCommit;
	
	public ServerActionResize() {}
}
