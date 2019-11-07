package appcloud.common.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author lzc
 * 管理员操作日志记录
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class AdminLog {
	private Integer id;
	private String adminUsername;	//管理员用户名
	private String operation;		//操作详情
	private String reason;			//操作原因
	private String result;			//操作结果
	private Timestamp time;			//操作时间
	
	public AdminLog() {
		super();
	}
	
	public AdminLog(Integer id, String adminUsername, String operation,
			String reason, String result, Timestamp time) {
		super();
		this.id = id;
		this.adminUsername = adminUsername;
		this.operation = operation;
		this.reason = reason;
		this.result = result;
		this.time = time;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAdminUsername() {
		return adminUsername;
	}
	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "AdminLog [id=" + id + ", adminUsername=" + adminUsername
				+ ", operation=" + operation + ", reason=" + reason
				+ ", result=" + result + ", time=" + time + "]";
	}
	
}
