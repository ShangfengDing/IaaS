package appcloud.api.beans;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

@XmlRootElement(name="message_log")
public class AcMessageLog {
	@XmlAttribute
	public AcModuleEnum module;
	@XmlAttribute
	public String transactionId;
	@XmlAttribute
	public String userId;
	@XmlAttribute
	public String vmHdUuid;
	@XmlAttribute
	public String operateDrpt;
	@XmlAttribute
	public String logContent;
	@XmlAttribute
	public String sourceClass;
	@XmlAttribute
	public String ipAddress;
	@XmlAttribute
	public AcLogLevelEnum logLevel;
	@XmlAttribute
	public Date logTime;
	
	public AcMessageLog() {
		super();
	}

	public AcMessageLog(AcModuleEnum module, String transactionId,
			String userId, String vmHdUuid, String operateDrpt, String logContent,
			String sourceClass, String ipAddress, AcLogLevelEnum logLevel,
			Date logTime) {
		super();
		this.module = module;
		this.transactionId = transactionId;
		this.userId = userId;
		this.vmHdUuid = vmHdUuid;
		this.operateDrpt = operateDrpt;
		this.logContent = logContent;
		this.sourceClass = sourceClass;
		this.ipAddress = ipAddress;
		this.logLevel = logLevel;
		this.logTime = logTime;
	}

	@Override
	public String toString() {
		return "AcMessageLog [module=" + module + ", transactionId="
				+ transactionId + ", userId=" + userId + ", vmHdUuid=" + vmHdUuid
				+ ", operateDrpt=" + operateDrpt + ", logContent=" + logContent
				+ ", sourceClass=" + sourceClass + ", ipAddress=" + ipAddress
				+ ", logLevel=" + logLevel + ", logTime=" + logTime + "]";
	}
	
}
