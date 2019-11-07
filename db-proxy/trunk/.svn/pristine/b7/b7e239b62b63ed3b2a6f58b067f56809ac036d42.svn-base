/**
 * File: J2EEInfoTable.java
 * Author: weed
 * Create Time: 2012-11-25
 */
package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.J2EEInfo;

/**
 * @author weed
 *
 */
@Entity
@Table(name="j2ee_info")
public class J2EEInfoTable extends J2EEInfo {

	public J2EEInfoTable() {
		super();
	}

	public J2EEInfoTable(J2EEInfo j2eeInfo) {
		this.setId(j2eeInfo.getId());
		this.setDevId(j2eeInfo.getDevId());
		this.setName(j2eeInfo.getName());
		this.setDescription(j2eeInfo.getDescription());
		this.setDomainPrefix(j2eeInfo.getDomainPrefix());
		this.setDomainSuffix(j2eeInfo.getDomainSuffix());
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
	
    @Column(name="dev_id")
	public Integer getDevId() {
		return super.getDevId();
	}
	public void setDevId(Integer devId) {
		super.setDevId(devId);
	}
	
    @Column(name="name")
	public String getName() {
		return super.getName();
	}
	public void setName(String name) {
		super.setName(name);
	}
	
    @Column(name="description")
	public String getDescription() {
		return super.getDescription();
	}
	public void setDescription(String description) {
		super.setDescription(description);
	}
	
    @Column(name="domain_prefix")
	public String getDomainPrefix() {
		return super.getDomainPrefix();
	}
	public void setDomainPrefix(String domainPrefix) {
		super.setDomainPrefix(domainPrefix);
	}
	
    @Column(name="domain_suffix")
	public String getDomainSuffix() {
		return super.getDomainSuffix();
	}
	public void setDomainSuffix(String domainSuffix) {
		super.setDomainSuffix(domainSuffix);
	}
	
}
