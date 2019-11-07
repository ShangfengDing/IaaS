package appcloud.common.model;

/**
 * @ClassName: RouteItems
 * @Description: TODO
 * @author wenchaoz361
 * @date 2013-4-9 上午11:17:21
 */
public class RouteItem {

    private String mac;
    private String ip;
    
    public RouteItem(String mac, String ip) {
        this.setMac(mac);
        this.setIp(ip);
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    
}
