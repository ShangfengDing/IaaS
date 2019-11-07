package appcloud.common.model;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author lzc J2EE应用信息
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class J2EEApp {
	//TODO:这实际代表的是应用版本信息，待抽象出一层App，让J2EEApp和VMApp分别继承自它；原来的VMApp只是虚拟机的业务信息，可改为VMInfo
	private Integer id;
	private String uuid;	//应用全局唯一标识：若是j2ee应用，则为j2ee标识；否则为虚拟机的uuid
	private Integer devId;	//开发者id
	private Integer j2eeInfoId;	// 所属大应用j2eeInfo的id
	private String name;		// 应用名称
	private Integer version; 	// 应用版本
	private String description; // 版本描述
	private J2EEAppTypeEnum type;	//版本类型
	private Timestamp createTime; 	// 创建时间
	private Timestamp releaseTime; 	// 通过审核，发布时间
	private J2EEAppStatusEnum status;// 状态信息
	private String reviewInfo; 		// 审核信息
	private String domainPrefix; 	// 域名前缀
	private String domainSuffix; 	// 域名后缀
	private String testPrefix; 		// 测试实例的域名前缀
	private Integer testNum; 		// 获得测试实例的次数
	
	/*以下三项是j2ee应用需要的*/
	private Integer resrcStrategyId; // 资源策略id
	private Integer minMemory; 		// 最小内存
	private Integer maxMemory; 		// 最大内存
	/*以下两项是虚拟机应用需要的*/
	private String vmIp;			// 虚拟机IP
	private Integer vmPort;			// 虚拟机端口

	private Developer developer;	//开发者详细信息
	private List<Instance> instances;//应用的实例信息
	private Integer instanceNum; 	// 实例数

	public J2EEApp() {
		super();
	}

	public J2EEApp(Integer id, String uuid, Integer devId, Integer j2eeInfoId,
			String name, Integer version, String description,
			J2EEAppTypeEnum type, Timestamp createTime, Timestamp releaseTime,
			J2EEAppStatusEnum status, String reviewInfo, String domainPrefix,
			String domainSuffix, String testPrefix, Integer testNum,
			Integer resrcStrategyId, Integer minMemory, Integer maxMemory,
			String vmIp, Integer vmPort) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.devId = devId;
		this.j2eeInfoId = j2eeInfoId;
		this.name = name;
		this.version = version;
		this.description = description;
		this.type = type;
		this.createTime = createTime;
		this.releaseTime = releaseTime;
		this.status = status;
		this.reviewInfo = reviewInfo;
		this.domainPrefix = domainPrefix;
		this.domainSuffix = domainSuffix;
		this.testPrefix = testPrefix;
		this.testNum = testNum;
		this.resrcStrategyId = resrcStrategyId;
		this.minMemory = minMemory;
		this.maxMemory = maxMemory;
		this.vmIp = vmIp;
		this.vmPort = vmPort;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDevId() {
		return devId;
	}

	public void setDevId(Integer devId) {
		this.devId = devId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getJ2eeInfoId() {
		return j2eeInfoId;
	}

	public void setJ2eeInfoId(Integer j2eeInfoId) {
		this.j2eeInfoId = j2eeInfoId;
	}

	public Integer getResrcStrategyId() {
		return resrcStrategyId;
	}

	public void setResrcStrategyId(Integer resrcStrategyId) {
		this.resrcStrategyId = resrcStrategyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Timestamp releaseTime) {
		this.releaseTime = releaseTime;
	}

	public J2EEAppStatusEnum getStatus() {
		return status;
	}

	public void setStatus(J2EEAppStatusEnum status) {
		this.status = status;
	}

	public String getReviewInfo() {
		return reviewInfo;
	}

	public void setReviewInfo(String reviewInfo) {
		this.reviewInfo = reviewInfo;
	}

	public String getDomainPrefix() {
		return domainPrefix;
	}

	public void setDomainPrefix(String domainPrefix) {
		this.domainPrefix = domainPrefix;
	}

	public String getDomainSuffix() {
		return domainSuffix;
	}

	public void setDomainSuffix(String domainSuffix) {
		this.domainSuffix = domainSuffix;
	}
	
	public String getTestPrefix() {
		return testPrefix;
	}

	public void setTestPrefix(String testPrefix) {
		this.testPrefix = testPrefix;
	}

	public Integer getTestNum() {
		return testNum;
	}

	public void setTestNum(Integer testNum) {
		this.testNum = testNum;
	}

	public Integer getMinMemory() {
		return minMemory;
	}

	public void setMinMemory(Integer minMemory) {
		this.minMemory = minMemory;
	}

	public Integer getMaxMemory() {
		return maxMemory;
	}

	public void setMaxMemory(Integer maxMemory) {
		this.maxMemory = maxMemory;
	}

	public J2EEAppTypeEnum getType() {
		return type;
	}

	public void setType(J2EEAppTypeEnum type) {
		this.type = type;
	}

	public String getVmIp() {
		return vmIp;
	}

	public void setVmIp(String vmIp) {
		this.vmIp = vmIp;
	}

	public Integer getVmPort() {
		return vmPort;
	}

	public void setVmPort(Integer vmPort) {
		this.vmPort = vmPort;
	}

	public Developer getDeveloper() {
		return developer;
	}

	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}

	public List<Instance> getInstances() {
		return instances;
	}

	public void setInstances(List<Instance> instances) {
		this.instances = instances;
	}

	public Integer getInstanceNum() {
		return instanceNum;
	}

	public void setInstanceNum(Integer instanceNum) {
		this.instanceNum = instanceNum;
	}

	@Override
	public String toString() {
		return "J2EEApp [id=" + id + ", uuid=" + uuid + ", devId=" + devId
				+ ", j2eeInfoId=" + j2eeInfoId + ", name=" + name
				+ ", version=" + version + ", description=" + description
				+ ", type=" + type + ", createTime=" + createTime
				+ ", releaseTime=" + releaseTime + ", status=" + status
				+ ", reviewInfo=" + reviewInfo + ", domainPrefix="
				+ domainPrefix + ", domainSuffix=" + domainSuffix
				+ ", testPrefix=" + testPrefix + ", testNum=" + testNum
				+ ", resrcStrategyId=" + resrcStrategyId + ", minMemory="
				+ minMemory + ", maxMemory=" + maxMemory + ", vmIp=" + vmIp
				+ ", vmPort=" + vmPort + ", developer=" + developer
				+ ", instances=" + instances + ", instanceNum=" + instanceNum
				+ "]";
	}

	public static enum J2EEAppStatusEnum {
		NOT_UPLOADED, UPLOADED_NOT_SUBMITTED, SUBMMITED_NOT_VERIFIED, VERIFING, VERIFIED, NOT_VERIFIED, STOPPED;

		public String toString() {
			switch (this) {
			case NOT_UPLOADED:
				return "未上传";
			case UPLOADED_NOT_SUBMITTED:
				return "已上传未提交";
			case SUBMMITED_NOT_VERIFIED:
				return "已提交待审核";
			case VERIFING:
				return "审核中";
			case VERIFIED:
				return "审核通过";
			case NOT_VERIFIED:
				return "审核未通过";
			case STOPPED:
				return "停止";
			}
			return super.toString();
		}
	}
	
	public static enum J2EEAppTypeEnum {
		J2EE, VM;

		public String toString() {
			switch (this) {
			case J2EE:
				return "J2EE";
			case VM:
				return "VM";
			}
			return super.toString();
		}
	}
}
