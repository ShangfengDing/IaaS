package appcloud.openapi.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import appcloud.openapi.datatype.InstanceAttributes;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DescribeInstancesResponse")
public class DescribeInstancesResponse extends BaseResponse {

	@XmlElementWrapper(name="Instances")
	@XmlElement(name="Instance")
	private List<InstanceAttributes> Instances;
	private Long TotalCount;
	private Integer PageNumber;
	private Integer PageSize;
	
	public DescribeInstancesResponse() {
		super();
	}
	public DescribeInstancesResponse(String requestId, String errorCode, String message) {
		super(requestId, errorCode, message);
	}
	public DescribeInstancesResponse(String requestId, List<InstanceAttributes> instances, Long totalCount, Integer pageNum, Integer pageSize) {
		super(requestId);
		this.Instances = instances;
		this.TotalCount = totalCount;
		this.PageNumber = pageNum;
		this.PageSize = pageSize;
	}
	
	
	public List<InstanceAttributes> getInstances() {
		return Instances;
	}
	public void setInstances(List<InstanceAttributes> instances) {
		Instances = instances;
	}
	public Long getTotalCount() {
		return TotalCount;
	}
	public void setTotalCount(Long totalCount) {
		TotalCount = totalCount;
	}
	public Integer getPageNumber() {
		return PageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		PageNumber = pageNumber;
	}
	public Integer getPageSize() {
		return PageSize;
	}
	public void setPageSize(Integer pageSize) {
		PageSize = pageSize;
	}
	
	
	
	
}
