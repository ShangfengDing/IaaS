package appcloud.common.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


/**
 * @author lzc
 *	最高层次的应用信息
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class J2EEInfo{
	private Integer id;
	private Integer devId;			//开发者id
	private String name;			//应用名称
	private String description;		//应用描述
	private String domainPrefix;	//应用申请的外部域名前缀
	private String domainSuffix;	//应用的域名后缀
	
	private List<? extends RoutingEntry> routingEntries;
	private Developer developer;
	
	public J2EEInfo() {
		super();
	}
	
	public J2EEInfo(Integer id, Integer devId, String name, String description,
			String domainPrefix, String domainSuffix) {
		super();
		this.id = id;
		this.devId = devId;
		this.name = name;
		this.description = description;
		this.domainPrefix = domainPrefix;
		this.domainSuffix = domainSuffix;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDomainPrefix() {
		return domainPrefix;
	}
	public void setDomainPrefix(String domainPrefix) {
		this.domainPrefix = domainPrefix;
	}
	public void setDomainSuffix(String domainSuffix) {
		this.domainSuffix = domainSuffix;
	}
	public String getDomainSuffix() {
		return domainSuffix;
	}
	public void setRoutingEntries(List<? extends RoutingEntry> routingEntries){
		this.routingEntries = routingEntries;
	}
	public List<? extends RoutingEntry> getRoutingEntries(){
		return this.routingEntries;
	}
	
	public void setDeveloper(Developer dev){
		this.developer = dev;
	}
	
	public Developer getDeveloper(){
		return this.developer;
	}
	@Override
	public String toString() {
		return "J2EEInfo [id=" + id + ", devId=" + devId + ", name=" + name
				+ ", description=" + description + ", domainPrefix="
				+ domainPrefix + ", domainSuffix=" + domainSuffix
				+ "]";
	}

}
