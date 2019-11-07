package appcloud.api.beans;

import javax.xml.bind.annotation.XmlValue;

public class CIDR {
	@XmlValue
	public String value;
	
	public CIDR() {}
	
	public CIDR(String value) {
		this.value = value;
	}

}
