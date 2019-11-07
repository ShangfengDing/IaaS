package appcloud.admin.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zouji on 2018/04/11.
 * 健康分数
 * @author BZbyr
 */
@Entity
@Table(name = "health")
public class Health {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "physics_health")
    private Integer physicsHealth;

    @Column(name = "service_health")
    private Integer serviceHealth;

    @Column(name = "cloud_health")
    private Integer cloudHealth;


    @Column(name = "created_time")
    private Timestamp createdTime;

    public Health() {}

    public Health(Integer physicsHealth, Integer serviceHealth, Integer cloudHealth, String reason, Timestamp createdTime) {
        this.physicsHealth = physicsHealth;
        this.serviceHealth = serviceHealth;
        this.cloudHealth = cloudHealth;
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "Health{" +
                "id=" + id +
                ", physicsHealth=" + physicsHealth +
                ", serviceHealth=" + serviceHealth +
                ", cloudHealth=" + cloudHealth +
                ", createdTime=" + createdTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPhysicsHealth() {
        return physicsHealth;
    }

    public void setPhysicsHealth(Integer physicsHealth) {
        this.physicsHealth = physicsHealth;
    }

    public Integer getServiceHealth() {
        return serviceHealth;
    }

    public void setServiceHealth(Integer serviceHealth) {
        this.serviceHealth = serviceHealth;
    }

    public Integer getCloudHealth() {
        return cloudHealth;
    }

    public void setCloudHealth(Integer cloudHealth) {
        this.cloudHealth = cloudHealth;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }
}
