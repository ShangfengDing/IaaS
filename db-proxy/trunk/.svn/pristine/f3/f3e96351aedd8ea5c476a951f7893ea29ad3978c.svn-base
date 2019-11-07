/**
 * File: J2EEAppTable.java
 * Author: weed
 * Create Time: 2012-11-25
 */
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

import appcloud.common.model.J2EEApp;

/**
 * @author weed
 *
 */
@Entity
@Table(name="j2ee_app")
public class J2EEAppTable extends J2EEApp {

	public J2EEAppTable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public J2EEAppTable(J2EEApp j2eeApp) {
		// TODO Auto-generated constructor stub
		this.setCreateTime(j2eeApp.getCreateTime());
		this.setDescription(j2eeApp.getDescription());
		this.setDevId(j2eeApp.getDevId());
		this.setDomainPrefix(j2eeApp.getDomainPrefix());
		this.setDomainSuffix(j2eeApp.getDomainSuffix());
		this.setId(j2eeApp.getId());
		this.setJ2eeInfoId(j2eeApp.getJ2eeInfoId());
		this.setMaxMemory(j2eeApp.getMaxMemory());
		this.setMinMemory(j2eeApp.getMinMemory());
		this.setName(j2eeApp.getName());
		this.setReleaseTime(j2eeApp.getReleaseTime());
		this.setResrcStrategyId(j2eeApp.getResrcStrategyId());
		this.setReviewInfo(j2eeApp.getReviewInfo());
		this.setStatus(j2eeApp.getStatus());
		this.setTestPrefix(j2eeApp.getTestPrefix());
		this.setTestNum(j2eeApp.getTestNum());
		this.setUuid(j2eeApp.getUuid());
		this.setVersion(j2eeApp.getVersion());
		this.setType(j2eeApp.getType());
		this.setVmIp(j2eeApp.getVmIp());
		this.setVmPort(j2eeApp.getVmPort());
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

    @Column(name="uuid")
	public String getUuid() {
		return super.getUuid();
	}

	public void setUuid(String uuid) {
		super.setUuid(uuid);
	}

    @Column(name="dev_id")
	public Integer getDevId() {
		return super.getDevId();
	}

	public void setDevId(Integer devId) {
		super.setDevId(devId);
	}

    @Column(name="j2ee_info_id")
	public Integer getJ2eeInfoId() {
		return super.getJ2eeInfoId();
	}

	public void setJ2eeInfoId(Integer j2eeInfoId) {
		super.setJ2eeInfoId(j2eeInfoId);
	}
	
    @Column(name="name")
	public String getName() {
		return super.getName();
	}

	public void setName(String name) {
		super.setName(name);
	}

    @Column(name="version")
	public Integer getVersion() {
		return super.getVersion();
	}

	public void setVersion(Integer version) {
		super.setVersion(version);
	}

    @Column(name="description")
	public String getDescription() {
		return super.getDescription();
	}

	public void setDescription(String description) {
		super.setDescription(description);
	}

    @Column(name="type")
	@Enumerated(EnumType.STRING)
	public J2EEAppTypeEnum getType() {
		return super.getType();
	}

	public void setType(J2EEAppTypeEnum type) {
		super.setType(type);
	}

    @Column(name="create_time")
	public Timestamp getCreateTime() {
		return super.getCreateTime();
	}

	public void setCreateTime(Timestamp createTime) {
		super.setCreateTime(createTime);
	}

    @Column(name="release_time")
	public Timestamp getReleaseTime() {
		return super.getReleaseTime();
	}

	public void setReleaseTime(Timestamp releaseTime) {
		super.setReleaseTime(releaseTime);
	}

    @Column(name="status")
	@Enumerated(EnumType.STRING)
	public J2EEAppStatusEnum getStatus() {
		return super.getStatus();
	}

	public void setStatus(J2EEAppStatusEnum status) {
		super.setStatus(status);
	}

    @Column(name="review_info")
	public String getReviewInfo() {
		return super.getReviewInfo();
	}

	public void setReviewInfo(String reviewInfo) {
		super.setReviewInfo(reviewInfo);
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

    @Column(name="test_prefix")
	public String getTestPrefix() {
		return super.getTestPrefix();
	}

	public void setTestPrefix(String testPrefix) {
		super.setTestPrefix(testPrefix);
	}

    @Column(name="test_num")
	public Integer getTestNum() {
		return super.getTestNum();
	}

	public void setTestNum(Integer testNum) {
		super.setTestNum(testNum);
	}

    @Column(name="resrc_strategy_id")
	public Integer getResrcStrategyId() {
		return super.getResrcStrategyId();
	}

	public void setResrcStrategyId(Integer resrcStrategyId) {
		super.setResrcStrategyId(resrcStrategyId);
	}
	
    @Column(name="min_memory")
	public Integer getMinMemory() {
		return super.getMinMemory();
	}

	public void setMinMemory(Integer minMemory) {
		super.setMinMemory(minMemory);
	}

    @Column(name="max_memory")
	public Integer getMaxMemory() {
		return super.getMaxMemory();
	}

	public void setMaxMemory(Integer maxMemory) {
		super.setMaxMemory(maxMemory);
	}
	
	@Column(name="vm_ip")
	public String getVmIp() {
		return super.getVmIp();
	}

	public void setVmIp(String vmIp) {
		super.setVmIp(vmIp);
	}

    @Column(name="vm_port")
	public Integer getVmPort() {
		return super.getVmPort();
	}

	public void setVmPort(Integer vmPort) {
		super.setVmPort(vmPort);
	}

}
