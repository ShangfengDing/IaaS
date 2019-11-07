/**
 * File: RoutingEntryTable.java
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

import appcloud.common.model.RoutingEntry;

/**
 * @author weed
 *
 */
@Entity
@Table(name="routing_entry")
public class RoutingEntryTable extends RoutingEntry {

	public RoutingEntryTable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoutingEntryTable(RoutingEntry routingEntry) {
		// TODO Auto-generated constructor stub
		this.setDestPrefix(routingEntry.getDestPrefix());
		this.setDestSuffix(routingEntry.getDestSuffix());
		this.setId(routingEntry.getId());
		this.setIsValid(routingEntry.getIsValid());
		this.setSrcPrefix(routingEntry.getSrcPrefix());
		this.setSrcSuffix(routingEntry.getSrcSuffix());
		this.setType(routingEntry.getType());
		this.setWeight(routingEntry.getWeight());
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
	
	@Column(name="src_prefix")
	public String getSrcPrefix() {
		return super.getSrcPrefix();
	}
	public void setSrcPrefix(String srcPrefix) {
		super.setSrcPrefix(srcPrefix);
	}
	
	@Column(name="src_suffix")
	public String getSrcSuffix() {
		return super.getSrcSuffix();
	}
	public void setSrcSuffix(String srcSuffix) {
		super.setSrcSuffix(srcSuffix);
	}
	
	@Column(name="dest_prefix")
	public String getDestPrefix() {
		return super.getDestPrefix();
	}
	public void setDestPrefix(String destPrefix) {
		super.setDestPrefix(destPrefix);
	}
	
	@Column(name="dest_suffix")
	public String getDestSuffix() {
		return super.getDestSuffix();
	}
	public void setDestSuffix(String destSuffix) {
		super.setDestSuffix(destSuffix);
	}
	
	@Column(name="weight")
	public Integer getWeight() {
		return super.getWeight();
	}
	public void setWeight(Integer weight) {
		super.setWeight(weight);
	}
	
	@Column(name="is_valid")
	public Integer getIsValid() {
		return super.getIsValid();
	}
	public void setIsValid(Integer isValid) {
		super.setIsValid(isValid);
	}

	@Column(name="type")
	@Enumerated(EnumType.STRING)
	public RETypeEnum getType() {
		return super.getType();
	}
	public void setType(RETypeEnum type) {
		super.setType(type);
	}
}
