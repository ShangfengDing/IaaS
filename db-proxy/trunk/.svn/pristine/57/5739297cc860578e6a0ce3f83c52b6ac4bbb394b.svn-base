package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.AdminURL;

@Entity
@Table(name="admin_url")
public class AdminURLTable extends AdminURL {
	public AdminURLTable() {
	
	}
	public AdminURLTable(AdminURL adminURL) {
		this.setDescription(adminURL.getDescription());
		this.setId(adminURL.getId());
		this.setResourceId(adminURL.getResourceId());
		this.setUrl(adminURL.getUrl());
	}
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	public Integer getId() {
		return super.getId();
	}
	public void setId(Integer id) {
		super.setId(id);
	}
	@Column(name="resource_id")
	public Integer getResourceId() {
		return super.getResourceId();
	}
	public void setResourceId(Integer resourceId) {
		// TODO Auto-generated method stub
		super.setResourceId(resourceId);
	}
	@Column(name="url")
	public String getUrl() {
		// TODO Auto-generated method stub
		return super.getUrl();
	}
	@Override
	public void setUrl(String url) {
		// TODO Auto-generated method stub
		super.setUrl(url);
	}
	@Column(name="description")
	public String getDescription() {
		// TODO Auto-generated method stub
		return super.getDescription();
	}
	@Override
	public void setDescription(String description) {
		// TODO Auto-generated method stub
		super.setDescription(description);
	}
}
