package appcloud.openapi.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CreateImageResponse")
public class CreateImageResponse {
	
	private String RequestId;
	private String ErrorCode;
	private String Message;
	
	private String ImageId;
	
	/**
     * 直接把参数名命名为子节点的名字，然后统一使用
     * @XmlAccessorType(XmlAccessType.FIELD)进行注解，省去单独为其设置子节点名字的麻烦
     * 除此之外，使用@XmlAccessorType(XmlAccessType.FIELD)注解，可以使子节点名的显示顺序与
     * 参数声明的顺序一致
     */
	public CreateImageResponse() {
	}

	public CreateImageResponse(String requestId, String errorCode, String message) {
		super();
		RequestId = requestId;
		ErrorCode = errorCode;
		Message = message;
	}
	
	public CreateImageResponse(String requestId, String imageId) {
		super();
		RequestId = requestId;
		ImageId = imageId;
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

    public String getImageId() {
        return ImageId;
    }

    public void setImageId(String imageId) {
        ImageId = imageId;
    }



	
}
