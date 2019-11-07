package appcloud.api.beans;

import appcloud.api.XmlAdapter.TimestampXmlAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.sql.Timestamp;

/**
 * Created by zouji on 2017/11/24.
 */

@XmlRootElement(name="operate_log")
public class AcOperateLog {
    @XmlAttribute
    public String result;
    @XmlAttribute
    public String appkeyId;
    @XmlAttribute
    public String userId;
    @XmlAttribute
    public String device;
    @XmlAttribute
    public String provider;
    @XmlAttribute
    public String operateType;
    @XmlAttribute
    public String deviceId;
    @XmlAttribute
    public String infoType;
    @XmlAttribute
    @XmlJavaTypeAdapter(value = TimestampXmlAdapter.class)
    public Timestamp createdTime;

    public AcOperateLog() {}

    public AcOperateLog(String result, String appkeyId, String userId, String device, String provider, String operateType, String deviceId, String infoType, Timestamp createdTime) {
        this.result = result;
        this.appkeyId = appkeyId;
        this.userId = userId;
        this.device = device;
        this.provider = provider;
        this.operateType = operateType;
        this.deviceId = deviceId;
        this.infoType = infoType;
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "AcOperateLog[" +
                "result=" + result +
                ", appkeyId=" + appkeyId +
                ", userId=" + userId +
                ", device=" + device +
                ", provider=" + provider +
                ", operateType=" + operateType +
                ", deviceId=" + deviceId +
                ", infoType=" + infoType +
                ", createdTime=" + createdTime +
                "]";
    }
}
