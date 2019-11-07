package appcloud.dbproxy.mysql.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.AdminLog;

@Entity
@Table(name="admin_log")
public class AdminLogTable extends AdminLog {
	
	public AdminLogTable() {
		super();
	}
	
	public AdminLogTable(AdminLog adminLog) {
		this.setId(adminLog.getId());
		this.setAdminUsername(adminLog.getAdminUsername());
		this.setOperation(adminLog.getOperation());
		this.setReason(adminLog.getReason());
		this.setResult(adminLog.getResult());
		this.setTime(adminLog.getTime());
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
	
    @Column(name="admin_username")
	public String getAdminUsername() {
		return super.getAdminUsername();
	}
	public void setAdminUsername(String adminUsername) {
		super.setAdminUsername(adminUsername);
	}
	
	@Column(name="operation")
	public String getOperation() {
		return super.getOperation();
	}
	public void setOperation(String operation) {
		super.setOperation(operation);
	}
	
	@Column(name="reason")
	public String getReason() {
		return super.getReason();
	}
	public void setReason(String reason) {
		super.setReason(reason);
	}
	
	@Column(name="result")
	public String getResult() {
		return super.getResult();
	}
	public void setResult(String result) {
		super.setResult(result);
	}
	
	@Column(name="time")
	public Timestamp getTime() {
		return super.getTime();
	}
	public void setTime(Timestamp time) {
		super.setTime(time);
	}
}
