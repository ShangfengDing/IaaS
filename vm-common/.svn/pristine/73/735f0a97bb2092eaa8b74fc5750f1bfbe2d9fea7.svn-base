package appcloud.common.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@jid")
public class ResourceStrategy {

	private Integer id;
	private String uuid;
	private StrategyTypeEnum type;
	private String name;
	private String description;
	private String clazzs;
	private String params;
	private Timestamp time;
	
	public ResourceStrategy() {
		super();
	}
	
	public ResourceStrategy(Integer id, String uuid, StrategyTypeEnum type,
			String name, String description, String clazz, String params, Timestamp time) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.type = type;
		this.name = name;
		this.description = description;
		this.clazzs = clazz;
		this.params = params;
		this.time = time;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public StrategyTypeEnum getType() {
		return type;
	}

	public void setType(StrategyTypeEnum type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getClazzs() {
		return clazzs;
	}

	public void setClazzs(String clazzs) {
		this.clazzs = clazzs;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public static enum StrategyTypeEnum {
		CPU_MEMORY, DISK;
		public String toString() {
			switch (this) {
				case CPU_MEMORY:
					return "cpu_memory";
				case DISK:
					return "disk";
			}
			return super.toString();
		}
	}

	@Override
	public String toString() {
		return "ResourceStrategy [id=" + id + ", uuid=" + uuid + ", type="
				+ type + ", name=" + name + ", description=" + description
				+ ", clazzs=" + clazzs + ", params=" + params + ", time="
				+ time + "]";
	}
}
