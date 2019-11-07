package appcloud.dbproxy.mysql.model;

import appcloud.common.model.ServiceMonitor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "service_monitor")
public class ServiceMonitorTable extends ServiceMonitor {

    public ServiceMonitorTable(){}

    public ServiceMonitorTable(ServiceMonitor serviceMonitor) {
        this.setId(serviceMonitor.getId());
        this.setHostUuid(serviceMonitor.getHostUuid());
        this.setType(serviceMonitor.getType());
        this.setUpdateTime(serviceMonitor.getUpdateTime());
        this.setStopTime(serviceMonitor.getStopTime());
        this.setServiceStatus(serviceMonitor.getServiceStatus());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Integer getId() {
        return super.getId();
    }
    public void setId(Integer id) {
        super.setId(id);
    }

    @Column(name = "host_uuid")
    public String getHostUuid() {
        return super.getHostUuid();
    }
    public void setHostUuid(String hostUuid) {
        super.setHostUuid(hostUuid);
    }

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    public ServiceMonitorEnum getType() { return super.getType();}
    public void setType(ServiceMonitorEnum type) { super.setType(type);}

    @Column(name = "update_time")
    public Timestamp getUpdateTime() { return super.getUpdateTime();}
    public void setUpdateTime(Timestamp updateTime) { super.setUpdateTime(updateTime);}

    @Column(name = "stop_time")
    public Timestamp getStopTime() { return super.getStopTime();}
    public void setStopTime(Timestamp stopTime) { super.setStopTime(stopTime);}

    @Column(name = "service_status")
    @Enumerated(EnumType.STRING)
    public ServiceMonitorStatus getServiceStatus() { return super.getServiceStatus();}
    public void setServiceStatus(ServiceMonitorStatus serviceStatus) { super.setServiceStatus(serviceStatus);}
}
