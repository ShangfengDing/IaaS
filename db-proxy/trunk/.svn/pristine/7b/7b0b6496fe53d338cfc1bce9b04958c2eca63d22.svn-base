/**
 * File: NginxDSTable.java
 * Author: weed
 * Create Time: 2012-11-23
 */
package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.NginxToSuffix;

/**
 * @author weed
 *
 */
@Entity
@Table(name="nginx_to_suffix")
public class NginxToSuffixTable extends NginxToSuffix{

	public NginxToSuffixTable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NginxToSuffixTable(NginxToSuffix nginxToSuffix) {
		this.setDomainSuffixId(nginxToSuffix.getDomainSuffixId());
		this.setId(nginxToSuffix.getId());
		this.setNginxId(nginxToSuffix.getNginxId());
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

	@Column(name="nginx_id")
	public Integer getNginxId() {
		return super.getNginxId();
	}

	public void setNginxId(Integer nginxId) {
		super.setNginxId(nginxId);
	}

	@Column(name="domain_suffix_id")
	public Integer getDomainSuffixId() {
		return super.getDomainSuffixId();
	}

	public void setDomainSuffixId(Integer domainSuffixId) {
		super.setDomainSuffixId(domainSuffixId);
	}
}
