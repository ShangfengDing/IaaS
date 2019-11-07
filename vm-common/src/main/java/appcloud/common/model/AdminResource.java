package appcloud.common.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class AdminResource {
	private Integer id;
	private Integer topBarId; //0-->系统管理   1-->用户管理  2-->主机管理  3-->硬盘管理  4-->财务管理  5-->日志管理
	private Integer leftBarId; //
	private String topBarName;
	private String leftBarName;
	private List<AdminURL> adminURLs;
	
	public AdminResource() {
		super();
	}
	
	public AdminResource(Integer id, Integer topBarId, Integer leftBarId,
			String topBarName, String leftBarName, List<AdminURL> adminURLs) {
		super();
		this.id = id;
		this.topBarId = topBarId;
		this.leftBarId = leftBarId;
		this.topBarName = topBarName;
		this.leftBarName = leftBarName;
		this.adminURLs = adminURLs;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTopBarId() {
		return topBarId;
	}
	public void setTopBarId(Integer topBarId) {
		this.topBarId = topBarId;
	}
	public Integer getLeftBarId() {
		return leftBarId;
	}
	public void setLeftBarId(Integer leftBarId) {
		this.leftBarId = leftBarId;
	}
	public String getTopBarName() {
		return topBarName;
	}
	public void setTopBarName(String topBarName) {
		this.topBarName = topBarName;
	}
	public String getLeftBarName() {
		return leftBarName;
	}
	public void setLeftBarName(String leftBarName) {
		this.leftBarName = leftBarName;
	}

	public List<AdminURL> getAdminURLs() {
		return adminURLs;
	}

	public void setAdminURLs(List<AdminURL> adminURLs) {
		this.adminURLs = adminURLs;
	}

	@Override
	public String toString() {
		return "AdminResource [id=" + id + ", topBarId=" + topBarId
				+ ", leftBarId=" + leftBarId + ", topBarName=" + topBarName
				+ ", leftBarName=" + leftBarName + ", adminURLs=" + adminURLs
				+ "]";
	}
}
