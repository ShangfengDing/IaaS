package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author lzc
 * VM信息，继承自App
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class VMApp {
	private Integer id;
	private Integer devId;		//开发者id
	private String uuid;		//应用全局唯一标识
	private String tag;			//用户自定义名称
	private Integer templateId;	//模板id
	private Integer systemId;	//系统id
	private Integer menuId;		//cpu/mem套餐信息
	private String ip;			//ip地址
	private String mac;			//分配的mac地址
//	private VMAppStatusEnum status;//状态
	private Integer state;		//状态
	private String stateInfo;	//状态说明
	
	private Developer developer;//开发者详细信息
	private Instance instance;	//应用的实例信息
	
	public VMApp() {
		super();
	}

	public VMApp(Integer id, Integer devId, String uuid, String tag,
			Integer templateId, Integer systemId, Integer menuId, String ip,
			String mac, Integer state, String stateInfo) {
		super();
		this.id = id;
		this.devId = devId;
		this.uuid = uuid;
		this.tag = tag;
		this.templateId = templateId;
		this.systemId = systemId;
		this.menuId = menuId;
		this.ip = ip;
		this.mac = mac;
		this.state = state;
		this.stateInfo = stateInfo;
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

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getSystemId() {
		return systemId;
	}

	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public Developer getDeveloper() {
		return developer;
	}

	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}

	public Instance getInstance() {
		return instance;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}

	@Override
	public String toString() {
		return "VMApp [id=" + id + ", devId=" + devId + ", uuid=" + uuid
				+ ", tag=" + tag + ", templateId=" + templateId + ", systemId="
				+ systemId + ", menuId=" + menuId + ", ip=" + ip + ", mac="
				+ mac + ", state=" + state + ", stateInfo=" + stateInfo
				+ ", developer=" + developer + ", instance=" + instance + "]";
	}

//	public static enum VMAppStatusEnum {
//		RUNNING, SHUTDOWN, HANGED, DELETED, ERROR;
//
//		public String toString() {
//			switch (this) {
//			case RUNNING:
//				return "运行中";
//			case SHUTDOWN:
//				return "关机中";
//			case HANGED:
//				return "挂起";
//			case DELETED:
//				return "已删除";
//			case ERROR:
//				return "处理发生错误，请尝试重启虚拟机。如仍无法启动请联系管理员";
//			}
//			return super.toString();
//		}
//	}
}
