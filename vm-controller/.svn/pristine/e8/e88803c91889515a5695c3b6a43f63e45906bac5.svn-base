package appcloud.vmcontroller.virt;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import appcloud.common.model.VmSecurityGroup;
import appcloud.common.model.VmVirtualInterface;
import appcloud.vmcontroller.VMControllerConf;
import appcloud.vmcontroller.util.VMCUtil;

public class XMLHandler {
	private static final int INTERFACE_SIZE = 1;

    private static Logger logger = Logger.getLogger(LibvirtConfig.class);

	private Document domainXml;

	public XMLHandler(String xmlStr) {
		try {
			domainXml = DocumentHelper.parseText(xmlStr);
		} catch (DocumentException e) {
			logger.error("domain xml parse error!");
		}
	}
	
	/**
	 * 重置虚拟机cpu设置
	 * @param vcpus 
	 * @return
	 */
	public String resizeVcpus(int vcpus){
		Element vcpuElement = (Element)domainXml.selectSingleNode("//vcpu");
		vcpuElement.setText(String.valueOf(vcpus));
		Element topologyElement = (Element)domainXml.selectSingleNode("//topology");
		topologyElement.addAttribute("cores", String.valueOf(vcpus));
		return domainXml.asXML();
	}

	/**
	 * 重置虚拟机memory设置
	 * @param memory
	 * @return
	 */
	public String resizeMemory(int memory){
		Element memoryElement = (Element)domainXml.selectSingleNode("//memory");
		Element currentMemoryElement = (Element)domainXml.selectSingleNode("//currentMemory");
		memoryElement.setText(String.valueOf(VMCUtil.memUnitConvert(memory)));
		currentMemoryElement.setText(String.valueOf(VMCUtil.memUnitConvert(memory)));
		return domainXml.asXML();
		
	}
	
	/**
	 * 重置虚拟机带宽配置：
	 * @param instanceType
	 * @return
	 */
	public String resizeBandwidth(String metadataValue) {
		@SuppressWarnings("unchecked")
		List<Element> bandwidthList =  domainXml.selectNodes("//bandwidth");
		if(bandwidthList.isEmpty()){
		    logger.debug("bandwidthList is empty!");
		    if(Integer.valueOf(metadataValue) != 0){
		        logger.debug("metadataValue is not 0 !");
		        @SuppressWarnings("unchecked")
	            List<Element> sourceList = domainXml.selectNodes("//interface//source");
	            Iterator<Element> it = sourceList.iterator();
	            while(it.hasNext()){
	                Element sourceEle = it.next();
	                if (isPublicVIF(sourceEle)) {
	                	Element bandwidth = sourceEle.getParent().addElement("bandwidth");
		                bandwidth.addElement("outbound")
		                         .addAttribute("average", String.valueOf(VMCUtil.bandwidthUnitConvert(Integer.valueOf(metadataValue))))
		                         .addAttribute("peak", String.valueOf(VMCUtil.bandwidthUnitConvert(Integer.valueOf(metadataValue))))
		                         .addAttribute("burst", String.valueOf(VMCUtil.bandwidthUnitConvert(Integer.valueOf(metadataValue))));
		                bandwidth.addElement("inbound")
			                     .addAttribute("average", String.valueOf(VMCUtil.bandwidthUnitConvert(Integer.valueOf(metadataValue))))
			                     .addAttribute("peak", String.valueOf(VMCUtil.bandwidthUnitConvert(Integer.valueOf(metadataValue))))
			                     .addAttribute("burst", String.valueOf(VMCUtil.bandwidthUnitConvert(Integer.valueOf(metadataValue))));	
	                }
	            }
		    }
		    logger.debug("metadataValue is  0 !");
		}else{
		    logger.debug("bandwidthList is not empty !");
		    if(Integer.valueOf(metadataValue) != 0){
		        logger.debug("metadataValue is not 0 !");
	            Iterator<Element> it = bandwidthList.iterator();
	            while(it.hasNext()){
	                Element bandwidth = it.next();
	                Element outbound = bandwidth.element("outbound");
	                bandwidth.remove(outbound);
	                Element inbound = bandwidth.element("inbound");
                    bandwidth.remove(inbound);
	                bandwidth.addElement("outbound")
	                		 .addAttribute("average", String.valueOf(VMCUtil.bandwidthUnitConvert(Integer.valueOf(metadataValue))))
	                		 .addAttribute("peak", String.valueOf(VMCUtil.bandwidthUnitConvert(Integer.valueOf(metadataValue))))
	                		 .addAttribute("burst", String.valueOf(VMCUtil.bandwidthUnitConvert(Integer.valueOf(metadataValue))));   
	                bandwidth.addElement("inbound")
		                     .addAttribute("average", String.valueOf(VMCUtil.bandwidthUnitConvert(Integer.valueOf(metadataValue))))
		                     .addAttribute("peak", String.valueOf(VMCUtil.bandwidthUnitConvert(Integer.valueOf(metadataValue))))
		                     .addAttribute("burst", String.valueOf(VMCUtil.bandwidthUnitConvert(Integer.valueOf(metadataValue))));
	            }
		    }else{
		        logger.debug("metadataValue is  0 !");
		        Iterator<Element> it = bandwidthList.iterator();
		        while(it.hasNext()){
		            it.next().detach();
		        }
		    }
		}	
		return domainXml.asXML();
	}
	
	/**
	 * 重置虚拟机带宽和网卡
	 * @Title: resizeInterface
	 * @Description: TODO
	 * @param value
	 */
	public String resizeInterface(VmVirtualInterface vif,VmSecurityGroup securityGroup, String metadataValue) {

	    if(Integer.valueOf(metadataValue) != 0){
	        List<Element> interfaceList =  domainXml.selectNodes("//interface");
	        if(interfaceList.size() > INTERFACE_SIZE){
	            return domainXml.asXML();
	        }else{
	            if(vif == null){
	                return domainXml.asXML();
	            }
	            Element interfaceEle = interfaceList.get(0);
	            Element device = interfaceEle.getParent();
	                Element netInterface = device.addElement("interface").addAttribute("type", VMControllerConf.DEFAULT_NETWORK);
	                //设置基本网卡信息
                    netInterface.addElement("mac")
                                .addAttribute("address", vif.getMac());
                    netInterface.addElement("source")
                                .addAttribute("bridge", this.getVifBridge(vif));
                    netInterface.addElement("ip")
                                .addAttribute("address", vif.getAddress());
                    netInterface.addElement("filterref").addAttribute("filter", securityGroup.getUuid());        
	            return domainXml.asXML();
	        }
	    }else{
	        List<Element> interfaceList = domainXml.selectNodes("//interface/source");
	        if(interfaceList.size() > INTERFACE_SIZE){
	            for(int i = 0; i < interfaceList.size(); i++){
	                if(interfaceList.get(i).attribute("bridge").getValue().equalsIgnoreCase(VMControllerConf.PUBLIC_NETWORK)){
	                    interfaceList.get(i).getParent().detach();
	                }
	            }
	        }
	        return domainXml.asXML();
	    }
	    
    }
	
	public Boolean isPublicVIF(Element e) {
		if (e.attributeValue("bridge").equals(VMControllerConf.PUBLIC_NETWORK)) {
			return true;
		} 
		return false;
	}
	
	private String getVifBridge(VmVirtualInterface vif){
	    if(vif.getNetworkType().equalsIgnoreCase("private")){
	        return VMControllerConf.PRIVATE_NETWORK;
	    }else if(vif.getNetworkType().equalsIgnoreCase("public")){
	        return VMControllerConf.PUBLIC_NETWORK;
	    }else{
	        return "ERROR";
	    }
	}

	/**
	 * 获取vnc port TODO：修改为dom4j操作
	 * 
	 * @param xml
	 * @return
	 */
	public String getVNCPortXML() {
		String xml = domainXml.asXML();

		String regVnc = "port=[\'\"][\\s\\S]*?[\'\"]";
		logger.info("regVnc = " + regVnc);
		Pattern p = Pattern.compile(regVnc);
		Matcher m = p.matcher(xml);

		if (m.find()) {
			String[] tempArr = m.group().split("=");
			logger.info("length = " + tempArr.length);
			if (tempArr.length == 2) {
				logger.debug(tempArr[1]);
				logger.debug(tempArr[1].replaceAll("[\'\"]", ""));
				return tempArr[1].replaceAll("[\'\"]", "");
			}
		}

		return null;
	}

	/**
	 * 获取系统盘 target dev：hda / vda 。。。 TODO：重构过长函数
	 * 
	 * @return
	 */
	public String getSystemDiskTarget() {
		String devXml = null;

		Element devices = (Element) domainXml.selectSingleNode("//devices");

		/*
		 * test output
		 */
		logger.debug("starting get system disk target");

		for (Iterator i = devices.elementIterator(); i.hasNext();) {
			Element element = (Element) i.next();

			if (element.getName().equals("disk")) {
				logger.debug("find disk element" + element.asXML());

				// 获取disk元素内的target元素
				Element target = (Element) element.selectSingleNode("//target");
				// 获取target元素的dev属性的值
				devXml = target.valueOf("@dev");
				// 找到系统盘符
				if (devXml.equals("hda") || devXml.equals("vda")) {
					break;
				}
			}
		}

		if (devXml == null) {
			logger.error("no disk element in xml!");
		}

		return devXml;
	}

	/**
	 * 获取公网网卡 target dev：vnetxx 。。。 TODO：重构过长函数
	 * 
	 * @return
	 */
	public String getPublicVIFTarget() {
		String devXml = null;

		String publicSourceBridge = VMControllerConf.PUBLIC_NETWORK;
		String privateSourceBridge = VMControllerConf.PRIVATE_NETWORK;
		logger.info("starting get public vif target, publicSourceBridge: "
				+ publicSourceBridge);

		List<Element> interfaceEleList = domainXml.selectNodes("//interface");
		//logger.info(interfaceEleList.size());
		Map<String, String> bridgeMap = new HashMap<String, String>();
		for(Element interfaceEle: interfaceEleList){
			String tempBridge = interfaceEle.element("source").attributeValue("bridge");
			String tempTarget = interfaceEle.element("target").attributeValue("dev");
			//logger.info(tempBridge + " " + tempTarget);
			bridgeMap.put(tempBridge, tempTarget);
		}
//		for(Element interfaceEle : interfaceEleList){
//			Element tempInterfaceSorEle = (Element)interfaceEle.selectSingleNode("//interface/source");
//			Element tempInterfaceTarEle = (Element)interfaceEle.selectSingleNode("//interface/target"); 
//			String tempTarget = tempInterfaceTarEle.attributeValue("dev");
//			String tempBridge = tempInterfaceSorEle.attributeValue("bridge");
//			logger.info(tempBridge + " " + tempTarget);
//			bridgeMap.put(tempBridge, tempTarget);
//		}
		if(bridgeMap.containsKey(publicSourceBridge)){
			devXml = bridgeMap.get(publicSourceBridge);
		}else{
			devXml = bridgeMap.get(privateSourceBridge);
		}
		if (devXml == null) {
			devXml = "ERROR";
			logger.error("no public interface element in xml!");
		}

		
		return devXml;
	}

}
