package appcloud.openapi.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;
import java.util.Set;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RouteInfoResponse")
public class RouteInfoResponse {

    private String RequestId;
    private String ErrorCode;
    private String Message;
    private Set<String> RegionIds;
    private List<Map<String, String>> ProductDomains;

    public RouteInfoResponse() {
    }

    public RouteInfoResponse(String requestId, Set<String> regionIds, List<Map<String, String>> productDomains) {
        RequestId = requestId;
        RegionIds = regionIds;
        ProductDomains = productDomains;
    }

    public RouteInfoResponse(String requestId, String errorCode, String message) {
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

    public Set<String> getRegionIds() {
        return RegionIds;
    }

    public void setRegionIds(Set<String> regionIds) {
        RegionIds = regionIds;
    }

    public List<Map<String, String>> getProductDomains() {
        return ProductDomains;
    }

    public void setProductDomains(List<Map<String, String>> productDomains) {
        ProductDomains = productDomains;
    }
}
