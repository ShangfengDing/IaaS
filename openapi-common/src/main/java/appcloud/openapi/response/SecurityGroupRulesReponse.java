package appcloud.openapi.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import appcloud.openapi.datatype.SecurityGroupRuleDetailItem;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DescribeSecurityGroupsResponse")
public class SecurityGroupRulesReponse {
	
	@XmlElementWrapper(name="Permissions")
	@XmlElement(name="Permission")
	private List<SecurityGroupRuleDetailItem> Rules;
	private long TotalCount;
	private String RequestId;
	private String SecurityGroupId;
	private String SecurityGroupName;
	private String Description;
	
	private String ErrorCode;
	private String Message;

	public SecurityGroupRulesReponse() {
		super();
	}

	public SecurityGroupRulesReponse(String requestId, String errorCode, String message) {
		super();
		RequestId = requestId;
		ErrorCode = errorCode;
		Message = message;
	}
	
	
	
	public SecurityGroupRulesReponse(String requestId, List<SecurityGroupRuleDetailItem> rules, 
			long totalCount) {
		super();
		RequestId = requestId;
		Rules = rules;
		TotalCount = totalCount;
	}
	
	public List<SecurityGroupRuleDetailItem> getRules() {
		return Rules;
	}

	public void setRules(List<SecurityGroupRuleDetailItem> rules) {
		Rules = rules;
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

	public String getSecurityGroupId() {
		return SecurityGroupId;
	}

	public void setSecurityGroupId(String securityGroupId) {
		SecurityGroupId = securityGroupId;
	}

	public String getSecurityGroupName() {
		return SecurityGroupName;
	}

	public void setSecurityGroupName(String securityGroupName) {
		SecurityGroupName = securityGroupName;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
	
}
