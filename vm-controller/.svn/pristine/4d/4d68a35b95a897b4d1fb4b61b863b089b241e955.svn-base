package appcloud.vmcontroller.virt;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import appcloud.common.model.VmInstanceType;
import appcloud.vmcontroller.VMControllerConf;

import com.sun.jna.Memory;

public class XMLHandler {
	private static Logger logger = Logger.getLogger(LibvirtConfig.class);
	
	private Document domainXml;
	
	public XMLHandler(String xmlStr) {
		try {
			domainXml = DocumentHelper.parseText(xmlStr);
		} catch (DocumentException e) {
			logger.error("domain xml parse error!");
		}
	}
	
	/*
	 * 添加xml处理接口：
	 * 1）resize：传入instanceType、返回xml string
	 * 2）attach
	 * 3）detach
	 * 4）vnc port
	 * 5）防火墙
	 */
	public String resize(VmInstanceType instanceType) {
		//
		Element ele = (Element) domainXml.selectSingleNode("//memory");
		
		ele.setText("");
		return null;	
	}
	
	/*
	 * 获取xml
	 * 1）get 系统盘 target dev：hda / vda 。。。
	 */
	public String getSystemDiskTarget() {
		String devXml = null;
		
        Element devices = (Element) domainXml.selectSingleNode("//devices");
        
        for (Iterator i = devices.elementIterator(); i.hasNext(); ) {
            Element element = (Element) i.next();
            //
            if (element.getName().equals("disk")) {
            	//获取disk元素内的target元素
    			Element target = (Element) element.selectSingleNode("//target");    			
    			//获取target元素的dev属性的值
    			devXml = target.valueOf("@dev");
    			//找到系统盘符
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
	
	/*
	 * 获取xml
	 * 2）get 公网网卡 target dev：vnetxx 。。。
	 */
	public String getPublicVIFTarget() {
		String devXml = null;
		
		VMControllerConf conf = VMControllerConf.getInstance();
		String publicSourceBridge = conf.getPublicNetwork();
		
        Element devices = (Element) domainXml.selectSingleNode("//devices");
        
        for (Iterator i = devices.elementIterator(); i.hasNext(); ) {
            Element element = (Element) i.next();
            //
            if (element.getName().equals("interface")) {
            	//获取interface元素内的target元素，及其中的dev属性的值
    			Element target = (Element) element.selectSingleNode("//target");    			
    			devXml = target.valueOf("@dev");
    			
    			//获取interface元素内的source元素，及其中的bridge属性的值
    			Element source = (Element) element.selectSingleNode("//source");    			
    			if (source.valueOf("@bridge").equals(publicSourceBridge)) {
    				break;
    			}
    			
            }
        }
		
        if (devXml == null) {    	
    		logger.error("no public interface element in xml!");
    	}
        
		return devXml;
	}
}
