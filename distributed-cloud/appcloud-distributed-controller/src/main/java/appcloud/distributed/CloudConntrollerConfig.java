package appcloud.distributed;

/**
 * Created by lizhenhao on 2017/11/22.
 */
public class CloudConntrollerConfig {

    private String ipAddress;

    private String port;


    public CloudConntrollerConfig(String ipAddress, String port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
