package appcloud.openapi.datatype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ZoneItem {

	private String ZoneId;

	
	
	public ZoneItem() {
		super();
	}


	public ZoneItem(String zoneId) {
		ZoneId = zoneId;
	}
	

	public String getZoneId() {
		return ZoneId;
	}

	public void setZoneId(String zoneId) {
		ZoneId = zoneId;
	}

}
