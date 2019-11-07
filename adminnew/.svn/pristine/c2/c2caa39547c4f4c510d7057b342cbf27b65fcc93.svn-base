package appcloud.admin.model;

import javax.persistence.*;

/**
 * Created by zouji on 2018/4/30.
 */
@Entity
@Table(name = "alarmhistory")
public class AlarmHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "appName")
    private String appName;

    @Column(name = "description")
    private String description;

    @Column(name = "content")
    private String content;

    @Column(name = "log")
    private String log;

    @Column(name = "time")
    private Long time;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private String status;

    @Column(name = "comment")
    private String comment;

    public AlarmHistory() {}

    public AlarmHistory(String appName, String description, String content, String log, Long time, String email) {
        this.appName = appName;
        this.description = description;
        this.content = content;
        this.log = log;
        this.time = time;
        this.email = email;
    }

    @Override
    public String toString() {
        return "{AlarmHistory{" +
                "id=" + id +
                ", appName=" + appName +
                ", description=" + description +
                ", content=" + content +
                ", log=" + log +
                ", time=" + time +
                ", email=" + email +
                "}";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
