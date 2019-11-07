package appcloud.openapi.datatype;


import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.sql.Timestamp;

/**
 * Created by zrain on 2017/11/30.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ImageBackItem {

    private Integer id;
    private String volumeUuid;
    private String displayName;
    private Integer userId;
    private Integer availabilityZoneId;
    private Integer availabilityClusterId;
    private String parentVolumeUuid;
    private String sonVolumeUuid;
    private String activeVolumeUuid;
    private String instanceUuid;
    private String providerLocation;
    private String hostUuid;
    private Timestamp createdTime;					//创建时间
    private Timestamp updatedTime;					//上次更新时间
    private Timestamp deletedTime;
    private Integer volumeSize;
    private boolean isBackUp;
    private boolean isTop;
    private String imageStatus;
    /** raw ,qcow2 */
    private String volumeType;           //
    /** system, data, iso, network ..etc **/
    private String usageType;


    public ImageBackItem() {
        super();
    }

    public ImageBackItem(Integer id, String volumeUuid, String displayName, Integer userId, Integer availabilityZoneId, Integer availabilityClusterId, String parentVolumeUuid, String sonVolumeUuid, String activeVolumeUuid, String instanceUuid, String providerLocation, String hostUuid,
                         Timestamp createdTime, Timestamp updatedTime, Timestamp deletedTime, Integer volumeSize, boolean isBackUp, boolean isTop, String  imageStatus, String volumeType, String usageType) {
        this.id = id;
        this.volumeUuid = volumeUuid;
        this.displayName = displayName;
        this.userId = userId;
        this.availabilityZoneId = availabilityZoneId;
        this.availabilityClusterId = availabilityClusterId;
        this.parentVolumeUuid = parentVolumeUuid;
        this.sonVolumeUuid = sonVolumeUuid;
        this.activeVolumeUuid = activeVolumeUuid;
        this.instanceUuid = instanceUuid;
        this.providerLocation = providerLocation;
        this.hostUuid = hostUuid;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.deletedTime = deletedTime;
        this.volumeSize = volumeSize;
        this.isBackUp = isBackUp;
        this.isTop = isTop;
        this.imageStatus = imageStatus;
        this.volumeType = volumeType;
        this.usageType = usageType;
    }
}
