package appcloud.openapi.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "BackUpVmResponse")
public class BackUpVmResponse {

    private String RequestId;
    private String ErrorCode;
    private String Message;
    private boolean BackUpResult;

    public BackUpVmResponse() {
    }

    public BackUpVmResponse(String requestId, boolean backUpResult) {
        RequestId = requestId;
        BackUpResult = backUpResult;
    }

    public BackUpVmResponse(String requestId, String errorCode, String message) {
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

    public boolean isBackUpResult() {
        return BackUpResult;
    }
    public void setBackUpResult(boolean backUpResult) {
        BackUpResult = backUpResult;
    }
}
