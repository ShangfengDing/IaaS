package appcloud.common.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@jid")
public class VmSecurityGroup {
    private Integer id;
    private String uuid;
    private String name;
    private String description;
    private Integer userId;

    private List<VmSecurityGroupRule> vmSecurityGroupRules;

    public VmSecurityGroup() {
        super();
    }
    

    public VmSecurityGroup(Integer id, String name, String description, Integer userId) {
        super();
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.description = description;
    }

    public VmSecurityGroup(Integer id, String uuid, String name,
			String description, Integer userId) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.name = name;
		this.description = description;
		this.userId = userId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public List<VmSecurityGroupRule> getVmSecurityGroupRules() {
        return vmSecurityGroupRules;
    }

    public void setVmSecurityGroupRules(List<VmSecurityGroupRule> vmSecurityGroupRules) {
        this.vmSecurityGroupRules = vmSecurityGroupRules;
    }

	@Override
	public String toString() {
		return "VmSecurityGroup [id=" + id + "uuid=" + uuid + ", name=" + name
				+ ", description=" + description + ", userId=" + userId
				+ ", vmSecurityGroupRules="
				+ vmSecurityGroupRules + "]";
	}


}
