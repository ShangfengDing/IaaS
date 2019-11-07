package appcloud.dbproxy.mysql.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.Cluster;

/**
 * @author lzc
 *
 */
@Entity
@Table(name="vm_cluster")
public class ClusterTable extends Cluster {

	public ClusterTable() {
		super();
	}

	public ClusterTable(Cluster cluster) {
		this.setId(cluster.getId());
		this.setName(cluster.getName());
		this.setResrcStrategyUuid(cluster.getResrcStrategyUuid());
		this.setTaskStrategyId(cluster.getTaskStrategyId());
		this.setExtend(cluster.getExtend());
		this.setCreatedTime(cluster.getCreatedTime());
		this.setUpdatedTime(cluster.getUpdatedTime());
		this.setAvailabilityZoneId(cluster.getAvailabilityZoneId());
		this.setPublicVlan(cluster.getPublicVLAN());
		this.setPrivateVlan(cluster.getPrivateVLAN());
		this.setCpuOversell(cluster.getCpuOversell());
		this.setMemoryOversell(cluster.getMemoryOversell());
		this.setDiskOversell(cluster.getDiskOversell());
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

	@Column(name="resrc_strategy_uuid")
	public String getResrcStrategyUuid() {
		return super.getResrcStrategyUuid();
	}

	public void setResrcStrategyUuid(String resrcStrategyUuid) {
		super.setResrcStrategyUuid(resrcStrategyUuid);
	}
	
	@Column(name="task_strategy_id")
	public Integer getTaskStrategyId() {
		return super.getTaskStrategyId();
	}

	public void setTaskStrategyId(Integer taskStrategyId) {
		super.setTaskStrategyId(taskStrategyId);
	}

	@Column(name="extend")
	public String getExtend() {
		return super.getExtend();
	}

	public void setExtend(String extend) {
		super.setExtend(extend);
	}
	
	@Column(name = "created_time")
    public Timestamp getCreatedTime() {
        return super.getCreatedTime();
    }

    public void setCreatedTime(Timestamp createdTime) {
        super.setCreatedTime(createdTime);
    }
    
    @Column(name = "updated_time")
    public Timestamp getUpdatedTime() {
        return super.getUpdatedTime();
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        super.setUpdatedTime(updatedTime);
    }
	
	@Column(name = "availability_zone_id")
    public Integer getAvailabilityZoneId() {
		return super.getAvailabilityZoneId();
	}

	public void setAvailabilityZoneId(Integer availabilityZoneId) {
		super.setAvailabilityZoneId(availabilityZoneId);
	}

	@Column(name = "public_vlan")
    public Integer getPublicVlan() {
		return super.getPublicVLAN();
	}

	public void setPublicVlan(Integer publicVlan) {
		super.setPublicVLAN(publicVlan);
	}
	
	@Column(name = "private_vlan")
    public Integer getPrivateVlan() {
		return super.getPublicVLAN();
	}
	public void setPrivateVlan(Integer privateVlan) {
		super.setPrivateVLAN(privateVlan);
	}
	
	@Column(name = "cpu_oversell")
	public Integer getCpuOversell() {
		return super.getCpuOversell();
	}
	public void setCpuOversell(Integer cpuOversell) {
		super.setCpuOversell(cpuOversell);
	}

	@Column(name = "memory_oversell")
	public Integer getMemoryOversell() {
		return super.getMemoryOversell();
	}
	public void setMemoryOversell(Integer memoryOversell) {
		super.setMemoryOversell(memoryOversell);
	}

	@Column(name = "disk_oversell")
	public Integer getDiskOversell() {
		return super.getDiskOversell();
	}
	public void setDiskOversell(Integer diskOversell) {
		super.setDiskOversell(diskOversell);
	}
}
