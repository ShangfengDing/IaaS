package appcloud.openapi.response;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by zouji on 2017/11/1.
 */
@XmlAccessorType
@XmlRootElement(name = "SaveOperateLogResponse")
public class SaveOperateLogResponse extends BaseResponse {

    private String RequestId;
    private String ErrorCode;
    private String Message;

    public SaveOperateLogResponse() {}

    public SaveOperateLogResponse(String requestId, String errorCode, String message) {
        super();
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
}
