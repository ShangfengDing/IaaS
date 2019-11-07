package appcloud.api.beans;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.XmlAttribute;

@XmlRootElement(name="meta")
public class Meta {
	@XmlAttribute
	public String key;
	@XmlValue
	public String value;
	
	public Meta() {
	};
	
	public Meta(String key, String value) {
		this.key = key;
		this.value = value;
	}
}
