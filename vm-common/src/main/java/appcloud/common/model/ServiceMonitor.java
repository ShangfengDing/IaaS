package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.sql.Timestamp;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@jid")
public class ServiceMonitor {

    /**
     * @author lin
     * 服务监控-虚拟机数据收集进程、VNC进程
     * 2018/11/22
     */

    private Integer id;
    private String hostUuid;
    private ServiceMonitorEnum type;
    private Timestamp updateTime;
    private Timestamp stopTime;
    private ServiceMonitorStatus serviceStatus;


    public ServiceMonitor() {}

    public ServiceMonitor(Integer id, String hostUuid, ServiceMonitorEnum type, Timestamp updateTime, Timestamp stopTime, ServiceMonitorStatus serviceStatus) {
        super();
        this.id = id;
        this.hostUuid = hostUuid;
        this.type = type;
        this.updateTime = updateTime;
        this.stopTime = stopTime;
        this.serviceStatus = serviceStatus;
    }

    public static enum ServiceMonitorEnum {
        VM_DATA_COLLECT, VNC_SERVER;
        public String toString() {
            switch (this) {
                case VM_DATA_COLLECT:
                    return "虚拟机数据收集进程";
                case VNC_SERVER:
                    return "VNC进程";
            }
            return super.toString();
        }

        public static ServiceMonitorEnum toEnum(String type) {
            if (type.equalsIgnoreCase("虚拟机数据收集进程")) {
                return VM_DATA_COLLECT;
            } else {
                return VNC_SERVER;
            }
        }
    }


    public static enum ServiceMonitorStatus {
        RUNNING, STOPED;
    }

    @Override
    public String toString() {
        return "ServiceMonitor{" +
                "id=" + id +
                ", hostUuid='" + hostUuid + '\'' +
                ", type=" + type +
                ", updateTime=" + updateTime +
                ", stopTime=" + stopTime +
                ", serviceStatus=" + serviceStatus +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHostUuid() {
        return hostUuid;
    }

    public void setHostUuid(String hostUuid) {
        this.hostUuid = hostUuid;
    }

    public ServiceMonitorEnum getType() {
        return type;
    }

    public void setType(ServiceMonitorEnum type) {
        this.type = type;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Timestamp getStopTime() {
        return stopTime;
    }

    public void setStopTime(Timestamp stopTime) {
        this.stopTime = stopTime;
    }

    public ServiceMonitorStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceMonitorStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
}
