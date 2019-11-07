/**
 * File: DomainTable.java
 * Author: weed
 * Create Time: 2012-11-22
 */
package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.Domain;

/**
 * @author weed
 *
 */
@Entity
@Table(name="domain")
public class DomainTable extends Domain {

	public DomainTable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DomainTable(Domain domain) {
		this.setPrefix(domain.getPrefix());
		this.setSuffix(domain.getSuffix());
		this.setType(domain.getType());
		this.setId(domain.getId());
		this.setUserId(domain.getDevId());
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
	public Integer getUserId() {
		return super.getDevId();
	}
	public void setUserId(Integer userId) {
		super.setDevId(userId);
	}
	
	@Column(name="type")
	@Enumerated(EnumType.STRING)
	public DomainTypeEnum getType() {
		return super.getType();
	}
	public void setType(DomainTypeEnum type) {
		super.setType(type);
	}
	
	@Column(name="prefix")
	public String getPrefix() {
		return super.getPrefix();
	}
	public void setPrefix(String prefix) {
		super.setPrefix(prefix);
	}
	
	@Column(name="suffix")
	public String getSuffix() {
		return super.getSuffix();
	}
	public void setSuffix(String suffix) {
		super.setSuffix(suffix);
	}
}
