package appcloud.openapi.response;

import appcloud.openapi.datatype.ImageBackItem;
import appcloud.openapi.datatype.InstanceAttributes;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DescribeDiskImageBackResponse")
@Data
public class DescribeDiskImageBackResponse extends BaseResponse {

	@XmlElementWrapper(name="ImageBacks")
	@XmlElement(name="ImageBack")
	private List<ImageBackItem> imageBackItemList;
	private String instanceId;
	private String diskId;

	public DescribeDiskImageBackResponse() {
		super();
	}

	public DescribeDiskImageBackResponse(String requestId, String errorCode, String message) {
		super(requestId, errorCode, message);
	}

	public DescribeDiskImageBackResponse(String requestId, List<ImageBackItem> imageBackItemList, String instanceId, String diskId) {
		super(requestId);
		this.imageBackItemList = imageBackItemList;
		this.instanceId = instanceId;
		this.diskId = diskId;
	}
	
}
