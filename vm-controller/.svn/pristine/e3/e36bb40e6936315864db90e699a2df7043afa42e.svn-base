package appcloud.vmcontroller.virt;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import appcloud.common.model.*;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;

import appcloud.rpc.tools.RpcException;
import appcloud.vmcontroller.VMControllerConf;
import appcloud.vmcontroller.model.DiskConfig;
import appcloud.vmcontroller.model.NetworkConfig;
import appcloud.vmcontroller.util.VMCUtil;

public class LibvirtConfig {
    private static Logger logger = Logger.getLogger(LibvirtConfig.class);
    private static final String TAP_PREFIX = "v_";
    public String osType;  //当为cdrom时，为null
    public String osMode;
    private List<String> osBootDev;
    private List<DiskConfig> diskConfigList;
    public List<NetworkConfig> networkConfigList;
    public VmInstance vmInstance;
    private Element devices;
    private Document domainXml;
    private Element domain;
    
    
    /**
     * 构造初始配置文件：
     * 1）basic prop
     * 2）os参数设置
     * @param instance
     * @param instanceType
     * @throws RpcException 
     */
    public LibvirtConfig(VmInstance instance,
    		    		 VmInstanceType instanceType) throws Exception {
    	super();
    	checkParams(instance, instanceType);

    	this.domainXml = DocumentHelper.createDocument();
    	//<domain type='kvm' xmlns:qemu='http://libvirt.org/schemas/domain/qemu/1.0'>
    	 this.domain = domainXml.addElement("domain").addAttribute("type", "kvm")
                 .addNamespace("qemu", "http://libvirt.org/schemas/domain/qemu/1.0");
        
		
		String vmName = VMCUtil.generateName(instance.getUuid(), instance.getName());
		domain.addElement("name").setText(vmName);
        domain.addElement("uuid").setText(instance.getUuid());
        domain.addElement("memory").setText(String.valueOf(VMCUtil.memUnitConvert(instanceType.getMemoryMb())));
        domain.addElement("vcpu").setText(String.valueOf(instanceType.getVcpus()));
        
        domain.addElement("cpu").
				addElement("topology").addAttribute("sockets", "1").
				addAttribute("cores", String.valueOf(instanceType.getVcpus())).
				addAttribute("threads", "1");
        this.osMode = VMControllerConf.LIBVIRT_TYPE;
        if (!VMCUtil.isEmpty(instance.getOsMode())) {
            this.osMode = instance.getOsMode().toString();
        } 
        if (!VMCUtil.isEmpty(instance.getOsType())) {
        	this.osType = instance.getOsType().toString();	
        }
        
        //初始化 osBootDev
        setBootDev(instance.getRootDeviceLocation());
        this.diskConfigList = new ArrayList<DiskConfig>();
        this.networkConfigList = new ArrayList<NetworkConfig>();
        this.vmInstance = instance;
        
    }
    
//    public LibvirtConfig(){
//        this.domainXml = DocumentHelper.createDocument();
//        this.domain = domainXml.addElement("domain").addAttribute("type", "kvm")
//                .addNamespace("qemu", "http://libvirt.org/schemas/domain/qemu/1.0");
//        
//        
//        QName commandLine = new QName("qemu:commandline");
//        QName arg = new QName("qemu:arg");
//        Element qemu = domain.addElement(commandLine);
//        qemu.addElement(arg).addAttribute("value", "cpu");
//        qemu.addElement(arg).addAttribute("value", "host");
//        //this.formatQemu();
//        logger.info(domainXml.asXML());
//    }
//    
//    public static void main(String[] args) {
//       
//        LibvirtConfig libvirtConfig = new LibvirtConfig();
//    }
    /**
     * 生成xml描述文件：
     * 参数解析、流程控制
     * @param mds
     * @param volumes
     * @param vmSecurityGroup
     * @param vifs
     * @return
     * @throws Exception 
     */
    public String generateDesXML(List<VmInstanceMetadata> mds,
            					 List<VmVolume> volumes,
            					 VmSecurityGroup vmSecurityGroup,
            					 List<VmVirtualInterface> vifs) throws Exception {
		createVolume(volumes);
		createNetwork(vifs, vmSecurityGroup);
		setMetadata(mds);
		// 准备好数据后，创建xml --- getGuestConfig
    	formatDesXml();
    	return domainXml.asXML();
    }
      
    /**
     * 生成xml配置文件
     * @return
     */
    private void formatDesXml() {
        formatOs();
        formatDefaultConfig();
        if (diskConfigList.size() != 0) {
            formatStorageDevice();
        }
        if (networkConfigList.size() != 0) {
            formatNetworkDevice();
        }
        setTablet();
        setGraphicsVNC(vmInstance);
        logger.info(domain);
        
       /* formatQemu();*/
    }


    private void formatQemu() {
        QName commandLine = new QName("qemu:commandline");
        QName arg = new QName("qemu:arg");
        Element qemu = domain.addElement(commandLine);
        qemu.addElement(arg).addAttribute("value", "-cpu");
        qemu.addElement(arg).addAttribute("value", "host");
    }

    private void setTablet() {
       devices.addElement("input").addAttribute("type", "tablet")
                .addAttribute("bus", "usb");
    }

    /**
     * <os>
     * <type>hvm</type>
     * <boot dev='hd'/>
     * </os>
     */
    private void formatOs() {
        Element os = domain.addElement("os");
        os.addElement("type").setText(osMode);
        
        //启动顺序
        if (osBootDev != null) {
            Iterator<String> it = osBootDev.iterator();
            while (it.hasNext()){
                os.addElement("boot").addAttribute("dev", it.next());
            }
        }
        
    }

    /**
     * 缺省配置：feature、clock
     */
    
    private void formatDefaultConfig() {
    	Element features = domain.addElement("features");
        features.addElement("acpi");
        features.addElement("apic");
        features.addElement("pae");
        Element clock = domain.addElement("clock").addAttribute("offset", "localtime");
        clock.addElement("timer").addAttribute("name", "pit").addAttribute("tickpolicy", "delay");
        clock.addElement("timer").addAttribute("name", "rtc").addAttribute("tickpolicy", "catchup");
        domain.addElement("on_poweroff").setText("destroy");
        domain.addElement("on_reboot").setText("restart");
        domain.addElement("on_crash").setText("restart");
    }
    
    private void setGraphicsVNC(VmInstance instance) {
        Element graphicsVNC = devices.addElement("graphics")
                                .addAttribute("type", "vnc")
                                .addAttribute("port", "-1")
                                .addAttribute("autoport", "yes")
                                .addAttribute("listen", "0.0.0.0")
                                .addAttribute("passwd", VMCUtil.getVNCPasswd(instance.getId(), instance.getUuid(),instance.getUserId()));
        graphicsVNC.addElement("listen")
                   .addAttribute("type", "address")
                   .addAttribute("address", "0.0.0.0");                             
    }
    
    private void formatStorageDevice() {
        devices = domain.addElement("devices");
        if (VMControllerConf.EMULATOR_NAME != null) {
            devices.addElement("emulator").setText(VMControllerConf.EMULATOR_NAME);
        }
        for (DiskConfig dc : diskConfigList) {
            Element disk = devices.addElement("disk")
                                  .addAttribute("type", "file")
                                  .addAttribute("device", dc.getDiskDevice());   //disk, cdrom
            //cdrom, driver type --- raw
            if (dc.getDiskDevice().equals("cdrom")) {
            	disk.addElement("driver")
                	.addAttribute("name", "qemu")
                	.addAttribute("type", "raw");
            } else {
            	disk.addElement("driver")
                	.addAttribute("name", "qemu")
                	.addAttribute("type", "qcow2");	
            }
            
            disk.addElement("source").addAttribute("file", dc.getSourceFile());
            disk.addElement("target")
                .addAttribute("dev", dc.getTargetDev())
                .addAttribute("bus", dc.getTargetBus());
        }
    }

    private void formatNetworkDevice() {
    	//默认为虚拟机创建两个网卡，一个内网网卡，一个外网网卡
    	//内网网卡一定会分配IP
    	//外网网卡可能不分配IP
    	for (NetworkConfig nc : networkConfigList) {
            Element netInterface = devices.addElement("interface")
                                          .addAttribute("type", nc.getNetworkType());
            // 采用桥接网络,如下配置:
            if (nc.getNetworkType().equals("bridge")) {
            	//设置基本网卡信息
                netInterface.addElement("mac")
                            .addAttribute("address", nc.getNetworkMac());
                netInterface.addElement("source")
                            .addAttribute("bridge", nc.getNetworkBridge());
                if(nc.getNetworkIp() != null && !"".equals(nc.getNetworkIp()))
                	netInterface.addElement("ip")
                            	.addAttribute("address", nc.getNetworkIp());
                
                //设置为openvswitch网桥类型
                netInterface.addElement("virtualport")
                			.addAttribute("type", "openvswitch");
                //设置网卡接口名称
                logger.info("tap=" + getTap(nc.getNetworkMac()));
                netInterface.addElement("target")
                			.addAttribute("dev", getTap(nc.getNetworkMac()));
                //设置网卡型号为Virtio
                netInterface.addElement("model").
                			 addAttribute("type", "virtio");

                Element bandWidth = netInterface.addElement("bandwidth");
                bandWidth.addElement("inbound")
                        .addAttribute("average", "1000")
                        .addAttribute("peak", "1000")
                        .addAttribute("burst", "512");

                //设置防火墙
                if (nc.getFilterName() != null) {
                	netInterface.addElement("filterref")
    							.addAttribute("filter", nc.getFilterName());                	
                }
            }
        }
    }
    
    //-----------------------以下方法，创建volume、network-----------------------------------
    
    /**
     * 填充 diskConfigList
     * @param volumes
     * @throws Exception 
     */
    private void createVolume(List<VmVolume> volumes) throws Exception {
        for (VmVolume vo : volumes) {
            DiskConfig dc;
			try {
				dc = new DiskConfig( vo);
			} catch (Exception e) {
				logger.error("create volume fail: " + vo.getVolumeUuid());
				throw e;
			}
            diskConfigList.add(dc);
            logger.info("create disk: " + dc);
        }
    }
    
    /**
     * 填充networkConfigList：
     * @param vifs
     * @param securityGroup
     */
    private void createNetwork(List<VmVirtualInterface> vifs,
   				              VmSecurityGroup securityGroup) {
		for (VmVirtualInterface vif : vifs) {
			NetworkConfig network = null;
			if (securityGroup == null) {
				network = new NetworkConfig(vif,
				                            VMControllerConf.DEFAULT_NETWORK,
				                            VMControllerConf.PRIVATE_NETWORK,
				                            VMControllerConf.PUBLIC_NETWORK,
										    null);	
			} else {
				String sgName = VMCUtil.generateName(securityGroup.getUuid());
				network = new NetworkConfig(vif,
				                            VMControllerConf.DEFAULT_NETWORK,
                                            VMControllerConf.PRIVATE_NETWORK,
                                            VMControllerConf.PUBLIC_NETWORK,
                                            sgName);
			}
			networkConfigList.add(network);
		}

	}
    
    /**
     * 设置相关的metadata
     * @param mds
     */
    private void setMetadata(List<VmInstanceMetadata> mds) {
    	logger.debug("instance metadata" + mds);
    	
		for (VmInstanceMetadata md : mds) {
			/*
			* 配置带宽：从metadata中获取maxBandwidth
			*/
			if (md.getKey().equals("maxBandwidth")) {
				//value is not null && value is not 0
				if (md.getValue() != null && (!md.getValue().equals("0"))) {
					Integer maxBandwidth = Integer.valueOf(md.getValue());					
					logger.debug("limit maxBandwidth: " + maxBandwidth);
					setMaxBandwidth(maxBandwidth);
					break;
				}				
			}
		}
		    	
    }
    
    private void setMaxBandwidth(Integer maxBandwidth) {
    	String bandwidth = String.valueOf(VMCUtil.bandwidthUnitConvert(maxBandwidth));
    	/*
    	 * 原有：目前公网、内网同时受限，只限制出口带宽
    	 * 策略更新为：只限制公网带宽，同时限制入口、出口带宽
    	 */
    	for (NetworkConfig nc : networkConfigList) {
    		if (nc.getPublicOrPrivate().equals("public")) {
    			nc.setOutbound(bandwidth);
        		nc.setInbound(bandwidth);
        		break;
    		}
    	}
    }
    
    private void setBootDev(String rootDeviceLocation) {
    	this.osBootDev = new ArrayList<String>();
    	if (rootDeviceLocation.equals("cdrom")) {
        	osBootDev.add("cdrom");
        } else {
        	osBootDev.add("hd");
        }
    }

    /**
     * @Description 通过MAC地址生成一个tap名称
     * @param str  MAC地址
     * @return
     */
    private String getTap(String str) {
		logger.info("MAC="+str);
		str = str.replaceAll(":", "");
		return TAP_PREFIX + str;
	}
    
    private void checkParams(VmInstance instance, VmInstanceType instanceType) throws Exception {
    	if (VMCUtil.isEmpty(instance.getUuid()) || VMCUtil.isEmpty(instance.getName())){
    		String why = "check Params error : instance lose uuid or name";
   		    logger.error(why + instance);
            throw new Exception(why);
   		} else if(VMCUtil.isEmpty(instance.getRootDeviceLocation())) {
   			String why = "check Params error : instance with " + instance.getUuid() + " root device location is null";
   		    logger.error(why + instance);
            throw new Exception(why);
   		} else if (VMCUtil.isEmpty(instanceType.getVcpus()) || VMCUtil.isEmpty(instanceType.getMemoryMb())) {
   			String why = "check Params error : instance instance with " + instance.getUuid() + " lose vcpu or memory";
   		    logger.error(why + instanceType);
            throw new Exception(why);
   		}
    }

//    public static void main (String args[]) throws Exception {
//        VmInstance instance = new VmInstance(38, "3489c51377094512ab7fac9052743b4c", "haha", 10351, "0026B94CDF54", 1, 1, Timestamp.valueOf("2017-11-22 06:26:28.0"), Timestamp.valueOf("2017-11-22 06:26:28.0"),
//                Timestamp.valueOf("2017-11-22 06:26:28.0"), 10, VmInstance.VmStatusEnum.BUILDING, VmInstance.TaskStatusEnum.READY, "0d3bf40d2a9547d682447b4dd72363b9", 320, "vda", 171, VmImage.VmImageOSModeEnum.HVM, VmImage.VmImageOSArchEnum.X86, null, null, null);
//        VmInstanceType instanceType = new VmInstanceType(320, "735e2edea1184f45ac0b87ebe1936abc", "10351-flavor", 1, 1, 20);
//        LibvirtConfig libvirtConfig = new LibvirtConfig(instance, instanceType);
//
//
//        List<VmVirtualInterface> vifs = new ArrayList<VmVirtualInterface>();
//        VmVirtualInterface vmVirtualInterface = new VmVirtualInterface(55, "3489c51377094512ab7fac9052743b4c", "192.168.1.63",
//                "00:11:1E:F9:98:A9", "private", null);
//        vifs.add(vmVirtualInterface);
//        VmSecurityGroup vmSecurityGroup = new VmSecurityGroup(171, "5b82166e8b7244b9b81cb78d8a90ba1c", "开放所有端口",
//                "默认开放所有端口", 24);
//        VmVolume vmVolume = new VmVolume(18, "e10a8105cc3f41b4baa6dcaf362c797f", 10351, Timestamp.valueOf("2017-11-22 06:26:29.0"), Timestamp.valueOf("2017-11-22 06:26:33.0"), Timestamp.valueOf("2017-11-22 06:26:29.0"), "e10a8105cc3f41b4baa6dcaf362c797f",
//                "e10a8105cc3f41b4baa6dcaf362c797f", "e10a8105cc3f41b4baa6dcaf362c797f", 1, 1, "0026B94CDF54", "0d3bf40d2a9547d682447b4dd72363b9", null, 20, VmVolume.VmVolumeTypeEnum.QCOW2, VmVolume.VmVolumeUsageTypeEnum.SYSTEM, "192.168.1.24:/srv/nfs4/:volumes/d/i/e10a8105cc3f41b4baa6dcaf362c797f.img", "192.168.1.24", "/srv/nfs4/",
//                "volumes/d/i/e10a8105cc3f41b4baa6dcaf3624597f.img", null, VmVolume.VmVolumeStatusEnum.AVAILABLE, VmVolume.VmVolumeAttachStatusEnum.ATTACHED, "3489c51377094512ab7fac9052743b4c", "virtio", "vda", Timestamp.valueOf("2017-11-22 06:26:29.0"), null, null, null, null, null);
//        List<VmVolume> volumes = new ArrayList<VmVolume>();
//        volumes.add(vmVolume);
//        libvirtConfig.createVolume(volumes);
//        libvirtConfig.createNetwork(vifs, vmSecurityGroup);
//        libvirtConfig.formatDesXml();
//        String xml = libvirtConfig.domainXml.asXML();
//        System.out.println(xml);

//        String mac = "00:11:76:DD:41:C9";
//        String ovsInter = "v_"+mac.replace(":","");
//        System.out.println(ovsInter);
//    }

}
