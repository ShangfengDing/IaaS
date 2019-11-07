/**
 * File: Domain.java
 * Author: weed
 * Create Time: 2012-11-9
 */
package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author weed
 *
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class Domain {
	private Integer id;
	private Integer devId;
	private DomainTypeEnum type;
	private String prefix;
	private String suffix;
	
	public static enum DomainTypeEnum{
		INSIDE, OUTSIDE;
		
		public String toString(){
				switch (this) {
				case INSIDE:
					return "INSIDE";
				case OUTSIDE:
					return "OUTSIDE";
			}
			return super.toString();
		}
	};

	public Domain() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public Domain(Integer id, Integer devId, DomainTypeEnum type,
			String prefix, String suffix) {
		super();
		this.id = id;
		this.devId = devId;
		this.type = type;
		this.prefix = prefix;
		this.suffix = suffix;
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
	public DomainTypeEnum getType() {
		return type;
	}

	public void setType(DomainTypeEnum type) {
		this.type = type;
	}

	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((devId == null) ? 0 : devId.hashCode());
		result = prime * result
				+ ((prefix == null) ? 0 : prefix.hashCode());
		result = prime * result
				+ ((suffix == null) ? 0 : suffix.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Domain other = (Domain) obj;
		if (devId == null) {
			if (other.devId != null)
				return false;
		} else if (!devId.equals(other.devId))
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (suffix == null) {
			if (other.suffix != null)
				return false;
		} else if (!suffix.equals(other.suffix))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Domain [id=" + id + ", devId=" + devId + ", type=" + type
				+ ", prefix=" + prefix + ", suffix="
				+ suffix + "]";
	}
}
