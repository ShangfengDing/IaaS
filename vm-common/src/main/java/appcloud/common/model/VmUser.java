package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author XuanJiaxing
 * 
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class VmUser {
	private Integer preId;
	private Integer userId;
	private String userEmail;
	private Boolean active;
	private Integer groupId;
	private VmGroup vmGroup;
	private String appKeyId;
	private String appKeySecret;
	private Integer enterpriseId;
	public VmUser() {
		super();
	}
	public VmUser(Integer preId, Integer userId, String userEmail, Boolean active,
			Integer groupId, String appKeyId, String appKeySecret, Integer enterpriseId) {
		super();
		this.preId = preId;
		this.userId = userId;
		this.userEmail = userEmail;
		this.active = active;
		this.groupId = groupId;
		this.appKeyId = appKeyId;
		this.appKeySecret = appKeySecret;
		this.enterpriseId = enterpriseId;
	}

	public Integer getPreId() {
		return preId;
	}
	public void setPreId(Integer preId) {
		this.preId = preId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	
	public VmGroup getVmGroup() {
		return vmGroup;
	}
	public void setVmGroup(VmGroup vmGroup) {
		this.vmGroup = vmGroup;
	}
	
	public String getAppKeyId() {
		return appKeyId;
	}
	public void setAppKeyId(String appKeyId) {
		this.appKeyId = appKeyId;
	}
	public String getAppKeySecret() {
		return appKeySecret;
	}
	public void setAppKeySecret(String appKeySecret) {
		this.appKeySecret = appKeySecret;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public Integer getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	@Override
	public String toString() {
		return "VmUser [preId=" + preId + ", userId=" + userId + ", userEmail=" + userEmail + ", active=" + active + ", groupId="
				+ groupId + ", appKeyId=" + appKeyId + ", appKeySecret" + appKeySecret + ", enterpriseId=" + enterpriseId + "]";
	}
	
}
