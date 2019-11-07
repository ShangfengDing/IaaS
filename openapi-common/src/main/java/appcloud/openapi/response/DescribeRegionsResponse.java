package appcloud.openapi.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import appcloud.openapi.datatype.RegionItem;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DescribeRegionsResponse")
public class DescribeRegionsResponse extends BaseResponse{
	
	@XmlElementWrapper(name="Regions")
	@XmlElement(name="Region")
	private List<RegionItem> RegionItems;
	
	public DescribeRegionsResponse() {
		super();
	}

	public DescribeRegionsResponse(String requestId, List<RegionItem> regionItems) {
		super(requestId);
		this.RegionItems = regionItems;
	}
	
	public DescribeRegionsResponse(String requestId, String errorCode, String message) {
		super(requestId, errorCode, message);
	}

	public List<RegionItem> getRegionItems() {
		return RegionItems;
	}

	public void setRegionItems(List<RegionItem> regionItems) {
		this.RegionItems = regionItems;
	}
	
}
