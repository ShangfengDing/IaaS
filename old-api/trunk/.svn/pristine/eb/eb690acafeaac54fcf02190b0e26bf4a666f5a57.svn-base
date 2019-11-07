package appcloud.api.beans;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="metadata")
public class Metadata {
	@XmlElement(name="meta")
	public List<Meta> metas = new ArrayList<Meta>();
	
	public Metadata() {
		metas = new ArrayList<Meta>();
	}
	
	public Metadata(List<Meta> metas) {
		this.metas = metas;
	}
}
