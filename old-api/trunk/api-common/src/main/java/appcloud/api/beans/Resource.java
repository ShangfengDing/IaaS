package appcloud.api.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="resource")
public class Resource {

	@XmlElement(name="project")
	public String project;
	@XmlElement(name="memory_mb")
	public Integer memoryMb;
	@XmlElement(name="host")
	public String host;
	@XmlElement(name="cpu")
	public Integer cpu;
	@XmlElement(name="disk_gb")
	public Integer diskGb;
	
	public Resource() {
		super();
	}

	public Resource(String project, Integer memoryMb, String host, Integer cpu,
			Integer diskGb) {
		super();
		this.project = project;
		this.memoryMb = memoryMb;
		this.host = host;
		this.cpu = cpu;
		this.diskGb = diskGb;
	}
	
}
