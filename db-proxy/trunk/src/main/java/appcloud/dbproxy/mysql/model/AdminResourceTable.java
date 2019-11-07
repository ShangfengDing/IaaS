package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.AdminResource;

@Entity
@Table(name="admin_resource")
public class AdminResourceTable extends AdminResource{
	public AdminResourceTable() {
		
	}
	public AdminResourceTable(AdminResource adminResource) {
		this.setId(adminResource.getId());
		this.setTopBarId(adminResource.getTopBarId());
		this.setLeftBarId(adminResource.getLeftBarId());
		this.setTopBarName(adminResource.getTopBarName());
		this.setLeftBarName(adminResource.getLeftBarName());
		this.setAdminURLs(adminResource.getAdminURLs());
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

	@Column(name="top_bar_id")
	public Integer getTopBarId() {
		return super.getTopBarId();
	}
	public void setTopBarId(Integer topBarId) {
		super.setTopBarId(topBarId);
	}

	@Column(name="left_bar_id")
	public Integer getLeftBarId() {
		return super.getLeftBarId();
	}
	public void setLeftBarId(Integer leftBarId) {
		super.setLeftBarId(leftBarId);
	}

	@Column(name="top_bar_name")
	public String getTopBarName() {
		return super.getTopBarName();
	}
	public void setTopBarName(String topBarName) {
		super.setTopBarName(topBarName);
	}

	@Column(name="left_bar_name")
	public String getLeftBarName() {
		return super.getLeftBarName();
	}
	public void setLeftBarName(String leftBarName) {
		super.setLeftBarName(leftBarName);
	}
}
