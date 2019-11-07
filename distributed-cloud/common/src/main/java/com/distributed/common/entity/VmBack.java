package com.distributed.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Idan on 2017/12/7.
 */

@Data
public class VmBack implements Serializable{

    private static final Long serialVersionUID = 1L;

    private Integer id;

    private String uuid;

    private String instanceUuid;

    private String sourceDataCenter;

    private String destDataCenter;

    private String sourceHostUuid;

    private String destHostUuid;

    private String backIp;

    private String type;

    private String status;

    private Integer version;

    private Timestamp createTime;

    private String storeLocation;

    /**
     * 备份主机的详细信息
     */
    private InstanceBackInfo instanceBackInfo;

    /**
     * 备份镜像的top镜像
     */
    private VmImageBack vmImageBack;

    public VmBack() {
    }

    public VmBack(String uuid, String instanceUuid, String sourceDataCenter, String destDataCenter, String sourceHostUuid, String destHostUuid, String backIp, String type, String status) {
        this.uuid = uuid;
        this.instanceUuid = instanceUuid;
        this.sourceDataCenter = sourceDataCenter;
        this.destDataCenter = destDataCenter;
        this.sourceHostUuid = sourceHostUuid;
        this.destHostUuid = destHostUuid;
        this.backIp = backIp;
        this.type = type;
        this.status = status;
    }

    public VmBack(String uuid, String instanceUuid, String sourceDataCenter, String destDataCenter, String sourceHostUuid, String destHostUuid, String backIp, String type, String status, Integer version, Timestamp createTime) {
        this.uuid = uuid;
        this.instanceUuid = instanceUuid;
        this.sourceDataCenter = sourceDataCenter;
        this.destDataCenter = destDataCenter;
        this.sourceHostUuid = sourceHostUuid;
        this.destHostUuid = destHostUuid;
        this.backIp = backIp;
        this.type = type;
        this.status = status;
        this.version = version;
        this.createTime = createTime;
    }
}
