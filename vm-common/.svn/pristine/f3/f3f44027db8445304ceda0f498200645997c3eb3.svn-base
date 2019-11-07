package appcloud.common.model;

import java.sql.Timestamp;

/**
 * Created by zouji on 2017/11/9.
 */
public class InstanceLogType {

    private Integer userId;
    private String uuid;
    private Type type;
    private Timestamp endTime;
    private PayType payType;
    private Timestamp createdTime;
    private Integer groupId;

    public InstanceLogType() {

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public enum Type {
        HD, INSTANCETYPE, VM, VMPACKAGE;
    }

    public enum PayType {
        按需, 包年, 包日, 包月;
    }
}
