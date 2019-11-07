package appcloud.vmcontroller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import org.apache.log4j.Logger;
import appcloud.common.model.Host;
import appcloud.common.model.Host.HostTypeEnum;

public class VMControllerConf {
    private static VMControllerConf VMCAgent = null;
    static Logger logger = Logger.getLogger(VMControllerConf.class);
    private String uuid = "";
    private static Properties p = null;
    private static String privateNetwork = null;
    private static String publicNetwork = null;
    private static String defaultNetwork = null;
    // private static String nfsLocation = null;
    private static String virConStr = null;
    private static String libvirtType = null;
    private static String emulatorName = null;
    private static String port22Action = null;
    private static String filterRefName = null;

    public static VMControllerConf getInstance() {
        if (VMCAgent == null) {
            try {
                VMCAgent = new VMControllerConf();
            }
            catch (Exception e) {
                logger.info(e.getMessage());
            }
        }
        return VMCAgent;
    }

    private VMControllerConf() throws Exception {
        if (p == null) {
            p = new Properties();
            InputStream is = VMControllerConf.class.getClassLoader()
                                                   .getResourceAsStream("vmcontroller.properties");
            p.load(is);

            virConStr = p.getProperty("vmConnectStr");
            privateNetwork = p.getProperty("privateBridge");
            publicNetwork = p.getProperty("publicBridge");
            defaultNetwork = p.getProperty("defaultNetwork");
            // nfsLocation = p.getProperty("nfsLocation");
            libvirtType = p.getProperty("libvirtType");
            emulatorName = getQemuKvmLocation();
            port22Action = p.getProperty("port22Action");
            filterRefName = p.getProperty("filterRefName");
            // todo:set uuid
            uuid = "todo";
        }
    }

    public String getVirConnStr() {
        return virConStr;
    }

    public String getLibvirtType() {
        return libvirtType;
    }

    public String getEmulatorName() {
        return emulatorName;
    }

    public String getDefaultNetwork() {
        return defaultNetwork;
    }

    public Host getHostInfoWithUuid() {
        Host host = new Host();
        host.setUuid(this.getUuid());
        host.setType(this.getType());

        return host;
    }

    public String getUuid() {
        return uuid;
    }

    private HostTypeEnum type;

    public HostTypeEnum getType() {
        return type;
    }

    public String getPrivateNetwork() {
        return privateNetwork;
    }

    public String getPublicNetwork() {
        return publicNetwork;
    }

    public String getPort22Action() {
        return port22Action;
    }

    public String getFilterRefName() {
        return filterRefName;
    }

    private String getQemuKvmLocation() {
        String tempLocation = getCmd("whereis qemu-kvm");
        // qemu-kvm: /bin/qemu-kvm /usr/bin/qemu-kvm
        if (tempLocation.split(" ").length > 1) {
            return tempLocation.split(" ")[1].trim();
        } else {
            return null;
        }

    }

    private String getCmd(String command) {
        String msg = "";
        InputStream is = null;
        BufferedReader br = null;
        try {
            Process proc = Runtime.getRuntime().exec(command);
            is = proc.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            msg += br.readLine();
            while (br.ready()) {
                msg += "\n" + br.readLine();
            }
            // destroy 此进程 可以回收 相关资源 特别是 文件句柄
            proc.destroy();
        }
        catch (Exception ex) {
            msg = "";
        }

        try {
            br.close();
            is.close();
        }
        catch (Exception ex) {}

        return msg;
    }
}
