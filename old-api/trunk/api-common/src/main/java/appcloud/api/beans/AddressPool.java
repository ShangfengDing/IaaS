package appcloud.api.beans;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="address_pool")
public class AddressPool extends AbstractLinkItem{
	@XmlAttribute
	public Integer id;
	@XmlAttribute
	public String tenantId;
	@XmlAttribute
	public Integer aggregateId;
	@XmlAttribute
	public String type; //public private
	@XmlAttribute
	public String subnet;
	@XmlAttribute
	public String netmask;
	@XmlAttribute
	public String dns;
	@XmlAttribute
	public String gateway;
	public String ipStart;
	public String ipEnd;
	
	@XmlElementWrapper(name="ips")
	@XmlElements(
			@XmlElement(name="ip", type=IPUsage.class)
	)
	public List<IPUsage> ips = new ArrayList<IPUsage>();
	
	public AddressPool() {}
	
	@Override
	protected URI _getBookmark() {
		UriBuilder builder = getUriBuilder();
		builder.path("admin");
		builder.path("ac-address_pools");
		//builder.path(ServerResource.class, "detail");
		return builder.path(String.valueOf(this.id)).build();
		//return null;
	}
	
	public static class IPUsage {
		@XmlAttribute
		public String ipAddress;
		@XmlAttribute
		public String status;		//空闲，占用
		@XmlAttribute
		public String macAddress;			//MAC地址
		@XmlAttribute
		public String serverName;	//占用的虚拟机名称
		
		public IPUsage() {}
		
		public IPUsage(String ipAddress, String status, String macAddress,
				String serverName) {
			super();
			this.ipAddress = ipAddress;
			this.status = status;
			this.macAddress = macAddress;
			this.serverName = serverName;
		}
	}
}
