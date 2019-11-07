package appcloud.distributed.nativeConfig;

import appcloud.distributed.common.Constants;
import io.netty.util.internal.StringUtil;
import org.apache.log4j.Logger;

/**
 * Created by zouji on 2017/12/7.
 * 生成DNS配置格式
 */
public class DnsDirector {
    private static Logger logger = Logger.getLogger(DnsDirector.class);

    private String domain;
    private String type = "master";
    private String filename;
    private String ipAddress;

    DnsDirector(String domain, String ipAddress) {
        this.domain = domain;
        this.filename = domain + ".zone";
        this.ipAddress = ipAddress;
    }

    DnsDirector(String domain, String type, String filename, String ipAddress) {
        this.domain = domain;
        this.type = type;
        this.filename = filename;
        this.ipAddress = ipAddress;
    }

    public String genConfigStr() {
        if (StringUtil.isNullOrEmpty(domain) || StringUtil.isNullOrEmpty(type) || StringUtil.isNullOrEmpty(filename)) {
            logger.info("domain, type or filename is empty");
            return "";
        }
        return "zone \"" + domain + "\"" + " IN {\n"
                + "        type " + type + ";\n"
                + "        file \"" + filename + "\";\n"
                + "};\n\n";
    }

    public String genConfigFileStr() {
        if (!isIpAddress(ipAddress)) {
            logger.info("Generate file " + filename + "failed, ipAddress v4 is illegal!");
            return "";
        }
        return "$TTL " + Constants.DNS_TTL + "\n"
                + "@ IN SOA " + domain + ". " + "admin." + domain + ".(\n"
                + "                2017120701\n"
                + "                7H\n"
                + "                0M\n"
                + "                5D\n"
                + "                5D )\n"
                + "@    IN NS  "+ domain +".\n"
                + "@      IN    A    " + ipAddress + "\n"
                + "*      IN    A    " + ipAddress + "\n";
    }

    private Boolean isIpAddress(String ipAddress) {
        String regex = "^((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)$";
        return ipAddress.matches(regex);
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public static void main(String[] args) {
        DnsDirector dnsDirector = new DnsDirector("test.com", "202.149.33.23");
        System.out.println(dnsDirector.genConfigStr());
        System.out.println(dnsDirector.genConfigFileStr());
        System.out.println(dnsDirector.getDomain());
    }
}
