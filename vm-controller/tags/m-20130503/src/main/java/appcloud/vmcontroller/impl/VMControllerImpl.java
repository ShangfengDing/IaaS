package appcloud.vmcontroller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.libvirt.Connect;
import org.libvirt.Domain;
import org.libvirt.DomainBlockStats;
import org.libvirt.DomainInterfaceStats;
import org.libvirt.LibvirtException;
import org.libvirt.NetworkFilter;
import org.libvirt.VcpuInfo;

import appcloud.common.model.HostLoad;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstanceMetadata;
import appcloud.common.model.VmInstanceType;
import appcloud.common.model.VmSecurityGroup;
import appcloud.common.model.VmSecurityGroupRule;
import appcloud.common.model.VmVirtualInterface;
import appcloud.common.model.VmVolume;
import appcloud.common.service.VMControllerService;
import appcloud.vmcontroller.VMControllerConf;
import appcloud.vmcontroller.disk.NFSManager;
import appcloud.vmcontroller.model.DiskConfig;
import appcloud.vmcontroller.model.NetworkConfig;
import appcloud.vmcontroller.virt.LibvirtConfig;
import appcloud.vmcontroller.virt.NwFilterConfig;
import appcloud.vmcontroller.virt.RuleConfig;

public class VMControllerImpl implements VMControllerService {
    private static VMControllerImpl service = null;
    private static Connect virtCon = null;

    static final Logger logger = Logger.getLogger(VMControllerImpl.class);
    private VMControllerConf conf = VMControllerConf.getInstance();

    private VMControllerImpl() {
        if (virtCon == null) {
            String conStr = conf.getVirConnStr();
            try {
                virtCon = new Connect(conStr);
            }
            catch (LibvirtException e) {
                logger.info(e.getMessage());
            }
        }
    }

    public static VMControllerImpl getInstance() {
        if (service == null) {
            service = new VMControllerImpl();
            logger.info("service init successful");
        }
        return service;
    }


   
  
    


    


    
    /**
     * 创建虚拟机
     * 
     * @param instanceUUID
     *            实例uuid，name与uuid相同
     * @param instanceType
     *            实例硬件配置，包括vcpus、memory、local_Gb
     * @param volume
     *            volume provider相关参数，挂载到虚拟机的系统盘、数据盘
     * @param vmSecurityGroup
     *            防火墙策略
     * @param vifs
     *            网卡（尚未定义）
     * @throws Exception
     */
    public void createVM(String routingKey,
			             VmInstance instance,
			             VmInstanceType instanceType,
			             List<VmInstanceMetadata> instanceMetadata,
			             List<VmVolume> volume,
			             VmSecurityGroup vmSecurityGroup,
			             List<VmVirtualInterface> vifs) throws Exception {
		logger.info("starting create VM: " + instance.getUuid());
		
		// 1. xml 基本配置
		LibvirtConfig domainXml = new LibvirtConfig();
		domainXml.osMode = conf.getLibvirtType();
		domainXml.emulator = conf.getEmulatorName();
		/**
		* <type arch="x86_64" machine="pc-1.2">hvm</type>
		* arch和machine都不用填写，会自动填上 如果要填写的话，要按照规范填写（capabilites）
		*/
		
		// 2. create_image
		createVolume(instance, volume, domainXml);
		
		// 3. create_network
		createNetwork(vifs, vmSecurityGroup, instanceMetadata, domainXml);
		
		// 4. getGuestConfig
		String xml = getGuestConfig(instance, instanceType, instanceMetadata, domainXml);
		logger.info("CREATE: uuid: " + domainXml.uuid);		
		logger.info("CREATE: domian xml: " + xml);
		
		//5. create_domain
		try {
			Domain d = virtCon.domainDefineXML(xml);
			d.create();
		
			logger.info("create  domian successful!");
		}
		catch (LibvirtException e) {
			logger.error("create error: " + domainXml.uuid);
			throw new Exception("create error: " + domainXml.uuid);
		}
	}
    
    /**
     * 启动虚拟机
     * 
     * @param instance
     * @throws Exception
     */
    public void startVM(String routingKey, VmInstance instance) throws Exception {
    	logger.info("starting start VM: " + instance.getUuid());
        String uuid = instance.getUuid();
        String name = instance.getName();
        Domain d = null;
        
        if (uuid == null && name == null)
        	throw new Exception("vm name and uuid are empty");
        
        if (uuid == null) {
            d = virtCon.domainLookupByName(name);
        } else {
            d = virtCon.domainLookupByUUIDString(uuid);
        }

        if (d == null) {
            throw new Exception("no domain with uuid: '" + uuid + "'or name: '" + name + "'");
        }

        if (d.isActive() > 0) {
            d.destroy();
        }

        d.create();
        
        logger.info("start domian successful!");
    }
    
    /**
     * 删除虚拟机
     * destroy
     * @param instanceUUID
     * @throws Exception
     */
    public void deleteVM(String routingKey, String instanceUUID) throws Exception {
    	logger.info("starting delete VM: " + instanceUUID);
    	
        if (instanceUUID == null) {
            throw new Exception("vm uuid is empty");
        }
        
        Domain d = virtCon.domainLookupByUUIDString(instanceUUID);
        if (d == null) {
            throw new Exception("no domain with uuid: " + instanceUUID);
        }
        if (d.isActive() > 0) {
            d.destroy();
        }
        
        d.undefine();
        logger.info("delete domian successful!");
    }

    /**
     * 停止虚拟机
     * shut down
     * @param instanceUUID
     * @throws Exception
     */
    public void stopVM(String routingKey, String instanceUUID) throws Exception {
    	logger.info("starting stop VM: " + instanceUUID);
    	
        if (instanceUUID == null) {
            throw new Exception("vm uuid is empty");
        }

        Domain d = virtCon.domainLookupByUUIDString(instanceUUID);
        if (d == null) {
            throw new Exception("no domain with uuid: " + instanceUUID);
        }

        if (d.isActive() > 0) {
            d.shutdown();
        }

        while (d.isActive() > 0) {
            Thread.sleep(1000);
        }
        logger.info("stop domian successful!");
    }

    /**
     * 重启虚拟机
     * 
     * @param instanceUUID
     * @throws Exception
     */
    public void rebootVM(String routingKey, String instanceUUID) throws Exception {
    	//注意：reboot 目前尚不支持：Reboot is not supported without the JSON monitor
    	logger.info("starting reboot VM: " + instanceUUID);
    	
        if (instanceUUID == null) {
        	throw new Exception("vm uuid is empty");
        }

        Domain d = virtCon.domainLookupByUUIDString(instanceUUID);

        if (d == null) {
            throw new Exception("no domain with uuid: " + instanceUUID);
        }

        if (d.isActive() > 0) {
            d.destroy();
        }

        d.reboot(0);
        logger.info("reboot domian successful!");
    }

    /**
     * 挂起虚拟机
     * 
     * @param instanceUUID
     * @throws Exception
     */
    public void suspendVM(String routingKey, String instanceUUID) throws Exception {
    	logger.info("starting suspend VM: " + instanceUUID);
    	
        if (instanceUUID == null) {
        	throw new Exception("vm uuid is empty");
        }

        Domain d = virtCon.domainLookupByUUIDString(instanceUUID);

        if (d == null) {
            throw new Exception("no domain with uuid: " + instanceUUID);
        }

        d.suspend();
        logger.info("suspend domian successful!");
    }

    /**
     * 恢复虚拟机
     * 
     * @param instanceUUID
     * @throws Exception
     */
    public void resumeVM(String routingKey, String instanceUUID) throws Exception {
    	logger.info("starting resume VM: " + instanceUUID);
    	
        if (instanceUUID == null) {
        	throw new Exception("vm uuid is empty");
        }

        Domain d = virtCon.domainLookupByUUIDString(instanceUUID);

        if (d == null) {
            throw new Exception("no domain with uuid: " + instanceUUID);
        }

        d.resume();
        logger.info("resume domian successful!");
    }
    
    /**
     * 重置虚拟机配置
     * 
     * @param instanceUUID
     * @param instanceType
     * @param instanceMetadata，目前只传入maxBandwidth
     * @return
     */
    public void resizeVM(String routingKey,
                         String instanceUUID,
                         VmInstanceType instanceType,
                         VmInstanceMetadata instanceMetadata) throws Exception {
    	logger.info("starting resize VM: " + instanceUUID);
    	
    	if (instanceUUID == null) {
    		throw new Exception("vm uuid is empty");
        }
    	
    	//1. 获取domain
        Domain d = virtCon.domainLookupByUUIDString(instanceUUID);

        if (d == null) {
            throw new Exception("no domain with uuid: " + instanceUUID);
        }
        
        /*
         * 2. 获取该domain的xml描述信息，并且将instance type中的描述修改为新的配置
         * 注意：目前暂时使用字符串匹配，后期修改为dom4j，单独封装在一个类库中
         */
        String tempXml = d.getXMLDesc(0);
        String xml = getInstanceTypeConfig(tempXml, instanceType, instanceMetadata);
        
        /*
         * 3. 如果虚拟机活动，关闭虚拟机，undefine配置文件
         */
        if (d.isActive() > 0) {
            logger.info("destroy vm");
            d.destroy();
        }        
        d.undefine();
        logger.debug("RESET: undefine old domain");
        
        //4. 重新定义配置文件，重启机器
        d = virtCon.domainDefineXML(xml);
        d.create();   
        
        logger.info("resize domian successful!");
    }
    
    /*
     * 设置instance type的配置信息，后期修改为操作dom4j
     */
    private String getInstanceTypeConfig(String tempXml,
			 							 VmInstanceType instanceType,
			 							 VmInstanceMetadata instanceMetadata) {
		//修改currentMemory：注意此处暂时使用固定1024*1024做匹配
    	tempXml = tempXml.replaceAll("<currentMemory[\\s\\S]*?>[\\s\\S]*?</currentMemory>", 
									 "<currentMemory>" + instanceType.getMemoryMb() * 1024 * 1024 + "</currentMemory>");
		
    	//修改memory
		tempXml = tempXml.replaceAll("<memory[\\s\\S]*?>[\\s\\S]*?</memory>", 
				 					 "<memory>" + instanceType.getMemoryMb() * 1024 * 1024 + "</memory>");
		
		//修改vcpu个数
		tempXml = tempXml.replaceAll("<vcpu[\\s\\S]*?>[\\s\\S]*?</vcpu>", 
				 					 "<vcpu>" + instanceType.getVcpus() + "</vcpu>");

		//修改带宽，格式如：<outbound average='128' peak='256' burst='256'/>
		if (instanceMetadata != null && instanceMetadata.getKey() == "maxBandwidth") {
			String s = instanceMetadata.getValue();
			int maxBandwidth = (Integer.parseInt(s)) * 1000;
			tempXml = tempXml.replaceAll("<outbound[\\s\\S]*?/>",
										 "<outbound average=\\'" + maxBandwidth + "\\' peak=\\'" + maxBandwidth + "\\' burst=\\'" + maxBandwidth + "\\' />");
		}
		
		return tempXml;
    }
    
    /**
     * 挂载网络硬盘
     * 
     * @param instanceUUID
     * @param volume
     * @return
     */
    public void attachVolume(String routingKey, String instanceUUID, VmVolume volume) throws Exception {
    	logger.info("starting attach volume to VM: " + instanceUUID);
    	
    	if (instanceUUID == null) {
    		throw new Exception("vm uuid is empty");
        }
    	
    	//1. 获取domain
        Domain d = virtCon.domainLookupByUUIDString(instanceUUID);

        if (d == null) {
            throw new Exception("no domain with uuid: " + instanceUUID);
        }
        
        /*
         * 2. 获取该domain的xml描述信息，并且将instance type中的描述修改为新的配置
         * 注意：目前暂时使用字符串匹配，后期修改为dom4j，单独封装在一个类库中
         */
        String tempXml = d.getXMLDesc(0);
        String xml = getAttachVolumeXml(tempXml, volume);
        logger.info(xml);
        /*
         * 3. 如果虚拟机活动，关闭虚拟机，undefine配置文件
         */
        if (d.isActive() > 0) {
            logger.info("destroy vm");
            d.destroy();
        }        
        d.undefine();
        logger.debug("RESET: undefine old domain");
        
        //4. 重新定义配置文件，重启机器
        d = virtCon.domainDefineXML(xml);
        d.create();   
    }
    
    /**
     * 卸载网络硬盘
     * 
     * @param instanceUUID
     * @param volume
     * @return
     */   
    //注意: detach/resize/attach的操作一样,尝试是否能定义函数模板,用函数指针传入具体的修改配置文件的操作! 
    public void detachVolume(String routingKey, String instanceUUID, VmVolume volume) throws Exception {
    	logger.info("starting detach volume to VM: " + instanceUUID);
    	
    	if (instanceUUID == null) {
    		throw new Exception("vm uuid is empty");
        }
    	
    	//1. 获取domain    	
        Domain d = virtCon.domainLookupByUUIDString(instanceUUID);
        if (d == null) {
            throw new Exception("no domain with uuid: " + instanceUUID);
        }
        
        /*
         * 2. 获取该domain的xml描述信息，并且将instance type中的描述修改为新的配置
         * 注意：目前暂时使用字符串匹配，后期修改为dom4j，单独封装在一个类库中
         */
        String tempXml = d.getXMLDesc(0);
        String xml = getDetachVolumeXml(tempXml, volume);
        logger.info(xml);
        /*
         * 3. 如果虚拟机活动，关闭虚拟机，undefine配置文件
         */
        if (d.isActive() > 0) {
            logger.info("destroy vm");
            d.destroy();
        }        
        d.undefine();
        logger.debug("RESET: undefine old domain");
        
        //4. 重新定义配置文件，重启机器
        d = virtCon.domainDefineXML(xml);
        d.create();   
    }
    
    /**
     * 获取虚拟机的 vnc port
     * 
     * @param instanceUUID
     * @return port的字符串形式，如 5001,查询不到返回null
     * @throws Exception
     */
    public String getVncPort(String routringkey, String instanceUUID) throws Exception{    	
    	logger.info("starting get vnc port: " + instanceUUID);
    	if (instanceUUID == null) {
    		throw new Exception("vm uuid is empty");
        }
    	
    	//1. 获取domain    	
        Domain d = virtCon.domainLookupByUUIDString(instanceUUID);
        if (d == null) {
            throw new Exception("no domain with uuid: " + instanceUUID);
        }
        
        /*
         * 2. 获取该domain的xml描述信息，并且将instance type中的描述修改为新的配置
         * 注意：目前暂时使用字符串匹配，后期修改为dom4j，单独封装在一个类库中
         */
        String tempXml = d.getXMLDesc(0);
       
        String vncPort = getVNCPortXML(tempXml);
        
        return vncPort;
    }

    /*
     *  得到domain的配置文件。获取其中的port字段
     *  注意：后期修改为dom4j操作！！
     */
    private String getVNCPortXML(String xml) {
    	String regVnc = "port=[\'\"][\\s\\S]*?[\'\"]";
        logger.info("regVnc = " + regVnc);
        Pattern p = Pattern.compile(regVnc);
        Matcher m = p.matcher(xml);
        
        if (m.find()) {
           String[] tempArr =  m.group().split("=");
           logger.info("length = " + tempArr.length);
           if (tempArr.length == 2) {
               logger.debug(tempArr[1]);
               logger.debug(tempArr[1].replaceAll("[\'\"]", ""));
               return tempArr[1].replaceAll("[\'\"]", "");
           }
        }
        
        return null;
    }
    
    /*
     *  得到domain的配置文件。并根据detach的volume重新写配置文件
     *  注意：后期修改为dom4j操作！！
     */
    private String getDetachVolumeXml(String xml, VmVolume volume) {
        // match the disk tag
        String pattern1 = "<disk[^>]*>[\\s\\S]*?</disk>";
        // match the disk tag which contains <target .. dev="hde" ..>
        String pattern2 = "<disk[^>]*>[\\s\\S]*?<target[^>]*dev=\'" 
        				  + volume.getMountPoint() + "\'[\\s\\S]*?/>[\\s\\S]*?</disk>";
        
        Pattern p = Pattern.compile(pattern1);
        Matcher m = p.matcher(xml);
        String temp = new String();
        
        while (m.find()) {
            if (xml.substring(m.start(), m.end()).matches(pattern2)) {
                temp += xml.substring(0, m.start());
                xml = xml.substring(m.end() + 1);
                m = p.matcher(xml);
            }
        }
        temp += xml;
        
        return temp;
    }    

    /*
     *  得到domain的配置文件。并根据attach的volume重新写配置文件
     *  注意：后期修改为dom4j操作！！
     */
    private String getAttachVolumeXml(String xml, VmVolume volume) {
        String firstStr = xml.substring(0, xml.lastIndexOf("</disk>") + "</disk>".length());
        String lastStr = xml.substring(xml.lastIndexOf("</disk>") + "</disk>".length());
        StringBuilder sb = new StringBuilder(firstStr);
        if (true == NFSManager.handleProviderLocation(volume.getProviderLocation())) {
            sb.append("<disk type=\"file\" device=\"disk\">"
                    + "<driver name=\"qemu\" type=\""
                    + volume.getVolumeType().toString()
                    + "\" />"
                    + "<source file=\""
                    + NFSManager.getSourceFile(volume.getProviderLocation())
                    + "\" />"
                    + "<target dev=\""
                    + volume.getMountPoint()
                    + "\" bus="
                    + "\""
                    + volume.getBus()
                    + "\" />"
                    + "</disk>");

          sb.append(lastStr);
          return sb.toString();
        } else {
            return xml;
        }        
    }

    /**
     * 收集指定虚拟机的负载信息
     * 
     * @param instanceUUID
     * @return
     * @throws Exception
     */
    public HostLoad collectSpecificVMLoad(String routingKey, String instanceUUID) throws Exception {
        Domain d = null;
        HostLoad load = new HostLoad();

        if (instanceUUID != null) {
            d = virtCon.domainLookupByUUIDString(instanceUUID);
        } else {
            throw new Exception("启动虚拟机的时候既没有uuid也没有name！");
        }

        if (d == null) {
            throw new Exception("no domain with uuid");
        }

        if (d.isActive() > 0) {
            d.destroy();
        }

        // cpu
        VcpuInfo[] vcpuInfo = d.getVcpusInfo();
        long cpuTime = vcpuInfo[0].cpuTime;

        // block
        // 获取vda等，怎么获取，少了list接口？
        DomainBlockStats blockStats = d.blockStats("vda"); // todo
        long readBytes = blockStats.rd_bytes;
        long writeBytes = blockStats.wr_bytes;

        // interface
        DomainInterfaceStats vifStats = d.interfaceStats("vnet0");
        long inBytes = vifStats.rx_bytes;
        long outBytes = vifStats.tx_bytes;

        return load;

    }
    
    /**
     * 收集本机的全部虚拟机的负载信息
     * 
     * @param instanceUUID
     * @return
     * @throws Exception
     */
    public List<HostLoad> collectVMLoadList(String routingKey) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * 定义 Network Filters
     * 
     * @param vmSecurityGroup
     *            防火墙组策略，使用的是其name
     * @param vmSecurityGroupRules
     *            具体的防火墙规则列表
     * @throws Exception
     */
    public void defineNWFilter(String routingkey,
                               VmSecurityGroup vmSecurityGroup,
                               List<VmSecurityGroupRule> vmSecurityGroupRules) throws Exception {
    	logger.info("starting define NwFilter: " + vmSecurityGroup.getName());
    	
    	NwFilterConfig nwFilterXml = new NwFilterConfig();
        
        List<RuleConfig> ruleConfigs = SGRulesToRConfigs(vmSecurityGroupRules);
        
        nwFilterXml.setRuleConfigs(ruleConfigs);
        nwFilterXml.setName(vmSecurityGroup.getName());
        String nwFilterXmlStr = nwFilterXml.getNwFilterConf();


    	logger.info("define NwFilter: " + nwFilterXmlStr);
    	virtCon.networkFilterDefineXML(nwFilterXmlStr);
    	logger.info("define NwFilter successful: " + nwFilterXml.getName());
    }
    
    private List<RuleConfig> SGRulesToRConfigs(List<VmSecurityGroupRule> securityGroupRules) {
        if (securityGroupRules != null) {
            logger.debug("change VmSecurityGroupRule to RuleConfig ");
            List<RuleConfig> ruleConfigs = new ArrayList<RuleConfig>();
            for (VmSecurityGroupRule vmSecurityGroupRule : securityGroupRules) {
                ruleConfigs.add(new RuleConfig(vmSecurityGroupRule));
            }
            return ruleConfigs;
        } else {
            logger.debug("securityGroupRules is null ");
            return null;
        }

    }
    
    /**
     * 
     * 取消定义 Network Filters
     * 
     * @param securityGroupName
     *            防火墙规则组，使用是其name
     * @throws Exception
     */
    public void deleteNWFilter(String routingkey, String securityGroupName) throws Exception {
    	logger.info("starting delete NwFilter: " + securityGroupName);
        
        logger.debug("start undefineNwFilter with name" + securityGroupName);
        NetworkFilter networkFilter = virtCon.networkFilterLookupByName(securityGroupName);
        if(networkFilter == null){
            throw new Exception("now NetworkFilter with name" + securityGroupName);
        }
        networkFilter.undefine();
        
        //注意！此处可能需要先暂停使用该network filter的机器，并且删除这些filter绑定的机器
    }
    
    /**
     * 
     * 绑定防火墙组策略至给定虚拟机
     * @param instanceUUID
     * @param vmSecurityGroup
     *              需要绑定防火墙组策略
     * @throws Exception
     */
    public void changeNWFilter(String routingkey, 
    						   String instanceUUID, 
    						   VmSecurityGroup vmSecurityGroup) throws Exception{
    	logger.info("starting change NwFilter: " + instanceUUID);
    	
        Domain d = virtCon.domainLookupByUUIDString(instanceUUID);
        if(d == null){
            throw new Exception("no domain with instanceUUID " + instanceUUID);
        }
        
        /*
         * 2. 获取该domain的xml描述信息，并且将filter中的ref修改为新的nw filter
         * 注意：目前暂时使用字符串匹配，后期修改为dom4j，单独封装在一个类库中
         */
        String tempXml = d.getXMLDesc(0);
        String xml = tempXml.replaceAll("<filterref[\\s\\S]*?/>", 
        								"<filterref filter=\'" + vmSecurityGroup.getName() + "\' />");
        logger.info(xml);
        /*
         * 3. 如果虚拟机活动，关闭虚拟机，undefine配置文件
         */
        if (d.isActive() > 0) {
            logger.info("destroy vm");
            d.destroy();
        }        
        d.undefine();
        logger.debug("RESET: undefine old domain");
        
        //4. 重新定义配置文件，重启机器
        d = virtCon.domainDefineXML(xml);
        d.create();   
        
        logger.info("change NwFilter successful!");
    }
    
    /*
     * 创建虚拟机时，构造xml配置文件
     */
    public String getGuestConfig(VmInstance instance,
                                 VmInstanceType instanceType,
                                 List<VmInstanceMetadata> instanceMetadata,
                                 LibvirtConfig domainXml) {
        // 1. basic prop
        domainXml.name = instance.getName();
        domainXml.uuid = instance.getUuid();
        domainXml.vcpus = instanceType.getVcpus();
        domainXml.memory *= instanceType.getMemoryMb();
        // 2. os
        if (instance.getOsMode() != null) {
            domainXml.osMode = instance.getOsMode().toString();
        }
        // if(instance.getOsArch() !=null ){
        // domainXml.osArch = instance.getOsArch().toString();
        // }
        if (instance.getOsType() != null) {
            domainXml.osType = instance.getOsType().toString();
        }

        domainXml.osBootDev.add("hd");

        /*
         * feature clock cpu tune
         */

        /*
         * device:storage device:network
         */

        String str = domainXml.formatDom().getStringConfig();

        return str;
    }

    /*
     * 创建镜像和volume
     */
    private void createVolume(VmInstance instance, List<VmVolume> volume, LibvirtConfig domainXml)
            throws Exception {
        domainXml.diskConfigList = new ArrayList<DiskConfig>();
        for (int i = 0; i < volume.size(); i++) {
            if (true == NFSManager.handleProviderLocation(volume.get(i).getProviderLocation())) {
                String sourceFile = NFSManager.getSourceFile(volume.get(i).getProviderLocation());
                if (instance.getRootDeviceLocation().equals(volume.get(i).getMountPoint())) {
                	//注意:此处 disk name固定写为qemu,后期再确认是否需要添加扩展
                    DiskConfig disk = new DiskConfig("qemu",
                                                     volume.get(i).getVolumeType().toString(),
                                                     sourceFile,
                                                     volume.get(i).getMountPoint(),
                                                     volume.get(i).getBus(),
                                                     null);
                    domainXml.diskConfigList.add(0, disk);
                    logger.info("处理完成系统盘");
                } else {
                    DiskConfig disk = new DiskConfig("qemu",
                                                     volume.get(i).getVolumeType().toString(),
                                                     sourceFile,
                                                     volume.get(i).getMountPoint(),
                                                     volume.get(i).getBus(),
                                                     null);
                    domainXml.diskConfigList.add(disk);
                    logger.info("处理完成数据盘");
                }
            } else {
                throw new Exception("远端挂载点挂载失败");
            }
        }
    }
    
    /*
     * 创建网络
     */
    private void createNetwork(List<VmVirtualInterface> vifs,
                               VmSecurityGroup vmSecurityGroup,
                               List<VmInstanceMetadata> instanceMetadata,
                               LibvirtConfig domainXml) {
        domainXml.networkConfigList = new ArrayList<NetworkConfig>();
        /*
         * 从metadata中获取maxBandwidth,配置带宽
         */
        int maxBandwidth = 0;
        for (int i = 0; i < instanceMetadata.size(); i++) {
            if (instanceMetadata.get(i).getKey() == "maxBandwidth") {
            	//后期把单位换算修改为常量,读配置文件
                String s = instanceMetadata.get(i).getValue();
                maxBandwidth = (Integer.parseInt(s)) * 1000;
                break;
            }
        }
        /*
         * 配置vif，包括防火墙、带宽
         */
        for (int i = 0; i < vifs.size(); i++) {
            NetworkConfig network = new NetworkConfig();
            if (null == vifs.get(i).getIpPoolId()) {
                network.setNetworkType(conf.getDefaultNetwork());
                network.setNetworkMac(vifs.get(i).getMac());
                network.setNetworkIp(vifs.get(i).getAddress());
                //设置防火墙
                if (vmSecurityGroup != null) {
                	network.setFilterName(vmSecurityGroup.getName());
                } else {
                	network.setFilterName("default");
                }
                //设置连接到宿主机的网桥
                if (vifs.get(i).getNetworkType().equals("private")) {
                    network.setNetworkBridge(conf.getPrivateNetwork());
                } else if (vifs.get(i).getNetworkType().equals("public")) {
                    network.setNetworkBridge(conf.getPublicNetwork());
                    //设置公网出口带宽
                    if (maxBandwidth != 0) {
                        network.setOutbound(String.valueOf(maxBandwidth));
                    }
                }
                //设置模式
                network.setModelType("virtio");
            }
            domainXml.networkConfigList.add(network);
        }
    }

    /*
     * 获取所有活动主机的信息
     */
    public List<String> getActiveDomaimUUIDList() {
        List<String> domainList = new ArrayList<String>();
        try {
            for (int id : virtCon.listDomains()) {
                System.out.println("lookup id:　" + id);
                Domain d = virtCon.domainLookupByID(id);
                domainList.add(d.getUUIDString());
            }
        }
        catch (LibvirtException e) {
            e.printStackTrace();
        }
        return domainList;
    }

    public Domain getActiveDomain(String instanceUUID) throws Exception {
        if (instanceUUID == null) {
            throw new Exception("缺少uuid！");
        }

        Domain d = virtCon.domainLookupByUUIDString(instanceUUID);

        if (d == null) {
            throw new Exception("no domain with uuid: " + instanceUUID);
        }

        return d;
    }



        
}
    
   
