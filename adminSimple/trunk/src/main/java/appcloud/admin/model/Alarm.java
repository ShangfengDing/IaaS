package appcloud.admin.model;

import javax.persistence.*;

/**
 * Created by zouji on 2018/5/7.
 */
@Entity
@Table(name = "alarm")
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "alarm_id")
    private Integer alarmId;

    @Column(name = "status")
    private AlarmStatus status;

    public Alarm() {}

    public Alarm(Integer alarmId, AlarmStatus status) {
        this.alarmId = alarmId;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlarmId() {
        return alarmId;
    }

    public void setAlarm_id(Integer alarmId) {
        this.alarmId = alarmId;
    }

    public AlarmStatus getStatus() {
        return status;
    }

    public void setStatus(AlarmStatus status) {
        this.status = status;
    }
}
