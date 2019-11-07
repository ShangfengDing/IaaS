package appcloud.api.beans;

import appcloud.api.XmlAdapter.TimestampXmlAdapter;
import appcloud.common.model.InstanceLogType;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.sql.Timestamp;

/**
 * Created by zouji on 2017/11/22.
 */

@XmlRootElement(name="instance_log")
public class AcInstanceLog {
    @XmlAttribute
    public Integer userId;
    @XmlAttribute
    public String uuid;
    @XmlAttribute
    public InstanceLogType.Type type;
    @XmlAttribute
    @XmlJavaTypeAdapter(value= TimestampXmlAdapter.class)
    public Timestamp endTime;
    @XmlAttribute
    public InstanceLogType.PayType payType;
    @XmlAttribute
    @XmlJavaTypeAdapter(value= TimestampXmlAdapter.class)
    public Timestamp createdTime;
    @XmlAttribute
    public Integer groupId;

    public AcInstanceLog() {
        super();
    }

    public AcInstanceLog(Integer userId, String uuid, InstanceLogType.Type type, Timestamp endTime, InstanceLogType.PayType payType, Timestamp createdTime, Integer groupId) {
        this.userId = userId;
        this.uuid = uuid;
        this.type = type;
        this.endTime = endTime;
        this.payType = payType;
        this.createdTime = createdTime;
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "AcInstanceLog [userId=" + userId + ", uuid=" + uuid
                + ", type=" + type + ", endTime=" + endTime + ", payType"
                + payType + ", createdTime=" + createdTime + ", groupId="
                + groupId + "]";
    }
}
