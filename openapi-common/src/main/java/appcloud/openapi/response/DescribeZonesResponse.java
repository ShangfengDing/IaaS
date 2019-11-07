package appcloud.openapi.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import appcloud.openapi.datatype.ZoneItem;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DescribeZonesResponse")
public class DescribeZonesResponse extends BaseResponse{
	
	@XmlElementWrapper(name="Zones")
	@XmlElement(name="Zone")
	private List<ZoneItem> ZoneItems;

	public DescribeZonesResponse() {
		super();
	}

	public DescribeZonesResponse(String requestId, List<ZoneItem> zoneItems) {
		super(requestId);
		this.ZoneItems = zoneItems;
	}
	
	public DescribeZonesResponse(String requestId, String errorCode, String message) {
		super(requestId, errorCode, message);
	}

	public List<ZoneItem> getZoneItems() {
		return ZoneItems;
	}

	public void setZoneItems(List<ZoneItem> zoneItems) {
		this.ZoneItems = zoneItems;
	}

}
