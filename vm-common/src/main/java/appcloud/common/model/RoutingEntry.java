package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class RoutingEntry {
	private Integer id;
	private RETypeEnum type; 	//TOIP; TONAME
	private String srcPrefix; 
	private String srcSuffix; 
	private String destPrefix;
	private String destSuffix;
	private Integer weight;
	private Integer isValid;
	
	public static enum RETypeEnum{
		IP, DOMAIN;
		
		public String toString(){
				switch (this) {
				case IP:
					return "IP";
				case DOMAIN:
					return "DOMAIN";
			}
			return super.toString();
		}
	};
	
	public RoutingEntry() {
		// TODO Auto-generated constructor stub
	}
	
	public RoutingEntry(Integer id, RETypeEnum type, String srcPrefix,
			String srcSuffix, String destPrefix, String destSuffix,
			Integer weight, Integer isValid) {
		this.id = id;
		this.type = type;
		this.srcPrefix = srcPrefix;
		this.srcSuffix = srcSuffix;
		this.destPrefix = destPrefix;
		this.destSuffix = destSuffix;
		this.weight = weight;
		this.isValid = isValid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public RETypeEnum getType() {
		return type;
	}
	public void setType(RETypeEnum type) {
		this.type = type;
	}
	public String getSrcPrefix() {
		return srcPrefix;
	}
	public void setSrcPrefix(String srcPrefix) {
		this.srcPrefix = srcPrefix;
	}
	public String getSrcSuffix() {
		return srcSuffix;
	}
	public void setSrcSuffix(String srcSuffix) {
		this.srcSuffix = srcSuffix;
	}
	public String getDestPrefix() {
		return destPrefix;
	}
	public void setDestPrefix(String destPrefix) {
		this.destPrefix = destPrefix;
	}
	public String getDestSuffix() {
		return destSuffix;
	}
	public void setDestSuffix(String destSuffix) {
		this.destSuffix = destSuffix;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((destPrefix == null) ? 0 : destPrefix.hashCode());
		result = prime * result
				+ ((destSuffix == null) ? 0 : destSuffix.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isValid == null) ? 0 : isValid.hashCode());
		result = prime * result
				+ ((srcPrefix == null) ? 0 : srcPrefix.hashCode());
		result = prime * result
				+ ((srcSuffix == null) ? 0 : srcSuffix.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
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
		RoutingEntry other = (RoutingEntry) obj;
		if (destPrefix == null) {
			if (other.destPrefix != null)
				return false;
		} else if (!destPrefix.equals(other.destPrefix))
			return false;
		if (destSuffix == null) {
			if (other.destSuffix != null)
				return false;
		} else if (!destSuffix.equals(other.destSuffix))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isValid == null) {
			if (other.isValid != null)
				return false;
		} else if (!isValid.equals(other.isValid))
			return false;
		if (srcPrefix == null) {
			if (other.srcPrefix != null)
				return false;
		} else if (!srcPrefix.equals(other.srcPrefix))
			return false;
		if (srcSuffix == null) {
			if (other.srcSuffix != null)
				return false;
		} else if (!srcSuffix.equals(other.srcSuffix))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RoutingEntry [id=" + id + ", type=" + type + ", srcPrefix="
				+ srcPrefix + ", srcSuffix=" + srcSuffix + ", destPrefix="
				+ destPrefix + ", destSuffix=" + destSuffix + ", weight="
				+ weight + ", isValid=" + isValid + "]";
	}
}
