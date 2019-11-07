package appcloud.distributed.nativeConfig;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zouji on 2017/12/5.
 * 修改dns地址
 */


// TODO: 2018/1/2 需要修正，所有操作在/etc/resolve文件下进行

public class DnsConfig {
    private static Logger logger = Logger.getLogger(DnsConfig.class);

    private String path = "/etc/sysconfig/network-scripts/ifcfg-em1";


    DnsConfig(String path) {
        this.path = path;
    }

    /**
     * 获取本机设置的域名服务器地址
     * @return
     */
    public Set<String> getDns() {

        Set<String> dnsSet = new HashSet<String>();

        File file = getFile(path);
        if (file == null) {
            return dnsSet;
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String dns = "";
            while ((dns = reader.readLine()) != null) {
                if (!dns.trim().equals("") && dns.trim().toUpperCase().startsWith("DNS")) {
                    String[] strs = dns.trim().split("=");
                    if (strs.length > 1) {
                        dnsSet.add(strs[1].trim());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Get DNS configuration failed!");
            return dnsSet;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("file:" + path +" close failed!");
                }
            }
        }
        return dnsSet;
    }

    /**
     *
     * @param dnsSet
     * @return
     */
    private Boolean setDns(Set<String> dnsSet) {
        File file = getFile(path);
        if (file == null) {
            System.out.println("modify dns failed!");
            return false;
        }

        BufferedReader reader = null;
        String str = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().equals("") && !line.trim().toUpperCase().startsWith("DNS")) {
                    str = str + line.trim()+"\n";
                }
            }
        } catch (IOException e) {
            System.out.println("Get DNS configuration failed!");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("file:" + path +" close failed!");
                }
            }
        }
        Integer count = 1;
        for(String dns : dnsSet) {
            if (!dns.trim().equals("")) {
                str = str + "DNS"+ count + "=" + dns + "\n";
                count++;
            }
        }
        System.out.println(str);
        try {
            FileWriter writer = new FileWriter(path, false);
            writer.write(str);
            writer.close();
            Process process = Runtime.getRuntime().exec("com.distributed.service network restart");
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                System.out.println("com.distributed.service network restart failed!");
            }
        } catch (IOException e) {
            System.out.println("modify dns failed!");
            return false;
        }
        return true;
    }

    private File getFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("file is not existed");
            return null;
        }
        return file;
    }

    public static void main(String[] args) {
        DnsConfig dnsConfig = new DnsConfig("ifcfg-em1");
        System.out.println(dnsConfig.getDns());
        Set<String> dnsSet = new HashSet<String>();
        for (String arg : args) {
            dnsSet.add(arg);
        }
        dnsConfig.setDns(dnsSet);
        System.out.println("set successfully");
    }
}
