package appcloud.openapi.response;

import appcloud.openapi.datatype.HostDetailItem;
import appcloud.openapi.datatype.ServiceDetailItem;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by lizhenhao on 2016/11/17.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "HostsResponse")
public class HostsResponse {
    @XmlElementWrapper(name="Hosts")
    @XmlElement(name="Hosts")
    private List<HostDetailItem> Hosts;
    private long TotalCount;
    private String RequestId;
    private String PageNum;
    private String PageSize;

    private String ErrorCode;
    private String Message;

    public HostsResponse() {
        super();
    }

    public HostsResponse(String requestId, String errorCode, String message) {
        RequestId = requestId;
        ErrorCode = errorCode;
        Message = message;
    }

    public HostsResponse(List<HostDetailItem> hosts, long totalCount, String requestId, String pageNum, String pageSize) {
        Hosts = hosts;
        TotalCount = totalCount;
        RequestId = requestId;
        PageNum = pageNum;
        PageSize = pageSize;
    }

    public List<HostDetailItem> getHosts() {
        return Hosts;
    }

    public void setHosts(List<HostDetailItem> hosts) {
        Hosts = hosts;
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
