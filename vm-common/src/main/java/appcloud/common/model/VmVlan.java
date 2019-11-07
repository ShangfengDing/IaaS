package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class VmVlan {
	private Integer id;
	private String name;
	private VmVlanTypeEnum type;
	private String description;
	
	public VmVlan() {
		super();
	}
	
	public VmVlan(Integer id, String name, VmVlanTypeEnum type,
			String description) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public VmVlanTypeEnum getType() {
		return type;
	}

	public void setType(VmVlanTypeEnum type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

	@Override
	public String toString() {
		return "VmVlan [id=" + id + ", name=" + name + ", type=" + type
				+ ", description=" + description + "]";
	}

	public static enum VmVlanTypeEnum {
		PUBLIC, PRIVATE;
		
		public String toString () {
			switch (this){
			case PUBLIC: return "public";
			case PRIVATE: return "private";
			}
			return super.toString();
		}
	}
}
