package appcloud.api.beans;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;

@XmlRootElement(name="image")
public class Image extends AbstractLinkItem{
	@XmlAttribute
	public String id;
	@XmlAttribute
	public String name;
	@XmlAttribute
	public String tenantId;
	
	//The detailed part
	@XmlAttribute
	public String status;
	@XmlAttribute
	public Integer minDisk;
	@XmlAttribute
	public Integer minRam;
	@XmlAttribute
	public Integer progress;
	@XmlAttribute
	public Date created;
	@XmlAttribute
	public String md5sum;
	@XmlAttribute
	public String signal;
	@XmlAttribute
	public String distribution;
//	@XmlAttribute
//	public String password;
//	@XmlAttribute
//	public String software;
//	@XmlAttribute
//	public String type;
	@XmlJavaTypeAdapter(MetadataAdaptor.class)
	public HashMap<String, String> metadata = null;
		
	public Image() {}
 
	public Image(String tenantId, String id, String name) {
		this.id = id;
		this.name = name;
		this.tenantId = tenantId;
	}

	public Image(String id, String name, String tenantId, String status,
			Integer minDisk, Integer minRam, Integer progress, Date created,
			HashMap<String, String> metadata) {
		super();
		this.id = id;
		this.name = name;
		this.tenantId = tenantId;
		this.status = status;
		this.minDisk = minDisk;
		this.minRam = minRam;
		this.progress = progress;
		this.created = created;
		this.metadata = metadata;
	}
	

	public Image(String id, String name, String tenantId, String status,
			Integer minDisk, Integer minRam, Integer progress, Date created,
			String distribution, HashMap<String, String> metadata) {
		super();
		this.id = id;
		this.name = name;
		this.tenantId = tenantId;
		this.status = status;
		this.minDisk = minDisk;
		this.minRam = minRam;
		this.progress = progress;
		this.created = created;
		this.distribution = distribution;
		this.metadata = metadata;
	}

	@Override
	protected URI _getBookmark() {
		UriBuilder builder = getUriBuilder();
		builder.path(this.tenantId);
		builder.path("images");
		
		
		builder.path(this.id);
		return builder.build();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		Image temp = (Image) obj;
		return this.signal.equals(temp.signal);
	}
}
