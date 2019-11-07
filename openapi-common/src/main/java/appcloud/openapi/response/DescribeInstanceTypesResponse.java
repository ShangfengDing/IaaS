package appcloud.openapi.response;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import appcloud.openapi.constant.Constants;
import appcloud.openapi.datatype.InstanceTypeSet;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DescribeInstanceTypesResponse")
public class DescribeInstanceTypesResponse {

	
	private String RequestId;
	private String ErrorCode;
	private String Message;
	private InstanceTypeSet InstanceTypes;
	
	public DescribeInstanceTypesResponse()
	{
		
	}
	
	public DescribeInstanceTypesResponse(String requestId, String errorCode, String message) {
		RequestId = requestId;
		ErrorCode = errorCode;
		Message = message;
	}
	
	public DescribeInstanceTypesResponse(String requestId, Map<String,Object> map) {
		RequestId = requestId;
		InstanceTypes = (InstanceTypeSet) map.get(Constants.INSTANCE_TYPES);
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

	public InstanceTypeSet getInstanceTypes() {
		return InstanceTypes;
	}

	public void setInstanceTypes(InstanceTypeSet instanceTypes) {
		InstanceTypes = instanceTypes;
	}
	
}
