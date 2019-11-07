package appcloud.openapi.response;

import appcloud.openapi.datatype.ServiceDetailItem;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by lizhenhao on 2016/11/16.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ServicesResponse")
public class ServicesResponse {

    @XmlElementWrapper(name="Servers")
    @XmlElement(name="Servers")
    private List<ServiceDetailItem> Services;
    private long TotalCount; //服务暂时没有用到此量，默认为0
    private String RequestId;
    private String PageNum;
    private String PageSize;

    private String ErrorCode;
    private String Message;

    public ServicesResponse() {
    }


    public ServicesResponse(String requestId, String errorCode, String message) {
        RequestId = requestId;
        ErrorCode = errorCode;
        Message = message;
    }

    public ServicesResponse(List<ServiceDetailItem> servers, long totalCount, String requestId, String pageNum, String pageSize) {
        Services = servers;
        TotalCount = totalCount;
        RequestId = requestId;
        PageNum = pageNum;
        PageSize = pageSize;
    }

    public List<ServiceDetailItem> getServers() {
        return Services;
    }

    public void setServers(List<ServiceDetailItem> servers) {
        Services = servers;
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

    public String getPageNum() {
        return PageNum;
    }

    public void setPageNum(String pageNum) {
        PageNum = pageNum;
    }

    public String getPageSize() {
        return PageSize;
    }

    public void setPageSize(String pageSize) {
        PageSize = pageSize;
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
