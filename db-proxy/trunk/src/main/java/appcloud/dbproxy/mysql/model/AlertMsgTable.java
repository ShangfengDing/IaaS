package appcloud.dbproxy.mysql.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.AlertMsg;
import appcloud.common.model.AlertMsg.AlertMsgStatus;

@Entity
@Table(name="alert_msg")
public class AlertMsgTable extends AlertMsg {
	
	public AlertMsgTable() {
		super();
	}
	
	public AlertMsgTable(AlertMsg alertMsg) {
		this.setId(alertMsg.getId());
		this.setMsg(alertMsg.getMsg());
		this.setTime(alertMsg.getTime());
		this.setModule(alertMsg.getModule());
		this.setDetail(alertMsg.getDetail());
		this.setStatus(alertMsg.getStatus());
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
	
	@Column(name="msg")
	public String getMsg() {
		return super.getMsg();
	}
	public void setMsg(String msg) {
		super.setMsg(msg);
	}
	
	@Column(name="time")
	public Timestamp getTime() {
		return super.getTime();
	}
	public void setTime(Timestamp time) {
		super.setTime(time);
	}
	
	@Column(name="module")
	public String getModule() {
		return super.getModule();
	}

	public void setModule(String module) {
		super.setModule(module);
	}

	@Column(name="detail")
	public String getDetail() {
		return super.getDetail();
	}

	public void setDetail(String detail) {
		super.setDetail(detail);
	}

	@Column(name="status")
    @Enumerated(EnumType.STRING)
	public AlertMsgStatus getStatus() {
		return super.getStatus();
	}

	public void setStatus(AlertMsgStatus status) {
		super.setStatus(status);
	}
}
