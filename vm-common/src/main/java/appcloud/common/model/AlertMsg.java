package appcloud.common.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author lzc
 * 告警信息记录
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class AlertMsg {

	private Integer id;
	private String module;
	private String msg;		//告警详情
	private String detail;
	private Timestamp time;	//操作时间
	private AlertMsgStatus status;
	
	public AlertMsg() {
		super();
	}
	
	public AlertMsg(String module, String msg, String detail, Timestamp time,
			AlertMsgStatus status) {
		super();
		this.module = module;
		this.msg = msg;
		this.detail = detail;
		this.time = time;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public AlertMsgStatus getStatus() {
		return status;
	}

	public void setStatus(AlertMsgStatus status) {
		this.status = status;
	}

	public enum AlertMsgStatus{
		ALERTED, INFOED, PROCESSED;

		@Override
		public String toString() {
			switch (this) {
			case ALERTED:
				return "已告警";
			case INFOED:
				return "已通知";
			case PROCESSED:
				return "已处理";
		}
			return super.toString();
		}
	}

	@Override
	public String toString() {
		return "AlertMsg [id=" + id + ", module=" + module + ", msg=" + msg
				+ ", detail=" + detail + ", time=" + time + ", status="
				+ status + "]";
	}
}
