package appcloud.api.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name="aggregate")
public class Aggregate {
	public String name;
	public Integer id;
	public String availabilityZone;
	public Boolean deleted;
	public Date createdAt;
	public Date updatedAt;
	public Date deleteAt;
	@XmlElementWrapper(name="hosts")
	@XmlElements(
		@XmlElement(name="host", type=String.class)
	)
	public List<String> hosts = new ArrayList<String>();
	@XmlJavaTypeAdapter(MetadataAdaptor.class)
	public HashMap<String, String> metadata = null;

		
	public Aggregate() {}
}
