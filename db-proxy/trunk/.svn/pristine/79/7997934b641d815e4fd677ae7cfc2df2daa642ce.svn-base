package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.VmUser;

@Entity
@Table(name="vm_user")
public class VmUserTable extends VmUser{

	public VmUserTable() {
		super();
	}
	public VmUserTable(VmUser user) {
		this.setPreId(user.getPreId());
		this.setUserId(user.getUserId());
		this.setUserEmail(user.getUserEmail());
		this.setActive(user.getActive());
		this.setGroupId(user.getGroupId());
		this.setAppKeyId(user.getAppKeyId());
		this.setAppKeySecret(user.getAppKeySecret());
		this.setEnterpriseId(user.getEnterpriseId());
	}
	@Column(name="pre_id")
	public Integer getPreId() {
		return super.getPreId();
	}
	public void setPreId(Integer preId) {
		super.setPreId(preId);
	}

	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getUserId() {
		return super.getUserId();
	}
	public void setUserId(Integer userId) {
		super.setUserId(userId);
	}
	
	@Column(name="user_email")
	public String getUserEmail() {
		return super.getUserEmail();
	}
	public void setUserEmail(String userEmail) {
		super.setUserEmail(userEmail);
	}
	
	@Column(name="is_active")
	public Boolean getActive() {
		return super.getActive();
	}
	public void setActive(Boolean active) {
		super.setActive(active);
	}
	
	@Column(name="group_id")
	public Integer getGroupId() {
		return super.getGroupId();
	}
	public void setGroupId(Integer groupId) {
		super.setGroupId(groupId);
	}

	@Column(name="appkey_id")
	public String getAppKeyId() {
		return super.getAppKeyId();
	}
	public void setAppKeyId(String appKeyId) {
		super.setAppKeyId(appKeyId);
	}

	@Column(name="appkey_secret")
	public String getAppKeySecret() {
		return super.getAppKeySecret();
	}
	public void setAppKeySecret(String appKeySecret) {
		super.setAppKeySecret(appKeySecret);
	}

	@Column(name="enterprise_id") 
	public Integer getEnterpriseId() {
		return super.getEnterpriseId();
	}
	public void setEnterpriseId(Integer enterpriseId) {
		super.setEnterpriseId(enterpriseId);
	}
}
