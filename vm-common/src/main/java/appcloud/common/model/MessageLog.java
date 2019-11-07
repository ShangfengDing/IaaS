package appcloud.common.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * 各个模块的操作记录
 * 
 * @author huahui
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class MessageLog {

	public MessageLog() {
		super();
	}

	// example:volume-provider
	private ModuleEnum module;

	// uuid
	private String tranctionId;

	// who has done this operation
	private String userId;
	
	private String vmUuid;

	// example: createVm, stopVm
	private String operateDrpt;

	// log content
	private String logContent;

	// example:MessageLog.class
	private String sourceClass;

	// the module in which machine(ip)
	private String ipAddress;

	// example: info warn error
	private LogLevelEnum logLevel;
	
	private Timestamp logTime;

	public Timestamp getLogTime() {
		return logTime;
	}

	public void setLogTime(Timestamp logTime) {
		this.logTime = logTime;
	}

	public static enum LogLevelEnum {
		INFO, WARN, ERROR, DEBUG;

		public String toString() {
			return super.toString().toLowerCase();
		}
	}

	public static enum ModuleEnum {
		RESOURCE_SCHEDULER, VOLUME_SCHEDULER, VOLUME_PROVIDER, VM_SCHDULER, VM_CONTROLLER, IMAGE_SERVER,
		NODE_AGENT, NODE_MONITOR, NETWORK_PROVIDER, DHCP_CONTROLLER, LOL_SERVER, API_SERVER, VM_FRONT, 
		VM_ADMIN, IAAS_CHECK, UN_KNOWN;

		
		public String toString() {
			return super.toString().toLowerCase();
		}
		
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ipAddress == null) ? 0 : ipAddress.hashCode());
		result = prime * result
				+ ((logContent == null) ? 0 : logContent.hashCode());
		result = prime * result
				+ ((logLevel == null) ? 0 : logLevel.hashCode());
		result = prime * result + ((module == null) ? 0 : module.hashCode());
		result = prime * result
				+ ((operateDrpt == null) ? 0 : operateDrpt.hashCode());
		result = prime * result
				+ ((sourceClass == null) ? 0 : sourceClass.hashCode());
		result = prime * result
				+ ((tranctionId == null) ? 0 : tranctionId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageLog other = (MessageLog) obj;
		if (ipAddress == null) {
			if (other.ipAddress != null)
				return false;
		} else if (!ipAddress.equals(other.ipAddress))
			return false;
		if (logContent == null) {
			if (other.logContent != null)
				return false;
		} else if (!logContent.equals(other.logContent))
			return false;
		if (logLevel == null) {
			if (other.logLevel != null)
				return false;
		} else if (!logLevel.equals(other.logLevel))
			return false;
		if (module == null) {
			if (other.module != null)
				return false;
		} else if (!module.equals(other.module))
			return false;
		if (operateDrpt == null) {
			if (other.operateDrpt != null)
				return false;
		} else if (!operateDrpt.equals(other.operateDrpt))
			return false;
		if (sourceClass == null) {
			if (other.sourceClass != null)
				return false;
		} else if (!sourceClass.equals(other.sourceClass))
			return false;
		if (tranctionId == null) {
			if (other.tranctionId != null)
				return false;
		} else if (!tranctionId.equals(other.tranctionId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	/**
	 * @param module  ModuleEnum
	 * @param tranctionId example:uuid
	 * @param userId
	 * @param operateDrpt
	 * @param logContent
	 * @param sourceClass example:MessageLog.class
	 * @param ipAddress
	 * @param logLevel LogLevelEnum
	 */
	public MessageLog(ModuleEnum module, String tranctionId, String userId, String vmUuid,
			String operateDrpt, String logContent, String sourceClass,
			String ipAddress, LogLevelEnum logLevel) {
		super();
		this.module = module;
		this.tranctionId = tranctionId;
		this.userId = userId;
		this.vmUuid = vmUuid;
		this.operateDrpt = operateDrpt;
		this.logContent = logContent;
		this.sourceClass = sourceClass;
		this.ipAddress = ipAddress;
		this.logLevel = logLevel;
	}
	
	public MessageLog(ModuleEnum module, String tranctionId, String userId,
			String operateDrpt, String logContent, String sourceClass,
			String ipAddress, LogLevelEnum logLevel) {
		super();
		this.module = module;
		this.tranctionId = tranctionId;
		this.userId = userId;
		this.operateDrpt = operateDrpt;
		this.logContent = logContent;
		this.sourceClass = sourceClass;
		this.ipAddress = ipAddress;
		this.logLevel = logLevel;
	}

	@Override
	public String toString() {
		return "MessageLog [module=" + module + ", tranctionId=" + tranctionId
				+ ", userId=" + userId+ ", vmUuid=" + vmUuid + ", operateDrpt=" + operateDrpt
				+ ", logContent=" + logContent + ", sourceClass=" + sourceClass
				+ ", ipAddress=" + ipAddress + ", logLevel=" + logLevel
				+ ", logTime=" + logTime + "]";
	}
	
	public String toEmailString() {
		return "<table><tr>云海系统警告内容 ,请相关人员注意处理:</tr><tr>模块:" + module + "</tr><tr>事务ID:" + tranctionId
				+ "</tr><tr>用户ID:" + userId+ "</tr><tr>虚拟机/硬盘标识:" + vmUuid + "</tr><tr>操作:" + operateDrpt
				+ "</tr><tr>日志内容:" + logContent + "</tr><tr>操作源:" + sourceClass
				+ "</tr><tr>IP地址:" + ipAddress + "</tr><tr>日志级别:" + logLevel
				+ "</tr><tr>日志时间:" + logTime + "</tr></table>";
	}

	public ModuleEnum getModule() {
		return module;
	}

	public void setModule(ModuleEnum module) {
		this.module = module;
	}

	public String getTranctionId() {
		return tranctionId;
	}

	public void setTranctionId(String tranctionId) {
		this.tranctionId = tranctionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOperateDrpt() {
		return operateDrpt;
	}

	public void setOperateDrpt(String operateDrpt) {
		this.operateDrpt = operateDrpt;
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	public String getSourceClass() {
		return sourceClass;
	}

	public void setSourceClass(String sourceClass) {
		this.sourceClass = sourceClass;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public LogLevelEnum getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(LogLevelEnum logLevel) {
		this.logLevel = logLevel;
	}

	public String getVmUuid() {
		return vmUuid;
	}

	public void setVmUuid(String vmUuid) {
		this.vmUuid = vmUuid;
	}

}
