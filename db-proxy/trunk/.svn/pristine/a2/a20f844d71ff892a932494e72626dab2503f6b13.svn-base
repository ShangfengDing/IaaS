/**
 * File: DomainSuffixTable.java
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

import appcloud.common.model.DomainSuffix;

/**
 * @author weed
 *
 */
@Entity
@Table(name="domain_suffix")
public class DomainSuffixTable extends DomainSuffix {

	public DomainSuffixTable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DomainSuffixTable(DomainSuffix domainSuffix) {
		// TODO Auto-generated constructor stub
		this.setExtend(domainSuffix.getExtend());
		this.setId(domainSuffix.getId());
		this.setName(domainSuffix.getName());
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
	
	@Column(name="name")
	public String getName() {
		return super.getName();
	}
	public void setName(String name) {
		super.setName(name);
	}
	
	@Column(name="extend")
	public String getExtend() {
		return super.getExtend();
	}
	public void setExtend(String extend) {
		super.setExtend(extend);
	}
}
