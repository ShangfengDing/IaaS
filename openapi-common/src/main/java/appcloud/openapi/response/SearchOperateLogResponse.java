package appcloud.openapi.response;

import appcloud.openapi.datatype.OperateLogItem;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by zouji on 2017/11/1.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SearchOperateLogResponse")
public class SearchOperateLogResponse extends BaseResponse{

    @XmlElementWrapper(name="OperateLogs")
    @XmlElement(name="OperateLog")
    private List<OperateLogItem> OperateLogs;
    private long TotalCount;
    private String RequestId;

    private String ErrorCode;
    private String Message;

    public SearchOperateLogResponse() {
        super();
    }

    public SearchOperateLogResponse(String requestId, String errorCode, String message) {
        super();
        RequestId = requestId;
        ErrorCode = errorCode;
        Message = message;
    }

    public SearchOperateLogResponse(String requestId, List<OperateLogItem> operateLogs,
                                    long totalCount) {
        super();
        RequestId = requestId;
        OperateLogs = operateLogs;
        TotalCount = totalCount;
    }

    public List<OperateLogItem> getOperateLogs() {
        return OperateLogs;
    }

    public void setOperateLogs(List<OperateLogItem> operateLogs) {
        OperateLogs = operateLogs;
    }

    public long getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(long totalCount) {
        TotalCount = totalCount;
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
