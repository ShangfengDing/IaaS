package appcloud.vmcontroller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import appcloud.common.model.VmImage.VmImageOSModeEnum;
import appcloud.common.model.VmImage.VmImageOSTypeEnum;
import appcloud.common.model.VmVolume.VmVolumeTypeEnum;
import appcloud.common.service.VMControllerService;
import appcloud.vmcontroller.VMControllerConf;
import appcloud.vmcontroller.disk.NFSManager;
import appcloud.vmcontroller.virt.DiskConfig;
import appcloud.vmcontroller.virt.LibvirtConfig;
import appcloud.vmcontroller.virt.NetworkConfig;
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

    public void defineNwFilter(String routringkey,
                               VmSecurityGroup vmSecurityGroup,
                               List<VmSecurityGroupRule> vmSecurityGroupIngressRules)
            throws Exception {
        NwFilterConfig nwFilterXml = new NwFilterConfig();
        logger.info("starting define NwFilter: " + vmSecurityGroup.getName());
        List<RuleConfig> ruleConfigs = SGRulesToRConfigs(vmSecurityGroupIngressRules);
        nwFilterXml.setRuleConfigs(ruleConfigs);
        nwFilterXml.setName(vmSecurityGroup.getName());
        String nwFilterXmlStr = nwFilterXml.getNwFilterConf();
        if (nwFilterXml.getName() == null) {
            throw new Exception("define nwFilter 缺少name！");
        }
        try {
            virtCon.networkFilterDefineXML(nwFilterXmlStr);
        }
        catch (LibvirtException e) {
            logger.error("DEFINE NEFILTER error: " + nwFilterXml.getName());
            logger.info(e.getMessage());
        }

    }
    
    public void undefineNwFilter(String routingkey, String securityGroupName) throws Exception {
       if(securityGroupName == null){
           throw new Exception("缺少SecurityGroup Name");
       }
       logger.debug("start undefineNwFilter with name" + securityGroupName);
       NetworkFilter networkFilter = virtCon.networkFilterLookupByName(securityGroupName);
       if(networkFilter == null){
           throw new Exception("now NetworkFilter with name" + securityGroupName);
       }
       networkFilter.undefine();
    }
    

    private List<RuleConfig> SGRulesToRConfigs(List<VmSecurityGroupRule> securityGroupRules) {
        if (securityGroupRules != null) {
            logger.debug("change VmSecurityGroupIngressRule to RuleConfig ");
            List<RuleConfig> ruleConfigs = new ArrayList<RuleConfig>();
            for (VmSecurityGroupRule vmSecurityGroupIngressRule : securityGroupRules) {
                ruleConfigs.add(new RuleConfig(vmSecurityGroupIngressRule));
            }
            return ruleConfigs;
        } else {
            logger.debug("securityGroupRules is null ");
            return null;
        }

    }

    public void startVM(String routingKey, VmInstance instance) throws Exception {
        String uuid = instance.getUuid();
        String name = instance.getName();
        Domain d = null;

        if (uuid == null) {
            if (name != null) {
                d = virtCon.domainLookupByName(name);
            } else {
                logger.info("START: uuid: " + uuid);
                throw new Exception("启动虚拟机的时候既没有uuid也没有name！");
            }
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
    }

    public void deleteVM(String routingKey, String instanceUUID) throws Exception {
        if (instanceUUID == null) {
            logger.info("DELETE: uuid: " + instanceUUID);
            throw new Exception("缺少uuid！");
        }
        Domain d = virtCon.domainLookupByUUIDString(instanceUUID);
        if (d == null) {
            throw new Exception("no domain with uuid: " + instanceUUID);
        }
        if (d.isActive() > 0) {
            d.destroy();
        }
        d.undefine();
        // 系统镜像是在哪？数据盘？删除之类的工作。
    }

    public void stopVM(String routingKey, String instanceUUID) throws Exception {

        if (instanceUUID == null) {
            logger.info("STOP: uuid: " + instanceUUID);
            throw new Exception("缺少uuid！");
        }

        Domain d = virtCon.domainLookupByUUIDString(instanceUUID);
        if (d == null) {
            throw new Exception("no domain with uuid: " + instanceUUID);
        }

        if (d.isActive() > 0) {
            d.destroy();
        }

        while (d.isActive() > 0) {
            Thread.sleep(1000);
        }
    }

    public void rebootVM(String routingKey, String instanceUUID) throws Exception {

        if (instanceUUID == null) {
            logger.info("REBOOT: uuid: " + instanceUUID);
            throw new Exception("缺少uuid！");
        }

        Domain d = virtCon.domainLookupByUUIDString(instanceUUID);

        if (d == null) {
            throw new Exception("no domain with uuid: " + instanceUUID);
        }

        if (d.isActive() > 0) {
            d.destroy();
        }

        d.reboot(0);
    }

    public void suspendVM(String routingKey, String instanceUUID) throws Exception {
        if (instanceUUID == null) {
            throw new Exception("缺少uuid！");
        }

        Domain d = virtCon.domainLookupByUUIDString(instanceUUID);

        if (d == null) {
            throw new Exception("no domain with uuid: " + instanceUUID);
        }

        d.suspend();
    }

    public void resumeVM(String routingKey, String instanceUUID) throws Exception {

        if (instanceUUID == null) {
            throw new Exception("缺少uuid！");
        }

        Domain d = virtCon.domainLookupByUUIDString(instanceUUID);

        if (d == null) {
            throw new Exception("no domain with uuid: " + instanceUUID);
        }

        d.resume();
    }

    public void detachVolume(String routingKey, String instanceUUID, VmVolume volume) throws Exception {
        if (instanceUUID == null) {
            throw new Exception("缺少uuid！");
        }
        Domain d = virtCon.domainLookupByUUIDString(instanceUUID);
        String tempXml = d.getXMLDesc(0);
        String xml = getDetachVolumeXml(tempXml, volume);
        if(xml == null){
            throw new Exception("getDetachVolumeXml return null");
        }
        // 如果虚拟机活动，关闭虚拟机
        if (d.isActive() > 0) {
            d.destroy();
        }
        d.undefine();
        logger.debug("RESET: undefine old domain");
        d = virtCon.domainDefineXML(xml);
        logger.debug("RESET: new domian xml: " + xml);
        d.create();
    }

    private String getDetachVolumeXml(String xml, VmVolume volume) {
        // match the disk tag
        String pattern1 = "<disk[^>]*>[\\s\\S]*?</disk>";
        // match the disk tag which contains <target .. dev="hde" ..>
        String pattern2 = "<disk[^>]*>[\\s\\S]*?<target[^>]*dev=\"" + volume.getMountPoint() + "\"[\\s\\S]*?/>[\\s\\S]*?</disk>";
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

    public void attachVolume(String routingKey, String instanceUUID, VmVolume volume) throws Exception {
        if (instanceUUID == null) {
            throw new Exception("缺少uuid！");
        }
        Domain d = virtCon.domainLookupByUUIDString(instanceUUID);
        String tempXml = d.getXMLDesc(0);
        String xml = getAttachVolumeXml(tempXml, volume);
        if(xml == null){
            throw new Exception("getAttachVolumeXml return null");
        }
        // 如果虚拟机活动，关闭虚拟机
        if (d.isActive() > 0) {
            d.destroy();
        }
        d.undefine();
        logger.debug("RESET: undefine old domain");
        d = virtCon.domainDefineXML(xml);
        logger.debug("RESET: new domian xml: " + xml);
        d.create();
    }

    // 得到domain的配置文件。并根据attach的volume重新写配置文件
    private String getAttachVolumeXml(String xml, VmVolume volume) {
        //logger.debug("volume.getProviderLocation() = " + volume.getProviderLocation());
        String firstStr = xml.substring(0, xml.lastIndexOf("</disk>") + "</disk>".length());
        String lastStr = xml.substring(xml.lastIndexOf("</disk>") + "</disk>".length());
        StringBuilder sb = new StringBuilder(firstStr);
        if(true == NFSManager.handleProviderLocation(volume.getProviderLocation())){
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
          //logger.debug("RESET: new domian xml: " + sb.toString());
          return sb.toString();
        }else{
            return null;
        }
        
    }

    public HostLoad collectSpecificVM(String routingKey, String instanceUUID) throws Exception {
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

    private void createImage(VmInstance instance, List<VmVolume> volume, LibvirtConfig domainXml)
            throws Exception {
        domainXml.diskConfigList = new ArrayList<DiskConfig>();
        for (int i = 0; i < volume.size(); i++) {
            if (true == NFSManager.handleProviderLocation(volume.get(i).getProviderLocation())) {
                String sourceFile = NFSManager.getSourceFile(volume.get(i).getProviderLocation());
                if (instance.getRootDeviceLocation().equals(volume.get(i).getMountPoint())) {
                    DiskConfig disk = new DiskConfig("qemu",
                                                     volume.get(i).getVolumeType().toString(),
                                                     sourceFile,
                                                     volume.get(i).getMountPoint(),
                                                     volume.get(i).getBus(),
                                                     "1");
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

    private void createNetwork(List<VmVirtualInterface> vifs,
                               VmSecurityGroup vmSecurityGroup,
                               List<VmInstanceMetadata> instanceMetadata,
                               LibvirtConfig domainXml) {
        domainXml.networkConfigList = new ArrayList<NetworkConfig>();
        /*
         * 获取maxBandwidth
         */
        int maxBandwidth = 0;
        for (int i = 0; i < instanceMetadata.size(); i++) {
            if (instanceMetadata.get(i).getKey() == "maxBandwidth") {
                String s = instanceMetadata.get(i).getValue();
                maxBandwidth = (Integer.parseInt(s)) * 1000;
                break;
            }
        }
        for (int i = 0; i < vifs.size(); i++) {
            NetworkConfig network = new NetworkConfig();
            if (vifs.get(i).getIpPoolId() == null) {
                network.setNetworkType(conf.getDefaultNetwork());
                network.setNetworkMac(vifs.get(i).getMac());
                network.setNetworkIp(vifs.get(i).getAddress());
                network.setFilterName(vmSecurityGroup.getName());
                if (vifs.get(i).getNetworkType().equals("private")) {
                    network.setNetworkBridge(conf.getPrivateNetwork());
                } else if (vifs.get(i).getNetworkType().equals("public")) {
                    network.setNetworkBridge(conf.getPublicNetwork());
                    if (maxBandwidth != 0) {
                        network.setOutbound(String.valueOf(maxBandwidth));
                    }
                }
                network.setModelType("virtio");
            }
            domainXml.networkConfigList.add(network);
        }
    }

    // private List<String> getDomainUuidList() {
    // List<String> list = new ArrayList<String>();
    // try {
    // System.out.println("length1:" + virtCon.listDomains().length);
    // System.out.println("length2:" + virtCon.listDefinedDomains().length);
    // System.out.println("num:" + virtCon.numOfDomains());
    // for (String name : virtCon.listDefinedDomains()) {
    // System.out.println("lookup name:　" + name);
    // if (name != null) {
    // Domain d = virtCon.domainLookupByName(name);
    // list.add(d.getUUIDString().replace("-", ""));
    // }
    // }
    // for (int id : virtCon.listDomains()) {
    // System.out.println("lookup id:　" + id);
    // Domain d = virtCon.domainLookupByID(id);
    // list.add(d.getUUIDString().replace("-", ""));
    // }
    // }
    // catch (Exception ex) {}
    // return list;
    // }

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

    public List<HostLoad> collectSpecificVM(String routingKey) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public void resizeVM(String routingKey,
                         String instanceUUID,
                         VmInstanceType instanceType,
                         VmInstanceMetadata instanceMetadata) throws Exception {
        if(instanceUUID == null){
            throw new Exception("缺少uuid！");
        }
        logger.debug("begin find the domain instanceUUID is" + instanceUUID);
        Domain d = virtCon.domainLookupByUUIDString(instanceUUID);
        if(d == null){
            throw new Exception("no domain with uuid: " + instanceUUID);
        }
        String tempXml = d.getXMLDesc(0);
        String xml = resizeVM(tempXml,instanceType,instanceMetadata);
        //logger.info("xml = " + xml);
        if(xml == null){
            throw new Exception("resizeVM return null");
        }
        // 如果虚拟机活动，关闭虚拟机
        if (d.isActive() > 0) {
            //logger.info("destroy the vm");
            d.destroy();
        }
        d.undefine();
        logger.debug("RESET: undefine old domain");
        d = virtCon.domainDefineXML(xml);
        logger.debug("RESET: new domian xml: " + xml);
        d.create();   
    }


  
    private String resizeVM(String tempXml,
                            VmInstanceType instanceType,
                            VmInstanceMetadata instanceMetadata) {
        tempXml = tempXml.replaceAll("<currentMemory[\\s\\S]*?>[\\s\\S]*?</currentMemory>", "<currentMemory>" + instanceType.getMemoryMb() * 1024 + "</currentMemory>");
        tempXml = tempXml.replaceAll("<memory[\\s\\S]*?>[\\s\\S]*?</memory>", "<memory>" + instanceType.getMemoryMb() * 1024 + "</memory>");
        tempXml = tempXml.replaceAll("<vcpu[\\s\\S]*?>[\\s\\S]*?</vcpu>", "<vcpu>" + instanceType.getVcpus() + "</vcpu>");
        //<outbound average='128' peak='256' burst='256'/>
        if (instanceMetadata.getKey() == "maxBandwidth") {
            String s = instanceMetadata.getValue();
             int maxBandwidth = (Integer.parseInt(s)) * 1000;
             tempXml = tempXml.
                     replaceAll("<outbound[\\s\\S]*?/>","<outbound average=\\'" + maxBandwidth + "\\' peak=\\'" 
                             + maxBandwidth + "\\' burst=\\'" + maxBandwidth + "\\' />");
        }
        // TODO 硬盘木有实现。
        return tempXml;
    }
    public static void main(String[] args) throws Exception {
        VMControllerImpl vmControllerImpl = VMControllerImpl.getInstance();
        VmInstance instance = new VmInstance();
        instance.setUuid("b9dcda93-9bcb-1456-3938-1982a9746a12");
        instance.setName("test-vino");
        instance.setOsType(VmImageOSTypeEnum.LINUX);
        instance.setRootDeviceLocation("hda");
        instance.setOsMode(VmImageOSModeEnum.HVM);

        VmInstanceType instanceType = new VmInstanceType();
        instanceType.setLocalGb(50);
        instanceType.setVcpus(4);
        instanceType.setMemoryMb(2);


        List<VmVolume> volume = new ArrayList<VmVolume>();
        VmVolume v1 = new VmVolume();
        v1.setName("qemu");
        v1.setProviderLocation("192.168.1.67:/srv/nfs4:volumes/e/k/hhh.img");
        v1.setVolumeType(VmVolumeTypeEnum.QCOW2);
        v1.setMountPoint("hda");
        v1.setBus("ide");
        volume.add(v1);

        List<VmVirtualInterface> vifs = new ArrayList<VmVirtualInterface>();
        VmVirtualInterface vif1 = new VmVirtualInterface();
        vif1.setNetworkType("private");
        vif1.setAddress("192.168.15.101");
        vif1.setMac("00:10:14:5a:b2:9f");
        VmVirtualInterface vif2= new VmVirtualInterface();
        vif2.setNetworkType("public");
        vif2.setAddress("10.109.247.156");
        vif2.setMac("00:11:54:b0:64:f0");
        vifs.add(vif1);
        vifs.add(vif2);
        
        List<VmInstanceMetadata> vims = new ArrayList<VmInstanceMetadata>();
        VmInstanceMetadata vim1= new VmInstanceMetadata();
        vim1.setKey("maxBandwidth");
        vim1.setValue("24");
        vims.add(vim1);
        
        VmSecurityGroup vmSecurityGroup = new VmSecurityGroup(null, "default", null, null);
        vmControllerImpl.createVM(null,instance , instanceType, vims, volume, vmSecurityGroup, vifs);
        
        
    }

    public void createVM(String routingKey,
                         VmInstance instance,
                         VmInstanceType instanceType,
                         List<VmInstanceMetadata> instanceMetadata,
                         List<VmVolume> volume,
                         VmSecurityGroup vmSecurityGroup,
                         List<VmVirtualInterface> vifs) throws Exception {

            // 测试
            logger.debug("starting create VM: " + instance.getUuid());
            // 1. xml 配置
            LibvirtConfig domainXml = new LibvirtConfig();
            domainXml.osMode = conf.getLibvirtType();
            domainXml.emulator = conf.getEmulatorName();
            // domainXml.filter = instance.getName();
            /**
             * <type arch="x86_64" machine="pc-1.2">hvm</type>
             * arch和machine都不用填写，会自动填上 如果要填写的话，要按照规范填写（capabilites）
             */
            // domainXml.osArch = conf.getOSArch();
            // domainXml.osMachine = conf.getOSMachine();

            // 2. create_image
            createImage(instance, volume, domainXml);

            // 3. create_network
            createNetwork(vifs, vmSecurityGroup, instanceMetadata, domainXml);

            // 4. getGuestConfig
            String xml = getGuestConfig(instance, instanceType, instanceMetadata, domainXml);

            if (domainXml.uuid == null) {
                throw new Exception("缺少uuid！");
            }
            logger.info("CREATE: uuid:\t" + domainXml.uuid);

            logger.info("CREATE: domian xml: " + xml);

             //6. create_domain
            try {
                Domain d = virtCon.domainDefineXML(xml);
                d.create();

                logger.info("CREATE  domian successful!");
            }
            catch (LibvirtException e) {
                logger.error("CREATE error: " + domainXml.uuid);
                e.printStackTrace();
            }
        }

        
    }
    
    
    //TODO:这样实现不行。 和volume不兼容。
//    public String revertToSnapShot(String routingKey,String instanceUUID, String snapshotName) throws Exception{
//        if(instanceUUID == null){
//            throw new Exception("instanceUUID is null");
//        }
//        
//        Domain d = virtCon.domainLookupByUUIDString(instanceUUID);
//        if (d == null) {
//            throw new Exception("no domain with uuid: " + instanceUUID);
//        }
//    
//        String[] strArr = d.snapshotListNames();
//        if(strArr == null){
//            logger.info("strArr = " + null);
//        }
//        logger.debug(strArr.length);
//        if(snapshotName == null){
//            throw new Exception("snapshotName == null");
//        }
//        DomainSnapshot ds = d.snapshotLookupByName(snapshotName);
//        if(ds == null){
//            throw new Exception("no domainSnapshot with uuid: " + snapshotName );
//        }
//        
//        if(d.revertToSnapshot(ds) == 1){
//            return "SUCCESS";
//        }else{
//            return "ERROR";
//        }
//    }
    
   
