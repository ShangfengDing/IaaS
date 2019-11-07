/**
 * File: NginxTable.java
 * Author: weed
 * Create Time: 2012-11-24
 */
package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.Nginx;

/**
 * @author weed
 *
 */
@Entity
@Table(name="nginx")
public class NginxTable extends Nginx {
	
	public NginxTable() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public NginxTable(Nginx nginx) {
		// TODO Auto-generated constructor stub
		this.setExtend(nginx.getExtend());
		this.setExternalIp(nginx.getExternalIp());
		this.setId(nginx.getId());
		this.setInnerIp(nginx.getInnerIp());
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
	
	@Column(name="external_ip")
	public String getExternalIp() {
		return super.getExternalIp();
	}
	public void setExternalIp(String externalIp) {
		super.setExternalIp(externalIp);
	}
	
	@Column(name="inner_ip")
	public String getInnerIp() {
		return super.getInnerIp();
	}
	public void setInnerIp(String innerIp) {
		super.setInnerIp(innerIp);
	}
//	public List<DomainSuffix> getDomainSuffixs() {
//		return domainSuffixs;
//	}
//	public void setDomainSuffixs(List<DomainSuffix> domainSuffixs) {
//		this.domainSuffixs = domainSuffixs;
//	}
	@Column(name="extend")
	public String getExtend() {
		return super.getExtend();
	}
	public void setExtend(String extend) {
		super.setExtend(extend);
	}
	
}
