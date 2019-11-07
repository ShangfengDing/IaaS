package appcloud.openapi.response;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import appcloud.openapi.constant.ResultConstants;
import appcloud.openapi.datatype.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ShowImageDetailResponse")
public class ShowImageDetailResponse {
	
	/**
	 * 直接把参数名命名为子节点的名字，然后统一使用
	 * @XmlAccessorType(XmlAccessType.FIELD)进行注解，省去单独为其设置子节点名字的麻烦
	 * 除此之外，使用@XmlAccessorType(XmlAccessType.FIELD)注解，可以使子节点名的显示顺序与
	 * 参数声明的顺序一致
	 */
	private String RequestId;
	private ImageDetailItem ImageDetailItem;  
	private String ErrorCode;
	private String Message;
	
	protected ShowImageDetailResponse() {
	}

	public ShowImageDetailResponse(String requestId, Map<String, Object> resultMap) {
		RequestId = requestId;
		setImageDetailItem((ImageDetailItem) resultMap.get(ResultConstants.IMAGE_DETAIL_ITEM));
	}
	
	public ShowImageDetailResponse(String requestId, String errorCode, String message) {
		RequestId = requestId;
		ErrorCode = errorCode;
		Message = message;
	}

	public String getRequestId() {
		return RequestId;
	}
	public void setRequestId(String requestId) {
		RequestId = requestId;
	}

	public String getErrorCode() {
		return ErrorCode;
	}
	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}

	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}

    public ImageDetailItem getImageDetailItem() {
        return ImageDetailItem;
    }

    public void setImageDetailItem(ImageDetailItem imageDetailItem) {
        ImageDetailItem = imageDetailItem;
    }

}
