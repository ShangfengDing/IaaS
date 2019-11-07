package appcloud.openapi.datatype;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class RegionItem {

	private String RegionId;

	public RegionItem() {
		super();
	}

	public RegionItem(String regionId) {
		RegionId = regionId;
	}

	public String getRegionId() {
		return RegionId;
	}

	public void setRegionId(String regionId) {
		RegionId = regionId;
	}
}
