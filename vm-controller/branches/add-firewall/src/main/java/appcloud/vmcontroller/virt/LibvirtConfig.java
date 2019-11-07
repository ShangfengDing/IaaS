package appcloud.vmcontroller.virt;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class LibvirtConfig {
    private static Logger logger = Logger.getLogger(LibvirtConfig.class);
    
    public String uuid = null;
    public String name = null;
    public Integer memory = 1024 * 1024;
    public Integer vcpus = 1;
    public String cpu = null;
    public String cpuQuota = null;
    public String cpuPeriod = null;
    public Element features;
    public Element clock;

    public Element devices;
    public String emulator = null;
    public ArrayList<DiskConfig> diskConfigList = null;
    public ArrayList<NetworkConfig> networkConfigList = null;

    public String osType = null;
    public String osMode = null;
    public String osArch = null;
    public String osMachine = null;
    public ArrayList<String> osBootDev = new ArrayList<String>();
    public String osLoader = null;
    public String osKernel = null;
    public String osInitrd = null;
    // public String os_cmdline = null;
    // public String os_root = null;
    // public String os_init_path = null;
    // public String os_smbios = null;
    public String filter = null;

    private Document domainXml;
    private Element domain;

    public Element formatBasicProp() {
        domainXml = DocumentHelper.createDocument();
        domain = domainXml.addElement("domain").addAttribute("type", "kvm");
        domain.addElement("name").setText(name);
        domain.addElement("uuid").setText(uuid);
        domain.addElement("memory").setText(memory.toString());
        domain.addElement("vcpus").setText(vcpus.toString());

        return domain;
    }

    public void formatOs() {
        Element os = domain.addElement("os");
        Element type = os.addElement("type");
        type.setText(osMode);
        if (osArch != null)
            type.addAttribute("arch", osArch);
        if (osMachine != null)
            type.addAttribute("machine", osMachine);
        if (osBootDev != null) {
            Iterator<String> it = osBootDev.iterator();
            while (it.hasNext())
                os.addElement("boot").addAttribute("dev", it.next());
        }
        if (osLoader != null)
            os.addElement("loader").setText(osLoader);
        if (osKernel != null)
            os.addElement("kernel").setText(osLoader);
        if (osInitrd != null)
            os.addElement("initrd").setText(osInitrd);
    }

    public void formatFeatures() {
        features = domain.addElement("features");
        features.addElement("acpi");
        features.addElement("apic");
        features.addElement("pae");
    }

    public void setClock() {
        clock = domain.addElement("clock").addAttribute("offset", "localtime");
        clock.addElement("timer").addAttribute("name", "pit").addAttribute("tickpolicy", "delay");
        clock.addElement("timer").addAttribute("name", "rtc").addAttribute("tickpolicy", "catchup");
    }
    
    public void setGraphicsVNC() {
        Element graphicsVNC = devices.addElement("graphics")
                                .addAttribute("type", "vnc")
                                .addAttribute("port", "-1")
                                .addAttribute("autoport", "yes")
                                .addAttribute("listen", "0.0.0.0");
        graphicsVNC.addElement("listen")
                   .addAttribute("type", "address")
                   .addAttribute("address", "0.0.0.0");                             
    }
    
    public void formatStorageDevice() {
        devices = domain.addElement("devices");
        if (emulator != null) {
            devices.addElement("emulator").setText(emulator);
        }
        for (int i = 0; i < diskConfigList.size(); i++) {
     
            Element disk = devices.addElement("disk")
                                  .addAttribute("type", "file")
                                  .addAttribute("device", "disk");
            disk.addElement("driver")
                .addAttribute("name", diskConfigList.get(i).getDiskName())
                .addAttribute("type", diskConfigList.get(i).getDiskType());
            disk.addElement("source").addAttribute("file", diskConfigList.get(i).getSourceFile());
            disk.addElement("target")
                .addAttribute("dev", diskConfigList.get(i).getTargetDev())
                .addAttribute("bus", diskConfigList.get(i).getTargetBus());
        }
    }

    public void formatNetworkDevice() {
        for (int i = 0; i < networkConfigList.size(); i++) {
            // 生成网卡信息
            Element netInterface = devices.addElement("interface")
                                          .addAttribute("type",
                                                        networkConfigList.get(i).getNetworkType());
            if (networkConfigList.get(i).getNetworkType().equals("bridge")) {
                netInterface.addElement("mac")
                            .addAttribute("address", networkConfigList.get(i).getNetworkMac());
                netInterface.addElement("source")
                            .addAttribute("bridge", networkConfigList.get(i).getNetworkBridge());
                netInterface.addElement("ip")
                            .addAttribute("address", networkConfigList.get(i).getNetworkIp());
                netInterface.addElement("model").addAttribute("type",
                                                              networkConfigList.get(i)
                                                                               .getModelType());
                netInterface.addElement("filterref").addAttribute("filter", networkConfigList.get(i).getFilterName());
                if (networkConfigList.get(i).getInbound() != null) {
                    Element bandwidth = netInterface.addElement("bandwidth");
                    bandwidth.addElement("outbound")
                             .addAttribute("average", networkConfigList.get(i).getOutbound())
                             .addAttribute("peak", networkConfigList.get(i).getOutbound())
                             .addAttribute("peak", networkConfigList.get(i).getOutbound());

                }
            }
        }
    }

    public LibvirtConfig formatDom() {
        formatBasicProp();
        formatOs();
        formatFeatures();
        setClock();
        setPower();
        if (diskConfigList.size() != 0) {
            //
            formatStorageDevice();
            if (networkConfigList.size() != 0)
                formatNetworkDevice();
        }
        setGraphicsVNC();
        return this;
    }

    private void setPower() {
        domain.addElement("on_poweroff").setText("destroy");
        domain.addElement("on_reboot").setText("restart");
        domain.addElement("on_crash").setText("restart");
    }

    public String getStringConfig() {
        return domainXml.asXML();
    }

}
