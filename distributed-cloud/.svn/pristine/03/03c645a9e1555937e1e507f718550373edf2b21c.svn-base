package appcloud.distributed.configmanager;

import lombok.Data;

/**
 * Created by lizhenhao on 2017/11/22.
 */
@Data
public class RouteInfo {

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


    //负载属性

    public RouteInfo() {
    }

    public RouteInfo(String ipAddress, String port, String domainName,String addr,String dataCenter,String uuid) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.domainName = domainName;
        this.addr = addr;
        this.dataCenter = dataCenter;
        this.uuid = uuid;
    }
}
