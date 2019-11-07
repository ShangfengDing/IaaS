package com.distributed.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * created by wangzhi 2018-05-09 10:32
 **/
@Data
public class CloudInfo implements Serializable{
    private static final Long serialVersionUID = 1L;
    /**
     * 基本属性
     */
    private String ipAddress;
    private String port;
    /**
     * 该云平台的域名
     */
    private String domainName;
    /**
     * ip+port
     */
    private String addr;
    /**
     * 实际是zoneId
     */
    private String dataCenter;
    /**
     * 每个云平台的唯一标识
     */
    private String uuid;

    /**
     * 角色
     * "follower" or "master"
     */
    private String role;

    public CloudInfo() {
    }

    public CloudInfo(String ipAddress, String port, String domainName,String addr,String dataCenter,String uuid) {
        this(ipAddress,port,domainName,addr,dataCenter,uuid,"follower");
    }

    public CloudInfo(String ipAddress, String port, String domainName,String addr,String dataCenter,String uuid, String role) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.domainName = domainName;
        this.addr = addr;
        this.dataCenter = dataCenter;
        this.uuid = uuid;
        this.role = role;
    }
}
