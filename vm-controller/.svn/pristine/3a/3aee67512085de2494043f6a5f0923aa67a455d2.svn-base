package appcloud.vmcontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

public class VMControllerConf {
    public static Logger logger = Logger.getLogger(VMControllerConf.class);
    public static String VIR_CON_STR = "qemu:///system";
    public static String PRIVATE_NETWORK = "br0";
    public static String PUBLIC_NETWORK = "br1";
    public static String DEFAULT_NETWORK = "bridge";
    public static String LIBVIRT_TYPE = "hvm";
    public static String EMULATOR_NAME;
    public static String POER_22_ACTION = "accept";
    public static String FILTER_REF_NAME = "clean-traffic";
    public static long MONITER_INTERVAL = 30000;
    
    static{
        InputStream is = VMControllerConf.class.getClassLoader().getResourceAsStream("vmcontroller.properties");
        Properties p = new Properties();
        try {
            p.load(is);
            VIR_CON_STR = p.getProperty("vmConnectStr", VIR_CON_STR);
            PRIVATE_NETWORK = p.getProperty("privateBridge", PRIVATE_NETWORK);
            PUBLIC_NETWORK = p.getProperty("publicBridge", PUBLIC_NETWORK);    //br1
            DEFAULT_NETWORK = p.getProperty("defaultNetwork", DEFAULT_NETWORK);
            LIBVIRT_TYPE = p.getProperty("libvirtType", LIBVIRT_TYPE);
            EMULATOR_NAME = getQemuKvmLocation();
            POER_22_ACTION = p.getProperty("port22Action",POER_22_ACTION);
            FILTER_REF_NAME = p.getProperty("filterRefName",FILTER_REF_NAME);
            try{
            	MONITER_INTERVAL = Long.parseLong(p.getProperty("monitorInterval",String.valueOf(MONITER_INTERVAL)));
            }catch(NumberFormatException e){
            	logger.warn("parse MONITER_INTERVAL ERROR,Use default MONITER_INTERVAL! ",e);
            	logger.warn(e.getMessage());
            }
            
            logger.info("Load vmcontroller default properties:");
            logger.info(VIR_CON_STR);
            logger.info(PRIVATE_NETWORK);
            logger.info(PUBLIC_NETWORK);
            logger.info(DEFAULT_NETWORK);
            logger.info(LIBVIRT_TYPE);
            logger.info(EMULATOR_NAME);
            logger.info(POER_22_ACTION);
            logger.info(FILTER_REF_NAME);
            logger.info(MONITER_INTERVAL);
        }
        catch (IOException e) {
           logger.error("Invallid vmcontroller.properties", e);
           logger.error(e.getMessage());
        }
    }

    private static String getQemuKvmLocation() {
    	logger.info("execute \"whereis qemu-kvm\" cmd ");
        String tempLocation = getCmd("whereis qemu-kvm");
        // qemu-kvm: /bin/qemu-kvm /usr/bin/qemu-kvm
        if (tempLocation.split(" ").length > 1) {
            return tempLocation.split(" ")[1].trim();
        } else {
            return null;
        }

    }

    private static String getCmd(String command) {
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
